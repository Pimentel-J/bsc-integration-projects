/**
 *
 */
package eapli.shopfloor.core;

/**
 * A "global" static class with the application registry of well known objects
 *
 *Paulo Gandra Sousa
 *
 */
public class Application {

    public static final String VERSION = "5.0.0";
    public static final String COPYRIGHT = "(C) 2016 - 2019, ISEP's Professors of EAPLI";

    private static final AppSettings SETTINGS = new AppSettings();

    public static AppSettings settings() {
        return SETTINGS;
    }

    private Application() {
        // private visibility to ensure singleton & utility
    }
}
