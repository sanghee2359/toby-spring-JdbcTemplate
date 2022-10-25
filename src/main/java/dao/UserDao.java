package dao;

import domain.User;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker; // 클래스
    private ConnectionImple connectionImple; // 인터페이스
    public UserDao() {
        this.connectionMaker = new ConnectionMaker();
    }
    public UserDao(ConnectionImple aws){
        this.connectionImple = aws;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
//        Connection conn = connectionMaker.openConnection();
        Connection conn = connectionImple.makeConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users" +
                "(id, name, password) VALUES(?, ?, ?);");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        ps.executeUpdate();

        ps.close();
        conn.close();
    }
    public User findById(String id) throws SQLException, ClassNotFoundException {
//        Connection conn = connectionMaker.openConnection();
        Connection conn = connectionImple.makeConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT id, name, password FROM users WHERE id=?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));

        rs.close();
        ps.close();
        conn.close();
        return user;
    }
}
