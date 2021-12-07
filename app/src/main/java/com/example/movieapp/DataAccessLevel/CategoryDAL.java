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

    public static ArrayList<String> getAllCategoriesNames() {
        CallableStatement callableStatement = null;
        ArrayList<String> categoriesNames = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            Connection connect = connectionHelper.connectionClass();
            if (connect == null) {
                return null;
            }

            callableStatement = connect.prepareCall("{call GetAllCategoriesNames(?)}");
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    categoriesNames.add(resultSet.getString(1));
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
        return categoriesNames;
    }
}
