/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

/**
 *
 *
 */
public class ProviderPairing {
    
    private String m_strDate;
    private String m_strTime;
    private ServiceProvider m_oServProvider;

    public ProviderPairing(String strDate, String strTime, ServiceProvider oServProvider) {
        this.m_strDate = strDate;
        this.m_strTime = strTime;
        this.m_oServProvider = oServProvider;
    }
    
    public boolean fillProviderSchedule() {
        return this.m_oServProvider.changeAvailabilityStatus(m_strDate, m_strTime);
    }
    
}
