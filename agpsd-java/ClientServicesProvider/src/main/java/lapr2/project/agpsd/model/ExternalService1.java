/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import lapr2.project.agpsd.utils.Constants;
import lapr2.project.agpsd.utils.Utils;

/**
 *
 *
 */
public class ExternalService1 
{
    
    public ExternalService1() 
    {
    }
    
    /**
     *
     * @param strZipCode
     * @param dRadius
     * @return
     */
    public List<String> getActivity(String strZipCode, double dRadius) 
    {
        if ((strZipCode == null) || (strZipCode.isEmpty()) || (dRadius <= 0))
            return null;
        
//        try 
//        {

        List<String[]> fileContent = Utils.readFileWithBuffer(Constants.FILE_ZIP_CODES);
        if (!validateFileContent(fileContent)) {
//            System.out.println("File not valid");
            return null;
        }    

        double[] latAndLong = Utils.getLatAndLongOfZipCode(strZipCode, fileContent);
        if (latAndLong == null) {
//            System.out.println("Lat and Long not found");
            return null;
        }

        List<String> activity = getActivityList(fileContent, latAndLong[0], latAndLong[1], dRadius);
//        System.out.println("I got the activity list!");
//        for (String str : activity) {
//            System.out.println(str);
//        }
        return activity;
        
//        } 
//        catch (IOException ioe) 
//        {
//            System.out.println("Error");
//            return null;
//        }
    }
    
    private boolean validateFileContent(List<String[]> fileContent) 
    {
        if (fileContent == null || fileContent.isEmpty() || fileContent.size() <= 1 || 
                fileContent.get(0).length != 15)
            return false;
        
        return true;
    }
    
    private List<String> getActivityList(List<String[]> fileContent, double lat1, double longit1, double dRadius) 
    {
        List<String> activity = new ArrayList<>();
        
        for(String[] line : fileContent) 
        {
            try 
            {
                if (line.length == 15) 
                {
                    double lat = Double.parseDouble(line[10].trim().replaceAll(",","."));
                    double longit = Double.parseDouble(line[11].trim().replaceAll(",","."));
                    double distance = Utils.calculateDistance(lat1, longit1, lat, longit);
                    if (distance <= dRadius) 
                    {
                        activity.add(line[7].trim() + "-" + line[8].trim() + ";" + distance);
                    }
                }
                
            } 
            catch(IllegalArgumentException iae) 
            {
            }
        
        }
        
        return activity;
    }
    
}
