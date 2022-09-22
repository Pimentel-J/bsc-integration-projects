/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.Objects;
import lapr.project.data.UserDB;

/**
 *
 *
 */
public class User {

    /**
     * userId, atribute of user.
     */
    private int userId;
    /**
     * Username, atribute of user.
     */
    private String userName;
    /**
     * Email, atribute of user.
     */
    private String email;
    /**
     * Password, atribute of user.
     */
    private String password;
    /**
     * Credit card number, atribute of user.
     */
    private String cardNumber;
    /**
     * Height, atribute of user.
     */
    private int height;
    /**
     * Weight(kg), atribute of user.
     */
    private int weight;
    /**
     * Points, atribute of user.
     */
    private int points;
    /**
     * Gender, gender of user.
     */
    private String gender;
    /**
     * Initial Fee, atribute of user.
     */
    private double initialFee;
    /**
     * Averege Speed(m/s), atribute of user.
     */
    private double avgSpeed;

    //Static variables 
    private static final long CHARACTER_NUMBER = 6;
    private static final double INITIAL_FEE = 0.5;

    UserDB userDataBase;

    /**
     * Complete constructor: a string with a username, a string with the user
     * email, a string with password, a string with credit card number, a double
     * whith height, a double with weight and a double with initialCost, a
     * double with averege speed and assigns it to the user.
     *
     * @param userName username of the user
     * @param email email of the user
     * @param password password of the user
     * @param cardNumber credit card number of the user
     * @param height height of the user
     * @param weight weight of the user
     * @param avgSpeed averege speed of user
     * @param gender gender of the user
     */
    public User(String userName, String email, String password, String cardNumber, int height, int weight, double avgSpeed, String gender) {
        this.userName = userName;
        setEmail(email);
        setPassword(password);
        this.cardNumber = cardNumber;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.avgSpeed = avgSpeed;
        this.initialFee = INITIAL_FEE;
    }

    /**
     * User constructor: a string with a username, a string with the user email,
     * a doublewhith height, a double with weight , a double with averege speed,
     * a string with credit card and assigns it to the user.
     *
     * @param userName username of the user
     * @param email email of the user
     * @param height height of the user
     * @param weight weight of the user
     * @param avgSpeed averege speed of the user
     * @param cardNumber credit card number of the user
     * @param gender gender of user
     * @param password password of user
     */
    public User(String userName, String email, int height, int weight, Double avgSpeed, String cardNumber, String gender, String password) {
        this.userName = userName;
        setEmail(email);
        this.height = height;
        this.weight = weight;
        this.avgSpeed = avgSpeed;
        this.cardNumber = cardNumber;
        this.gender = gender;
        setPassword(password);
    }

    /**
     * Constructor: receives a string with the user email and a string with
     * password.
     *
     * @param email email of user
     * @param password password of user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Empty contructor
     */
    public User() {

    }

    /**
     * Method that returns the id of an user
     *
     * @return idUser id of user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Method that returns the username of an user
     *
     * @return username username of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method that returns the email of an user
     *
     * @return email email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method that returns the password of an user
     *
     * @return password password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method that returns the cardNumber of an user
     *
     * @return cardNumber credit card number of user
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Method that returns the height of an user
     *
     * @return height height of user
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method that returns the weight of an user
     *
     * @return weight weight of user
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Method that returns the points of an user
     *
     * @return points points of user
     */
    public int getPoints() {
        return points;
    }

    /**
     * Method that returns the initial cost of an user
     *
     * @return initialCost initial cost of user
     */
    public double getInitialFee() {
        return initialFee;
    }

    /**
     * Method that returns the averege speed of an user
     *
     * @return avgSpeed averege speed of user
     */
    public double getAvgSpeed() {
        return avgSpeed;
    }

    /**
     * Method that returns the gender of an user
     *
     * @return gender of user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set idUser to instance.
     *
     * @param userId user id of user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Set username to instance.
     *
     * @param userName user name of user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Set email to instance.
     *
     * @param email email of user
     */
    public final void setEmail(String email) {
        if (!validateEmail(email)) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    /**
     * Set password to instance.
     *
     * @param password password of user
     */
    public final void setPassword(String password) {
        if (!validatePassword(password)) {
            throw new IllegalArgumentException("Invalid Password!");
        }
        this.password = password;
    }

    /**
     * Set cardNumber to instance.
     *
     * @param cardNumber credit card number of user
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Set height to instance.
     *
     * @param height height of user
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Set weight to instance.
     *
     * @param weight weight of user
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Set points to instance.
     *
     * @param points points of user
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Method that adds points to user
     *
     * @param points points of user
     */
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * Set initialCost to instance.
     *
     * @param initialFee inital fee of user
     */
    public void setInitialFee(double initialFee) {
        this.initialFee = initialFee;
    }

    /**
     * Set averege speed to instance.
     *
     * @param avgSpeed averege speed of user
     */
    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    /**
     * Set gender to instance.
     *
     * @param gender gender of user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Method hashcode of User
     *
     * @return hashcoce hashcoce of user
     */
    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    /**
     * Method to compare Users
     *
     * @param obj obj representative user
     * @return boolean true if equals
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final User other = (User) obj;

        if (this.userId != other.userId) {
            return false;
        }
        if (Double.doubleToLongBits(this.height) != Double.doubleToLongBits(other.height)) {
            return false;
        }
        if (Double.doubleToLongBits(this.avgSpeed) != Double.doubleToLongBits(other.avgSpeed)) {
            return false;
        }
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) {
            return false;
        }
        if (this.points != other.points) {
            return false;
        }
        if (Double.doubleToLongBits(this.initialFee) != Double.doubleToLongBits(other.initialFee)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return Objects.equals(this.cardNumber, other.cardNumber);
    }

    /**
     * Method that verifies if a email follows the minimun request: verifies if
     * it contains "@" and ".".
     *
     * @param email email of user
     * @return true case it follows the request or false in case of not
     * following.
     */
    public boolean validateEmail(String email) {
        boolean validEmail = true;
        if (email.contains("@") && email.contains(".")) {
            return validEmail;
        }
        return false;
    }

    /**
     * Method that verifies if a password follows the minimun request: verifies
     * if it has six or more characters.
     *
     * @param password password of user
     * @return true case it follows the request or false in case of not
     * following.
     */
    public boolean validatePassword(String password) {
        boolean validPassword = true;
        if (password.length() >= CHARACTER_NUMBER) {
            return validPassword;
        }
        return false;
    }

    /**
     * Method to print Users
     *
     * @return string with user info
     */
    @Override
    public String toString() {
        return "User: \n" + "userId=" + userId + "\n username=" + userName + "\n email=" + email + "\n password=" + password + "\n cardNumber=" + cardNumber + "\n height=" + height + "\n weight=" + weight + "\n points=" + points + "\n initialFee=" + initialFee + "\n averege speed= " + avgSpeed + "\n gender= " + gender + '.';
    }

}
