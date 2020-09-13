package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {

        private static String url = "jdbc:mysql://localhost:3306/nomedobancodedados";
        private static String user = "usuario";
        private static String pass = "senha";
        private static String driver = "com.mysql.jdbc.Driver";
        private static Connection con;

        public static Connection obter() {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                e.getMessage();
            } catch (NullPointerException e) {
                e.getMessage();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return con;
        }

        public static void fechar() {
            try {
                con.close();
            } catch (SQLException e) {
                e.getMessage();
            } 
        }

        public String getDriver(){

            return driver;
        }
    }



