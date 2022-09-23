package eapli.shopfloor.general.application;

/**
 * Classe com métodos de auxílio a exportação e importação de ficheiros para a pasta correta
 *
 *João Pimentel 
 */
public class ListRightPathService {

    public static String getRightPathExportedFiles() {
        return (System.getProperty("user.dir").contains("ShopFloor_g35")) ?
                "./exported_files/" : "./ShopFloor_g35/exported_files/";
    }

    public static String getRightPathConfigFiles() {
        return (System.getProperty("user.dir").contains("ShopFloor_g35")) ?
                "./maquina_config_files/" : "./ShopFloor_g35/maquina_config_files/";
    }
}
