/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;


import java.util.List;
import lapr.project.data.InvoiceLineDB;

import lapr.project.model.InvoiceLine;

/**
 *
 *
 */
public class InvoiceLineController {

    private InvoiceLineDB invoiceLineDB;

    TripController tripController = new TripController();

    public InvoiceLineController() {
        invoiceLineDB = new InvoiceLineDB();
    }

    /**
     * Set parkDb to instance.
     *
     * @param invoiceLineDB new instance of POIDB
     */
    public void setInvoiceLineDB(InvoiceLineDB invoiceLineDB) {
        this.invoiceLineDB = invoiceLineDB;
    }

    public InvoiceLine addInvoice(String username, int previousPoints, int earnedPoints, int discountedPoints, int actualPoints, double totalCost) {
        InvoiceLine invoice = new InvoiceLine();
        invoice.setUsername(username);
        invoice.setPreviousPoints(previousPoints);
        invoice.setEarnedPoints(earnedPoints);
        invoice.setDiscountedPoints(discountedPoints);
        invoice.setActualPoints(actualPoints);
        invoice.setCost(totalCost);
        Integer invoiceId = invoiceLineDB.addInvoiceLine(invoice);
        invoice.setInvoiceLineId(invoiceId);
        if (invoiceId != null) {
            return invoice;
        } else {
            return null;
        }
    }

    /**
     * Returns all invoices belonging to a specific user
     *
     * @param username username of the trips
     * @return list of invoices
     */
    public List<InvoiceLine> getInvoicesByUsername(String username) {
        return invoiceLineDB.getInvoicesByUsername(username);
    }
}
