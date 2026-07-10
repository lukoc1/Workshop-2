package pl.coderslab.entity;

import pl.coderslab.utils.DbUtil;
import pl.coderslab.utils.PasswordUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {


    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?);";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users set userName = ?, email = ? WHERE id = ?;";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users where id = ?;";
    private static final String FIND_ALL_USER_QUERY =
            "SELECT * FROM users;";
    private static final String CHANGE_PASSWORD_QUERY =
            "UPDATE users SET password = ? WHERE id = ?";



    //    create - zapisuje obiekt do tabeli jako nowy wiersz
    public User create(User user) {

        try (Connection conn = DbUtil.connect()) {

            int id = DbUtil.insert(conn, CREATE_USER_QUERY, user.getUserName(),
                        user.getEmail(), PasswordUtil.hashPassword(user.getPassword()));
            user.setId(id);
            return user;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }


//    read - wczytuje jeden wiersz z tabeli i zwraca obiekt, ktory ten wiersz reprezentuje
    public User read(int userId) {

        try (Connection conn = DbUtil.connect()) {

            String[][] temp = DbUtil.getData(READ_USER_QUERY, userId);

            if (temp.length == 0) {
                System.out.println("User with id " + userId + " not found.");
                return null;
            }

            User user = new User();
            user.setId(Integer.parseInt(temp[0][0]));
            user.setEmail(temp[0][1]);
            user.setUserName(temp[0][2]);
            user.setPassword(temp[0][3]);

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    update - zapisuje obiekt do tabeli dokonujac modyfikacji istniejacego wczesniej wiersza tabeli
    public void update(User user) {

        try (Connection conn = DbUtil.connect()) {
            DbUtil.update(conn, UPDATE_USER_QUERY, user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    delete - usuwa obiekt z tabeli czyli usuwa wiersz o id takim samym jak zapisany w obiekcie
    public void delete(int userId) {

        try (Connection conn = DbUtil.connect()) {
            DbUtil.remove(conn, DELETE_USER_QUERY, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    findAll - lista obiektow stworzonych z tabeli (wszystkich)
      public User[] findAll() {

          try (Connection conn = DbUtil.connect();
               PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_QUERY)){

              ResultSet resultSet = statement.executeQuery();

              User[] users = new User[0];

              while (resultSet.next()) {

                  User user = new User();
                  user.setId(resultSet.getInt("id"));
                  user.setUserName(resultSet.getString("username"));
                  user.setEmail(resultSet.getString("email"));
                  user.setPassword(resultSet.getString("password"));

                  users = addToArray(user, users);
              }
              return users;
          } catch (SQLException e) {
              e.printStackTrace();
          }

          return new User[0];
     }

              //          Będziemy również potrzebować mechanizmu, który pozwoli nam automatycznie powiększać tablicę.
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }


    public void updatePassword(int userId, String newPassword) {

        try (Connection conn = DbUtil.connect();
             PreparedStatement statement =
                     conn.prepareStatement(CHANGE_PASSWORD_QUERY)) {

            statement.setString(1, PasswordUtil.hashPassword(newPassword));
            statement.setInt(2, userId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("User with id " + userId + " not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

