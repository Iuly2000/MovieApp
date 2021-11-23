package com.example.movieapp.DataAccessLevel;

import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.EntityLevel.User;
import com.example.movieapp.Helpers.ConnectionHelper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;

public class MovieDAL {
    private Connection connect;

    public ArrayList<Movie> GetAllMovies() {
        CallableStatement callableStatement = null;
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            this.connect = connectionHelper.connectionClass();
            if (this.connect == null) {
                return null;
            }

            callableStatement = connect.prepareCall("{call GetAllMovies(?,?,?,?)}");
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    movies.add(new Movie(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)));
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
        Collections.reverse(movies);
        return movies;
    }
}
