package lapr.project.ui;

import lapr.project.data.DataHandler;
import lapr.project.model.CalculatorExample;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.assessment.TestScenario;
import lapr.project.controller.*;
import lapr.project.data.*;
import lapr.project.model.*;
import lapr.project.utils.Utils;

/**
 *
 */
class Main {

    /**
     * Logger class.
     */
    private static final Logger LOGGER = Logger.getLogger("MainLog");

    /**
     * Private constructor to hide implicit public one.
     */
    private Main() {

    }

    /**
     * Application main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {
        CalculatorExample calculatorExample = new CalculatorExample();
        int value = calculatorExample.sum(3, 5);

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, String.valueOf(value));
        }

        //load database properties
        try {
            Properties properties
                    = new Properties(System.getProperties());
            InputStream input = new FileInputStream("target/classes/application.properties");
            properties.load(input);
            input.close();
            System.setProperties(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //faz drop das tables , cria tables e funcoes 
        DataHandler dataHandler = new DataHandler();
        dataHandler.scriptRunner("target/test-classes/dropTable.sql");
        dataHandler.scriptRunner("target/test-classes/creat.sql");
        dataHandler.scriptRunner("target/test-classes/function.sql");
        dataHandler.scriptRunner("target/test-classes/triggers.sql");

        //TESTES PARA VEÍCULOS
        AdminController admin = new AdminController();

        //insert park
        admin.addPark("2", 2, -2, 200, "Lisboa", 10, 12, 220, 16);

        //tests bicycle methods
        admin.addBicycle("PT050", 15, 2, -2, 1.2f, 0.5f, 16);
        admin.addBicycle("PT051", 12, 2, -2, 2.2f, 0.7f, 18);
        admin.addBicycle("PT053", 17, 2, -2, 1.7f, 0.9f, 17);
        //attempt to insert a bicycle into a non-existent park
        admin.addBicycle("PT052", 12, 4, -4, 2.2f, 0.7f, 26);
        //attempt to insert a bicycle with a description that already exists
        admin.addBicycle("PT050", 10, 2, -2, 1.5f, 0.5f, 17);

        //tests escooter methods
        admin.addEscooter("ePT050", 25, Escooter.Type.CITY, 2, -2, 0.35f, 100, 1.10f, 0.3f, 250);
        admin.addEscooter("ePT051", 22, Escooter.Type.OFFROAD, 2, -2, 0.95f, 50, 2.20f, 0.7f, 220);
        admin.addEscooter("ePT053", 17, Escooter.Type.OFFROAD, 2, -2, 0.37f, 70, 3.70f, 0.77f, 270);
        //attempt to insert a escooter into a non-existent park
        admin.addEscooter("ePT052", 22, Escooter.Type.OFFROAD, 3, -3, 0.22f, 75, 2.20f, 0.7f, 170);
        //attempt to insert a escooter with a description that already exists
        admin.addEscooter("ePT050", 30, Escooter.Type.CITY, 2, -2, 0.44f, 25, 1.3f, 0.5f, 190);

        //update an escooter
        VehicleController veiControll = new VehicleController();
        veiControll.updateVehicle("ePT050", 1, 1, 1, 1, 1, Escooter.Type.OFFROAD, 0.11f, 50, Utils.NO_WHEEL_SIZE, 150);
        //update an bicycle
        veiControll.updateVehicle("PT050", 1, 1, 1, 1, 1, Utils.NO_TYPE, Utils.NO_MAX_BATTERY, Utils.NO_ACT_BATTERY, 21, Utils.NO_MOTOR);

        //delete a bicycle
        admin.deleteBicycle("PT053");
        //delete an escooter
        admin.deleteEscooter("ePT053");

        admin.addBicycle("PT055", 15, 2, -2, 1.2f, 0.5f, 16);
        veiControll.updateVehicle("PT055", 15, 0, 0, 1.2f, 0.5f, Utils.NO_TYPE,
                Utils.NO_MAX_BATTERY, Utils.NO_ACT_BATTERY, 21, Utils.NO_MOTOR);
        admin.addEscooter("ePT055", 25, Escooter.Type.CITY, 2, -2, 0.35f, 100, 1.10f, 0.3f, 250);
        veiControll.updateVehicle("ePT055", 25, 0, 0, 1.10f, 0.3f, Escooter.Type.CITY,
                0.35f, 50, Utils.NO_WHEEL_SIZE, 250);

        //delete an unlocked bicycle
        admin.deleteBicycle("PT055");
        //delete an unlocked escooter
        admin.deleteEscooter("ePT055");

        //tests addBicycles method - all bikes ok
        admin.addBicycles(Arrays.asList("PT101", "PT102", "PT103"), Arrays.asList(15, 12, 17),
                Arrays.asList(2.0, 2.0, 2.0), Arrays.asList(-2.0, -2.0, -2.0),
                Arrays.asList(1.2f, 2.2f, 1.7f), Arrays.asList(0.5f, 0.7f, 0.9f),
                Arrays.asList(16, 18, 17));
        //attempt to insert a bicycle into a non-existent park
        admin.addBicycles(Arrays.asList("PT121"), Arrays.asList(12), Arrays.asList(4.0),
                Arrays.asList(-4.0), Arrays.asList(2.2f), Arrays.asList(0.7f), Arrays.asList(26));
        //attempt to insert a bicycle with a description that already exists
        admin.addBicycles(Arrays.asList("PT050"), Arrays.asList(10), Arrays.asList(2.0),
                Arrays.asList(-2.0), Arrays.asList(1.5f), Arrays.asList(0.5f), Arrays.asList(10));
        //tests if rollback works (1st bike - ok / 2nd bike - nok [same descr])
        admin.addBicycles(Arrays.asList("PT121", "PT121", "PT122"), Arrays.asList(15, 12, 17),
                Arrays.asList(2.0, 2.0, 11.0), Arrays.asList(-2.0, -2.0, -11.0),
                Arrays.asList(1.2f, 2.2f, 1.7f), Arrays.asList(0.5f, 0.7f, 0.9f),
                Arrays.asList(16, 18, 17));
        //tests if rollback works (1st bike - ok / 2nd bike - nok [no park] / 3rd bike - ok)
        admin.addBicycles(Arrays.asList("PT125", "PT127", "PT126"), Arrays.asList(15, 12, 17),
                Arrays.asList(2.0, 21.0, 2.0), Arrays.asList(-2.0, -21.0, -2.0),
                Arrays.asList(1.2f, 2.2f, 1.7f), Arrays.asList(0.5f, 0.7f, 0.9f),
                Arrays.asList(16, 18, 17));

        //tests addBicycles method - all escooters ok
        admin.addEscooters(Arrays.asList("ePT201", "ePT212", "ePT200"), Arrays.asList(25, 22, 20),
                Arrays.asList(Escooter.Type.CITY, Escooter.Type.OFFROAD, Escooter.Type.OFFROAD), Arrays.asList(2.0, 2.0, 2.0),
                Arrays.asList(-2.0, -2.0, -2.0), Arrays.asList(0.35f, 0.75f, 0.75f), Arrays.asList(100, 60, 60),
                Arrays.asList(1.10f, 2.20f, 2.20f), Arrays.asList(0.3f, 0.7f, 0.7f), Arrays.asList(250f, 220f, 210f));

        //test getParkChargingReport
        List<Escooter> scooters = admin.getParkChargingReport("2");
        System.out.println("Time charge size: " + scooters.size());
        System.out.println(scooters.toString());
        System.out.println("Escooter1: " + scooters.get(0).getDescr() + " - " + scooters.get(0).getTotalChargeTime());
        System.out.println("Escooter2: " + scooters.get(1).getDescr() + " - " + scooters.get(1).getTotalChargeTime());
        System.out.println("Escooter2: " + scooters.get(2).getDescr() + " - " + scooters.get(2).getTotalChargeTime());

        // Test1 end
        System.out.printf("\n\n%s\n\n", "--------------------------");
        TestScenario test = new TestScenario();
        System.out.printf("Inserting Parks...\n\n");
        test.addParks("input/scenario1/parks.csv");
        System.out.printf("Inserting POIs...\n\n");
        test.addPOIs("input/scenario1/pois.csv");

        System.out.printf("%s\n\n", "Linear distances");
        System.out.println("Clérigos - Majestic: exp=640m res=" + test.linearDistanceTo(41.14582, -8.61398, 41.14723, -8.60657) + "m\n"
                + "Clérigos - Bolhão: exp=634m res=" + test.linearDistanceTo(41.14582, -8.61398, 41.14871, -8.60746) + "m\n"
                + "Bolhão - Cais da Ribeira: exp=951m res=" + test.linearDistanceTo(41.14871, -8.60746, 41.14063, -8.61118) + "m\n"
                + "Majestic - Cais da Ribeira: exp=829m res=" + test.linearDistanceTo(41.14723, -8.60657, 41.14063, -8.61118) + "m\n"
                + "Clérigos - Majestic - Cais da Ribeira: exp=1469m res=" + (test.linearDistanceTo(41.14582, -8.61398, 41.14723, -8.60657) + test.linearDistanceTo(41.14723, -8.60657, 41.14063, -8.61118)) + "m\n"
                + "Clérigos - Bolhão - Cais da Ribeira: exp=1585m res=" + (test.linearDistanceTo(41.14582, -8.61398, 41.14871, -8.60746) + test.linearDistanceTo(41.14871, -8.60746, 41.14063, -8.61118)) + "m\n");

        System.out.printf("\n\nInserting Paths...\n\n");
        test.addPaths("input/scenario1/paths.csv");

        List<POI> pois = new POIDB().getAllPois();
        List<Park> parks = new ParkDB().getAllParks();
        List<Path> paths = new PathDB().getAllPaths();

        System.out.println("");
        System.out.println("POIs");
        for (POI poi : pois) {
            System.out.println(poi.getDescription());
        }

        System.out.println("");
        System.out.println("Parks");
        for (Park park : parks) {
            System.out.println(park.getDescription());
        }

        System.out.println("");
        System.out.println("Paths");
        for (Path path : paths) {
            System.out.println(path.getLatA());
        }

        System.out.printf("\n\nCalculating shortest route between Trindade and Cais da Ribeira...\n\n");
        test.shortestRouteBetweenTwoParks("Trindade", "Cais da Ribeira", 0, "output/paths.csv");

        // Test2 end
        
   //TESTES PARA VIAGENS
        UserController userController = new UserController();
        AdminController adminController = new AdminController();
        ParkController parkController = new ParkController();
        TripController tripController = new TripController();
        VehicleController vehicleController = new VehicleController();
        //adiciona um user
        userController.newUser("joao", "123456@isep.ipp.pt", "1234545", "123124124", 180, 90, 5.5, "Masculino");
        // cria objeto user pela BD
        User joao = userController.getUserByUserName("joao");
        //adiciona parques
        adminController.addPark("20", 10, -10, 100, "porto", 10, 12, 16, 2);
        adminController.addPark("21", 20, -20, 200, "Algarve", 10, 12, 16, 2);
        // cria objetos parques atraves da DB
        Park p1 = parkController.getParkById("20");
        Park p2 = parkController.getParkById("21");
        //cria uma scooter
        adminController.addEscooter("ePT070", 25, Escooter.Type.CITY, 10, -10, 9.35f, 100, 1.10f, 0.3f, 250);

        // desbloquear viaturas e criar viagens
        Trip t3 = tripController.unlockEscooter("ePT070", "joao", 10, -10);
        Escooter e1 = vehicleController.getEscooterByDesc("ePT070");
        Trip t4 = tripController.lockEscooter(e1, p2, joao);
        Trip t5 = tripController.unlockEscooter("ePT070", "joao", 20, -20);
        Escooter e2 = vehicleController.getEscooterByDesc("ePT070");
        Trip t6 = tripController.lockEscooter(e2, p1, joao);
        Trip t7 = tripController.unlockEscooter("ePT070", "joao", 10, -10);
        Escooter e3 = vehicleController.getEscooterByDesc("ePT070");
        Trip t8 = tripController.lockEscooter(e3, p2, joao);

        //locking e unlocking history
        tripController.getLockingHistory("joao");
        /////scenario 1
        TestScenario testeScenario = new TestScenario();
        //adiciona users de file
        testeScenario.addUsers("input/user/users.csv");
        //gera invoice
        System.out.println(testeScenario.getInvoiceForMonth(1, "joao", "output/invoice.csv"));
        // User Points
        System.out.println(testeScenario.getUserCurrentPoints("joao", "output/points.csv"));
        // Current debt
        System.out.println(testeScenario.getUserCurrentDebt("joao", "output/debt.csv"));
        //Number of Escooters at a park
        System.out.println(testeScenario.getNumberOfEScootersAtPark("21", "output/numberscooters.csv"));
        //sugest one escooter
        System.out.println(testeScenario.suggestEscootersToGoFromOneParkToAnother("21", "joao", 20, -20, "output/sugestscooter.csv"));

        //scenario 2
        //adiciona um user
        userController.newUser("joao", "123456@isep.ipp.pt", "1234545", "123124124", 180, 90, 5.5, "Masculino");

        // cria objeto user pela BD
        //User joao = userController.getUserByUserName("joao");

        // desbloquear viaturas e criar viagens
        Trip t3a = tripController.unlockEscooter("ePT070", "joao", 10, -10);
        Escooter e1a = vehicleController.getEscooterByDesc("ePT070");
        Trip t4a = tripController.lockEscooter(e1, p2, joao);

        TestScenario testeScenario2 = new TestScenario();
        //unlock any with destination
        System.out.println(testeScenario2.unlockAnyEscooterAtParkForDestination("21", "joao", 20, -20, "output/unlockwithDest.csv"));

        //scenario 3

        //adiciona um user
        userController.newUser("joao", "123456@isep.ipp.pt", "1234545", "123124124", 180, 90, 5.5, "Masculino");

        // cria objeto user pela BD
        //User joao = userController.getUserByUserName("joao");

        //cria uma scooter
        adminController.addEscooter("ePT070", 25, Escooter.Type.CITY, 10, -10, 9.35f, 100, 1.10f, 0.3f, 250);

        // desbloquear viaturas e criar viagens
        Trip t3b = tripController.unlockEscooter("ePT070", "joao", 10, -10);
        Escooter e1b = vehicleController.getEscooterByDesc("ePT070");
        Trip t4b = tripController.lockEscooter(e1, p2, joao);

        TestScenario testeScenario3 = new TestScenario();
        //unlock any with destination
        System.out.println(testeScenario3.unlockAnyEscooterAtPark("21", "joao", "output/unlockAny.csv"));
 
    }
}
