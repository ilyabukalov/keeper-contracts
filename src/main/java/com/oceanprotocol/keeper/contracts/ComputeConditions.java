package com.oceanprotocol.keeper.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class ComputeConditions extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506040516020806118e98339810160405251600160a060020a03811615156100bf57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152602a60248201527f696e76616c696420736572766963652061677265656d656e7420636f6e74726160448201527f6374206164647265737300000000000000000000000000000000000000000000606482015290519081900360840190fd5b60008054600160a060020a03909216600160a060020a03199092169190911790556117fa806100ef6000396000f3006080604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416635234932e811461005b57806379615531146100cd57806387319b11146100ea575b600080fd5b34801561006757600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100b99583359536956044949193909101919081908401838280828437509497506101059650505050505050565b604080519115158252519081900360200190f35b3480156100d957600080fd5b506100b9600435602435151561067a565b3480156100f657600080fd5b506100b9600435602435611222565b600080546040805160e160020a6329a4d4570281526004810186905290518592600160a060020a031691635349a8ae91602480830192602092919082900301818887803b15801561015557600080fd5b505af1158015610169573d6000803e3d6000fd5b505050506040513d602081101561017f57600080fd5b5051600160a060020a031633146101e0576040805160e560020a62461bcd02815260206004820152601f60248201527f496e76616c6964206461746120736369656e7469737420616464726573732100604482015290519081900360640190fd5b60008481526001602052604090205460ff16156103ae5760008481526001602052604090205462010000900460ff161561036857600080546040805160e160020a6329a4d4570281526004810188905290517f4a0327db9557da063f8f93b605829549f7337fae75e5325916c509f68e61b717938893600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b15801561028757600080fd5b505af115801561029b573d6000803e3d6000fd5b505050506040513d60208110156102b157600080fd5b5051600080546040805160e160020a634f6a42c5028152600481018b90529051600160a060020a0390921692639ed4858a926024808401936020939083900390910190829087803b15801561030557600080fd5b505af1158015610319573d6000803e3d6000fd5b505050506040513d602081101561032f57600080fd5b505160408051938452600160a060020a03928316602085015291168282015260006060830152519081900360800190a160009150610673565b6000848152600160209081526040909120805462ff0000191662010000178155845161039c92600290920191860190611733565b506103a884600161067a565b5061050a565b6040805160c081018252600180825260006020808401829052838501929092528054845160e160020a6329a4d457028152600481018a9052945193946060860194600160a060020a0390921693635349a8ae9360248084019492938390030190829087803b15801561041f57600080fd5b505af1158015610433573d6000803e3d6000fd5b505050506040513d602081101561044957600080fd5b5051600160a060020a039081168252600060208084018290526040938401889052888252600180825291849020855181548784015196880151606089015160ff199092169215159290921761ff001916610100971515979097029690961762ff0000191662010000911515919091021776ffffffffffffffffffffffffffffffffffffffff00000019166301000000959094169490940292909217835560808401519083015560a083015180516105069260028501920190611733565b5050505b600080546040805160e160020a6329a4d4570281526004810188905290517f4a0327db9557da063f8f93b605829549f7337fae75e5325916c509f68e61b717938893600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b15801561057d57600080fd5b505af1158015610591573d6000803e3d6000fd5b505050506040513d60208110156105a757600080fd5b5051600080546040805160e160020a634f6a42c5028152600481018b90529051600160a060020a0390921692639ed4858a926024808401936020939083900390910190829087803b1580156105fb57600080fd5b505af115801561060f573d6000803e3d6000fd5b505050506040513d602081101561062557600080fd5b505160408051938452600160a060020a03928316602085015291168282015260016060830152519081900360800190a16000848152600160208190526040909120805462ff00001916905591505b5092915050565b600080546040805160e160020a634f6a42c502815260048101869052905183928692600160a060020a0390911691639ed4858a9160248082019260209290919082900301818887803b1580156106cf57600080fd5b505af11580156106e3573d6000803e3d6000fd5b505050506040513d60208110156106f957600080fd5b5051600160a060020a03163314806107965750600080546040805160e160020a6329a4d457028152600481018590529051600160a060020a0390921692635349a8ae926024808401936020939083900390910190829087803b15801561075e57600080fd5b505af1158015610772573d6000803e3d6000fd5b505050506040513d602081101561078857600080fd5b5051600160a060020a031633145b15156107ec576040805160e560020a62461bcd02815260206004820152600d60248201527f4163636573732064656e69656400000000000000000000000000000000000000604482015290519081900360640190fd5b600081815260016020526040902054610100900460ff1615610858576040805160e560020a62461bcd02815260206004820152601360248201527f61766f6964207265706c61792061747461636b00000000000000000000000000604482015290519081900360640190fd5b60008054604080517ffbb7f209000000000000000000000000000000000000000000000000000000008152600481018990523060248201527f796155310000000000000000000000000000000000000000000000000000000060448201529051600160a060020a039092169263fbb7f209926064808401936020939083900390910190829087803b1580156108ec57600080fd5b505af1158015610900573d6000803e3d6000fd5b505050506040513d602081101561091657600080fd5b505160008054604080517f5f9766f5000000000000000000000000000000000000000000000000000000008152600481018a9052602481018590529051939550600160a060020a0390911692635f9766f592604480840193602093929083900390910190829087803b15801561098b57600080fd5b505af115801561099f573d6000803e3d6000fd5b505050506040513d60208110156109b557600080fd5b505115610b0957600080546040805160e160020a6329a4d4570281526004810189905290517f21ef4a24230c05d11e0a1ebb3f3223184d4f6fdb83a099b15a34f77c86031cc7938993600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b158015610a2f57600080fd5b505af1158015610a43573d6000803e3d6000fd5b505050506040513d6020811015610a5957600080fd5b5051600080546040805160e160020a634f6a42c5028152600481018c90529051600160a060020a0390921692639ed4858a926024808401936020939083900390910190829087803b158015610aad57600080fd5b505af1158015610ac1573d6000803e3d6000fd5b505050506040513d6020811015610ad757600080fd5b505160408051938452600160a060020a039283166020850152911682820152519081900360600190a16000925061121a565b60008054604080517fca7e4fd000000000000000000000000000000000000000000000000000000000815260048101899052602481018690529051600160a060020a039092169263ca7e4fd0926044808401936020939083900390910190829087803b158015610b7857600080fd5b505af1158015610b8c573d6000803e3d6000fd5b505050506040513d6020811015610ba257600080fd5b505160ff1660011415610cfc57600080546040805160e160020a6329a4d4570281526004810189905290517fd9ea9cac51100309042cbf7b941e07703c030fa594c39029451c3fce5f84cdc7938993600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b158015610c2257600080fd5b505af1158015610c36573d6000803e3d6000fd5b505050506040513d6020811015610c4c57600080fd5b5051600080546040805160e160020a634f6a42c5028152600481018c90529051600160a060020a0390921692639ed4858a926024808401936020939083900390910190829087803b158015610ca057600080fd5b505af1158015610cb4573d6000803e3d6000fd5b505050506040513d6020811015610cca57600080fd5b505160408051938452600160a060020a039283166020850152911682820152519081900360600190a16001925061121a565b60008581526001602081905260409091200154610dbc90610d1c906115b4565b6000878152600160208181526040928390206002908101805485516000199582161561010002959095011691909104601f8101839004830284018301909452838352919290830182828015610db25780601f10610d8757610100808354040283529160200191610db2565b820191906000526020600020905b815481529060010190602001808311610d9557829003601f168201915b505050505061165e565b60008681526001602052604090205463010000009004600160a060020a03908116911614156110d157600054604080517f010000000000000000000000000000000000000000000000000000000000000087151502602080830191909152825180830360010181526021909201928390528151600160a060020a039094169363ed25e7c9938a937f796155310000000000000000000000000000000000000000000000000000000093909282918401908083835b60208310610e8f5780518252601f199092019160209182019101610e70565b51815160209384036101000a60001901801990921691161790526040805192909401829003822063ffffffff8a167c010000000000000000000000000000000000000000000000000000000002835260048301989098527fffffffff00000000000000000000000000000000000000000000000000000000969096166024820152604481019690965250516064808601959193509083900301905081600087803b158015610f3c57600080fd5b505af1158015610f50573d6000803e3d6000fd5b505050506040513d6020811015610f6657600080fd5b5050600080546040805160e160020a6329a4d4570281526004810189905290517fd9ea9cac51100309042cbf7b941e07703c030fa594c39029451c3fce5f84cdc7938993600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b158015610fdb57600080fd5b505af1158015610fef573d6000803e3d6000fd5b505050506040513d602081101561100557600080fd5b5051600080546040805160e160020a634f6a42c5028152600481018c90529051600160a060020a0390921692639ed4858a926024808401936020939083900390910190829087803b15801561105957600080fd5b505af115801561106d573d6000803e3d6000fd5b505050506040513d602081101561108357600080fd5b505160408051938452600160a060020a039283166020850152911682820152519081900360600190a16000858152600160208190526040909120805461ff001916610100179055925061121a565b600080546040805160e160020a6329a4d4570281526004810189905290517f21ef4a24230c05d11e0a1ebb3f3223184d4f6fdb83a099b15a34f77c86031cc7938993600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b15801561114457600080fd5b505af1158015611158573d6000803e3d6000fd5b505050506040513d602081101561116e57600080fd5b5051600080546040805160e160020a634f6a42c5028152600481018c90529051600160a060020a0390921692639ed4858a926024808401936020939083900390910190829087803b1580156111c257600080fd5b505af11580156111d6573d6000803e3d6000fd5b505050506040513d60208110156111ec57600080fd5b505160408051938452600160a060020a039283166020850152911682820152519081900360600190a1600092505b505092915050565b600080546040805160e160020a634f6a42c50281526004810186905290518592600160a060020a031691639ed4858a91602480830192602092919082900301818887803b15801561127257600080fd5b505af1158015611286573d6000803e3d6000fd5b505050506040513d602081101561129c57600080fd5b5051600160a060020a031633146112fd576040805160e560020a62461bcd02815260206004820152601960248201527f496e76616c6964207075626c6973686572206164647265737300000000000000604482015290519081900360640190fd5b60008481526001602052604090205460ff16156113da5760008481526001602052604090205462010000900460ff16156113a457600080546040805160e160020a6329a4d4570281526004810188905290517f897558797366d5d4262deda4e529d533f0a53544f3de12288165dcd019590c62938893600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b15801561028757600080fd5b6000848152600160208190526040909120805462ff000019166201000017815581018490556113d490859061067a565b50611541565b6040805160c081018252600180825260006020808401829052838501929092528054845160e160020a6329a4d457028152600481018a9052945193946060860194600160a060020a0390921693635349a8ae9360248084019492938390030190829087803b15801561144b57600080fd5b505af115801561145f573d6000803e3d6000fd5b505050506040513d602081101561147557600080fd5b5051600160a060020a0390811682526020808301879052604080516000808252818401835294820152888452600180835293819020855181548785015193880151606089015190961663010000000276ffffffffffffffffffffffffffffffffffffffff00000019961515620100000262ff0000199515156101000261ff001994151560ff1990941693909317939093169190911793909316179390931617825560808401519282019290925560a08301518051919261153d92600285019290910190611733565b5050505b600080546040805160e160020a6329a4d4570281526004810188905290517f897558797366d5d4262deda4e529d533f0a53544f3de12288165dcd019590c62938893600160a060020a031692635349a8ae9260248083019360209383900390910190829087803b15801561057d57600080fd5b604080517f19457468657265756d205369676e6564204d6573736167653a0a333200000000602080830191909152603c80830185905283518084039091018152605c909201928390528151600093918291908401908083835b6020831061162c5780518252601f19909201916020918201910161160d565b5181516020939093036101000a6000190180199091169216919091179052604051920182900390912095945050505050565b60008060008084516041141515611678576000935061172a565b50505060208201516040830151606084015160001a601b60ff8216101561169d57601b015b8060ff16601b141580156116b557508060ff16601c14155b156116c3576000935061172a565b60408051600080825260208083018085528a905260ff8516838501526060830187905260808301869052925160019360a0808501949193601f19840193928390039091019190865af115801561171d573d6000803e3d6000fd5b5050506020604051035193505b50505092915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061177457805160ff19168380011785556117a1565b828001600101855582156117a1579182015b828111156117a1578251825591602001919060010190611786565b506117ad9291506117b1565b5090565b6117cb91905b808211156117ad57600081556001016117b7565b905600a165627a7a7230582030a9c2d5c0ad0ffbc261e35e425350e65bc4880a371e95d061830a6b846981480029";

    public static final String FUNC_SUBMITHASHSIGNATURE = "submitHashSignature";

    public static final String FUNC_SUBMITALGORITHMHASH = "submitAlgorithmHash";

    public static final String FUNC_FULFILLUPLOAD = "fulfillUpload";

    public static final Event HASHSIGNATURESUBMITTED_EVENT = new Event("HashSignatureSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event HASHSUBMITTED_EVENT = new Event("HashSubmitted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event PROOFOFUPLOADVALID_EVENT = new Event("ProofOfUploadValid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event PROOFOFUPLOADINVALID_EVENT = new Event("ProofOfUploadInvalid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("8995", "0x9e40b82acff5660bc97fbb4c2a2878c7c2ae4e40");
    }

    @Deprecated
    protected ComputeConditions(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ComputeConditions(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ComputeConditions(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ComputeConditions(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<HashSignatureSubmittedEventResponse> getHashSignatureSubmittedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HASHSIGNATURESUBMITTED_EVENT, transactionReceipt);
        ArrayList<HashSignatureSubmittedEventResponse> responses = new ArrayList<HashSignatureSubmittedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HashSignatureSubmittedEventResponse typedResponse = new HashSignatureSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.state = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<HashSignatureSubmittedEventResponse> hashSignatureSubmittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, HashSignatureSubmittedEventResponse>() {
            @Override
            public HashSignatureSubmittedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HASHSIGNATURESUBMITTED_EVENT, log);
                HashSignatureSubmittedEventResponse typedResponse = new HashSignatureSubmittedEventResponse();
                typedResponse.log = log;
                typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.state = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<HashSignatureSubmittedEventResponse> hashSignatureSubmittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HASHSIGNATURESUBMITTED_EVENT));
        return hashSignatureSubmittedEventFlowable(filter);
    }

    public List<HashSubmittedEventResponse> getHashSubmittedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(HASHSUBMITTED_EVENT, transactionReceipt);
        ArrayList<HashSubmittedEventResponse> responses = new ArrayList<HashSubmittedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            HashSubmittedEventResponse typedResponse = new HashSubmittedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.state = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<HashSubmittedEventResponse> hashSubmittedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, HashSubmittedEventResponse>() {
            @Override
            public HashSubmittedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(HASHSUBMITTED_EVENT, log);
                HashSubmittedEventResponse typedResponse = new HashSubmittedEventResponse();
                typedResponse.log = log;
                typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.state = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<HashSubmittedEventResponse> hashSubmittedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(HASHSUBMITTED_EVENT));
        return hashSubmittedEventFlowable(filter);
    }

    public List<ProofOfUploadValidEventResponse> getProofOfUploadValidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROOFOFUPLOADVALID_EVENT, transactionReceipt);
        ArrayList<ProofOfUploadValidEventResponse> responses = new ArrayList<ProofOfUploadValidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProofOfUploadValidEventResponse typedResponse = new ProofOfUploadValidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProofOfUploadValidEventResponse> proofOfUploadValidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProofOfUploadValidEventResponse>() {
            @Override
            public ProofOfUploadValidEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROOFOFUPLOADVALID_EVENT, log);
                ProofOfUploadValidEventResponse typedResponse = new ProofOfUploadValidEventResponse();
                typedResponse.log = log;
                typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProofOfUploadValidEventResponse> proofOfUploadValidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROOFOFUPLOADVALID_EVENT));
        return proofOfUploadValidEventFlowable(filter);
    }

    public List<ProofOfUploadInvalidEventResponse> getProofOfUploadInvalidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROOFOFUPLOADINVALID_EVENT, transactionReceipt);
        ArrayList<ProofOfUploadInvalidEventResponse> responses = new ArrayList<ProofOfUploadInvalidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProofOfUploadInvalidEventResponse typedResponse = new ProofOfUploadInvalidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProofOfUploadInvalidEventResponse> proofOfUploadInvalidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ProofOfUploadInvalidEventResponse>() {
            @Override
            public ProofOfUploadInvalidEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROOFOFUPLOADINVALID_EVENT, log);
                ProofOfUploadInvalidEventResponse typedResponse = new ProofOfUploadInvalidEventResponse();
                typedResponse.log = log;
                typedResponse.serviceAgreementId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.dataScientist = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.publisher = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProofOfUploadInvalidEventResponse> proofOfUploadInvalidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROOFOFUPLOADINVALID_EVENT));
        return proofOfUploadInvalidEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> submitHashSignature(byte[] serviceAgreementId, byte[] signature) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMITHASHSIGNATURE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(serviceAgreementId), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> submitAlgorithmHash(byte[] serviceAgreementId, byte[] hash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SUBMITALGORITHMHASH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(serviceAgreementId), 
                new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> fulfillUpload(byte[] serviceAgreementId, Boolean state) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FULFILLUPLOAD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(serviceAgreementId), 
                new org.web3j.abi.datatypes.Bool(state)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ComputeConditions load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ComputeConditions(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ComputeConditions load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ComputeConditions(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ComputeConditions load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ComputeConditions(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ComputeConditions load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ComputeConditions(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ComputeConditions> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String serviceAgreementAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(serviceAgreementAddress)));
        return deployRemoteCall(ComputeConditions.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ComputeConditions> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String serviceAgreementAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(serviceAgreementAddress)));
        return deployRemoteCall(ComputeConditions.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ComputeConditions> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String serviceAgreementAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(serviceAgreementAddress)));
        return deployRemoteCall(ComputeConditions.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ComputeConditions> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String serviceAgreementAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(serviceAgreementAddress)));
        return deployRemoteCall(ComputeConditions.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class HashSignatureSubmittedEventResponse {
        public Log log;

        public byte[] serviceAgreementId;

        public String dataScientist;

        public String publisher;

        public Boolean state;
    }

    public static class HashSubmittedEventResponse {
        public Log log;

        public byte[] serviceAgreementId;

        public String dataScientist;

        public String publisher;

        public Boolean state;
    }

    public static class ProofOfUploadValidEventResponse {
        public Log log;

        public byte[] serviceAgreementId;

        public String dataScientist;

        public String publisher;
    }

    public static class ProofOfUploadInvalidEventResponse {
        public Log log;

        public byte[] serviceAgreementId;

        public String dataScientist;

        public String publisher;
    }
}
