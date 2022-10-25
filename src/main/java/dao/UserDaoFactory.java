package dao;

import org.springframework.context.annotation.Bean;

public class UserDaoFactory {
    @Bean
    UserDao awsUserDao() {
        AWSConnectionImple impleAWS = new AWSConnectionImple();
        UserDao userDao = new UserDao(impleAWS);
        return userDao;
    }

}
