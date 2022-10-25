package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

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
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connectionImple.makeConnection();
//            ps = conn.prepareStatement("DELETE FROM users");
            ps = stmt.makePreparedStatement(conn);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(new DeleteAllStrategy());
    }
    public int getCount() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = connectionImple.makeConnection();
            ps = conn.prepareStatement("SELECT count(*) FROM users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    public void add(User user) throws ClassNotFoundException, SQLException {
//        Connection conn = connectionMaker.openConnection();
        jdbcContextWithStatementStrategy(new AddStrategy(user));
    }
    public User findById(String id) throws SQLException, ClassNotFoundException {
//        Connection conn = connectionMaker.openConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = connectionImple.makeConnection();
            ps = conn.prepareStatement("SELECT id, name, password FROM users WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.next();
            user = null;
            if(rs.next()){
                user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
            }
            return user;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
