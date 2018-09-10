package org.quickstart.repository.mysql;

import org.quickstart.components.Roles;
import org.quickstart.db.ConnectionHolder;
import org.quickstart.exceptions.NotExistRoleException;
import org.quickstart.repository.UserRoleRepository;
import org.quickstart.exceptions.NotFoundEnumRoleException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleRepositoryMySql implements UserRoleRepository {

    @Override
    public List<Roles> getRoles(int userId) throws NotFoundEnumRoleException, SQLException {
        String sql = "SELECT role_id FROM user_roles WHERE user_id = ?";
        Connection con = ConnectionHolder.get();
        List<Roles> listRoles = new ArrayList<>();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                Integer roleId = rs.getInt("role_id");
                listRoles.add(Roles.getById(roleId));
            }
        }
        return listRoles;
    }

    @Override
    public void updateRoles(int userId, List<Roles> roles) throws SQLException, NotExistRoleException {
        removeRoles(userId);
        setRoles(userId, roles);
    }

    private void removeRoles(int userId) throws SQLException, NotExistRoleException {
        String sql = "DELETE FROM user_roles WHERE user_id = ?";
        Connection con = ConnectionHolder.get();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            stm.setInt(1, userId);
            int affectedRows = stm.executeUpdate();

            if (affectedRows == 0)
                throw new NotExistRoleException("Not exist user by id: " + userId);
        }
    }

    @Override
    public boolean isManager(int userId) throws NotFoundEnumRoleException, SQLException {
        List<Roles> roles = getRoles(userId);
        return roles.contains(Roles.MANAGER) || roles.contains(Roles.ADMIN);
    }

    @Override
    public void setRoles(int userId, List<Roles> roles) throws SQLException {
        String sql = "INSERT IGNORE INTO user_roles (user_id, role_id) VALUES(?, ?)";
        Connection con = ConnectionHolder.get();

        try (PreparedStatement stm = con.prepareStatement(sql)) {
            for (Roles role : roles) {
                stm.setInt(1, userId);
                stm.setInt(2, role.getCode());
                stm.execute(); // the INSERT happens here
            }
        }
    }
}
