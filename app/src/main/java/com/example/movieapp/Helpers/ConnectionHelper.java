package com.example.movieapp.Helpers;

import android.app.Application;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.text.format.Formatter;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;

public class ConnectionHelper extends Application {
    Connection connection = null;
    String uname, pass, ipString, port, database;

    public Connection connectionClass() {
        try {
            ipString = "SQL5105.site4now.net";
            this.database = "db_a7d320_movieup";
            this.uname = "db_a7d320_movieup_admin";
            this.pass = "Database123!!!";
            this.port="1433";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.setProperty("java.net.preferIPv4Stack", "true");

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String connectionURL = "jdbc:jtds:sqlserver://" + ipString + ":" + port + ";" + "databasename=" + database + ";user=" + uname + ";password=" + pass + ";";
            this.connection = DriverManager.getConnection(connectionURL);
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());

        }
        return this.connection;
    }

}
