package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker; // 클래스
    private ConnectionImple connectionImple; // 인터페이스
    private JdbcContext jdbcContext;
    private DataSource dataSource;
    public UserDao() {
        this.connectionMaker = new ConnectionMaker();
    }
    public UserDao(DataSource dataSource){
//        this.connectionImple = aws;
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
    }
    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContext.workWithStatementStrategy(new DeleteAllStrategy());
    }
    public int getCount() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
//            conn = connectionImple.makeConnection();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT count(*) FROM users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

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
        jdbcContext.workWithStatementStrategy(new AddStrategy(user));
    }
    public User findById(String id) throws SQLException, ClassNotFoundException {
//        Connection conn = connectionMaker.openConnection();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
//            conn = connectionImple.makeConnection();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT id, name, password FROM users WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.next();
            user = null;
            if(rs.next()){
                user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
            }
            return user;

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
