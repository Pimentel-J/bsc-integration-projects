/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.List;

/**
 *
 *
 */
public interface ExternalService {
    
    /**
     *
     * @param oZipCode
     * @param dRadius
     * @return
     */
    List<ActsOn> getActivity(ZipCode oZipCode, double dRadius);
    
}
