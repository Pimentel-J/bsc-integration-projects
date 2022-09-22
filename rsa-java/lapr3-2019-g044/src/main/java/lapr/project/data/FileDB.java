/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lapr.project.controller.ParkController;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.InvoiceLine;
import lapr.project.model.Park;
import lapr.project.model.Point;
import lapr.project.model.Trip;
import lapr.project.model.Vehicle;
import lapr.project.utils.Utils;

/**
 *
 *
 */
public class FileDB {

    /**
     * Writes a report concerning currently unlocked vehicles and places it in a
     * file in the path passed as argument.
     *
     * @param filePath path for new file
     * @param trips list of the all ongoing trips
     */
    public void writeReportOfUnlockedVehicles(String filePath, List<Trip> trips) {
        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.print("Header%n"); //modify to include proper header
            for (Trip trip : trips) {
                String vehicle = trip.getVehicleDescription();
                String user = trip.getUserName();

                String line = String.format("%s;%s%n", vehicle, user);
                printWriter.print(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a a list of routes to a file in the path passed as argument.
     *
     * @param filePath path for new file
     * @param distances list of total distances of routes (one per route)
     * @param energies list of total energies of routes (one per route)
     * @param elevations list of elevations of routes (one per route)
     * @param routes list of routes to write in file
     */
    public void writePaths(String filePath, List<Integer> distances,
            List<Integer> energies, List<Integer> elevations, List<List<Point>> routes) {

        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            for (int i = 0; i < routes.size(); i++) {
                printWriter.format("Path %d%n", (i+1));
                printWriter.format("total_distance:%d%n", distances.get(i));
                printWriter.format("total_energy:%d%n", energies.get(i));
                printWriter.format("elevation:%d%n", elevations.get(i));

                List<Point> route = routes.get(i);
                for (Point point : route) {
                    printWriter.format("%.6f;%.6f%n", point.getLatitude(), point.getLongitude());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a report for the escooter charging status at a given park and
     * places it in a file in the path passed as argument.
     *
     * @param filePath path for new file
     * @param escooters list of escooters charging at the moment that are not
     * 100% fully charged
     */
    public void writeParkChargingReport(String filePath, List<Escooter> escooters) {
        Iterator<Escooter> escooterItera = escooters.iterator();

        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.format("%s%n", Utils.HEADER_OUTPUT_CHARGING);
            while (escooterItera.hasNext()) {
                Escooter escooter = escooterItera.next();
                printWriter.format("%s;%d;%d%n", escooter.getDescr(),
                        escooter.getActualBattery(), escooter.getTotalChargeTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of parks (and its distances to a given point) to a file in
     * the path passed as argument.
     *
     * @param filePath path for new file
     * @param parks list of parks
     * @param distance list of distances
     */
    public void writeParks(String filePath, List<Park> parks, List<Integer> distance) {

        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.format("%s%n", Utils.HEADER_NEAREST_PARKS);
            int i = 0;
            for (Park park : parks) {
                printWriter.write(park.getLatitude() + ";"
                        + park.getLongitude() + ";"
                        + distance.get(i) + "\n");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of bicycles to a file in the path passed as argument.
     *
     * @param filePath path for new file
     * @param bicycles list of bicycles
     */
    public void writeBicycles(String filePath, List<Bicycle> bicycles) {

        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.format("%s%n", Utils.HEADER_OUTPUT_BICYCLES);
            for (Bicycle bicycle : bicycles) {
                printWriter.format("%s;%d;%n", bicycle.getDescr(), bicycle.getWheelSize());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Writes a list of escooters to a file in the path passed as argument.
     *
     * @param filePath path for new file
     * @param escooters list of escooters
     */
    public void writeEscooters(String filePath, List<Escooter> escooters) {

        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.format("%s%n", Utils.HEADER_OUTPUT_ESCOOTERS);
            for (Escooter escooter : escooters) {
                printWriter.format("%s;%s;%d%n", escooter.getDescr(),
                        escooter.getType().toString(), escooter.getActualBattery());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of trips data for Balace output to a file in the path
     * passed as argument.
     *
     * @param filePath path for new file
     * @param trips list of trips
     */
    public void writeBalance(String filePath, List<Trip> trips) {
        ParkController parkController = new ParkController();
        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.format("%s%n", Utils.HEADER_OUTPUT_BALANCE);
            int i = 0;
            for (Trip tmp : trips) {
                Date startTime = Utils.stringToDate(tmp.getStartTime());
                Date endTime = Utils.stringToDate(tmp.getEndTime());
                Park origPark = parkController.getParkById(tmp.getStartParkId());
                Park destPark = parkController.getParkById(tmp.getEndParkId());
                double tripDuration = Utils.getTripDurationInSeconds(tmp.getStartTime(), tmp.getEndTime());
                printWriter.write(tmp.getVehicleDescription() + ";"
                        + startTime.getTime() + ";"
                        + endTime.getTime() + ";"
                        + origPark.getLatitude() + ";"
                        + origPark.getLongitude() + ";"
                        + destPark.getLatitude() + ";"
                        + destPark.getLongitude() + ";"
                        + (int) tripDuration + ";"
                        + tmp.getCost() + "\n");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of trips data for Points output to a file in the path
     * passed as argument.
     *
     * @param filePath path for new file
     * @param trips list of trips
     */
    public void writePoints(String filePath, List<Trip> trips) {
        ParkController parkController = new ParkController();
        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {
            printWriter.format("%s%n", Utils.HEADER_OUTPUT_POINTS);
            int i = 0;
            for (Trip tmp : trips) {
                Date startTime = Utils.stringToDate(tmp.getStartTime());
                Date endTime = Utils.stringToDate(tmp.getEndTime());
                Park origPark = parkController.getParkById(tmp.getStartParkId());
                Park destPark = parkController.getParkById(tmp.getEndParkId());
                printWriter.write(tmp.getVehicleDescription() + ";"
                        + startTime.getTime() + ";"
                        + endTime.getTime() + ";"
                        + origPark.getLatitude() + ";"
                        + origPark.getLongitude() + ";"
                        + origPark.getElevation() + ";"
                        + destPark.getLatitude() + ";"
                        + destPark.getLongitude() + ";"
                        + destPark.getElevation() + ";"
                        +  Math.abs(origPark.getElevation() - destPark.getElevation()) + ";"
                        + tmp.getPoints() + "\n");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of data about Invoices to a file in the path passed as
     * argument.
     *
     * @param filePath path for new file
     * @param username
     * @param previousPoints
     * @param earnedPoints
     * @param discountedPoints
     * @param actualPoints
     * @param totalCost
     * @param output
     */
    public void writeInvoice(String filePath, String username, int previousPoints, int earnedPoints, int discountedPoints, int actualPoints, double totalCost, List<String> output) {
        try (PrintWriter printWriter = new PrintWriter(filePath, "UTF-8")) {

            printWriter.format("USERNAME %s%n", username);
            printWriter.format("Previous points:%d%n", previousPoints);
            printWriter.format("Earned points:%d%n", earnedPoints);
            printWriter.format("Discounted points:%d%n", discountedPoints);
            printWriter.format("Actual points:%d%n", actualPoints);
            printWriter.format("Charged Value:%.2f%n", totalCost);
            printWriter.format("%s%n", Utils.HEADER_OUTPUT_BALANCE);
            int i = 0;
            for (String tmp : output) {

                printWriter.write(tmp + "\n");
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
