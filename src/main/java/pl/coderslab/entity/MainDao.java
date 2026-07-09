package pl.coderslab.entity;

public class MainDao {

    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserName("lukasz").setEmail("lukasz@gmail.com").setPassword("pass");

        userDao.create(user);
    }

}
