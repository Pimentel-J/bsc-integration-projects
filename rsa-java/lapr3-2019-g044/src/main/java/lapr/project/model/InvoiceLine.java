/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 * Describes a InvoiceLine.
 */
public class InvoiceLine {

    /**
     * Identifier of the InvoiceLine.
     */
    private Integer invoiceLineId;
    /**
     * username of the user relating to InvoiceLine.
     */
    private String username;

    /**
     * cost of the InvoiceLine.
     */
    private int previousPoints;

    /**
     * points of the InvoiceLine.
     */
    private int earnedPoints;
    /**
     * discoutedCost of the InvoiceLine.
     */
    private int discountedPoints;
    /**
     * Identifier if InvoiceLine is paid.
     */
    private int actualPoints;
    /**
     * Identifier if InvoiceLine is paid.
     */
    private double cost;

    /**
     * Empty constructor.
     */
    public InvoiceLine() {

    }

    /**
     * Full constructor.
     *
     * @param invoiceLineId invoiceLineId that invoice refers.
     * @param username username that invoice refers.
     * @param previousPoints cost that invoice refers.
     * @param earnedPoints points that invoice refers.
     * @param discountedPoints discoutedCost that invoice refers.
     * @param actualPoints discoutedCost that invoice refers.
     * @param cost actual_points that invoice refers.
     */
    public InvoiceLine(int invoiceLineId, String username, int previousPoints, int earnedPoints, int discountedPoints, int actualPoints, double cost) {
        this.invoiceLineId = invoiceLineId;
        this.username = username;
        this.previousPoints = previousPoints;
        this.earnedPoints = earnedPoints;
        this.discountedPoints = discountedPoints;
        this.actualPoints = actualPoints;
        this.cost = cost;
    }

    /**
     * Returns the id of the InvoiceLine.
     *
     * @return invoiceLineId identifier of the InvoiceLine
     */
    public Integer getInvoiceLineId() {
        return invoiceLineId;
    }

    /**
     * Returns the username of the InvoiceLine.
     *
     * @return username username that invoice refers.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the previousPoints of the InvoiceLine.
     *
     * @return previousPoints previousPoints of the invoice
     */
    public int getPreviousPoints() {
        return previousPoints;
    }

    /**
     * Returns the earnedPoints of the Invoice.
     *
     * @return earnedPoints of the InvoiceLine
     */
    public int getEarnedPoints() {
        return earnedPoints;
    }

    /**
     * Returns the actualPoints of the Invoice.
     *
     * @return actualPoints of the InvoiceLine
     */
    public int getDiscountedPoints() {
        return discountedPoints;
    }

    /**
     * Returns the identifier actualPoints of the InvoiceLine.
     *
     * @return actualPoints identifier if it is paid
     */
    public int getActualPoints() {
        return actualPoints;
    }

    /**
     * Returns the identifier cost of the InvoiceLine.
     *
     * @return cost identifier if it is paid
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets invoice id to the value passed as parameter, if valid.
     *
     * @param invoicelineId new identifier of the invoice.
     */
    public void setInvoiceLineId(Integer invoicelineId) {
        this.invoiceLineId = invoicelineId;
    }

    /**
     * Sets invoice username to the value passed as parameter, if valid.
     *
     * @param username new username of the invoice.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets previousPoints to the value passed as parameter, if valid.
     *
     * @param previousPoints new identifier of the invoice.
     */
    public void setPreviousPoints(int previousPoints) {
        this.previousPoints = previousPoints;
    }

    /**
     * Sets earnedPoints to the value passed as parameter, if valid.
     *
     * @param earnedPoints new identifier of the invoice.
     */
    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    /**
     * Sets discountedPoints to the value passed as parameter, if valid.
     *
     * @param discountedPoints new identifier of the invoice.
     */
    public void setDiscountedPoints(int discountedPoints) {
        this.discountedPoints = discountedPoints;
    }

    /**
     * Sets points to the value passed as parameter, if valid.
     *
     * @param actualPoints new identifier of the invoice.
     */
    public void setActualPoints(int actualPoints) {
        this.actualPoints = actualPoints;
    }

    /**
     * Sets invoice cost to the value passed as parameter, if valid.
     *
     * @param cost new cost of the invoice.
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.invoiceLineId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceLine other = (InvoiceLine) obj;
        if (this.invoiceLineId != other.invoiceLineId) {
            return false;
        }
        return true;
    }

    
    /**
     * Method to print InvoiceLine
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Invoice: \n" + "invoice id=" + invoiceLineId + "\n =" + "\n username=" + username +  "\n previousPoints=" + previousPoints + "\n earnedPoints=" + earnedPoints + "\n discountedPoints=" + discountedPoints + "\n actualPoints=" + actualPoints + "\n cost=" +cost +  '.';
    }
}
