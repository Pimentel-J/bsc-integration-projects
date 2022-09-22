/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lapr.project.model.Park;
import lapr.project.model.User;
import oracle.jdbc.OracleTypes;

/**
 *
 *
 */
public class UserDB extends DataHandler {

    /**
     * Adds specified user to the table "users".
     *
     * @param user user specified
     */
    public void addUser(User user) {
        addUser(user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getCardNumber(),
                user.getHeight(),
                user.getWeight(),
                user.getPoints(),
                user.getInitialFee(),
                user.getAvgSpeed(),
                user.getGender());
    }

    /**
     * Invocation of a "stored function". Adds specified user to the table
     * "User".
     *
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     * @param cardNumber credit card number of the user
     * @param height height of the user
     * @param weight weight of the user
     * @param points points of the user
     * @param initialFee initialfee of ther user
     * @param avgSpeed averege speed of the user
     * @param gender gender of the user
     *
     */
    public void addUser(String username, String email, String password, String cardNumber, int height, int weight, int points, double initialFee, double avgSpeed, String gender) {
        openConnection();
        CallableStatement callStmt;
        try {
            // Regista o tipo de dados SQL para interpretar o resultado obtido.

            // Especifica o parâmetro de entrada da função "NEWUSER".
            callStmt = getConnection().prepareCall("{ call NEWUSER(?,?,?,?,?,?,?,?,?,?) }");
            callStmt.setString(1, username);
            callStmt.setString(2, email);
            callStmt.setString(3, password);
            callStmt.setString(4, cardNumber);
            callStmt.setInt(5, height);
            callStmt.setInt(6, weight);
            callStmt.setInt(7, points);
            callStmt.setDouble(8, initialFee);
            callStmt.setDouble(9, avgSpeed);
            callStmt.setString(10, gender);

            callStmt.execute();

            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Something went rong when adding a user.");
        } finally {
            closeAll();
        }
    }

    /**
     * Adds multiple users to the table "Users". If not able to insert one, does
     * not insert any. It's all or nothing.
     *
     * @param users users to insert
     */
    public void addUsers(List<User> users) {

        try {
            openConnection();
            getConnection().setAutoCommit(false);

            /*
             *  Object "callStmt" to call the procedure "users" saved in the 
             * DB.
             *
             *  PROCEDURE addPark(id INTEGER, latitude NUMBER, longitude NUMBER, 
             * elevation INTEGER, description VARCHAR, maxNumberBicycles INTEGER,
             * maxNumberEscooters INTEGER, inputVoltage INTEGER, inputCurrent INTEGER)
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addUser(?,?,?,?,?,?,?,?) }");

            int numUsers = users.size();
            for (int i = 0; i < numUsers; i++) {
                User user = users.get(i);

                callStmt.setString(1, user.getUserName());
                callStmt.setString(2, user.getEmail());
                callStmt.setInt(3, user.getHeight());
                callStmt.setInt(4, user.getWeight());
                callStmt.setDouble(5, user.getAvgSpeed());
                callStmt.setString(6, user.getCardNumber());
                callStmt.setString(7, user.getGender());
                callStmt.setString(8, user.getPassword());

                callStmt.execute();
            }

            getConnection().commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                getConnection().rollback();
                throw new IllegalArgumentException("Not possible to add Users");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }

    /**
     * Method that returns an user by email
     *
     * @param email email of an user
     * @return User User Object with matching user name
     */
    public User getUserByEmail(String email) {

        CallableStatement callStmt;
        try {
            callStmt = getConnection().prepareCall("{ ? = call GETUSERBYEMAIL(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getUser".
            callStmt.setString(2, email);

            // Executa a invocação da função "getUser".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                User user = new User();
                user.setUserId(rSet.getInt(1));
                user.setUserName(rSet.getString(2));
                user.setEmail(rSet.getString(3));
                user.setPassword(rSet.getString(4));
                user.setCardNumber(rSet.getString(5));
                user.setHeight(rSet.getInt(6));
                user.setWeight(rSet.getInt(7));
                user.setPoints(rSet.getInt(8));
                user.setInitialFee(rSet.getDouble(9));
                user.setAvgSpeed(rSet.getDouble(10));
                user.setGender(rSet.getString(11));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No User with Email:" + email);
    }

    /**
     * Method that returns an user by user name
     *
     * @param userName user Name of the User
     * @return User User that has macthing username
     */
    public User getUserByUserName(String userName) {

        CallableStatement callStmt;
        try {
            callStmt = getConnection().prepareCall("{ ? = call GETUSERBYUSERNAME(?) }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "GETUSERBYUSERNAME".
            callStmt.setString(2, userName);

            // Executa a invocação da função "GETUSERBYUSERNAME".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                User user = new User();
                user.setUserId(rSet.getInt(1));
                user.setUserName(rSet.getString(2));
                user.setEmail(rSet.getString(3));
                user.setPassword(rSet.getString(4));
                user.setCardNumber(rSet.getString(5));
                user.setHeight(rSet.getInt(6));
                user.setWeight(rSet.getInt(7));
                user.setPoints(rSet.getInt(8));
                user.setInitialFee(rSet.getDouble(9));
                user.setAvgSpeed(rSet.getDouble(10));
                user.setGender(rSet.getString(11));
                return user;
            }
        } catch (SQLException e) {

        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No User with user name:" + userName);
    }

    /**
     * Get the list of allParks
     *
     * @return list of all parks
     */
    public List<Park> getAllParks() {

        List<Park> lst_parks = new ArrayList<>();

        CallableStatement callStmt;
        try {
            callStmt = getConnection().prepareCall("{ ? = call GETALLPARKS() }");

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            // Executa a invocação da função "getAllParks".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                Park park = new Park();

                park.setId(rSet.getString(1));
                park.setLatitude(Double.parseDouble(rSet.getString(2)));
                park.setLongitude(Double.parseDouble(rSet.getString(3)));
                park.setElevation(Integer.parseInt(rSet.getString(4)));
                park.setDescription(rSet.getString(5));
                park.setMaxNumberBicycles(Integer.parseInt(rSet.getString(6)));
                park.setMaxNumberEscooters(Integer.parseInt(rSet.getString(7)));
                park.setInputVoltage(Integer.parseInt(rSet.getString(8)));
                park.setInputCurrent(Integer.parseInt(rSet.getString(9)));

                lst_parks.add(park);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("No parks registered!");
        } finally {
            closeAll();
        }
        return lst_parks.size() > 0 ? lst_parks : null;
    }

    /**
     * Method that removes a user
     *
     * @param email of the user to remove
     * @return boolean true if removed
     */
    public boolean removeUser(String email) {

        try {
            openConnection();

            CallableStatement callStmt = getConnection().prepareCall("{ call removeUser(?) }");

            callStmt.setString(1, email);

            callStmt.execute();

            closeAll();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeAll();
        }
    }

    /**
     * Updates specified user to the table "USER_APP".
     *
     * @param user user specified
     */
    public void updateUser(User user) {
        updateUser(user.getUserId(), user.getUserName(), user.getEmail(), user.getPassword(), user.getCardNumber(), user.getHeight(), user.getWeight(), user.getPoints(), user.getInitialFee(), user.getAvgSpeed(), user.getGender());
    }

    /**
     * Invocation of a "stored function". Updates specified user to the table
     * "USER_APP".
     *
     * @param userId user id of the user
     * @param username username of the user
     * @param email email of the user
     * @param password password of the user
     * @param cardNumber credit card number of the user
     * @param height height of the user
     * @param weight weight of the user
     * @param points points of the user
     * @param initialFee initialfee of ther user
     * @param avgSpeed averege speed of the user
     * @param gender gender of user
     *
     */
    private void updateUser(int userId, String username, String email, String password, String cardNumber, int height, int weight, int points, double initialFee, double avgSpeed, String gender) {
        openConnection();
        CallableStatement callStmt;
        try {
            // Regista o tipo de dados SQL para interpretar o resultado obtido.

            // Especifica o parâmetro de entrada da função "UPDATEUSER".
            callStmt = getConnection().prepareCall("{ call UPDATEUSER(?,?,?,?,?,?,?,?,?,?,?) }");
            callStmt.setInt(1, userId);
            callStmt.setString(2, username);
            callStmt.setString(3, email);
            callStmt.setString(4, password);
            callStmt.setString(5, cardNumber);
            callStmt.setInt(6, height);
            callStmt.setInt(7, weight);
            callStmt.setInt(8, points);
            callStmt.setDouble(9, initialFee);
            callStmt.setDouble(10, avgSpeed);
            callStmt.setString(11, gender);

            callStmt.execute();
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Something went rong when updating a user.");
        } finally {
            closeAll();
        }
    }
}
