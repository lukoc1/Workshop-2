package pl.coderslab.entity;

public class MainDao {

    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserName("lukasz").setEmail("lukasz@onet.com").setPassword("pass");

//        userDao.create(user);


        User userToUpdate = userDao.read(1);
        System.out.println(userToUpdate);
        userToUpdate.setUserName("Arkadiusz");
        userToUpdate.setEmail("arek@coderslab.pl");
        userToUpdate.setPassword("superPassword");
        System.out.println(userToUpdate);
//        userDao.update(userToUpdate);


//        userDao.delete(1);
//        User[] all = userDao.findAll();
//        for (User u : all) {
//            System.out.println(u);
//        }
    }

}
