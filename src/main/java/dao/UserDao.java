package dao;

import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
//        this.connectionImple = aws;
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            return user;
        }
    };

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }
    public int getCount()  {
        return this.jdbcTemplate.queryForObject("SELECT count(*) FROM users;", Integer.class);
    }
    public void add(User user) {
//        Connection conn = connectionMaker.openConnection();
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?, ?, ?);",
                user.getId(), user.getName(), user.getPassword());

    }
    public User findById(String id)  {
        String sql = "SELECT id, name, password FROM users WHERE id=?;";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<User> getAll(){
        String sql = "SELECT * from users order by id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}
