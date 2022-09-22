/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 *
 */
public class SchedulePreference {
    
    private int order;
    private String date;
    private String time;

    /**
     *
     * @return order
     */
    public int getOrder() {
        return order;
    }

    /**
     *
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @param order
     * @param date
     * @param time
     */
    public SchedulePreference(int order, String date, String time) {
        this.order = order;
        this.date = date;
        this.time = time;
    }

    /**
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     *
     * @param obj
     * @return true or false
     */
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
        final SchedulePreference other = (SchedulePreference) obj;
        if (this.order != other.order) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return Schedule Preference: order, date, time
     */
    @Override
    public String toString() {
        return "SchedulePreference{" + "order=" + order + ", date=" + date + ", time=" + time + '}';
    }
    
    /**
     *
     * @return boolean for past date or not
     */
    public boolean dateHasPassed() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate prefDate = LocalDate.parse(date, df);
        LocalDate now = LocalDate.now();
        return prefDate.isBefore(now);
    }
    
}
