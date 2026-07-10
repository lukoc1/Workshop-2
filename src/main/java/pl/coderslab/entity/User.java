package pl.coderslab.entity;

public class User {

    private int id; // 0 gdy nie jest jeszcze wpisany do bazy, baza sama ma nadać
    private String userName;
    private String email;
    private String password; // hashowane bedzie

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return this.id + " " + this.userName + " " + this.email;
    }
}
