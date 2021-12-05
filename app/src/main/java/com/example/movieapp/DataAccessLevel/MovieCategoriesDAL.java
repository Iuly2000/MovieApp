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
    public ArrayList<Movie> GetMoviesByCategory(String categoryName) {
        CallableStatement callableStatement = null;
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            this.connect = connectionHelper.connectionClass();
            if (this.connect == null) {
                return null;
            }

            callableStatement = connect.prepareCall("{call GetMoviesByCategory(?,?,?,?,?,?,?,?)}");
            callableStatement.setString("@category_name",categoryName);
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

    public ArrayList<String> GetCategoriesNamesForMovie(int movieID) {
        CallableStatement callableStatement = null;
        ArrayList<String> categoriesNames = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            this.connect = connectionHelper.connectionClass();
            if (this.connect == null) {
                return null;
            }

            callableStatement = connect.prepareCall("{call GetCategoriesNamesForMovie(?,?)}");
            callableStatement.setInt("@movie_id",movieID);
            callableStatement.registerOutParameter(2, Types.VARCHAR);

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
