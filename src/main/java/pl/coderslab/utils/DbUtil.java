package pl.coderslab.utils;

import pl.coderslab.entity.User;

import java.sql.*;

public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2?useSSL=false&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "NoweHaslo123!";

    private static final String DB_URL_NO_DB = "jdbc:mysql://localhost:3306/%%%?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    //            2. Metoda connect, która przyjmuje nazwę bazy do której ma się połączyć
    public static Connection connect(String DbName) throws SQLException {
        return DriverManager.getConnection(DB_URL_NO_DB.replace("%%%", DbName), DB_USER, DB_PASS);
    }

    public static int insert(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, params[0]);
            statement.setString(2, params[1]);
            statement.setString(3, params[2]);
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

    public static void remove(Connection conn, String query, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(query);) {
            statement.setInt(1, id);

            int row = statement.executeUpdate();

            if (row == 0) {
                System.out.println("Couldnt find User id: " + id + ", to remove");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    UPDATE
    public static void update(Connection conn, String query, String... params) {
        try ( PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Could not find user to update with this query: " + query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    UPDATE - but takes user as parameter
    public static void update(Connection conn, String query, User user) {

        if (user == null) {
            return;
        }

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("Could not find user with id: " + user.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    //            4. Metoda count(String sqlQuery) - która zwraca ilość wierszy dla zadanego zapytania
    public static int count(String sqlQuery) {
        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            int count = 0;

            while (resultSet.next()) {
                count++;
            }

            return count;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //  5. Metoda zwracająca czy wiersz o zadanym id istnieje w tabeli:
    //      public static boolean exists(Connection conn, String tableName, int id)
    public static boolean exists(Connection conn, int id) {

        String sqlQuery = "SELECT * FROM users WHERE id = ?;";
        try (PreparedStatement statement = conn.prepareStatement(sqlQuery)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    //            6. Metoda getData - która zwróci tablicę tablic w wynikami dla zadanego zapytania
    public static String[][] getData(String sqlQuery) {
        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)){

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();
            int rowsNumber = count(sqlQuery);

            String[][] data = new String[rowsNumber][columnsNumber];

            int row = 0;

            while (resultSet.next()) {
                for (int col = 0; col < columnsNumber; col++) {
                    data[row][col] = resultSet.getString(col + 1);
                }
                row++;
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new String[0][0];

    }


    //    //            6. Metoda getData - która zwróci tablicę tablic w wynikami dla zadanego zapytania
    public static String[][] getData(String sqlQuery, int id) {
        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(sqlQuery)){

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();

            if (!resultSet.next()) {
                return new String[0][0];
            }

            String[][] data = new String[1][columnsNumber];

            for (int col = 0; col < columnsNumber; col++) {
                data[0][col] = resultSet.getString(col + 1);
            }

            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new String[0][0];

    }
}


