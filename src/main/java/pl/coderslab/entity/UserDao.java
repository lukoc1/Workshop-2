package pl.coderslab.entity;

import org.example.DbUtil;
import org.example.PasswordUtil;

import java.sql.*;

public class UserDao {


    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users WHERE id = ?;";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users set userName = ?, email = ?, password = ? WHERE id = ?;";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users where id = ?";


//    create - zapisuje obiekt do tabeli jako nowy wiersz
    public User create(User user) {

        try (Connection conn = DbUtil.connect()) {
            //            zapisać do bazy danych informacje z obiektu
            //              pobrać id nowo zapisanego użytkownika
            int id = DbUtil.insert(conn, CREATE_USER_QUERY, user.getUserName(),
                        user.getEmail(), PasswordUtil.hashPassword(user.getPassword()));
            //            uzupełnić id w obiekcie
            user.setId(id);
            //            zwrócić uzupełniony obiekt
            return user;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }


//    read - wczytuje jeden wiersz z tabeli i zwraca obiekt, ktory ten wiersz reprezentuje
    public User read(int userId) {

        try (Connection conn = DbUtil.connect()) {

            //        pobrać z bazy danych wiersz dla zadanego identyfikatora
            String[][] temp = DbUtil.getData(READ_USER_QUERY, userId);

            if (temp.length == 0) {
                return null;
            }

            //        utworzyć nowy obiekt klasy User
            User user = new User();
            //        uzupełnić obiekt danymi z bazy
            user.setId(Integer.parseInt(temp[0][0]));
            user.setEmail(temp[0][1]);
            user.setUserName(temp[0][2]);
            user.setPassword(temp[0][3]);

            //        zwrócić uzupełniony obiekt
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

//    update - zapisuje obiekt do tabeli dokonujac modyfikacji istniejacego wczesniej wiersza tabeli
    public void update(User user) {

        try (Connection conn = DbUtil.connect()) {
            //        W ramach metody należy zmienić dane w bazie na podstawie danych z obiektu.
            DbUtil.update(conn, UPDATE_USER_QUERY, user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//    delete - usuwa obiekt z tabeli czyli usuwa wiersz o id takim samym jak zapisany w obiekcie
    public void delete(int userId) {

        try (Connection conn = DbUtil.connect()) {
//        W ramach metody należy usunąć wiersz z bazy danych na podstawie przekazanego identyfikatora.
            DbUtil.remove(conn, DELETE_USER_QUERY, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

////    findAll - lista obiektow stworzonych z tabeli (wszystkich)
//      public User[] findAll() {
////          pobrać z bazy danych wszystkie wiersze z tabeli users
////          na podstawie każdego wiersza utworzyć obiekt klasy User
////          obiekty umieścić w tablicy
////          zwrócić tablicę obiektów
////          Będziemy również potrzebować mechanizmu, który pozwoli nam automatycznie powiększać tablicę.
//      }
}

