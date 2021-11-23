package com.example.movieapp.DataAccessLevel;

import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.Helpers.ConnectionHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class UserDAL {
    private Connection connect;

    public void InsertUser(User user) {
        CallableStatement callableStatement = null;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();

            callableStatement = connect.prepareCall("{call InsertUser(?,?,?)}");
            callableStatement.setString("@username", user.getUsername());
            callableStatement.setString("@password", user.getPassword());
            callableStatement.setString("@email", user.getEmail());
            callableStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert callableStatement != null;
                callableStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public ArrayList<User> GetAllUsers() {
        CallableStatement callableStatement = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if (connect == null) {
                return null;
            }

            callableStatement = connect.prepareCall("{call GetAllUsers(?,?,?)}");
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    users.add(new User(resultSet.getString(1),
                            resultSet.getString(2), resultSet.getString(3)));
                }
                hadResults = callableStatement.getMoreResults();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert callableStatement != null;
                callableStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return users;
    }

    public boolean VerifiedUser(String username, String password) {
        ArrayList<User> users = GetAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
