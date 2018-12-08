package com.mira.services.impl;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.mira.contracts.Users;
import com.mira.exception.UserAlreadyExistException;
import com.mira.model.User;
import com.mira.services.ContractService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;

@Service
public class UsersServiceImpl extends ContractService implements InitializingBean {
    private Users usersContract;

    @Autowired
    private Environment env;

    @Value("${contractaddress}")
    private String contractAddress;

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);

    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);

    @Override
    public void afterPropertiesSet() throws Exception {
        usersContract = Users.load(contractAddress,
                        web3j, credentialSvc.getCredentials(),GAS_PRICE, GAS_LIMIT);
    }

    public User addUser(User user, MultipartFile file) throws Exception {
        FingerprintTemplate candidate = getTemplateFromImage(file);
        checkIfUserExists(candidate);

        usersContract.addUser(user.getName(), user.getDob(), user.getSex(), candidate.serialize())
                .send();
        return user;
    }

    public User probeUser(MultipartFile image) throws Exception{
        FingerprintTemplate probe = getTemplateFromImage(image);

        return getMatchedUserUsingMatcher(probe);
    }

    private FingerprintTemplate getTemplateFromImage(MultipartFile image) throws Exception{
        byte[] probeImage = image.getBytes();
        FingerprintTemplate probe = new FingerprintTemplate()
                .dpi(400)
                .create(probeImage);
        return probe;
    }

    private User getMatchedUserUsingMatcher(FingerprintTemplate probe) throws Exception{
        FingerprintMatcher matcher = new FingerprintMatcher().index(probe);
        User user = null;
        String match = null;
        double high = 0;

        int size = usersContract.getTemplatesSize().send().intValue();
        for (long i=0; i < size; i++) {
            String candidate = usersContract.userTemplates(BigInteger.valueOf(i)).send();

            double score = matcher.match(new FingerprintTemplate().deserialize(candidate));
            if (score > high) {
                high = score;
                match = candidate;
            }
        }

        double threshold = 40;
        if (high > threshold) {
            Tuple3<String, String, String> tuple = usersContract.getUser(match).send();
            user = new User(tuple.getValue1(), tuple.getValue2(), tuple.getValue3());
        }
        return user;
    }

    private void checkIfUserExists(FingerprintTemplate candidate) throws Exception{
        User user = getMatchedUserUsingMatcher(candidate);
        if (user != null) throw new UserAlreadyExistException(env.getProperty("userexists"));

    }
}
