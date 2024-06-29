package co.istad.sms.database;

import java.sql.*;

public class DbSingleton {
    private  static  DbSingleton dbSingleton;
    private Connection connection;

    private  DbSingleton(){
        String url = "jdbc:postgresql://localhost:5432/db_shop";
        String username = "postgres";
        String password = "Koing01042002";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static DbSingleton getDbSingleton(){
        if (dbSingleton == null){
            dbSingleton = new DbSingleton();
        }
        return dbSingleton;
    }
    public Connection getConnection(){
        return connection;
    }
}
