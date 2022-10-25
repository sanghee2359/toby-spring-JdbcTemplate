package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {
    //     1. Class 로 connection 실행
    //        UserDao userDao = new UserDao();
    //     2. Interface로 connection 실행
    //        AWSConnectionImple aws = new AWSConnectionImple());
    //        UserDao userDao = new UserDaoFactory().awsUserDao();
    // test 방법 ; userdaofactory에서 특정 조합을 사용
    //     3. spring 사용
    //        userDao = context.getBean("awsUserDao", UserDao.class);

    @Autowired
    ApplicationContext context;
    UserDao userDao;
    User user1;
    User user2;
    User user3;
    @BeforeEach
    void setUp() {
        userDao = context.getBean("awsUserDao", UserDao.class);
        user1= new User("1", "정상희", "1106");
        user2= new User("2", "신지원", "0129");
        user3= new User("3", "박정현", "0308");

    }
    @Test
    void addAndSelect() throws SQLException, ClassNotFoundException {
//        AWSConnectionImplement userDao = new AWSConnectionMaker());
//        UserDao userDao = new UserDaoFactory().localUserDao();
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        String id ="4";
        userDao.add(new User(id, "sanghee", "25345"));

        User user = userDao.findById(id);
        Assertions.assertEquals("sanghee", user.getName());
    }
    @Test
    @DisplayName("deleteAll과 Add가 잘 되는 지")
    void DeleteAll() throws SQLException, ClassNotFoundException {
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        userDao.add(user1);
        assertEquals(1, userDao.getCount());

    }

}