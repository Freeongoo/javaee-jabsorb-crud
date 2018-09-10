package org.quickstart.repository.mysql;

import org.quickstart.db.ConnectionHolder;
import org.quickstart.bean.User;
import org.quickstart.repository.UserRepository;
import org.quickstart.exceptions.DuplicateUserNameException;
import org.quickstart.exceptions.NotExistUserException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryMySql implements UserRepository {
    public static final int MYSQL_DUPLICATE_PK = 1062;

    @Override
	public List<User> getListUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        Connection con = ConnectionHolder.get();
        List<User> listUsers = new ArrayList<>();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();

			while(rs.next()) {
				User user = getUserFromResultSet(rs);
				listUsers.add(user);
			}
        }
        return listUsers;
	}

	@Override
	public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?;";
        Connection con = ConnectionHolder.get();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);

            return getUserFromPrepareStatement(stm);
        }
	}

	@Override
	public User getUserByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?;";

        Connection con = ConnectionHolder.get();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setString(1, userName);

            return getUserFromPrepareStatement(stm);
        }
	}

	private User getUserFromPrepareStatement(PreparedStatement stm) throws SQLException {
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            return getUserFromResultSet(rs);
        } else {
            return null;
        }
    }

	private User getUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUserName(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        return user;
    }

	@Override
	public void deleteUser(int id) throws NotExistUserException, SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        Connection con = ConnectionHolder.get();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, id);
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0)
                throw new NotExistUserException("Not exist user by id: " + id);
        }
	}

    @Override
    public int createUser(User user) throws DuplicateUserNameException, SQLException {
        String sql = "INSERT INTO users (username, first_name, last_name, password) VALUES (?, ?, ?, ?)";
        Connection con = ConnectionHolder.get();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            setUserPreparedStatement(stm, user);
            stm.executeUpdate();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
			if (e.getErrorCode() == MYSQL_DUPLICATE_PK )
				throw new DuplicateUserNameException("Duplicate username: " + user.getUserName(), e);
			throw e;
        }
    }

    @Override
	public void updateUser(User user) throws NotExistUserException, SQLException, DuplicateUserNameException {
        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, password = ? WHERE id = ?";
		executeUpdateUser(sql, user, true);
	}

    @Override
	public void updateUserWithoutPassword(User user) throws NotExistUserException, SQLException, DuplicateUserNameException {
        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ? WHERE id = ?";
		executeUpdateUser(sql, user, false);
	}

	private void executeUpdateUser(String sql, User user, boolean isChangePassword) throws NotExistUserException, DuplicateUserNameException, SQLException {
		Connection con = ConnectionHolder.get();
		try (PreparedStatement stm = con.prepareStatement(sql)) {
			if (isChangePassword) {
				setUserPreparedStatement(stm, user);
				stm.setInt(5, user.getId());
			} else {
				setUserPreparedStatementWithoutPassword(stm, user);
				stm.setInt(4, user.getId());
			}
			int affectedRows = stm.executeUpdate();

			if (affectedRows == 0)
				throw new NotExistUserException("Not exist user by id: " + user.getId());
		} catch (SQLException e) {
			if (e.getErrorCode() == MYSQL_DUPLICATE_PK )
				throw new DuplicateUserNameException("Duplicate username: " + user.getUserName(), e);
			throw e;
		}
	}

	private void setUserPreparedStatementWithoutPassword(PreparedStatement stm, User user) throws SQLException {
        stm.setString(1, user.getUserName());
        stm.setString(2, user.getFirstName());
        stm.setString(3, user.getLastName());
    }

	private void setUserPreparedStatement(PreparedStatement stm, User user) throws SQLException {
		setUserPreparedStatementWithoutPassword(stm, user);
        stm.setString(4, user.getPassword());
    }
}
