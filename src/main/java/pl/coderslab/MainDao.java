package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;
import pl.coderslab.utils.PasswordUtil;

public class MainDao {

    public static void main(String[] args) {

        UserDao userDao = new UserDao();

////      CREATE USER
//        User user = new User();
//        user.setUserName("ola").setEmail("aleksandra@wp.pl").setPassword("dobre hasloo");
//        userDao.create(user);

////      READ + UPDATE USER

////            problem gdyby ktos zrobil - bo bedziemy robic hash hasła które jest już hashowane
        User userToUpdate = userDao.read(7);
        PasswordUtil.checkPassword("jakies haslo", userToUpdate);
        userDao.changePassword(7, "haslo");
        userToUpdate = userDao.read(7);
        PasswordUtil.checkPassword("haslo", userToUpdate);


//        User userToUpdate = userDao.read(4);
//        System.out.println(userToUpdate);
//        userToUpdate.setUserName("Adam");
//        userToUpdate.setEmail("malysz@coderslab.pl");
//        userToUpdate.setPassword("skoczek");
//        System.out.println(userToUpdate);
//        userDao.update(userToUpdate);

////      DELETE USER + FIND_ALL USERS
//        userDao.delete(5);
//        User[] all = userDao.findAll();
//        for (User u : all) {
//            System.out.println(u);
//        }

////      CHECK PASSWORD
//        User userToUpdate = userDao.read(11);
//        PasswordUtil.checkPassword("dobre hasloo",  userToUpdate);
//        PasswordUtil.checkPassword("to nie to haslo",  userToUpdate);


    }

}
