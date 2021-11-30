package com.example.movieapp.DataAccessLevel;

import com.example.movieapp.EntityLevel.Category;
import com.example.movieapp.Helpers.ConnectionHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class CategoryDAL {
    private Connection connect;

    public ArrayList<Category> GetAllCategories() {
        CallableStatement callableStatement = null;
        ArrayList<Category> categories = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();
            if (connect == null) {
                return null;
            }

            callableStatement = connect.prepareCall("{call GetAllCategories(?,?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    categories.add(new Category(resultSet.getInt(1), resultSet.getString(2)));
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
        return categories;
    }
}
