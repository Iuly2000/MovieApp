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
            ipString = "192.168.0.171";
            this.database = "MovieUp!";
            this.uname = "Iuly";
            this.pass = "test123";
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

//    public void getLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf
//                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    System.out.println("ip1--:" + inetAddress);
//                    System.out.println("ip2--:" + inetAddress.getHostAddress());
//
//                    // for getting IPV4 format
//                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
//
//                        String ip = inetAddress.getHostAddress();
//                        System.out.println("ip---::" + ip);
//                        this.ipString = ip;
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            Log.e("IP Address", ex.toString());
//        }
//    }
}
