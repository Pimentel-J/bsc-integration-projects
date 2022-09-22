/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lapr2.project.agpsd.model.DailyAvailability.AvailabilityStatus;
import lapr2.project.agpsd.utils.Utils;

/**
 *
 *
 */
public class RandomScheduling {

    public RandomScheduling() {
    }
    
    /*Para cada pedido:
            Identifica prestadores com aquela área geográfica e com aquela Disponibilidade
            Para cada Descrição Serviço:
                Identifica prestadores naquela Categoria
                Ordena prestadores por ranking
                Se >1=, ordena esses por distância
                Se >1=, ordena esses por ordem alfabética
                Guarda esse prestador e apaga o slot de Disponibilidade correspondente
        */
    
    public List<String> getProviderPairings(List<ServiceRequest> lstRequests, List<ServiceProvider> lstProviders) {
        
        List<String> result = new ArrayList<>();
        
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
    
    private List<ServiceProvider> getProvidersWithinSchedule(String date, String time, List<ServiceProvider> lstProviders) {
        
        List<ServiceProvider> newList = new ArrayList<>();
        
        for (ServiceProvider provider : lstProviders) {
            for (DailyAvailability avail : provider.getDailyAvailabilityList()) {
                if ((avail.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE) &&
                        avail.checkIfPeriodFitsAvailability(date, time)) {
                    newList.add(provider);
                    break;
                }
            }
        }
        return newList;
    }
    
    private SchedulePreference getScheduleWithOrder(int order, List<SchedulePreference> lst) {
        for (SchedulePreference sched : lst) {
            if (order == sched.getOrder()) {
                return sched;
            }
        }
        return null;
    }
    
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
    
    private ServiceProvider getFirstProviderByName(List<ServiceProvider> lstProviders) {
        lstProviders.sort(byName);
        return lstProviders.get(0);
    }
    
    private void eraseAvailabilitySlot(String date, String time, ServiceProvider prov) {
        prov.changeAvailabilityStatus(date, time);
    }
    
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
}
