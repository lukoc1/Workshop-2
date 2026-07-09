package pl.coderslab.entity;

import org.example.DbUtil;

import java.sql.*;

public class UserDao {


    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "";
    private static final String UPDATE_USER_QUERY =
            "";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM tableName where id = ?";


//    create - zapisuje obiekt do tabeli jako nowy wiersz
    public User create(User user) {

        try (Connection conn = DbUtil.connect()) {

                int id = DbUtil.insert(conn, CREATE_USER_QUERY, user.getUserName(),
                        user.getEmail(), hashPassword(user.getPassword()));
                //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
                user.setId(id);
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    //        zapisać do bazy danych informacje z obiektu
    //        pobrać id nowo zapisanego użytkownika
    //        uzupełnić id w obiekcie
//            zwrócić uzupełniony obiekt
    }

////    read - wczytuje jeden wiersz z tabeli i zwraca obiekt, ktory ten wiersz reprezentuje
//    public User read(int userId) {
////        pobrać z bazy danych wiersz dla zadanego identyfikatora
////        utworzyć nowy obiekt klasy User
////        uzupełnić obiekt danymi z bazy
////        zwrócić uzupełniony obiekt
//    }

////    update - zapisuje obiekt do tabeli dokonujac modyfikacji istniejacego wczesniej wiersza tabeli
//    public void update(User user) {
////        W ramach metody należy zmienić dane w bazie na podstawie danych z obiektu.
//    }

////    delete - usuwa obiekt z tabeli czyli usuwa wiersz o id takim samym jak zapisany w obiekcie
//    public void delete(int userId) {
////        W ramach metody należy usunąć wiersz z bazy danych na podstawie przekazanego identyfikatora.
//    }

////    findAll - lista obiektow stworzonych z tabeli (wszystkich)
//      public User[] findAll() {
////          pobrać z bazy danych wszystkie wiersze z tabeli users
////          na podstawie każdego wiersza utworzyć obiekt klasy User
////          obiekty umieścić w tablicy
////          zwrócić tablicę obiektów
////          Będziemy również potrzebować mechanizmu, który pozwoli nam automatycznie powiększać tablicę.
//      }
}

