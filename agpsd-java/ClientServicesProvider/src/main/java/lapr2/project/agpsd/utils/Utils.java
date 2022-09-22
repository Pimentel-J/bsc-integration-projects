package lapr2.project.agpsd.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 *
 */
public class Utils {

    public static List<String[]> loadFile(String fileName) throws FileNotFoundException {
        List<String[]> listVectors = new ArrayList<>();
        Scanner read = new Scanner(new File(fileName));
        while (read.hasNextLine()) {
            String linha = read.nextLine();
            if (!linha.trim().equals("")) {
                listVectors.add(linha.trim().split(";"));
            }
        }
        read.close();
        return listVectors;
    }

    // evita fechar o ficheiro demasiado cedo por encontrar um carater estranho (ex: ç, é)
    public static List<String[]> readFileWithBuffer(String strPrompt) {
        List<String[]> listVectors = new ArrayList<>();

        BufferedReader in = null;
        try {
            FileReader converter = new FileReader(strPrompt);
            in = new BufferedReader(converter);
            String line;
            while ((line = in.readLine()) != null) {
                if (!line.trim().equals("")) {
                    listVectors.add(line.trim().split(";"));
                }
            }
            return listVectors;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMenuPathForRole(String userRole) {
        switch (userRole) {
            case Constants.ROLE_ADMINISTRATOR:
                return Constants.ADMINISTRATOR_MENU;
            case Constants.ROLE_CLIENT:
                return Constants.CUSTOMER_MENU;
            case Constants.ROLE_HRO:
                return Constants.HRO_MENU;
            case Constants.ROLE_SERVICE_PROVIDER:
                return Constants.SERVICE_PROVIDER_MENU;
            default:
                break;
        }
        return null;
    }

    public static String getControllerPathForRole(String userRole) {
        switch (userRole) {
            case Constants.ROLE_ADMINISTRATOR:
                return Constants.ADMINISTRATOR_CONTROLLER;
            case Constants.ROLE_CLIENT:
                return Constants.CUSTOMER_CONTROLLER;
            case Constants.ROLE_HRO:
                return Constants.HRO_CONTROLLER;
            case Constants.ROLE_SERVICE_PROVIDER:
                return Constants.SERVICE_PROVIDER_CONTROLLER;
            default:
                break;
        }
        return null;
    }

    static public String readLineFromConsole(String strPrompt) {
        try {
            System.out.println("\n" + strPrompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String strPrompt) {
        do {
            try {
                String strInt = readLineFromConsole(strPrompt);

                int iValor = Integer.parseInt(strInt);

                return iValor;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String strPrompt) {
        do {
            try {
                String strDouble = readLineFromConsole(strPrompt);

                double dValor = Double.parseDouble(strDouble);

                return dValor;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String strPrompt) {
        do {
            try {
                String strDate = readLineFromConsole(strPrompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public boolean confirm(String sMessage) {
        String strConfirma;
        do {
            strConfirma = Utils.readLineFromConsole("\n" + sMessage + "\n");
        } while (!strConfirma.equalsIgnoreCase("s") && !strConfirma.equalsIgnoreCase("n"));

        return strConfirma.equalsIgnoreCase("s");
    }

    static public Object showAndSelect(List list, String sHeader) {
        Utils.selectObject(list, sHeader);
        return selectObject(list);
    }

    static public int showAndSelectIndex(List list, String sHeader) {
        Utils.selectObject(list, sHeader);
        return selectIndex(list);
    }

    static public void selectObject(List list, String sHeader) {
        System.out.println(sHeader);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println("");
        System.out.println("0 - Cancelar");
    }

    static public Object selectObject(List list) {
        String opcao;
        int nOpcao;
        do {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > list.size());

        if (nOpcao == 0) {
            return null;
        } else {
            return list.get(nOpcao - 1);
        }
    }

    static public int selectIndex(List list) {
        String opcao;
        int nOpcao;
        do {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > list.size());

        return nOpcao - 1;
    }

    /**
     * Calculates the distance between 2 points from the earth surface
     *
     * @param lat1 latitude from the 1st point
     * @param lon1 longitude from the 1st point
     * @param lat2 latitude from the 2nd point
     * @param lon2 longitude from the 2nd point
     * @return
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // shortest distance over the earth’s surface
        // https://www.movable-type.co.uk/scripts/latlong.html
        final double R = 6371e3;
        double theta1 = Math.toRadians(lat1);
        double theta2 = Math.toRadians(lat2);
        double deltaTheta = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaTheta / 2) * Math.sin(deltaTheta / 2)
                + Math.cos(theta1) * Math.cos(theta2)
                * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c * 0.001; // distância em km
        return d;
    }

    /**
     * Checks if a date & time are in between a period
     *
     * @param date
     * @param time
     * @param startDate start date of period
     * @param startTime start time of period
     * @param endDate end date of period
     * @param endTime end time of period
     * @return
     */
    public static boolean checkIfInstanceFitsPeriod(String date, String time,
            String startDate, String startTime, String endDate, String endTime) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate start_date = LocalDate.parse(startDate, df);
            LocalDate end_date = LocalDate.parse(endDate, df);
            LocalDate dateToCheck = LocalDate.parse(date, df);

            DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime start_time = LocalTime.parse(startTime, tf);
            LocalTime end_time = LocalTime.parse(endTime, tf);
            LocalTime timeToCheck = LocalTime.parse(time, tf);

            if (dateToCheck.isAfter(start_date) && dateToCheck.isBefore(end_date)) {
                return true;
            }

            if (dateToCheck.isAfter(start_date) && dateToCheck.isEqual(end_date) && timeToCheck.isBefore(end_time)) {
                return true;
            }

            if (dateToCheck.isEqual(start_date) && dateToCheck.isBefore(end_date) && timeToCheck.isAfter(start_time)) {
                return true;
            }

            return (dateToCheck.isEqual(start_date) && dateToCheck.isEqual(end_date)
                    && timeToCheck.isAfter(start_time) && timeToCheck.isBefore(end_time));

        } catch (DateTimeParseException exd) {
            return false;
        }
    }

    public static double[] getLatAndLongOfZipCode(String strZipCode, List<String[]> fileContent) {
        double[] latAndLong = new double[2];

        try {
            for (String[] line : fileContent) {
                if (line.length == 15) {
                    String strZip = line[7].trim() + "-" + validateSecondHalfZipCode(line[8].trim());
                    if (strZip.equals(strZipCode)) {
                        latAndLong[0] = Double.parseDouble(line[10].trim().replaceAll(",", "."));
                        latAndLong[1] = Double.parseDouble(line[11].trim().replaceAll(",", "."));
                        return latAndLong;
                    }
                }
            }
            return null;

        } catch (IllegalArgumentException iae) {
            return null;
        }
    }
    
    private static String validateSecondHalfZipCode(String zipCode) {
        if (zipCode.length() == 1) {
            return "00" + zipCode;
        } else if (zipCode.length() == 2) {
            return "0" + zipCode;
        } else {
            return zipCode;
        }
    }

    public static double calculateDistanceBetweenZipCode(String zipCode1, String zipCode2) {
        List<String[]> fileContent = readFileWithBuffer(Constants.FILE_ZIP_CODES);
        double[] latAndLong1 = getLatAndLongOfZipCode(zipCode1, fileContent);
        double[] latAndLong2 = getLatAndLongOfZipCode(zipCode2, fileContent);
        return calculateDistance(latAndLong1[0], latAndLong1[1], latAndLong2[0], latAndLong2[1]);
    }
    
    public static Alert createAlert(Alert.AlertType alertType, String title, String header,
            String message) {
        Alert alerta = new Alert(alertType);

        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(message);

        return alerta;
    }
}
