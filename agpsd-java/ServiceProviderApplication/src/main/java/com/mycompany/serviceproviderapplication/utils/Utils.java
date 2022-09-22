/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class Utils
{
    
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
    
    static public String readLineFromConsole(String strPrompt)
    {
        try
        {
            System.out.println("\n" + strPrompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    static public int readIntegerFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strInt = readLineFromConsole(strPrompt);

                int iValor = Integer.parseInt(strInt);

                return iValor;
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }
    
    static public double readDoubleFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strDouble = readLineFromConsole(strPrompt);

                double dValor = Double.parseDouble(strDouble);

                return dValor;
            } catch (NumberFormatException ex)
            {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String strPrompt)
    {
        do
        {
            try
            {
                String strDate = readLineFromConsole(strPrompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex)
            {
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
    static public Object showAndSelect(List list,String sHeader)
    {
        Utils.selectObject(list,sHeader);
        return selectObject(list);
    }
    static public int showAndSelectIndex(List list,String sHeader)
    {
        Utils.selectObject(list,sHeader);
        return selectIndex(list);
    }
    static public void selectObject(List list,String sHeader)
    {
        System.out.println(sHeader);

        int index = 0;
        for (Object o : list)
        {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        System.out.println("");
        System.out.println("0 - Cancelar");
    }
    
    static public Object selectObject(List list)
    {
        String opcao;
        int nOpcao;
        do
        {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > list.size());

        if (nOpcao == 0)
        {
            return null;
        } else
        {
            return list.get(nOpcao - 1);
        }
    }
    
    static public int selectIndex(List list)
    {
        String opcao;
        int nOpcao;
        do
        {
            opcao = Utils.readLineFromConsole("Introduza opção: ");
            nOpcao = new Integer(opcao);
        } while (nOpcao < 0 || nOpcao > list.size());

        return nOpcao - 1;
    }
}