package com.example.movieapp.DataAccessLevel;

import com.example.movieapp.Helpers.ConnectionHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class FavoritesMoviesDAL {
    private Connection connect;

    public void InsertFavoriteMovie(int userID, int movieID, boolean isFavorite) {
        CallableStatement callableStatement = null;
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionClass();

            callableStatement = connect.prepareCall("{call InsertFavoriteMovie(?,?,?)}");
            callableStatement.setInt("@movie_id", movieID);
            callableStatement.setInt("@categoryID", movieID);
            callableStatement.setBoolean("@is_favorite", isFavorite);
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