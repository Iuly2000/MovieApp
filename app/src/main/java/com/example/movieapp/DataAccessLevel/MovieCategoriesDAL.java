package com.example.movieapp.DataAccessLevel;

import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.Helpers.ConnectionHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class MovieCategoriesDAL {
    private Connection connect;

    public void InsertMovieCategory(int movieID,int categoryID) {
        CallableStatement callableStatement = null;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();

            callableStatement = connect.prepareCall("{call InsertMovieCategory(?,?)}");
            callableStatement.setInt("@movie_id", movieID);
            callableStatement.setInt("@categoryID", categoryID);
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
}
