package ir.uid.model.service.impl;

import ir.uid.contracts.Users;
import ir.uid.exception.NotFoundException;
import ir.uid.exception.UserAlreadyExistException;
import ir.uid.model.entity.OTQ;
import ir.uid.model.entity.User;
import ir.uid.model.repository.OTQRepository;
import ir.uid.model.service.ContractService;
import ir.uid.util.Encryptor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class UsersServiceImpl extends ContractService implements InitializingBean {
    private Users usersContract;

    @Autowired
    private Environment env;

    @Value("${contractaddress}")
    private String contractAddress;

    private final Encryptor encryptor;

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);

    private final static BigInteger GAS_PRICE = BigInteger.valueOf(0);

    private final OTQRepository otqRepository;

    private final RestTemplate restTemplate;

    private static final Predicate<Tuple3<String, String, String>> isNull = p -> !p.getValue1().isEmpty() || !p.getValue1().isEmpty() || !p.getValue3().isEmpty();

    @Autowired
    public UsersServiceImpl(Encryptor encryptor,
                            OTQRepository otqRepository,
                            RestTemplate restTemplate) {
        this.encryptor = encryptor;
        this.otqRepository = otqRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        usersContract = Users.load(contractAddress,
                                   web3j, credentialSvc.getCredentials(), GAS_PRICE, GAS_LIMIT);
//        deployContract();
    }

    public User addUser(User user) throws Exception {
        user.setKey(RandomStringUtils.random(16, true, true));
        checkIfUserExists(user.getKey());
        user = encryptor.encryptUserDatas(user, user.getKey());
        usersContract.addUser(user.getName(), user.getDob(), user.getSex(), user.getKey())
                .send();
        return user;
    }


    public User probeUser(String key) throws Exception {
        User encryptedUser = getMatchedUserUsingMatcher(key);
        if (Objects.isNull(encryptedUser)) throw new NotFoundException("user not found");
        return encryptor.decryptUser(encryptedUser, key);
    }

    private User getMatchedUserUsingMatcher(String key) throws Exception {
        User                           user;
        Tuple3<String, String, String> tuple = usersContract.getUser(key).send();
        if (!isNull.test(tuple)) return null;
        user = new User(tuple.getValue1(), tuple.getValue2(), tuple.getValue3());
        return user;
    }

    private void checkIfUserExists(String key) throws Exception {
        User user = getMatchedUserUsingMatcher(key);
        if (user != null) throw new UserAlreadyExistException(env.getProperty("userexists"));

    }

    private void deployContract() throws Exception {
        String      walletFilePath = "/home/siavash/Desktop/myDataDir/keystore/UTC--2019-04-21T08-11-09.057643861Z--9fa05352f21a02ff9ef2666300c99e1bcffe38c1";
        Credentials credentials    = WalletUtils.loadCredentials("siavash", walletFilePath);
        usersContract = Users.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
        contractAddress = usersContract.getContractAddress();
        System.out.println(contractAddress + usersContract.isValid());
    }

    public User getUserWithLid(String key, String lid) throws Exception {
        OTQ otq = otqRepository.findByLid(lid);
        if (Objects.isNull(otq)) throw new NotFoundException("lid not found");
        if (!otq.getUserId().equals(key))
            throw new NotFoundException("no user scanned this");
        User user = probeUser(key);
        restTemplate.postForEntity(otq.getCallBackUrl(), user, Object.class);
        otq.setDeleted(true);
        otqRepository.save(otq);
        return user;
    }
}
