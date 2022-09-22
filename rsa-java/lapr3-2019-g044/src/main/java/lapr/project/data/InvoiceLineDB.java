/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lapr.project.model.InvoiceLine;
import oracle.jdbc.OracleTypes;

/**
 *
 *
 */
public class InvoiceLineDB extends DataHandler {

    /**
     * Adds specified InvoiceLine to the table "InvoiceLine".
     *
     * @param invoice invoice specified
     * @return invoice id int
     */
    public Integer addInvoiceLine(InvoiceLine invoice) {
        return addInvoiceLine(invoice.getUsername(),
                invoice.getPreviousPoints(),
                invoice.getEarnedPoints(),
                invoice.getDiscountedPoints(),
                invoice.getActualPoints(),
                invoice.getCost());
    }

    /**
     * Invocation of a "stored procedure".
     * <p>
     * Adds specified InvoiceLine to the table "InvoiceLine".
     *
     * @param username username that invoice refers.
     * @param cost cost of the invoice
     * @param points points generated that month
     * @param discoutedCost cost with discouted points
     * @param paid if invoice is paid
     */
    private Integer addInvoiceLine(String username, int previousPoints, int earnedPoints, int discountedPoints, int actualPoints, double cost) {
        Integer invoiceId = null;

        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call addInvoiceLine(?,?,?,?,?,?) }");
            callStmt.registerOutParameter(1, OracleTypes.NUMBER);
            callStmt.setString(2, username);
            callStmt.setInt(3, previousPoints);
            callStmt.setInt(4, earnedPoints);
            callStmt.setInt(5, discountedPoints);
            callStmt.setInt(6, actualPoints);
            callStmt.setDouble(7, cost);
            callStmt.execute();
            invoiceId = callStmt.getInt(1);
            // 3. Close all connection
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return invoiceId;
    }

    /**
     * Method that querys the InvocieLine table to see if there is a invoice for a with
     * matching username and returns the the one with the highest id representing the lastest one.
     *
     * @param userName username of the Invoice.
     * @return the object of the specified Invoice or null if it doesn't exist.
     */
    public List<InvoiceLine> getInvoicesByUsername(String userName) {
        Statement stm;
        List<InvoiceLine> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoice_Line WHERE Invoice_Line.username = '" + userName + "' ORDER BY Invoice_Line_ID DESC";

        try {
            ResultSet rSet = null;
            stm = getConnection().createStatement();
            // 2. Query the database
            rSet = stm.executeQuery(query);
            // 3. Get the data
            while (rSet.next()) {
                Integer invoiceLineId = rSet.getInt(1);
                String username = rSet.getString(2);
                int previousPoints = rSet.getInt(3);
                int earnedPoints = rSet.getInt(4);
                int discountedPoints = rSet.getInt(5);
                int actualPoints = rSet.getInt(6);
                double cost = rSet.getDouble(7);
                InvoiceLine invoice = new InvoiceLine(invoiceLineId, username, previousPoints, earnedPoints,
                        discountedPoints, actualPoints, cost);
                invoices.add(invoice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return invoices.isEmpty() ? null : invoices;
    }

}
