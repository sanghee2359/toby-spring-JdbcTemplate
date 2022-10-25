package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
        AWSConnectionImple impleAWS = new AWSConnectionImple();
        UserDao userDao = new UserDao(impleAWS);
        return userDao;
    }

}
