package ir.uid.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;

public abstract class ContractService {
    @Autowired
    protected CredentialService credentialSvc;

    @Autowired
    protected Web3j web3j;

    private String contractAddress;

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
