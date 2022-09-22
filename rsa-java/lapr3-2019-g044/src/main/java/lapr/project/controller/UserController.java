/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import lapr.project.data.UserDB;
import lapr.project.model.Park;
import lapr.project.model.User;
import lapr.project.utils.Utils;
import lapr.project.utils.SendEmail;

/**
 *
 *
 */
public class UserController {

    private UserDB userDb;

    /**
     * An user.
     */
    private User user;

    /**
     * Empty constructor
     */
    public UserController() {
        userDb = new UserDB();
        user = new User();
    }

    /**
     * Set userDataBase to instance.
     *
     * @param userDataBase new instance of userDb
     */
    public void setUserDataBase(UserDB userDataBase) {
        this.userDb = userDataBase;
    }

    /**
     * Method that creates a new instance of User and add to data base.
     *
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     * @param cardNumber credit card number of the user
     * @param height height of the user
     * @param weight weight of the user
     * @param avgSpeed averege speed of user
     * @param gender gender of the user
     * @return true if added
     */
    public boolean newUser(String username, String email, String password, String cardNumber, int height, int weight,double avgSpeed, String gender) {
        try {
            User newUser = new User(username, email, password, cardNumber, height, weight,avgSpeed, gender);
            return saveUser(newUser);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Checks if user passed already exists, otherwise inserts it in the "User"
     * table.
     *
     * @param user user to insert
     * @return true if insert operation successful, false if not
     */
    private boolean saveUser(User user) {
        try {
            userDb.getUserByEmail(user.getEmail());
            return false;
        } catch (IllegalArgumentException ex) {
            //If the record does not exist, save it
            userDb.addUser(user);
            return true;
        }
    }

    /**
     * Validates, creates and adds Users to the table "Users" according to the
     * data passed as parameters.
     *
     * @param username username of users
     * @param email email of users
     * @param height height of users
     * @param weight wieght of users
     * @param avgSpeed averege speed of users
     * @param cardNumber credit card number of users
     * @param gender gender of user
     * @param password password of yser
     *
     * @return true if all saved, false if not
     */
    public boolean addUsers(List<String> username, List<String> email, List<Integer> height,
            List<Integer> weight, List<Double> avgSpeed, List<String> cardNumber,List<String> gender,List<String> password) {

        List<User> users = new LinkedList<>();
        int numUsers = username.size();

        try {
            for (int i = 0; i < numUsers; i++) {
                users.add(new User(
                        username.get(i),
                        email.get(i),
                        height.get(i),
                        weight.get(i),
                        avgSpeed.get(i),
                        cardNumber.get(i),
                        gender.get(i),
                        password.get(i)
                ));
            }
            return saveUsers(users);
        } catch (IllegalArgumentException ex) { // at least one record with invalid data
            return false;
        }
    }

    /**
     * Checks if Users passed already exist, otherwise inserts them in the
     * "Users" table.
     *
     * @param users list of Users to insert
     * @return true if all records were inserted, false if not
     */
    private boolean saveUsers(List<User> users) {
        int numUsers = users.size();

        // checks if any of the records exist already
        for (int i = 0; i < numUsers; i++) {
            User user = users.get(i);
            User temp = getUserByEmail(user.getEmail());
            if (temp != null) {
                return false;
            }
        }

        // if none of the records exist, saves all of them
        try {
            userDb.addUsers(users);
            return true;
        } catch (IllegalArgumentException ex2) {
            return false;
        }
    }

    /**
     * Method that returns the user.
     *
     * @param email
     * @return user
     */
    public User getUserByEmail(String email) {
        User user;
        try {
            user = userDb.getUserByEmail(email);
        } catch (IllegalArgumentException ex) {
            user = null;
        }
        return user;
    }

    /**
     * Method that returns the user.
     *
     * @param username user name of the user
     * @return user User with matching user name
     */
    public User getUserByUserName(String username) {
        User user;
        try {
            user = userDb.getUserByUserName(username);
        } catch (IllegalArgumentException ex) {
            user = null;
        }
        return user;
    }

    /**
     * Method that verifies if the user exists through email
     *
     * @param email email
     * @return true case if user exists or false in case of not exists
     */
    public boolean validateUser(String email) {
        boolean validEmail = true;
        if (userDb.getUserByEmail(email) != null) {
            return validEmail;
        }
        return false;
    }

    /**
     * Method that verifies if the user exists through email
     *
     * @param email
     * @return true case if user exists or false in case of not exists
     */
    public boolean removeUser(String email) {
        boolean remove = true;
        if (!userDb.removeUser(email)) {
            return remove;
        }
        return false;
    }

    /**
     * Method that sends email to user
     *
     * @param email
     * @param subject
     * @param body
     * @return true case if email was send or false in case of not send
     */
    public boolean sendEmail(String email, String subject, String body) {
        boolean sended = true;
        if (SendEmail.validateSendEmail(email, subject, body)) {
            return sended;
        }
        return false;
    }

    /**
     * Write in a file the nearest parks given a point (latitude , longitude)
     *
     * @param lst_park
     * @param lat latitude in degrees
     * @param lon longitude in degrees
     * @param radius radius
     * @return true if sucess
     */
    public List<Integer> getNearestParks(List<Park> lst_park, double lat, double lon, int radius) {

        List<Park> temp = userDb.getAllParks();
        List<Integer> distance = new ArrayList<>();

        if (temp == null) {
            return null;
        } else {
            orderByDistance(temp, lat, lon);
        }

        int dist = 0;
        Park park;

        for (int i = 0; (i < temp.size() && dist < radius); i++) {
            park = temp.get(i);
            dist = (int) Utils.distance(lat, lon, park.getLatitude(), park.getLongitude());
            if (validateDistance(dist, radius)) {
                lst_park.add(park);
                distance.add(dist);
            }
        }
        
        return distance;
    }

    /**
     * Return true, if the distance is lower then de radius (max distance)
     *
     * @param dist distance
     * @param radius radius
     * @return true if the distance is less than or equal to the radius
     */
    public boolean validateDistance(int dist, int radius) {
        if (radius <= 0) {
            return (dist - Utils.DEFAULT_RAIUS <= 0);
        }
        return (dist - radius <= 0);
    }

    /**
     * Order list of parks by distance
     *
     * @param lst_park list of all parks
     * @param lat latitude in degrees
     * @param lon longitude in degress
     * @return true if sucess
     */
    public boolean orderByDistance(List<Park> lst_park, double lat, double lon) {
        if (lst_park != null) {
            Collections.sort(lst_park, new Comparator<Park>() {
                @Override
                public int compare(Park p1, Park p2) {
                    return (int) (Utils.distance(lat, lon, p1.getLatitude(), p1.getLongitude())
                            - Utils.distance(lat, lon, p2.getLatitude(), p2.getLongitude()));
                }
            });
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Simulates a payment from a given user. Receives a username and an amount, 
     * checks if user exists and returns true if so. Returns false if not valid.
     * 
     * @param username username from the user who will be charged
     * @param amount amount to be charged
     * @return true if successful; false if not
     */
    public boolean payAmount(String username, double amount) {
        User user = this.getUserByUserName(username);
        if (user == null || amount <= 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Updates a user instance
     *
     * @param user user object to update 

     */
    public void updateUser(User user) {
         userDb.updateUser(user);
    }
}
