pragma solidity 0.4.21;

import '../Market.sol';

contract Auth {

    // marketplace global variables
    Market  public  market;

    // Sevice level agreement published on immutable storage
    struct SLA {
        string slaRef; // reference link or i.e IPFS hash
        string slaType; // type such as PDF/DOC/JSON/XML file.
    }

    // final agreement
    struct Commitment {
        bytes32 jwtHash; // committed by the provider (it could be ssh keys/ jwt token/ OTP)
        string encJWT;  // encrypted JWT using consumer's temp public key
    }

    // consent (initial agreement) provides details about the service availability given by the provider.
    struct Consent {
        bytes32 resource; // resource id
        string permissions; // comma sparated permissions in one string
        SLA serviceLevelAgreement;
        bool available; // availability of the resource
        uint256 timestamp; // in seconds
        uint256 expire;  // in seconds
        string discovery; // this is for authorization server configuration in the provider side
        uint256 timeout; // if the consumer didn't receive verified claim from the provider within timeout
        // the consumer can cancel the request and refund the payment from market contract
    }

    struct ACL {
        address consumer;
        address provider;
        bytes32 resource;
        Consent consent;
        string pubkey; // temp public key for access token encryption
        Commitment commitment;
        AccessStatus status; // Requested, Committed, Delivered, Revoked
    }

    mapping(bytes32 => ACL) private aclEntries;

    enum AccessStatus {Requested, Committed, Delivered, Revoked}

    // modifiers and access control
    modifier isAccessRequested(bytes32 id) {
        require(aclEntries[id].status == AccessStatus.Requested);
        _;
    }

    modifier isAccessComitted(bytes32 id) {
        require(aclEntries[id].status == AccessStatus.Committed);
        _;
    }

    modifier onlyProvider(bytes32 id) {
        require(aclEntries[id].provider == msg.sender);
        _;
    }

    modifier onlyConsumer(bytes32 id) {
        require(aclEntries[id].consumer == msg.sender);
        _;
    }

    // events
    event RequestAccessConsent(bytes32 _id, address _consumer, address _provider, bytes32 _resource, uint _timeout);

    event CommitConsent(bytes32 _id, uint256 _expire, string _discovery, string _permissions, string slaLink);

    event RefundPayment(address _consumer, address _provider, bytes32 _id);

    event PublishEncryptedToken(bytes32 _id, string encJWT);

    event ReleasePayment(address _consumer, address _provider, bytes32 _id);

    ///////////////////////////////////////////////////////////////////
    //  Constructor function
    ///////////////////////////////////////////////////////////////////
    // 1. constructor
    function Auth(address _marketAddress) public {
        require(_marketAddress != address(0));
        // instance of Market
        market = Market(_marketAddress);
    }


    //1. Access Request Phase
    function initiateAccessRequest(bytes32 id, bytes32 resourceId, address provider, string pubKey, uint256 timeout)
    public returns (bool) {
        // initialize SLA, Commitment, and claim
        SLA memory sla = SLA(new string(0), new string(0));
        Commitment memory commitment = Commitment(bytes32(0), new string(0));
        Consent memory consent = Consent(resourceId, new string(0), sla, false, 0, 0, new string(0), timeout);
        // initialize acl handler
        ACL memory acl = ACL(
            msg.sender,
            provider,
            resourceId,
            consent,
            pubKey, // temp public key
            commitment,
            AccessStatus.Requested // set access status to requested
        );
        aclEntries[id] = acl;
        emit RequestAccessConsent(id, msg.sender, provider, resourceId, timeout);
        return true;
    }

    function commitAccessRequest(bytes32 id, bool available, uint256 expire, string discovery, string permissions, string slaLink, string slaType, bytes32 jwtHash)
    public onlyProvider(id) isAccessRequested(id) returns (bool) {
        if (available && now < expire) {
            aclEntries[id].consent.available = available;
            aclEntries[id].consent.expire = expire;
            aclEntries[id].consent.timestamp = now;
            aclEntries[id].consent.discovery = discovery;
            aclEntries[id].consent.permissions = permissions;
            aclEntries[id].commitment.jwtHash = jwtHash;
            aclEntries[id].status = AccessStatus.Committed;
            SLA memory sla = SLA(
                slaLink,
                slaType
            );
            aclEntries[id].consent.serviceLevelAgreement = sla;
            emit CommitConsent(id, expire, discovery, permissions, slaLink);
            return true;
        }

        // otherwise, send refund
        aclEntries[id].status = AccessStatus.Revoked;
        emit RefundPayment(aclEntries[id].consumer, aclEntries[id].provider, id);
        return false;
    }

    // you can cancel consent and do refund only after timeout.
    function cancelConsent(bytes32 id)
    public
    isAccessRequested(id) {
        // timeout
        require(now > aclEntries[id].consent.timeout);
        aclEntries[id].status = AccessStatus.Revoked;
        emit RefundPayment(aclEntries[id].consumer, aclEntries[id].provider, id);
    }

    //3. Delivery phase
    // provider encypts the JWT using temp public key from cunsumer and publish it to on-chain
    // the encrypted JWT is stored on-chain for alpha version release, which will be moved to off-chain in future versions.
    function deliverAccessToken(bytes32 id, string encryptedJWT) public onlyProvider(id) isAccessComitted(id) returns (bool) {

        aclEntries[id].commitment.encJWT = encryptedJWT;
        emit PublishEncryptedToken(id, encryptedJWT);
        return true;
    }

    // provider get the temp public key
    function getTempPubKey(bytes32 id) public view onlyProvider(id) isAccessComitted(id) returns (string) {
        return aclEntries[id].pubkey;
    }

    // Consumer get the encrypted JWT from on-chain
    function getEncJWT(bytes32 id) public view onlyConsumer(id) isAccessComitted(id) returns (string) {
        return aclEntries[id].commitment.encJWT;
    }

    // provider verify the access token is delivered to consumer and request for payment
    function verifyAccessTokenDelivery(bytes32 id, bytes32 proofJWTHash) public onlyProvider(id) isAccessComitted(id) {

        // expire
        if (aclEntries[id].consent.expire < now) {
            // this means that consumer didn't make the request
            // revoke the access then raise event for refund
            aclEntries[id].status = AccessStatus.Revoked;
            emit RefundPayment(aclEntries[id].consumer, aclEntries[id].provider, id);
        } else {
            // provider confirms that consumer made a request by providing "proof of access"
            // This means that provider should get a signed jwt hash from the consumer and compares what was
            // committed by the provider with the signed one.
            if (aclEntries[id].commitment.jwtHash == proofJWTHash) {
                aclEntries[id].status = AccessStatus.Delivered;
                // send money to provider
                require(market.releasePayment(id));
                // emit an event
                emit ReleasePayment(aclEntries[id].consumer, aclEntries[id].provider, id);
            } else {
                aclEntries[id].status = AccessStatus.Revoked;
                emit RefundPayment(aclEntries[id].consumer, aclEntries[id].provider, id);
            }
        }
    }

    // Utility function: provider/consumer use this function to check access request status
    function generateRequestId(bytes32 resourceId, address provider, string pubKey) public view returns (bytes32) {
        return keccak256(resourceId, msg.sender, provider, pubKey);
    }

    function verifyAccessStatus(bytes32 id, AccessStatus status) public view returns (bool) {
        if (aclEntries[id].status == status) {
            return true;
        }
        return false;
    }

}
