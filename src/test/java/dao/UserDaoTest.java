package dao;

import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
/*@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = User)*/
class UserDaoTest {
    @Test
    @DisplayName("Add와 Get 실험")
    void AddAndGet() throws SQLException, ClassNotFoundException {
        User user1 = new User("1","sanghee","193915");

//        클래스 커넥터 사용 UserDao userdao = new UserDao();
//        AWSConnectionImple을 사용한 DB커넥트
        AWSConnectionImple aws = new AWSConnectionImple();
        UserDao userDao = new UserDao(aws);
//        userdao.add(user1);
        userDao.findById("1");
        System.out.println(user1.getName());
    }

}