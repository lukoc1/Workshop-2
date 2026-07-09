package org.example;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "NoweHaslo123!";

    //            2. Metoda connect, która przyjmuje nazwę bazy do której ma się połączyć
    private static final String DB_URL_NO_DB = "jdbc:mysql://localhost:3306/%%%?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static int insert(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setString(3, params[2]); //statement.setString(3, hashPassword(params[2]));
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {
//
//        try (PreparedStatement statement = conn.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery();) {
//            while (resultSet.next()) {
//                for (String columnName : columnNames) {
//                    System.out.println(resultSet.getString(columnName));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void printDataOneline(Connection conn, String query, String... columnNames) throws SQLException {
//
//        try (PreparedStatement statement = conn.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery();) {
//            while (resultSet.next()) {
//                for (String columnName : columnNames) {
//                    System.out.print(" " + resultSet.getString(columnName));
//                }
//                System.out.println();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                        conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    Rozbudowa klasy DbUtil:
//            1. Metoda update() - Analogicznie do insert()
//            2. Metoda connect, która przyjmuje nazwę bazy do której ma się połączyć
//            3. Metoda countAll(String tableName) - która zwraca ilość wierszy w tabeli,
//            4. Metoda count(String sqlQuery) - która zwraca ilość wierszy dla zadanego zapytania
//            5. Metoda zwracająca czy wiersz o zadanym id istnieje w tabeli:
//              public static boolean exists(Connection conn, String tableName, int id)
//            6. Metoda getData - która zwróci tablicę tablic w wynikami dla zadanego zapytania


    //            1. Metoda update() - Analogicznie do insert()
    public static void update(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Nie znaleziono rekordu o podanym id.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //            2. Metoda connect, która przyjmuje nazwę bazy do której ma się połączyć
    public static Connection connect(String DbName) throws SQLException {
        return DriverManager.getConnection(DB_URL_NO_DB.replace("%%%", DbName), DB_USER, DB_PASS);
        // można tez sprobowac z replace np
    }

//    //            3. Metoda countAll(String tableName) - która zwraca ilość wierszy w bazie,
//    public static int countAll(String tableName) {
//        String query = "SELECT count(*) as count FROM " + tableName;
//
//        try (Connection conn = DbUtil.connect();
//             PreparedStatement statement = conn.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            resultSet.next();
//            return resultSet.getInt("count");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//
//    }

//    //            4. Metoda count(String sqlQuery) - która zwraca ilość wierszy dla zadanego zapytania
//    public static int count(String sqlQuery) {
//        try (Connection conn = DbUtil.connect();
//             PreparedStatement statement = conn.prepareStatement(sqlQuery);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            int count = 0;
//
//            while (resultSet.next()) {
//                count++;
//            }
//
//            return count;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return 0;
//    }

//    //  5. Metoda zwracająca czy wiersz o zadanym id istnieje w tabeli:
//    //      public static boolean exists(Connection conn, String tableName, int id)
//    public static boolean exists(Connection conn, String tableName, int id) {
//
//        String sqlQuery = "SELECT * FROM " + tableName + " WHERE id = ?;";
//        try (PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
//
//            statement.setInt(1, id);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                return resultSet.next();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    //            6. Metoda getData - która zwróci tablicę tablic w wynikami dla zadanego zapytania
//    public static String[][] getData(String sqlQuery) {
//        try (Connection conn = DbUtil.connect();
//             PreparedStatement statement = conn.prepareStatement(sqlQuery);
//             ResultSet resultSet = statement.executeQuery()){
//
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//
//            int columnsNumber = rsmd.getColumnCount();
//            int rowsNumber = count(sqlQuery);
//
//            String[][] data = new String[rowsNumber][columnsNumber];
//
//            int row = 0;
//
//            while (resultSet.next()) {
//                for (int col = 0; col < columnsNumber; col++) {
//                    data[row][col] = resultSet.getString(col + 1);
//                }
//                row++;
//            }
//            return data;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return new String[0][0];
//
//    }


//          7. metoda Cinema getCinemaById(int id) - jeżeli potrzeba doadć conn jako dodatkowy parametr.
//          Zwraca obiekt typu Cinema wypełniony danymi z bazy danych.
//          8. metoda Cinama[] getAllCinemas() - jeżeli potrzeba doadć conn jako dodatkowy parametr.
//          Zwraca tablice obiektow typu Cinema wypełniony danymi z bazy danych.
//           Utworzyć klasę Cinema\


////          7. metoda Cinema getCinemaById(int id) - jeżeli potrzeba doadć conn jako dodatkowy parametr.
////          Zwraca obiekt typu Cinema wypełniony danymi z bazy danych.
//    public static Cinema getCinemaById(int id) {
//        String sqlQuery = "SELECT * FROM cinemas WHERE id = ?;";
//        sqlQuery = sqlQuery.replace("?", Integer.toString(id));
//        String[][] temp = DbUtil.getData(sqlQuery);
//
//        return new Cinema(Integer.parseInt(temp[0][0]), temp[0][1], temp[0][2]);
//    }


////          8. metoda Cinama[] getAllCinemas() - jeżeli potrzeba doadć conn jako dodatkowy parametr.
////          Zwraca tablice obiektow typu Cinema wypełniony danymi z bazy danych.
//    public static Cinema[] getAllCinemas() {
//        String sqlQuery = "SELECT * FROM cinemas;";
//        String[][] temp = DbUtil.getData(sqlQuery);
//
//        Cinema[] allCinemas = new Cinema[temp.length];
//
//        for (int i = 0; i < temp.length; i++) {
//            allCinemas[i] = new Cinema(Integer.parseInt(temp[i][0]), temp[i][1], temp[i][2]);
//        }
//
//        return allCinemas;
//    }



}


