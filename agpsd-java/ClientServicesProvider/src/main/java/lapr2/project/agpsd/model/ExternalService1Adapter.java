/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class ExternalService1Adapter implements ExternalService
{
    
    private ExternalService1 m_oExtServ1;
    
    public ExternalService1Adapter() 
    {
        this.m_oExtServ1 = new ExternalService1();
//        System.out.println("External Service creaated");
    }

    @Override
    public List<ActsOn> getActivity(ZipCode oZipCode, double dRadius) 
    {
        String strZipCode = oZipCode.getZipCode();
//        System.out.println("I'm in");
        List<String> lstString = m_oExtServ1.getActivity(strZipCode, dRadius);
//        if (lstString != null) {
//            System.out.println("List of strings created");
//        } else {
//            System.out.println("List of strings is null!!");
//        }
        return getListActsOn(lstString);
    }
    
    private List<ActsOn> getListActsOn(List<String> lstString) 
    {
        List<ActsOn> lstActsOn = new ArrayList<>();
        
        if (lstString == null || lstString.isEmpty())
            return null;
        
//        System.out.println("Let's do it!");
        for (String str : lstString) 
        {
            try {
                String[] data = str.split(";");
                if (data.length == 2) 
                {
                    ZipCode oZipCode = new ZipCode(data[0].trim());
                    lstActsOn.add(new ActsOn(oZipCode, Double.parseDouble(data[1].trim())));
                }
            } catch (IllegalArgumentException iae) {
                
            }
        }
        
//        for (ActsOn z : lstActsOn) {
//            System.out.println(z);
//        }
        
        return lstActsOn;
    }
    
}
