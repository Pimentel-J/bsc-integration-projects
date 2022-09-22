package lapr.project.assessment;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import lapr.project.controller.UserController;
import lapr.project.model.Park;
import lapr.project.utils.Utils;
import lapr.project.controller.AdminController;
import lapr.project.controller.InvoiceLineController;
import lapr.project.controller.POIController;
import lapr.project.controller.ParkController;
import lapr.project.controller.PathController;
import lapr.project.controller.PointNetworkController;
import lapr.project.controller.TripController;
import lapr.project.controller.VehicleController;
import lapr.project.data.FileDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.Escooter.Type;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Path;
import lapr.project.model.Point;
import lapr.project.model.Trip;
import lapr.project.model.User;
import lapr.project.model.Vehicle;
import lapr.project.utils.UtilsFiles;

public class Facade implements Serviceable {

    //Controllers
    private AdminController adminController = new AdminController();
    private POIController poiController = new POIController();
    private ParkController parkController = new ParkController();
    private UserController userController = new UserController();
    private VehicleController vehicleController = new VehicleController();
    private TripController tripController = new TripController();
    private PathController pathController = new PathController();
    private InvoiceLineController invoiceLineController = new InvoiceLineController();
    private PointNetworkController pointNetworkController = new PointNetworkController();

    private FileDB files = new FileDB();

    /**
     * Add Bicycles to the system. Basic: Add one bicycle to one park.
     * Intermediate: Add several bicycles to one park. Advanced: Add several
     * bicycles to several parks transactionally.
     *
     * @param inputFile - Path to file with bicycles to add, according to
     * input/bicycles.csv.
     * @return The number of added bicycles.
     */
    @Override
    public int addBicycles(String inputFile) {
        List<String[]> lines = UtilsFiles.readFile(inputFile);
        int numLines = lines.size();
        if (numLines == 0) {
            return 0;
        }
        boolean added = false;

        List<String> descriptions = new LinkedList<>();
        List<Integer> weights = new LinkedList<>();
        List<Double> parkLats = new LinkedList<>();
        List<Double> parkLongs = new LinkedList<>();
        List<Float> aerodynCoeffics = new LinkedList<>();
        List<Float> frontalAreas = new LinkedList<>();
        List<Integer> wheelSizes = new LinkedList<>();

        try {
            for (int i = 0; i < numLines; i++) {
                String[] arr = lines.get(i);
                if (arr.length == 7) {
                    descriptions.add(arr[0].trim());
                    weights.add(Integer.parseInt(arr[1].trim()));
                    parkLats.add(Double.parseDouble(arr[2].trim()));
                    parkLongs.add(Double.parseDouble(arr[3].trim()));
                    aerodynCoeffics.add(Float.parseFloat(arr[4].trim()));
                    frontalAreas.add(Float.parseFloat(arr[5].trim()));
                    wheelSizes.add(Integer.parseInt(arr[6].trim()));
                }
            }
            added = adminController.addBicycles(descriptions, weights, parkLats,
                    parkLongs, aerodynCoeffics, frontalAreas, wheelSizes);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return added ? descriptions.size() : 0;
    }

    /**
     * Add Escooters to the system. Basic: Add one Escooter to one park.
     * Intermediate: Add several Escooters to one park. Advanced: Add several
     * Escooters to several parks transactionally.
     *
     * @param inputFile - Path to file with Escooters to add, according to
     * input/escooters.csv.
     * @return The number of added escooters.
     */
    @Override
    public int addEscooters(String inputFile) {
        List<String[]> lines = UtilsFiles.readFile(inputFile);
        int numLines = lines.size();
        if (numLines == 0) {
            return 0;
        }
        boolean added = false;

        List<String> descriptions = new LinkedList<>();
        List<Integer> weights = new LinkedList<>();
        List<Escooter.Type> types = new LinkedList<>();
        List<Double> parkLats = new LinkedList<>();
        List<Double> parkLongs = new LinkedList<>();
        List<Float> maxBatteries = new LinkedList<>();
        List<Integer> actualBatteries = new LinkedList<>();
        List<Float> aerodynCoeffics = new LinkedList<>();
        List<Float> frontalAreas = new LinkedList<>();
        List<Float> motors = new LinkedList<>();

        try {
            for (int i = 0; i < numLines; i++) {
                String[] arr = lines.get(i);
                if (arr.length == 10) {
                    descriptions.add(arr[0].trim());
                    weights.add(Integer.parseInt(arr[1].trim()));
                    types.add(Type.valueOf(arr[2].trim()));
                    parkLats.add(Double.parseDouble(arr[3].trim()));
                    parkLongs.add(Double.parseDouble(arr[4].trim()));
                    maxBatteries.add(Float.parseFloat(arr[5].trim()));
                    actualBatteries.add(Integer.parseInt(arr[6].trim()));
                    aerodynCoeffics.add(Float.parseFloat(arr[7].trim()));
                    frontalAreas.add(Float.parseFloat(arr[8].trim()));
                    motors.add(Float.parseFloat(arr[9].trim()));
                }
            }
            added = adminController.addEscooters(descriptions, weights, types,
                    parkLats, parkLongs, maxBatteries, actualBatteries,
                    aerodynCoeffics, frontalAreas, motors);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return added ? descriptions.size() : 0;
    }

    /**
     * Add Parks to the system.
     *
     * @param s Path to file that contains the parks, according to file
     * input/parks.csv.
     * @return The number of added parks.
     */
    @Override
    public int addParks(String s) {
        List<String[]> lines = UtilsFiles.readFile(s);
        int numLines = lines.size();
        if (numLines == 0) {
            return 0;
        }

        List<String> ids = new LinkedList<>();
        List<Double> latitudes = new LinkedList<>();
        List<Double> longitudes = new LinkedList<>();
        List<Integer> elevations = new LinkedList<>();
        List<String> descriptions = new LinkedList<>();
        List<Integer> maxNumsBikes = new LinkedList<>();
        List<Integer> maxNumsEscooters = new LinkedList<>();
        List<Integer> inputVoltages = new LinkedList<>();
        List<Integer> inputCurrents = new LinkedList<>();
        boolean added = false;

        try {
            for (int i = 0; i < numLines; i++) {
                String[] arr = lines.get(i);
                if (arr.length == 9) {
                    ids.add(arr[0].trim());
                    latitudes.add(Double.parseDouble(arr[1].trim()));
                    longitudes.add(Double.parseDouble(arr[2].trim()));
                    elevations.add(arr[3].trim().isEmpty() ? 0 : Integer.parseInt(arr[3].trim()));
                    descriptions.add(arr[4].trim());
                    maxNumsBikes.add(Integer.parseInt(arr[5].trim()));
                    maxNumsEscooters.add(Integer.parseInt(arr[6].trim()));
                    inputVoltages.add(Integer.parseInt(arr[7].trim()));
                    inputCurrents.add(Integer.parseInt(arr[8].trim()));
                }
            }
            added = adminController.addParks(ids, latitudes, longitudes, elevations,
                    descriptions, maxNumsBikes, maxNumsEscooters, inputVoltages, inputCurrents);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return added ? ids.size() : 0;
    }

    /**
     * Add POIs to the system.
     *
     * @param s Path to file that contains the points of interest, according to
     * file input/pois.csv.
     * @return The number of added POIs.
     */
    @Override
    public int addPOIs(String s) {
        List<String[]> lines = UtilsFiles.readFile(s);
        int numLines = lines.size();
        if (numLines == 0) {
            return 0;
        }

        List<Double> latitudes = new LinkedList<>(), longitudes = new LinkedList<>();
        List<Integer> elevations = new LinkedList<>();
        List<String> descriptions = new LinkedList<>();
        boolean added = false;

        try {
            for (int i = 0; i < numLines; i++) {
                String[] arr = lines.get(i);
                if (arr.length == 4) {
                    latitudes.add(Double.parseDouble(arr[0].trim()));
                    longitudes.add(Double.parseDouble(arr[1].trim()));
                    elevations.add(arr[2].trim().isEmpty() ? 0 : Integer.parseInt(arr[2].trim()));
                    descriptions.add(arr[3].trim());
                }
            }
            added = poiController.addPois(latitudes, longitudes, elevations, descriptions);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return added ? latitudes.size() : 0;
    }

    /**
     * Add users to the system.
     *
     * @param s Path to file that contains the users, according to file
     * input/users.csv.
     * @return The number of added users.
     */
    @Override
    public int addUsers(String s) {

        List<String[]> lines = UtilsFiles.readFile(s);
        int numLines = lines.size();
        if (numLines == 0) {
            return 0;
        }
        List<String> usernames = new LinkedList<>();
        List<String> emails = new LinkedList<>();
        List<Integer> heights = new LinkedList<>();
        List<Integer> weights = new LinkedList<>();
        List<Double> avgSpeeds = new LinkedList<>();
        List<String> cardNumbers = new LinkedList<>();
        List<String> genders = new LinkedList<>();
        List<String> passwords = new LinkedList<>();
        boolean added = false;

        try {
            for (int i = 0; i < numLines; i++) {
                String[] arr = lines.get(i);
                if (arr.length == 8) {
                    usernames.add(arr[0].trim());
                    emails.add(arr[1].trim());
                    heights.add(Integer.parseInt(arr[2].trim()));
                    weights.add(Integer.parseInt(arr[3].trim()));
                    avgSpeeds.add(Double.parseDouble(arr[4].trim()));
                    cardNumbers.add(arr[5].trim());
                    genders.add(arr[6].trim());
                    passwords.add(arr[7].trim());
                }
            }
            added = userController.addUsers(usernames, emails, heights, weights,
                    avgSpeeds, cardNumbers, genders, passwords);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return added ? emails.size() : 0;
    }

    /**
     * Add paths to the system.
     *
     * @param s Path to file that contains the paths, according to file
     * input/paths.csv.
     * @return The number of added escooters.
     */
    @Override
    public int addPaths(String s) {

        List<String[]> lines = UtilsFiles.readFile(s);
        int numLines = lines.size();
        if (numLines == 0) {
            return 0;
        }

        List<Double> latA = new LinkedList<>(), longA = new LinkedList<>(),
                latB = new LinkedList<>(), longB = new LinkedList<>(), k_coeffic = new LinkedList<>();
        List<Integer> wind_dir = new LinkedList<>();
        List<Float> wind_speed = new LinkedList<>();
        boolean added = false;

        try {
            for (int i = 0; i < numLines; i++) {
                String[] arr = lines.get(i);
                if (arr.length == 7) {
                    latA.add(Double.parseDouble(arr[0].trim()));
                    longA.add(Double.parseDouble(arr[1].trim()));
                    latB.add(Double.parseDouble(arr[2].trim()));
                    longB.add(Double.parseDouble(arr[3].trim()));
                    k_coeffic.add(Double.parseDouble(arr[4].trim()));
                    wind_dir.add(Integer.parseInt(arr[5].trim()));
                    wind_speed.add(Float.parseFloat(arr[6].trim()));
                }
            }
            added = pathController.addPaths(latA, longA, latB, longB, k_coeffic, wind_dir, wind_speed);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return added ? latA.size() : 0;
    }

    /**
     * Get the list of bicycles parked at a given park.
     *
     * @param parkLatitudeInDegrees - Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees - Park Longitude in Decimal degrees.
     * @param outputFileName - Path to file where output should be written,
     * according to file output/bicycles.csv. Sort in ascending order by bike
     * description.
     * @return The number of bicycles at a given park.
     */
    @Override
    public int getNumberOfBicyclesAtPark(double parkLatitudeInDegrees, double parkLongitudeInDegrees,
            String outputFileName) {

        Park park = parkController.getParkByCoordinates(parkLatitudeInDegrees,
                parkLongitudeInDegrees);
        if (park == null) {
            return 0;
        }
        ArrayList<Bicycle> bikesAtPark = vehicleController.getAllBikes(park);
        files.writeBicycles(outputFileName, bikesAtPark);
        return bikesAtPark.size();
    }

    /**
     * Get the list of bicycles parked at a given park.
     *
     * @param parkIdentification - The Park Identification.
     * @param outputFileName - Path to file where output should be written,
     * according to file output/bicycles.csv. Sort in ascending order by bike
     * description.
     * @return The number of bicycles at a given park.
     */
    @Override
    public int getNumberOfBicyclesAtPark(String parkIdentification, String outputFileName) {

        Park park = parkController.getParkById(parkIdentification);
        if (park == null) {
            return 0;
        }
        ArrayList<Bicycle> bikesAtPark = vehicleController.getAllBikes(park);
        files.writeBicycles(parkIdentification, bikesAtPark);
        return bikesAtPark.size();
    }

    /**
     * Distance is returns in metres, rounded to the unit e.g. (281,58 rounds to
     * 282);
     *
     * @param v Latitude in degrees.
     * @param v1 Longitude in degrees.
     * @param s Filename for output.
     */
    @Override
    public void getNearestParks(double v, double v1, String s) {

        List<Park> lstPark = new ArrayList<>();
        List<Integer> distance;

        distance = userController.getNearestParks(lstPark, v, v1, Utils.DEFAULT_RAIUS);
        this.files.writeParks(s, lstPark, distance);
    }

    /**
     * Remove a park from the system
     *
     * @param string id of park
     * @return The number of removed parks.
     */
    @Override
    public int removePark(String string) {
        String num = adminController.deletePark(string);
        if (num.equals("")) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Get a list of the nearest parks to the user.
     *
     * @param d user latitude in Decimal Degrees
     * @param d1 longitude in Decimal Degrees
     * @param string Path to file where output should be written, according to
     * file output/locations.csv. Sort by distance in ascending order.
     * @param i The radius in meters to which extent the user desires the
     * results to be returned within.
     */
    @Override
    public void getNearestParks(double d, double d1, String string, int i) {

        List<Park> lstPark = new ArrayList<>();
        List<Integer> distance;

        distance = userController.getNearestParks(lstPark, d, d1, i);
        this.files.writeParks(string, lstPark, distance);

    }

    /**
     * Get the number of free bicyle parking places at a given park for the
     * loaned bicycle.
     *
     * @param string The Park Identification.
     * @param string1 The username that has an unlocked bicycle.
     * @return The number of free slots at a given park for the user's bicycle
     * type.
     */
    @Override
    public int getFreeBicycleSlotsAtPark(String string, String string1) {
        try {
            String vehicleId = tripController.getLoanedVehicle(string1);
            Vehicle vehicle = vehicleController.getVehicle(vehicleId);

            if (!(vehicle instanceof Bicycle)) {
                return 0;
            }

            Park park = parkController.getParkById(string);
            int maxSlots = park.getMaxNumberBicycles();
            int numBikes = vehicleController.getNumberOfBikesFromPark(park);
            return (maxSlots - numBikes < 0) ? 0 : (maxSlots - numBikes);

        } catch (NullPointerException ex) {
            return 0;
        }
    }

    /**
     * Get the number of free escooter parking places at a given park for the
     * loaned escooter.
     *
     * @param string The Park Identification.
     * @param string1 The username that has an unlocked escooter.
     * @return The number of free slots at a given park for the user's escooter
     * type.
     */
    @Override
    public int getFreeEscooterSlotsAtPark(String string, String string1) {
        try {
            String vehicleId = tripController.getLoanedVehicle(string1);
            Vehicle vehicle = vehicleController.getVehicle(vehicleId);

            if (!(vehicle instanceof Escooter)) {
                return 0;
            }

            Park park = parkController.getParkById(string);
            int maxSlots = park.getMaxNumberEscooters();
            int numEscooters = vehicleController.getNumberOfEscootersFromPark(park);
            return (maxSlots - numEscooters < 0) ? 0 : (maxSlots - numEscooters);

        } catch (NullPointerException ex) {
            return 0;
        }
    }

    /**
     * Get the number of free parking places at a given park for the user's
     * loaned vehicle.
     *
     * @param string The username that has an unlocked vehicle.
     * @param string1 The Park Identification.
     * @return The number of free slots at a given park for the user's vehicle.
     */
    @Override
    public int getFreeSlotsAtParkForMyLoanedVehicle(String string, String string1) {
        return getFreeBicycleSlotsAtPark(string1, string)
                + getFreeEscooterSlotsAtPark(string1, string);
    }

    /**
     * Returns the distance in meters from one location to another.
     *
     * @param d Origin latitude in Decimal Degrees.
     * @param d1 Origin longitude in Decimal Degrees.
     * @param d2 Destiny latitude in Decimal Degrees.
     * @param d3 Destiny longitude in Decimal Degrees.
     * @return Returns the distance in meters from one location to another.
     */
    @Override
    public int linearDistanceTo(double d, double d1, double d2, double d3) {
        return (int) Utils.distance(d, d1, d2, d3);
    }

    /**
     * Get the shortest path distance from one location to another.
     *
     * @param d Origin latitude in Decimal Degrees.
     * @param d1 Origin longitude in Decimal Degrees.
     * @param d2 Destiny latitude in Decimal Degrees.
     * @param d3 Destiny longitude in Decimal Degrees.
     * @return Returns the distance in meters from one location to another.
     */
    @Override
    public int pathDistanceTo(double d, double d1, double d2, double d3) {
        List<Point> route = new LinkedList<>();
        List<Path> pathsOfRoute = new LinkedList<>();
        List<Point> vertices = new LinkedList<>();
        vertices.addAll(this.adminController.getAllParks());
        vertices.addAll(this.poiController.getAllPois());
        List<Path> edges = this.pathController.getAllPaths();

        return this.pointNetworkController.shortestRoute(d, d1, d2, d3, route, pathsOfRoute, vertices, edges);
    }

    /**
     * Unlocks a specific bicycle.
     *
     * @param string User that requested the unlock.
     * @param string1 Bicycle description to unlock.
     * @return The time in milliseconds at which it was unlocked.
     *
     */
    @Override
    public long unlockBicycle(String string, String string1) {
        Bicycle bike = vehicleController.getBikeByDesc(string);
        if (bike == null) {
            return 0;
        }
        if (bike.getParkLat() == 0 && bike.getParkLong() == 0) {
            return 0;
        }

        Trip trip = tripController.unlockBicycle(string, string1, bike.getParkLat(), bike.getParkLong());
        if (trip == null) {
            return 0;
        }

        Date date = Utils.stringToDate(trip.getStartTime());
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Unlocks a specific escooter.
     *
     * @param string User that requested the unlock.
     * @param string1 escooter description to unlock.
     * @return The time in milliseconds at which it was unlocked.
     *
     */
    @Override
    public long unlockEscooter(String string, String string1) {

        Escooter eScooter = vehicleController.getEscooterByDesc(string);
        if (eScooter == null) {
            return 0;
        }
        if (eScooter.getParkLat() == 0 && eScooter.getParkLong() == 0) {
            return 0;
        }

        Trip trip = tripController.unlockEscooter(string, string1, eScooter.getParkLat(), eScooter.getParkLong());
        if (trip == null) {
            return 0;
        }

        Date date = Utils.stringToDate(trip.getStartTime());
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Lock a specific bicycle at a park. Basic: Lock a specific bicycle at a
     * park. Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param string Bicycle to lock.
     * @param d Park latitude in Decimal degrees.
     * @param d1 Park Longitude in Decimal degrees.
     * @param string1 User that requested the unlock.
     * @return The time in milliseconds at which it was locked.
     *
     */
    @Override
    public long lockBicycle(String string, double d, double d1, String string1) {
        Bicycle bike = vehicleController.getBikeByDesc(string);
        Park park = parkController.getParkByCoordinates(d, d1);
        User user = userController.getUserByUserName(string1);
        if (bike == null || park == null || user == null) {
            return 0;
        }

        if (getFreeBicycleSlotsAtPark(park.getId(), string1) < 0) {
            return 0;
        }

        Trip trip = tripController.lockBicycle(bike, park, user);
        if (trip == null) {
            return 0;
        }
        Date date = Utils.stringToDate(trip.getEndTime());
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Lock a specific bicycle at a park. Basic: Lock a specific bicycle at a
     * park. Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param string Bicycle to lock.
     * @param string1 The Park Identification.
     * @param string2 User that requested the unlock.
     * @return The time in milliseconds at which it was locked.
     *
     */
    @Override
    public long lockBicycle(String string, String string1, String string2) {
        Bicycle bike = vehicleController.getBikeByDesc(string);
        Park park = parkController.getParkById(string1);
        User user = userController.getUserByUserName(string1);
        if (bike == null || park == null || user == null) {
            return 0;
        }

        if (getFreeBicycleSlotsAtPark(park.getId(), string1) < 0) {
            return 0;
        }

        Trip trip = tripController.lockBicycle(bike, park, user);
        if (trip == null) {
            return 0;
        }
        Date date = Utils.stringToDate(trip.getEndTime());
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Lock a specific escooter at a park. Basic: Lock a specific escooter at a
     * park. Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param string escooter to lock.
     * @param d Park latitude in Decimal degrees.
     * @param d1 Park Longitude in Decimal degrees.
     * @param string1 User that requested the unlock.
     * @return The time in milliseconds at which it was locked.
     *
     */
    @Override
    public long lockEscooter(String string, double d, double d1, String string1) {
        Escooter scooter = vehicleController.getEscooterByDesc(string);
        Park park = parkController.getParkByCoordinates(d, d1);
        User user = userController.getUserByUserName(string1);
        if (scooter == null || park == null || user == null) {
            return 0;
        }

        if (getFreeBicycleSlotsAtPark(park.getId(), string1) < 0) {
            return 0;
        }

        Trip trip = tripController.lockEscooter(scooter, park, user);
        if (trip == null) {
            return 0;
        }
        Date date = Utils.stringToDate(trip.getEndTime());
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Lock a specific escooter at a park. Basic: Lock a specific escooter at a
     * park. Intermediate: Create an invoice line for the loaned vehicle.
     * Advanced: Add points to user.
     *
     * @param string escooter to lock.
     * @param string1 The Park Identification.
     * @param string2 User that requested the unlock.
     * @return The time in milliseconds at which it was locked.
     *
     */
    @Override
    public long lockEscooter(String string, String string1, String string2) {
        Escooter scooter = vehicleController.getEscooterByDesc(string);
        Park park = parkController.getParkById(string1);
        User user = userController.getUserByUserName(string1);
        if (scooter == null || park == null || user == null) {
            return 0;
        }

        if (getFreeBicycleSlotsAtPark(park.getId(), string1) < 0) {
            return 0;
        }

        Trip trip = tripController.lockEscooter(scooter, park, user);
        if (trip == null) {
            return 0;
        }
        Date date = Utils.stringToDate(trip.getEndTime());
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * Register a user on the system.
     *
     * @param string User's username.
     * @param string1 User's email.
     * @param string2 User's desired password.
     * @param string3 User's Visa Card number.
     * @param i User's height in cm.
     * @param i1 User's weight in kg.
     * @param bd User's average speed in m/s with two decimal places e.g 4.17.
     * @param string4 User's gender in text.
     *
     * @return Return 1 if a user is successfully registered.
     *
     */
    @Override
    public int registerUser(String string, String string1, String string2, String string3, int i, int i1, BigDecimal bd, String string4) {
        double avgSpeed = bd.doubleValue();
        if (userController.newUser(string, string1, string2, string3, i, i1, avgSpeed, string4)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Unlocks any escooter at one park. It should unlock the one with higher
     * battery capacity.
     *
     * @param string Park Identification where to unlock escooter.
     * @param string1 User that requested the unlock.
     * @param string2 Write the unlocked vehicle information to a file,
     * according to file output/escooters.csv.
     *
     * @return The time in milliseconds at which it was unlocked.
     *
     */
    @Override
    public long unlockAnyEscooterAtPark(String string, String string1, String string2) {
        Park park = parkController.getParkById(string);
        User user = userController.getUserByUserName(string1);
        if (park == null || user == null) {
            return 0;
        }
        ArrayList<Escooter> allEscooters = vehicleController.getAllEscooters(park);
        Escooter higherEscooter = null;

        if (allEscooters.isEmpty()) {
            return 0;
        }

        for (Escooter escooter : allEscooters) {
            float duration = Utils.calcDurationBattery(escooter.getMotor(), escooter.calcInKwHActBattery());
            if (higherEscooter == null) {
                higherEscooter = escooter;
            }
            if (Utils.calcDurationBattery(higherEscooter.getMotor(), higherEscooter.getActualBattery()) < duration) {
                higherEscooter = escooter;
            }
        }

        Trip trip = tripController.unlockEscooter(higherEscooter.getDescr(), string1, park.getLatitude(), park.getLongitude());
        if (trip == null) {
            return 0;
        }
        Date date = Utils.stringToDate(trip.getStartTime());
        if (date == null) {
            return 0;
        }
        List<Escooter> printEscooter = new ArrayList<>();
        printEscooter.add(higherEscooter);
        files.writeEscooters(string2, printEscooter);
        return date.getTime();
    }

    /**
     * Unlocks any escooter at one park that allows travelling to the
     * destination.
     *
     * @param string Park Identification where to unlock escooter.
     * @param string1 User that requested the unlock.
     * @param d Destiny latitude in Decimal Degrees.
     * @param d1 Destiny longitude in Decimal Degrees.
     * @param string2 Write the unlocked vehicle information to a file,
     * according to file output/escooters.csv.
     *
     * @return The time in milliseconds at which it was unlocked.
     *
     */
    @Override
    public long unlockAnyEscooterAtParkForDestination(String string, String string1, double d, double d1, String string2) {

        Park parkOrig = parkController.getParkById(string);
        Park parkDest = parkController.getParkByCoordinates(d, d1);
        User user = userController.getUserByUserName(string1);
        if (parkOrig == null || parkDest == null || user == null) {
            return 0;
        }
        List<String> capableEscooter = new ArrayList<>();
        ArrayList<Escooter> allEscooters = vehicleController.getAllEscooters(parkOrig);
        if (allEscooters.isEmpty()) {
            return 0;
        }
        double distance = pathDistanceTo(parkOrig.getLatitude(), parkOrig.getLongitude(), parkDest.getLatitude(), parkDest.getLongitude());

        for (Escooter escooter : allEscooters) {
            float duration = Utils.calcDurationBattery(escooter.getMotor(), escooter.calcInKwHActBattery());
            double capableDistance = Utils.calcMaxDistanceInPerfectConditions(duration);
            if (capableDistance > distance) {
                capableEscooter.add(escooter.getDescr());
            }
        }
        if (capableEscooter.isEmpty()) {
            return 0;
        }
        Trip trip = tripController.unlockEscooter(capableEscooter.get(0), string1, parkOrig.getLatitude(), parkOrig.getLongitude());
        if (trip == null) {
            return 0;
        }
        Date date = Utils.stringToDate(trip.getStartTime());
        if (date == null) {
            return 0;
        }
        Escooter myEscooter = vehicleController.getEscooterByDesc(trip.getVehicleDescription());
        List<Escooter> printEscooter = new ArrayList<>();
        printEscooter.add(myEscooter);
        files.writeEscooters(string2, printEscooter);
        return date.getTime();
    }

    /**
     * Suggest escooters with enough energy + 10% to go from one Park to
     * another.
     *
     * @param string Origin Park Identification.
     * @param string1 Username
     * @param d Destination Park latitude in Decimal degrees.
     * @param d1 Destination Park Longitude in Decimal degrees.
     * @param string2 Write the escooters information to a file, according to
     * file output/escooters.csv.
     *
     * @return The number of suggested vehicles.
     *
     */
    @Override
    public int suggestEscootersToGoFromOneParkToAnother(String string, String string1, double d, double d1, String string2) {
        Park parkOrig = parkController.getParkById(string);
        Park parkDest = parkController.getParkByCoordinates(d, d1);
        User user = userController.getUserByUserName(string1);
        if (parkOrig == null || parkDest == null || user == null) {
            return 0;
        }
        List<Escooter> capableEscooter = new ArrayList<>();
        ArrayList<Escooter> allEscooters = vehicleController.getAllEscooters(parkOrig);
        if (allEscooters.isEmpty()) {
            return 0;
        }
        double distance = pathDistanceTo(parkOrig.getLatitude(), parkOrig.getLongitude(), parkDest.getLatitude(), parkDest.getLongitude());
        distance = distance * 1.1;

        for (Escooter escooter : allEscooters) {
            float duration = Utils.calcDurationBattery(escooter.getMotor(), escooter.calcInKwHActBattery());
            double capableDistance = Utils.calcMaxDistanceInPerfectConditions(duration);
            if (capableDistance > distance) {
                capableEscooter.add(escooter);
            }
        }
        if (capableEscooter.isEmpty()) {
            return 0;
        }
        files.writeEscooters(string2, capableEscooter);
        return capableEscooter.size();
    }

    /**
     * Calculate the most energy efficient route from one park to another.
     * Basic: Does not consider wind. Intermediate: Considers wind. Advanced:
     * Considers the different mechanical and aerodynamic coefficients.
     *
     * @param string Origin Park Identification.
     * @param string1 Destination Park Identification.
     * @param string2 The type of vehicle required e.g. "bicycle" or "escooter".
     * @param string3 The specs for the vehicle e.g. "16", "19", "27" or any
     * other number for bicyles and "city" or "off-road" for any escooter.
     * @param string4 UThe username.
     * @param string5 Write to the file the Route between two parks according to
     * file output/paths.csv. More than one path may exist. If so, sort routes
     * by the ascending number of points between the parks and by ascending
     * order of elevation difference.
     * @return The distance in meters for the most energy efficient path.
     *
     */
    @Override
    public long mostEnergyEfficientRouteBetweenTwoParks(String string, String string1, String string2, String string3, String string4, String string5) {
        Park parkOrig = parkController.getParkById(string);
        Park parkDest = parkController.getParkById(string1);

        List<Point> vertices = new LinkedList<>();
        vertices.addAll(this.adminController.getAllParks());
        vertices.addAll(this.poiController.getAllPois());
        List<Path> edges = this.pathController.getAllPaths();

        double vel;
        if (string2.compareTo("bicycle") == 0) {
            vel = userController.getUserByUserName(string4).getAvgSpeed();
        } else {
            if (string2.compareTo("escooter") == 0) {
                vel = Utils.DEFAULT_VELOCITY;
            } else {
                vel = 0;
            }
        }

        // cria as listas para assimilar dados de várias rotas
        List<Integer> distances = new LinkedList<>();
        List<Integer> energies = new LinkedList<>();
        List<Integer> elevations = new LinkedList<>();
        List<List<Point>> routes = new LinkedList<>();

        double latA = parkOrig.getLatitude();
        double longA = parkOrig.getLongitude();
        double latB = parkDest.getLatitude();
        double longB = parkDest.getLongitude();

        // vai buscar a(s) rota(s) mai(s) próxima(s)
        List<Pair<List<Point>, List<Path>>> routesAndItsPaths = new LinkedList<>();
        int energy = this.pointNetworkController.shortestRoutes(latA, longA, latB, longB,
                routesAndItsPaths, vertices, edges, vel);
        int elevation = parkDest.getElevation() - parkOrig.getElevation();

        for (Pair<List<Point>, List<Path>> pair : routesAndItsPaths) {

            distances.add(pointNetworkController.getTotalDistance(pair.getValue()));
            energies.add(energy);
            elevations.add(elevation);
            routes.add(pair.getKey());
        }

        // escreve em ficheiro
        this.files.writePaths(string, distances, energies, elevations, routes);

        return energy;
    }

    /**
     * Get the list of escooters parked at a given park.
     *
     * @param parkLatitudeInDegrees - Park latitude in Decimal degrees.
     * @param parkLongitudeInDegrees - Park Longitude in Decimal degrees.
     * @param outputFileName - Path to file where output should be written,
     * according to file output/escooters.csv. Sort in ascending order by
     * escooter description.
     * @return The number of escooters at a given park.
     */
    @Override
    public int getNumberOfEscootersAtPark(double parkLatitudeInDegrees,
            double parkLongitudeInDegrees, String outputFileName) {
        Park park = parkController.getParkByCoordinates(parkLatitudeInDegrees,
                parkLongitudeInDegrees);
        if (park == null) {
            return 0;
        }
        ArrayList<Escooter> escootersList = vehicleController.getAllEscooters(park);
        if (escootersList.isEmpty()) {
            return 0;
        }
        files.writeEscooters(outputFileName, escootersList);
        return escootersList.size();
    }

    /**
     * Get the list of escooters parked at a given park.
     *
     * @param parkIdentification - The Park Identification.
     * @param outputFileName - Path to file where output should be written,
     * according to file output/escooters.csv. Sort in ascending order by
     * escooter description.
     * @return The number of escooters at a given park.
     */
    @Override
    public int getNumberOfEScootersAtPark(String parkIdentification, String outputFileName) {
        Park park = parkController.getParkById(parkIdentification);
        if (park == null) {
            return 0;
        }
        ArrayList<Escooter> escootersList = vehicleController.getAllEscooters(park);
        if (escootersList.isEmpty()) {
            return 0;
        }
        files.writeEscooters(outputFileName, escootersList);
        return escootersList.size();
    }

    /**
     * Return the current debt for the user.
     *
     * @param string The username.
     * @param string1 The path for the file to output the debt, according to
     * file output/balance.csv. Sort the information by unlock time in ascending
     * order (oldest to newest).
     * @return The User's current debt in euros, rounded to two decimal places.
     */
    @Override
    public double getUserCurrentDebt(String string, String string1) {
        User user = userController.getUserByUserName(string);
        if (user == null) {
            return 0;
        }
        List<InvoiceLine> invoices = invoiceLineController.getInvoicesByUsername(string);
        if (invoices == null) {
            return 0;
        }
        InvoiceLine latestInvoice = invoices.get(0);
        List<Trip> tripsList = tripController.getAllTrips(user.getUserName());
        tripsList.sort(Comparator.comparing(Trip::getStartTime));
        files.writeBalance(string1, tripsList);
        return latestInvoice.getCost();
    }

    /**
     * Return the current points for the user.
     *
     * @param string The user to get the points report from.
     * @param string1 The path for the file to output the points, according to
     * file output/points.csv. Sort the information by unlock time in ascenind
     * order (oldest to newest).
     * @return The User's current points.
     */
    @Override
    public double getUserCurrentPoints(String string, String string1) {
        User user = userController.getUserByUserName(string);
        if (user == null) {
            return 0;
        }
        List<Trip> tripsList = tripController.getAllTrips(user.getUserName());
        tripsList.sort(Comparator.comparing(Trip::getStartTime));
        files.writePoints(string1, tripsList);
        return user.getPoints();
    }

    /**
     * Calculate the amount of electrical energy required to travel from one
     * park to another.
     *
     * @param d Origin latitude in Decimal degrees.
     * @param d1 Origin Longitude in Decimal degrees.
     * @param d2 Destination Park latitude in Decimal degrees.
     * @param d3 Destination Park Longitude in Decimal degrees.
     * @param string Username.
     * @return The electrical energy required in kWh, rounded to two decimal
     * places.
     */
    @Override
    public double calculateElectricalEnergyToTravelFromOneLocationToAnother(double d, double d1, double d2, double d3, String string
    ) {

        return tripController.energyRequiredToTravelAtoB(d, d1, d2, d3, string);
    }

    /**
     * Get for how long has a vehicle has been unlocked.
     *
     * @param string Vehicle description.
     * @return The time in seconds since the vehicle was unlocked.
     */
    @Override
    public long forHowLongAVehicleIsUnlocked(String string
    ) {
        Vehicle vehicle = vehicleController.getVehicle(string);
        if (vehicle == null) {
            return 0;
        }
        List<Trip> allTrips = new ArrayList<>();
        allTrips = tripController.getOngoingTrips();
        if (allTrips == null) {
            return 0;
        }
        long unlockedTime = 0;
        for (Trip trips : allTrips) {
            if (trips.getVehicleDescription().equals(string)) {
                String strStartTime = trips.getStartTime();
                String strEndTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

                unlockedTime = Utils.getTripDurationInSeconds(strStartTime, strEndTime);
            }
        }
        return unlockedTime;
    }

    /**
     * Calculate the shortest Route from one park to another. Basic: Only one
     * shortest Route between two Parks is available. Advanced: More than one
     * Route between two parks are available with different number of POIs
     * (limit to a maximum of two) inbetween and different evelations
     * difference.
     *
     * @param d Origin latitude in Decimal degrees.
     * @param d1 Origin Longitude in Decimal degrees.
     * @param d2 Destination Park latitude in Decimal degrees.
     * @param d3 Destination Park Longitude in Decimal degrees.
     * @param i The number of POIs that should be included in the path. Default
     * can be 0.
     * @param string outputFileName - Write to the file the Route between two
     * parks according to file output/paths.csv. More than one path may exist.
     * If so, sort routes by the ascending number of points between the parks
     * and by ascending order of elevation difference.
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long shortestRouteBetweenTwoParks(double d, double d1, double d2, double d3, int i, String string
    ) {
        Park origPark = this.parkController.getParkByCoordinates(d, d1);
        Park destPark = this.parkController.getParkByCoordinates(d2, d3);

        if (origPark == null || destPark == null) {
            return -1; // se um dos parques nao existe, nao escreve em ficheiro sequer
        }

        // cria as listas de vértices e arestas
        List<Point> vertices = new LinkedList<>();
        vertices.addAll(this.adminController.getAllParks());
        vertices.addAll(this.poiController.getAllPois());
        List<Path> edges = this.pathController.getAllPaths();

        // cria as listas para assimilar dados de várias rotas
        List<Integer> distances = new LinkedList<>();
        List<Integer> energies = new LinkedList<>();
        List<Integer> elevations = new LinkedList<>();
        List<List<Point>> routes = new LinkedList<>();

        // vai buscar a(s) rota(s) mai(s) próxima(s)
        List<Pair<List<Point>, List<Path>>> routesAndItsPaths = new LinkedList<>();
        int distance = this.pointNetworkController.shortestRoutes(d, d1, d2, d3, routesAndItsPaths, vertices, edges);
        int elevation = destPark.getElevation() - origPark.getElevation();

        // preenche as listas para enviar info das rotas para ficheiro
        for (Pair<List<Point>, List<Path>> pair : routesAndItsPaths) {
            if (this.poiController.getNumPois(pair.getKey()) == i) {
                distances.add(distance);
                energies.add(0); //CORRIGIR PARA ENGLOBAR ENERGIA DA ROTA!!!
                elevations.add(elevation);
                routes.add(pair.getKey());
            }
        }

        // escreve em ficheiro
        this.files.writePaths(string, distances, energies, elevations, routes);

        return distance;
    }

    /**
     * Calculate the shortest Route from one park to another. Basic: Only one
     * shortest Route between two Parks is available. Advanced: More than one
     * Route between two parks are available with different number of POIs
     * (limit to a maximum of two) inbetween and different evelations
     * difference.
     *
     * @param string Origin Park Identification.
     * @param string1 Destination Park Identification.
     * @param i The number of POIs that should be included in the path. Default
     * can be 0.
     * @param string2 outputFileName - Write to the file the Route between two
     * parks according to file output/paths.csv. More than one path may exist.
     * If so, sort routes by the ascending number of points between the parks
     * and by ascending order of elevation difference.
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long shortestRouteBetweenTwoParks(String string, String string1,
            int i, String string2
    ) {
        Park origPark = this.parkController.getParkById(string);
        Park destPark = this.parkController.getParkById(string1);

        if (origPark == null || destPark == null) {
            return -1; // se um dos parques nao existe, nao escreve em ficheiro sequer
        }

        double lat1 = origPark.getLatitude();
        double long1 = origPark.getLongitude();
        double lat2 = destPark.getLatitude();
        double long2 = destPark.getLongitude();

        return shortestRouteBetweenTwoParks(lat1, long1, lat2, long2, i, string2);
    }

    /**
     * Calculate the shortest Route from one park to another. Basic: Only one
     * shortest Route between two Parks is available. Advanced: More than one
     * Route between two parks are available with different number of points
     * inbetween and different evelations difference.
     *
     * @param string Origin Park Identification.
     * @param string1 Destination Park Identification.
     * @param string2 inputPOIs - Path to file that contains the POIs that the
     * route must go through, according to file input/pois.csv.
     * @param string3 outputFileName - Write to the file the Route between two
     * parks according to file output/paths.csv. More than one path may exist.
     * If so, sort routes by the ascending number of points between the parks
     * and by ascending order of elevation difference.
     *
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(String string, String string1,
            String string2, String string3
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Calculate the shortest Route from one park to another. Basic: Only one
     * shortest Route between two Parks is available. Advanced: More than one
     * Route between two parks are available with different number of points
     * inbetween and different evelations difference.
     *
     * @param d Origin latitude in Decimal degrees.
     * @param d1 Origin Longitude in Decimal degrees.
     * @param d2 Destination Park latitude in Decimal degrees.
     * @param d3 Destination Park Longitude in Decimal degrees.
     * @param string inputPOIs - Path to file that contains the POIs that the
     * route must go through, according to file input/pois.csv.
     * @param string1 outputFileName - Write to the file the Route between two
     * parks according to file output/paths.csv. More than one path may exist.
     * If so, sort routes by the ascending number of points between the parks
     * and by ascending order of elevation difference.
     *
     * @return The distance in meters for the shortest path.
     */
    @Override
    public long shortestRouteBetweenTwoParksForGivenPOIs(double d, double d1, double d2, double d3, String string,
            String string1
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get a report for the escooter charging status at a given park.
     *
     * @param string Park Identification.
     * @param string1 outputFileName - Path to file where vehicles information
     * should be written, according to file output/chargingReport.csv. Sort
     * items by descending order of time to finish charge in seconds and
     * secondly by ascending escooter description order.
     * @return The number of escooters charging at the moment that are not 100%
     * fully charged.
     */
    @Override
    public long getParkChargingReport(String string, String string1) {
        List<Escooter> escooters = adminController.getParkChargingReport(string);

        this.files.writeParkChargingReport(string1, escooters);

        return escooters.size();
    }

    /**
     * Calculate the most energetically efficient route from one park to another
     * with sorting options.
     *
     * @param string Origin Park Identification.
     * @param string1 Destination Park Identification.
     * @param string2 The type of vehicle required e.g. "bicycle" or "escooter".
     * @param string3 The specs for the vehicle e.g. "16", "19", "27" or any
     * other number for bicyles and "city" or "off-road" for any escooter.
     * @param string4 The user that asked for the routes.
     * @param i The maximum number of suggestions to provide.
     * @param bln If routes should be ordered by ascending or descending order.
     * @param string5 The criteria to use for ordering "energy",
     * "shortest_distance", "number_of_points".
     * @param string6 inputPOIs - Path to file that contains the POIs that the
     * route must go through, according to file input/pois.csv. By default, the
     * file is empty.
     * @param string7 outputFileName - Write to the file the Route between two
     * parks according to file output/paths.csv. More than one path may exist.
     * @return The number of suggestions.
     */
    @Override
    public int suggestRoutesBetweenTwoLocations(String string, String string1,
            String string2, String string3,
            String string4, int i, boolean bln, String string5,
            String string6, String string7
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Get the current invoice for the current month, for a specific user. This
     * should include all loans that were charged the user, the number of points
     * the user had before the actual month, the number of points earned during
     * the month, the number of points converted to euros.
     *
     * @param i The month of the invoice e.g. 1 for January.
     * @param string username - The user for which the invoice should be
     * created.
     * @param string1 Path to file where the invoice should be written,
     * according to file output/invoice.csv.
     * @return User debt in euros rounded to two decimal places.
     */
    @Override
    public double getInvoiceForMonth(int i, String string,
            String string1
    ) {
        User user = userController.getUserByUserName(string);
        if (user == null) {
            return 0;
        }
        List<String> list = new LinkedList<>();

        //Vai buscar as viagens sem Invoice
        List<Trip> trip = tripController.getTripsWithoutInvoice(user.getUserName());
        int actualPoints = user.getPoints();
        int previousPoints = 0;
        int earnedPoints = 0;
        int discountedPoints = 0;
        double totalCost = 0;
        String output;

        for (Trip tmp : trip) {
            String startDate = tmp.getStartTime();
            String endDate = tmp.getEndTime();
            int startMonth = Integer.valueOf(startDate.substring(5, 7));
            int endMonth = Integer.valueOf(endDate.substring(5, 7));
            if (startMonth == i && endMonth == i) {
                earnedPoints += tmp.getPoints();
                totalCost += tmp.getCost();
                Park startPark = parkController.getParkById(tmp.getStartParkId());
                Park endPark = parkController.getParkById(tmp.getEndParkId());
                Date startTime = Utils.stringToDate(startDate);
                Date endTime = Utils.stringToDate(endDate);
                output = String.format("%s;%s;%s;%.6f;%.6f;%.6f;%.6f;%d;%.2f", tmp.getVehicleDescription(), startTime.getTime(), endTime.getTime(), startPark.getLatitude(), startPark.getLongitude(), endPark.getLatitude(), endPark.getLongitude(), Utils.getTripDurationInSeconds(tmp.getStartTime(), tmp.getEndTime()), tmp.getCost());
                list.add(output);
            }
        }
        previousPoints = actualPoints - earnedPoints;
        //calcula o desconto
        while (totalCost > 1 && actualPoints >= 10) {
            discountedPoints += 10;
            actualPoints -= 10;
            totalCost -= 1;
        }
        //gera o invoice
        InvoiceLine Invoice = invoiceLineController.addInvoice(string, previousPoints, earnedPoints, discountedPoints, actualPoints, totalCost);

        if (Invoice == null) { //se nao criou invoice acaba
            return 0;
        }

        //adiciona  points ao user
        user.setPoints(actualPoints);
        userController.updateUser(user);

        //actualiza as viagens com ivoice id
        for (Trip tmp : trip) {
            String startDate = tmp.getStartTime();
            String endDate = tmp.getStartTime();
            int startMonth = Integer.valueOf(startDate.substring(5, 7));
            int endMonth = Integer.valueOf(endDate.substring(5, 7));
            if (startMonth == i && endMonth == i) {
                tmp.setInvoiceId(Invoice.getInvoiceLineId());
                tripController.updateTrip(tmp);
            }
        }
        files.writeInvoice(string1, user.getUserName(), actualPoints, previousPoints, earnedPoints, discountedPoints, totalCost, list);
        return totalCost;
    }
}
