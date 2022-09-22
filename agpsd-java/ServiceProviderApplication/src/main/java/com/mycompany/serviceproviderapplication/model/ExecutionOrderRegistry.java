/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serviceproviderapplication.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 *
 */
public class ExecutionOrderRegistry {

    private List<ExecutionOrder> m_oExecOrderList;

    public ExecutionOrderRegistry() {
        this.m_oExecOrderList = new ArrayList<ExecutionOrder>();
    }

    public List<ExecutionOrder> getExecutionOrders() {
        List<ExecutionOrder> newList = new ArrayList<>();
        newList.addAll(m_oExecOrderList);
        return newList;
    }

    public List<ExecutionOrder> getExecutionOrdersByServiceProvider(ServiceProvider sp) {
        List<ExecutionOrder> execOrderList = new ArrayList<>();
        for (ExecutionOrder execOrd : m_oExecOrderList) {
            if (execOrd.getSp().equals(sp)) {
                execOrderList.add(execOrd);
            }
        }
        return execOrderList;
    }

    public List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        for (ExecutionOrder execOrder : m_oExecOrderList) {
            Customer custObj = execOrder.getServiceRequest().getCustomer();
            addIfNewCustomer(customerList, custObj);
        }
        return customerList;
    }

    public void addIfNewCustomer(List<Customer> customerList, Customer customer) {
        boolean doesNotHave = true;
        for (Customer customerObj : customerList) {
            if (customerObj.equals(customer)) {
                doesNotHave = false;
            }
        }
        if (doesNotHave) {
            customerList.add(customer);
        }
    }

    public List<ServiceRequest> getOrderedServiceRequestListByCustomerID(String customerID) {

        List<ServiceRequest> orderedServiceList = new ArrayList<>();
        if (!m_oExecOrderList.isEmpty()) {
            for (ExecutionOrder execOrder : m_oExecOrderList) {
                if (execOrder.getServiceRequest().getCustomer().getName().equals(customerID)) {
                    orderedServiceList.add(execOrder.getServiceRequest());
                }

            }
        }
        return validate(orderedServiceList);
    }

    private List<ServiceRequest> validate(List<ServiceRequest> orderedServiceList) {
        if (!orderedServiceList.isEmpty()) {
            Collections.sort(orderedServiceList, new ComparatorDate());
            return orderedServiceList;
        }
        return null;
    }

    public List<ExecutionOrder> sortExecutionOrders(DescribingField criteria) {
        List<ExecutionOrder> newList = getExecutionOrders();

        switch (criteria) {
            case CUSTOMER_NAME:
                newList.sort(byCustomerName);
                return newList;
            case DISTANCE_TO_CUSTOMER:
                newList.sort(byDistanceToClient);
                return newList;
            case SERVICE_CATEGORY:
                newList.sort(byServiceCategory);
                return newList;
            case SERVICE_START_DATE_AND_TIME:
                newList.sort(byServiceStartDateAndTime);
                return newList;
            case SERVICE_TYPE:
                newList.sort(byTypeOfService);
                return newList;
            case CUSTOMER_ADDRESS:
                newList.sort(byCustomerAddress);
                return newList;
            default:
                return null;
        }
    }

    Comparator<ExecutionOrder> byCustomerName = new Comparator<ExecutionOrder>() {

        @Override
        public int compare(ExecutionOrder eo1, ExecutionOrder eo2) {
            String name1 = eo1.getServiceRequest().getCustomer().getName();
            String name2 = eo2.getServiceRequest().getCustomer().getName();

            if (name1.compareTo(name2) < 0) {
                return -1;
            } else if (name1.compareTo(name2) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    Comparator<ExecutionOrder> byDistanceToClient = new Comparator<ExecutionOrder>() {

        @Override
        public int compare(ExecutionOrder eo1, ExecutionOrder eo2) {
            double distance1 = eo1.getDistanceToClient();
            double distance2 = eo2.getDistanceToClient();

            if (distance1 < distance2) {
                return -1;
            } else if (distance1 > distance2) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    Comparator<ExecutionOrder> byServiceCategory = new Comparator<ExecutionOrder>() {

        @Override
        public int compare(ExecutionOrder eo1, ExecutionOrder eo2) {
            String catDescription1 = eo1.getService().getCat().getDescription();
            String catDescription2 = eo2.getService().getCat().getDescription();

            if (catDescription1.compareTo(catDescription2) < 0) {
                return -1;
            } else if (catDescription1.compareTo(catDescription2) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    Comparator<ExecutionOrder> byServiceStartDateAndTime = new Comparator<ExecutionOrder>() {

        @Override
        public int compare(ExecutionOrder eo1, ExecutionOrder eo2) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.parse(eo1.getServiceRequest().getServDescriptionByServId(eo1.getService().getId()).getProviderPairing().getDate(), df);
            LocalDate date2 = LocalDate.parse(eo2.getServiceRequest().getServDescriptionByServId(eo1.getService().getId()).getProviderPairing().getDate(), df);

            DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time1 = LocalTime.parse(eo1.getServiceRequest().getServDescriptionByServId(eo1.getService().getId()).getProviderPairing().getTime(), tf);
            LocalTime time2 = LocalTime.parse(eo2.getServiceRequest().getServDescriptionByServId(eo1.getService().getId()).getProviderPairing().getTime(), tf);

            if (date1.isBefore(date2)) {
                return -1;
            } else if (date1.isAfter(date2)) {
                return 1;
            } else {
                if (time1.isBefore(time2)) {
                    return -1;
                } else if (time1.isAfter(time2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    };

    Comparator<ExecutionOrder> byTypeOfService = new Comparator<ExecutionOrder>() {

        @Override
        public int compare(ExecutionOrder eo1, ExecutionOrder eo2) {
            String serviceType1 = eo1.getService().getServiceType().getDesignation();
            String serviceType2 = eo2.getService().getServiceType().getDesignation();

            if (serviceType1.compareTo(serviceType2) < 0) {
                return -1;
            } else if (serviceType1.compareTo(serviceType2) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    Comparator<ExecutionOrder> byCustomerAddress = new Comparator<ExecutionOrder>() {

        @Override
        public int compare(ExecutionOrder eo1, ExecutionOrder eo2) {
            String address1 = eo1.getServiceRequest().getPostalAddress().getLocal();
            String address2 = eo2.getServiceRequest().getPostalAddress().getLocal();

            if (address1.compareTo(address2) < 0) {
                return -1;
            } else if (address1.compareTo(address2) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    };
}
