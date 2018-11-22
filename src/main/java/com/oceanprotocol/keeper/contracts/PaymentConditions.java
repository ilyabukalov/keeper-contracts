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
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
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
public class PaymentConditions extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506040516040806115fc833981016040528051602090910151600160a060020a03821615156100a057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601860248201527f696e76616c696420636f6e747261637420616464726573730000000000000000604482015290519081900360640190fd5b600160a060020a038116151561011757604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152601560248201527f696e76616c696420746f6b656e20616464726573730000000000000000000000604482015290519081900360640190fd5b60008054600160a060020a03938416600160a060020a031991821617909155600180549290931691161790556114aa806101526000396000f30060806040526004361061003d5763ffffffff60e060020a600035041663668453f081146100425780637e2e8ad1146100745780639983504a14610092575b600080fd5b34801561004e57600080fd5b506100606004356024356044356100b0565b604080519115158252519081900360200190f35b34801561008057600080fd5b50610060600435602435604435610723565b34801561009e57600080fd5b50610060600435602435604435610e0e565b60008054604080517f5349a8ae00000000000000000000000000000000000000000000000000000000815260048101879052905183928392839283923392600160a060020a0390911691635349a8ae9160248082019260209290919082900301818887803b15801561012157600080fd5b505af1158015610135573d6000803e3d6000fd5b505050506040513d602081101561014b57600080fd5b5051600160a060020a0316146101d1576040805160e560020a62461bcd02815260206004820152602660248201527f4f6e6c7920636f6e73756d65722063616e2074726967676572206c6f636b506160448201527f796d656e742e0000000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60008054604080517ffbb7f209000000000000000000000000000000000000000000000000000000008152600481018c90523060248201527f668453f00000000000000000000000000000000000000000000000000000000060448201529051600160a060020a039092169263fbb7f209926064808401936020939083900390910190829087803b15801561026557600080fd5b505af1158015610279573d6000803e3d6000fd5b505050506040513d602081101561028f57600080fd5b505160008054604080517f5f9766f5000000000000000000000000000000000000000000000000000000008152600481018d9052602481018590529051939750600160a060020a0390911692635f9766f592604480840193602093929083900390910190829087803b15801561030457600080fd5b505af1158015610318573d6000803e3d6000fd5b505050506040513d602081101561032e57600080fd5b50511561033e5760009450610718565b60008054604080517fca7e4fd0000000000000000000000000000000000000000000000000000000008152600481018c9052602481018890529051600160a060020a039092169263ca7e4fd0926044808401936020939083900390910190829087803b1580156103ad57600080fd5b505af11580156103c1573d6000803e3d6000fd5b505050506040513d60208110156103d757600080fd5b505160ff16600114156103ed5760019450610718565b6040805160208082018a9052818301899052825180830384018152606090920192839052815191929182918401908083835b6020831061043e5780518252601f19909201916020918201910161041f565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902092503391503090506000809054906101000a9004600160a060020a0316600160a060020a031663ed25e7c98963668453f060e060020a02866040518463ffffffff1660e060020a028152600401808460001916600019168152602001837bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916815260200182600019166000191681526020019350505050602060405180830381600087803b15801561053857600080fd5b505af115801561054c573d6000803e3d6000fd5b505050506040513d602081101561056257600080fd5b5050600154604080517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a0385811660048301528481166024830152604482018a9052915191909216916323b872dd9160648083019260209291908290030181600087803b1580156105db57600080fd5b505af11580156105ef573d6000803e3d6000fd5b505050506040513d602081101561060557600080fd5b5051151561065d576040805160e560020a62461bcd02815260206004820152601460248201527f43616e206e6f74206c6f636b207061796d656e74000000000000000000000000604482015290519081900360640190fd5b6040805160608181018352600160a060020a03808616835284811660208085019182528486018c815260008f81526002808452908890209651875490861673ffffffffffffffffffffffffffffffffffffffff199182161780895594516001890180549188169190921617908190559151960186905586519284168352909216918101919091528084019290925291518a927f221e03ddf78b7811d5256260c2c271082b8ce2dc66122e82c6f79c4b0d46251b928290030190a25b505050509392505050565b60008054604080517f9ed4858a000000000000000000000000000000000000000000000000000000008152600481018790529051839283923392600160a060020a0390921691639ed4858a9160248082019260209290919082900301818887803b15801561079057600080fd5b505af11580156107a4573d6000803e3d6000fd5b505050506040513d60208110156107ba57600080fd5b5051600160a060020a031614610840576040805160e560020a62461bcd02815260206004820152603c60248201527f4f6e6c7920736572766963652061677265656d656e74207075626c697368657260448201527f2063616e20747269676765722072656c656173655061796d656e742e00000000606482015290519081900360840190fd5b60008054604080517ffbb7f209000000000000000000000000000000000000000000000000000000008152600481018a90523060248201527f7e2e8ad10000000000000000000000000000000000000000000000000000000060448201529051600160a060020a039092169263fbb7f209926064808401936020939083900390910190829087803b1580156108d457600080fd5b505af11580156108e8573d6000803e3d6000fd5b505050506040513d60208110156108fe57600080fd5b505160008054604080517f5f9766f5000000000000000000000000000000000000000000000000000000008152600481018b9052602481018590529051939550600160a060020a0390911692635f9766f592604480840193602093929083900390910190829087803b15801561097357600080fd5b505af1158015610987573d6000803e3d6000fd5b505050506040513d602081101561099d57600080fd5b5051156109ad5760009250610e05565b60008054604080517fca7e4fd0000000000000000000000000000000000000000000000000000000008152600481018a9052602481018690529051600160a060020a039092169263ca7e4fd0926044808401936020939083900390910190829087803b158015610a1c57600080fd5b505af1158015610a30573d6000803e3d6000fd5b505050506040513d6020811015610a4657600080fd5b505160ff1660011415610a5c5760019250610e05565b604080516020808201889052818301879052825180830384018152606090920192839052815191929182918401908083835b60208310610aad5780518252601f199092019160209182019101610a8e565b51815160209384036101000a600019018019909216911617905260408051929094018290038220600080547fed25e7c9000000000000000000000000000000000000000000000000000000008552600485018f90527f7e2e8ad1000000000000000000000000000000000000000000000000000000006024860152604485018390529551919850600160a060020a03909516965063ed25e7c9955060648084019592945090928390030190829087803b158015610b6957600080fd5b505af1158015610b7d573d6000803e3d6000fd5b505050506040513d6020811015610b9357600080fd5b505060015460008781526002602081815260408084209092015482517f095ea7b300000000000000000000000000000000000000000000000000000000815230600482015260248101919091529151600160a060020a039094169363095ea7b3936044808501948390030190829087803b158015610c1057600080fd5b505af1158015610c24573d6000803e3d6000fd5b505050506040513d6020811015610c3a57600080fd5b50511515610c92576040805160e560020a62461bcd02815260206004820152601f60248201527f43616e206e6f7420617070726f766520746f6b656e206f7065726174696f6e00604482015290519081900360640190fd5b60018054600088815260026020818152604080842095860154959092015482517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a0396871660048201523360248201526044810191909152915194909316936323b872dd9360648084019491938390030190829087803b158015610d1e57600080fd5b505af1158015610d32573d6000803e3d6000fd5b505050506040513d6020811015610d4857600080fd5b50511515610da0576040805160e560020a62461bcd02815260206004820152601760248201527f43616e206e6f742072656c65617365207061796d656e74000000000000000000604482015290519081900360640190fd5b60008681526002602081815260409283902060018101549201548351600160a060020a039093168352339183019190915281830152905187917f8895f1b42dba89b953fe6fe74f5854edd2c96c7b2ac3123a216d9cca241225c6919081900360600190a25b50509392505050565b60008381526002602052604081205481908190600160a060020a03163314610ea6576040805160e560020a62461bcd02815260206004820152602860248201527f4f6e6c7920636f6e73756d65722063616e207472696767657220726566756e6460448201527f5061796d656e742e000000000000000000000000000000000000000000000000606482015290519081900360840190fd5b60008054604080517ffbb7f209000000000000000000000000000000000000000000000000000000008152600481018a90523060248201527f9983504a0000000000000000000000000000000000000000000000000000000060448201529051600160a060020a039092169263fbb7f209926064808401936020939083900390910190829087803b158015610f3a57600080fd5b505af1158015610f4e573d6000803e3d6000fd5b505050506040513d6020811015610f6457600080fd5b505160008054604080517f5f9766f5000000000000000000000000000000000000000000000000000000008152600481018b9052602481018590529051939550600160a060020a0390911692635f9766f592604480840193602093929083900390910190829087803b158015610fd957600080fd5b505af1158015610fed573d6000803e3d6000fd5b505050506040513d602081101561100357600080fd5b5051156110135760009250610e05565b60008054604080517fca7e4fd0000000000000000000000000000000000000000000000000000000008152600481018a9052602481018690529051600160a060020a039092169263ca7e4fd0926044808401936020939083900390910190829087803b15801561108257600080fd5b505af1158015611096573d6000803e3d6000fd5b505050506040513d60208110156110ac57600080fd5b505160ff16600114156110c25760019250610e05565b604080516020808201889052818301879052825180830384018152606090920192839052815191929182918401908083835b602083106111135780518252601f1990920191602091820191016110f4565b51815160209384036101000a600019018019909216911617905260408051929094018290038220600080547fed25e7c9000000000000000000000000000000000000000000000000000000008552600485018f90527f9983504a000000000000000000000000000000000000000000000000000000006024860152604485018390529551919850600160a060020a03909516965063ed25e7c9955060648084019592945090928390030190829087803b1580156111cf57600080fd5b505af11580156111e3573d6000803e3d6000fd5b505050506040513d60208110156111f957600080fd5b505060015460008781526002602081815260408084209092015482517f095ea7b300000000000000000000000000000000000000000000000000000000815230600482015260248101919091529151600160a060020a039094169363095ea7b3936044808501948390030190829087803b15801561127657600080fd5b505af115801561128a573d6000803e3d6000fd5b505050506040513d60208110156112a057600080fd5b505115156112f8576040805160e560020a62461bcd02815260206004820152601f60248201527f43616e206e6f7420617070726f766520746f6b656e206f7065726174696f6e00604482015290519081900360640190fd5b600180546000888152600260208181526040808420958601548654969093015481517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a039485166004820152968416602488015260448701525191909316936323b872dd936064808301949193928390030190829087803b15801561138557600080fd5b505af1158015611399573d6000803e3d6000fd5b505050506040513d60208110156113af57600080fd5b50511515611407576040805160e560020a62461bcd02815260206004820152601660248201527f43616e206e6f7420726566756e64207061796d656e7400000000000000000000604482015290519081900360640190fd5b6000868152600260208181526040928390206001810154815491909301548451600160a060020a039485168152919093169181019190915280830191909152905187917ffaa090563f0408339904cbbefd39700a074fa235a2557b0665fa2a24317c8bb0919081900360600190a2505093925050505600a165627a7a723058207cd5de7ba272a819e9b9b2b26937b95daaddac0f0df8ceecb8cdd1d03d900cec0029";

    public static final String FUNC_LOCKPAYMENT = "lockPayment";

    public static final String FUNC_RELEASEPAYMENT = "releasePayment";

    public static final String FUNC_REFUNDPAYMENT = "refundPayment";

    public static final Event PAYMENTLOCKED_EVENT = new Event("PaymentLocked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAYMENTRELEASED_EVENT = new Event("PaymentReleased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAYMENTREFUND_EVENT = new Event("PaymentRefund", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("8995", "0xdef49ec8c07e6042378032e76d30b0036d26a3fb");
    }

    @Deprecated
    protected PaymentConditions(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PaymentConditions(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PaymentConditions(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PaymentConditions(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<PaymentLockedEventResponse> getPaymentLockedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAYMENTLOCKED_EVENT, transactionReceipt);
        ArrayList<PaymentLockedEventResponse> responses = new ArrayList<PaymentLockedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentLockedEventResponse typedResponse = new PaymentLockedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaymentLockedEventResponse> paymentLockedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PaymentLockedEventResponse>() {
            @Override
            public PaymentLockedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAYMENTLOCKED_EVENT, log);
                PaymentLockedEventResponse typedResponse = new PaymentLockedEventResponse();
                typedResponse.log = log;
                typedResponse.serviceId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PaymentLockedEventResponse> paymentLockedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTLOCKED_EVENT));
        return paymentLockedEventFlowable(filter);
    }

    public List<PaymentReleasedEventResponse> getPaymentReleasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAYMENTRELEASED_EVENT, transactionReceipt);
        ArrayList<PaymentReleasedEventResponse> responses = new ArrayList<PaymentReleasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentReleasedEventResponse typedResponse = new PaymentReleasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaymentReleasedEventResponse> paymentReleasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PaymentReleasedEventResponse>() {
            @Override
            public PaymentReleasedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAYMENTRELEASED_EVENT, log);
                PaymentReleasedEventResponse typedResponse = new PaymentReleasedEventResponse();
                typedResponse.log = log;
                typedResponse.serviceId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PaymentReleasedEventResponse> paymentReleasedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTRELEASED_EVENT));
        return paymentReleasedEventFlowable(filter);
    }

    public List<PaymentRefundEventResponse> getPaymentRefundEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAYMENTREFUND_EVENT, transactionReceipt);
        ArrayList<PaymentRefundEventResponse> responses = new ArrayList<PaymentRefundEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentRefundEventResponse typedResponse = new PaymentRefundEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.serviceId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PaymentRefundEventResponse> paymentRefundEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PaymentRefundEventResponse>() {
            @Override
            public PaymentRefundEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAYMENTREFUND_EVENT, log);
                PaymentRefundEventResponse typedResponse = new PaymentRefundEventResponse();
                typedResponse.log = log;
                typedResponse.serviceId = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PaymentRefundEventResponse> paymentRefundEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTREFUND_EVENT));
        return paymentRefundEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> lockPayment(byte[] serviceId, byte[] assetId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_LOCKPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(serviceId), 
                new org.web3j.abi.datatypes.generated.Bytes32(assetId), 
                new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> releasePayment(byte[] serviceId, byte[] assetId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RELEASEPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(serviceId), 
                new org.web3j.abi.datatypes.generated.Bytes32(assetId), 
                new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> refundPayment(byte[] serviceId, byte[] assetId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REFUNDPAYMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(serviceId), 
                new org.web3j.abi.datatypes.generated.Bytes32(assetId), 
                new org.web3j.abi.datatypes.generated.Uint256(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PaymentConditions load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PaymentConditions(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PaymentConditions load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PaymentConditions(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PaymentConditions load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PaymentConditions(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PaymentConditions load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PaymentConditions(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PaymentConditions> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _serviceAgreementAddress, String _tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_serviceAgreementAddress), 
                new org.web3j.abi.datatypes.Address(_tokenAddress)));
        return deployRemoteCall(PaymentConditions.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<PaymentConditions> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _serviceAgreementAddress, String _tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_serviceAgreementAddress), 
                new org.web3j.abi.datatypes.Address(_tokenAddress)));
        return deployRemoteCall(PaymentConditions.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PaymentConditions> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _serviceAgreementAddress, String _tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_serviceAgreementAddress), 
                new org.web3j.abi.datatypes.Address(_tokenAddress)));
        return deployRemoteCall(PaymentConditions.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PaymentConditions> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _serviceAgreementAddress, String _tokenAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_serviceAgreementAddress), 
                new org.web3j.abi.datatypes.Address(_tokenAddress)));
        return deployRemoteCall(PaymentConditions.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class PaymentLockedEventResponse {
        public Log log;

        public byte[] serviceId;

        public String sender;

        public String receiver;

        public BigInteger amount;
    }

    public static class PaymentReleasedEventResponse {
        public Log log;

        public byte[] serviceId;

        public String sender;

        public String receiver;

        public BigInteger amount;
    }

    public static class PaymentRefundEventResponse {
        public Log log;

        public byte[] serviceId;

        public String sender;

        public String receiver;

        public BigInteger amount;
    }
}
