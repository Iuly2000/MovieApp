package com.example.movieapp.Helpers;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper extends Application {
    public static final Connection connection = connectionClass();

    public static Connection connectionClass() {
        Connection connection = null;
        try {
            String ipString = "192.168.1.4";
            String database = "MovieUp!";
            String uname = "Iuly";
            String pass = "test123";
            String port = "1433";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.setProperty("java.net.preferIPv4Stack", "true");

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionURL = "jdbc:jtds:sqlserver://" + ipString + ":" + port + ";" + "databasename=" + database + ";user=" + uname + ";password=" + pass + ";";
            connection = DriverManager.getConnection(connectionURL);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());

        }
        return connection;
    }

}
