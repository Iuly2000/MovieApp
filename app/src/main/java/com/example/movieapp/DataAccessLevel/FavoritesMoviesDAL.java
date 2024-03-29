package com.example.movieapp.DataAccessLevel;

import android.util.Pair;

import com.example.movieapp.EntityLevel.Movie;
import com.example.movieapp.Helpers.ConnectionHelper;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Objects;

public class FavoritesMoviesDAL {

    public static void insertFavoriteMovie(int userID, int movieID) {
        CallableStatement callableStatement = null;
        try {
            callableStatement = ConnectionHelper.connection.prepareCall("{call InsertFavoriteMovie(?,?)}");
            callableStatement.setInt("@user_id", userID);
            callableStatement.setInt("@movie_id", movieID);
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

    public static void deleteFavoriteMovie(int userID, int movieID) {
        CallableStatement callableStatement = null;
        try {
            callableStatement = ConnectionHelper.connection.prepareCall("{call DeleteFavoriteMovie(?,?)}");
            callableStatement.setInt("@user_id", userID);
            callableStatement.setInt("@movie_id", movieID);
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

    public static ArrayList<Pair<Integer, Integer>> getAllFavoriteMovies() {
        CallableStatement callableStatement = null;
        ArrayList<Pair<Integer, Integer>> favoriteMovies = new ArrayList<>();
        try {
            callableStatement = ConnectionHelper.connection.prepareCall("{call GetAllFavoriteMovies(?,?)}");
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.registerOutParameter(2, Types.INTEGER);

            boolean hadResults = callableStatement.execute();

            while (hadResults) {
                ResultSet resultSet = callableStatement.getResultSet();

                while (resultSet.next()) {
                    favoriteMovies.add(new Pair<>(resultSet.getInt(1), resultSet.getInt(2)));
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
        return favoriteMovies;
    }

    public static boolean checkIfFavorite(int userID, int movieID) {
        for (Pair<Integer, Integer> favoriteMovie : Objects.requireNonNull(getAllFavoriteMovies())) {
            if (favoriteMovie.first == userID && favoriteMovie.second == movieID)
                return true;
        }
        return false;
    }

    public static ArrayList<Movie> getFavoriteMoviesForUser(Integer userID) {
        CallableStatement callableStatement = null;
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            callableStatement = ConnectionHelper.connection.prepareCall("{call GetFavoriteMoviesForUser(?,?,?,?,?,?,?,?)}");
            callableStatement.setInt("@user_id",userID);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            callableStatement.registerOutParameter(6, Types.VARCHAR);
            callableStatement.registerOutParameter(7, Types.INTEGER);
            callableStatement.registerOutParameter(8, Types.FLOAT);

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
}
