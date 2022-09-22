/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lapr2.project.agpsd.utils.Utils;

/**
 *
 *
 */
public class FirstComeFirstServed {

    /**
     * Empty constructor
     */
    public FirstComeFirstServed() {
    }
    
    /**
     * Receives a list of Service Requests and a list of Service Providers and 
     * pairs the requests with providers, returning the result in the form a 
     * list of Strings, where each String has the information for one pairing. 
     * The older service requests are the first ones to enter the procedure of 
     * finding a pairing
     * 
     * @param lstRequests list of ServiceRequest
     * @param lstProviders list of ServiceProvider
     * @return List of Strings with each String in the form "requestNumber;serviceId;providerNumber;proposedDate;proposedTime"
     */
    public List<String> getProviderPairings(List<ServiceRequest> lstRequests, List<ServiceProvider> lstProviders) {
        
        List<String> result = new ArrayList<>();
        
        lstRequests.sort(byDateOfRequest);
        for (ServiceRequest request : lstRequests) {
            List<ServiceProvider> lstProvidersFiltered = getProvidersWithinGeoArea(request, lstProviders);
            List<RequestedServiceDescription> lstServDescr = request.getServiceDescriptions();
            List<SchedulePreference> schePrefs = request.getSchedules();
            
            for (int i = 1; i <= schePrefs.size(); i++) {
                SchedulePreference sched = getScheduleWithOrder(i, schePrefs);
                lstProvidersFiltered = getProvidersWithinSchedule(sched.getDate(), sched.getTime(), lstProvidersFiltered);
                
                if (lstProvidersFiltered != null && !(lstProvidersFiltered.isEmpty())) {
                    
                    for (RequestedServiceDescription servDescr : lstServDescr) {
                        List<ServiceProvider> lstProvidersFiltered2 = getProvidersWithinCategory(servDescr, lstProvidersFiltered);
                        lstProvidersFiltered2 = getHighestRankedProviders(lstProvidersFiltered2);

                        if (lstProvidersFiltered2.size() == 1) {
                            ServiceProvider prov = lstProvidersFiltered2.get(0);
                            result = addPairing(result, prov, request, servDescr, sched);

                        } else {
                            lstProvidersFiltered2 = getCloserProviders(request, lstProvidersFiltered2);
                            if (lstProvidersFiltered2.size() == 1) {
                                ServiceProvider prov = lstProvidersFiltered2.get(0);
                                result = addPairing(result, prov, request, servDescr, sched);

                            } else {
                                ServiceProvider prov = getFirstProviderByName(lstProvidersFiltered2);
                                if (prov != null) {
                                    result = addPairing(result, prov, request, servDescr, sched);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns the list of service providers (from the list passed as argument) 
     * which provide services in the same geographical area as the service 
     * request passed as argument
     * 
     * @param request
     * @param lstProviders
     * @return 
     */
    private List<ServiceProvider> getProvidersWithinGeoArea(ServiceRequest request, List<ServiceProvider> lstProviders) {
        
        List<ServiceProvider> newList = new ArrayList<>();
        
        GeographicalArea requestGeoArea = AGPSD.getInstance().getCompany().getGeographicalAreaRegistry().getCloserGeoArea(request.getpAddress().getZipCode().getZipCode());
        for (ServiceProvider provider : lstProviders) {
            List<GeographicalArea> lstGeoAreasProvider = provider.getSpGeoAreaList().getGeographicalAreas();
            for (GeographicalArea geoArea : lstGeoAreasProvider) {
                if (geoArea.equals(requestGeoArea)) {
                    newList.add(provider);
                }
            }
        }
        return newList;
    }
    
    /**
     * Returns the providers from the list that are able to provide services in 
     * that specific date and time
     * 
     * @param date
     * @param time
     * @param lstProviders
     * @return 
     */
    private List<ServiceProvider> getProvidersWithinSchedule(String date, String time, List<ServiceProvider> lstProviders) {
        
        List<ServiceProvider> newList = new ArrayList<>();
        
        for (ServiceProvider provider : lstProviders) {
            for (DailyAvailability avail : provider.getDailyAvailabilityList()) {
                if ((avail.getAvailabilityStatus() == DailyAvailability.AvailabilityStatus.AVAILABLE) &&
                        avail.checkIfPeriodFitsAvailability(date, time)) {
                    newList.add(provider);
                    break;
                }
            }
        }
        return newList;
    }
 /**
  * Method to obtain a specific Schedule Preference from a given order
  * @param order int related to the order of the schedule preference
  * @param lst related to the list of schedule preferences
  * @return object of the SchedulePreference type related to the obtained schedule preference
  */   
    private SchedulePreference getScheduleWithOrder(int order, List<SchedulePreference> lst) {
        for (SchedulePreference sched : lst) {
            if (order == sched.getOrder()) {
                return sched;
            }
        }
        return null;
    }
   /**
    * Method to obtain a list of service providers that perform under the same category descriprion
    * @param servDescr object of the RequestedServiceDescription type related to the service description
    * @param lstProviders related to the list of service providers 
    * @return list of ServiceProvider objects within category
    */ 
    private List<ServiceProvider> getProvidersWithinCategory(RequestedServiceDescription servDescr, List<ServiceProvider> lstProviders) {
        
        List<ServiceProvider> newList = new ArrayList<>();
        
        String categoryId = servDescr.getService().getCategory().getCategoryCode();
        for (ServiceProvider prov : lstProviders) {
            List<Category> lstCat = prov.getSpCatList().getCategories();
            for (Category cat : lstCat) {
                if (categoryId.equalsIgnoreCase(cat.getCategoryCode())) {
                    newList.add(prov);
                    break;
                }
            }
        }
        return newList;
    }
    /**
     * Method to obtain a list of the highest ranked service providers
     * @param lstProviders list of objects of the ServiceProvider type saved in the company
     * @return list of highest ranked providers
     */
    private List<ServiceProvider> getHighestRankedProviders(List<ServiceProvider> lstProviders) {
        
        List<ServiceProvider> newList = new ArrayList<>();
        
        lstProviders.sort(byRating);
        double highestRanking = lstProviders.get(0).getServiceProviderEvaluation().getAverage();
        newList.add(lstProviders.get(0));
        for (int i = 1; i < lstProviders.size(); i++) {
            double ranking = lstProviders.get(i).getServiceProviderEvaluation().getAverage();
            if (Math.abs(highestRanking - ranking) < 0.01) {
                newList.add(lstProviders.get(i));
            } else {
                break;
            }
        }
        return newList;
    }
    /**
     * Method to obtain the closest service providers given a certain service request
     * @param request object of the ServiceRequest type related to the concerned request
     * @param lstProviders related to the list of ServiceProvider objects within the company
     * @return list of ServiceProvider objects with the closest distance to the request's postal address
     */
    private List<ServiceProvider> getCloserProviders(ServiceRequest request, List<ServiceProvider> lstProviders) {
        String requestZipCode = request.getpAddress().getZipCode().getZipCode();
        double shortestDist = Double.POSITIVE_INFINITY;
        List<ServiceProvider> newList = new ArrayList<>();
        
        for (ServiceProvider prov : lstProviders) {
            String provZipCode = prov.getPostalAddress().getZipCode().getZipCode();
            double distance = Utils.calculateDistanceBetweenZipCode(requestZipCode, provZipCode);
            if (Math.abs(distance - shortestDist) < 0.1) {
                newList.add(prov);
            } else if ((shortestDist - distance) > 0.1) {
                newList.clear();
                newList.add(prov);
                shortestDist = distance;
            }
        }
        return newList;
    }
    /**
     * Method to obtain a certain service provider through a provided name
     * @param lstProviders list of objects of the ServiceProvider type containing all the service providers
     * @return object of the ServiceProvider type concerning the found service provider
     */
    private ServiceProvider getFirstProviderByName(List<ServiceProvider> lstProviders) {
        lstProviders.sort(byName);
        return lstProviders.get(0);
    }
   /**
    * Method to eliminate an availability from a certain service provider
    * @param date related to the date of the availability
    * @param time related to the time of the beginning of the availability
    * @param prov object of the ServiceProvider type related to the concerned service provider
    */ 
    private void eraseAvailabilitySlot(String date, String time, ServiceProvider prov) {
        prov.changeAvailabilityStatus(date, time);
    }
    /**
     * Method to pair a certain Service Request Description to a specific service provider
     * @param result list with the details of the pairing
     * @param prov object of the ServiceProvider type related to the service provider
     * @param request object of the ServiceRequest type related to the request to be paired
     * @param servDesc object of the RequestedServiceDescription type related to the description of a certain service request
     * @param sched object of the SchedulePreference type related to the schedule preference to be paired
     * @return 
     */
    private List<String> addPairing(List<String> result, ServiceProvider prov, 
            ServiceRequest request, RequestedServiceDescription servDesc, SchedulePreference sched) {
        
        result.add(String.format("%s;%s;%s;%s;%s", 
                request.getRequestNumber(), 
                servDesc.getService().getId(),
                prov.getRegistryNumber(),
                sched.getDate(),
                sched.getTime() ));
        
        eraseAvailabilitySlot(sched.getDate(),sched.getTime(), prov);
        return result;
    }
 /**
  * Rewrite of the Comparator method in order to compare ServiceProvider objects by rating
  */
    Comparator<ServiceProvider> byRating = new Comparator<ServiceProvider>() {

        @Override
        public int compare(ServiceProvider sp1, ServiceProvider sp2) {
            double rating1 = sp1.getServiceProviderEvaluation().getAverage();
            double rating2 = sp2.getServiceProviderEvaluation().getAverage();

            if (rating1 < rating2) {
                return -1;
            } else if (rating1 > rating2) {
                return 1;
            } else {
                return 0;
            }
        }
    };
     /**
  * Rewrite of the Comparator method in order to compare ServiceProvider objects by name
  */
  
    Comparator<ServiceProvider> byName = new Comparator<ServiceProvider>() {

        @Override
        public int compare(ServiceProvider sp1, ServiceProvider sp2) {
            String name1 = sp1.getFullName();
            String name2 = sp2.getFullName();

            if (name1.compareTo(name2) < 0) {
                return -1;
            } else if (name1.compareTo(name2) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    };
     /**
  * Rewrite of the Comparator method in order to compare ServiceRequest objects by date of request
  */
    
    Comparator<ServiceRequest> byDateOfRequest = new Comparator<ServiceRequest>() {

        @Override
        public int compare(ServiceRequest sr1, ServiceRequest sr2) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date1 = LocalDate.parse(sr1.getDate(), df);
            LocalDate date2 = LocalDate.parse(sr2.getDate(), df);

            if (date1.isBefore(date2)) {
                return -1;
            } else if (date1.isAfter(date2)) {
                return 1;
            } else {
                return 0;
            }
        }
    };
    
}
