package com.example.movieapp.DataAccessLevel;

import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.Helpers.ConnectionHelper;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Objects;

public class MovieDAL {

    public static ArrayList<Movie> getAllMovies() {
        CallableStatement callableStatement = null;
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            callableStatement = ConnectionHelper.connection.prepareCall("{call GetAllMovies(?,?,?,?,?,?,?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.registerOutParameter(7, Types.FLOAT);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    movies.add(new Movie(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getFloat(7)));
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
        return movies;
    }

    public static ArrayList<Movie> getTheLast5Movies() {
        CallableStatement callableStatement = null;
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            callableStatement = ConnectionHelper.connection.prepareCall("{call GetTheLast5Movies(?,?,?,?,?,?,?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            callableStatement.registerOutParameter(6, Types.INTEGER);
            callableStatement.registerOutParameter(7, Types.FLOAT);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    movies.add(new Movie(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getFloat(7)));
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
        return movies;
    }

    public static ArrayList<Movie> searchMoviesByWord(String word) {
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        for (Movie movie : Objects.requireNonNull(getAllMovies())) {
            if (movie.getName().toUpperCase().contains(word))
                searchedMovies.add(movie);
        }
        return searchedMovies;
    }
}
