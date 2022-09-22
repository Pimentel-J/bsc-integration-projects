/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import lapr2.project.agpsd.model.ExecutionOrder.RequestStatus;


/**
 *
 *
 */
public class ExecutionOrderRegistry {
    private List<ExecutionOrder> m_oExecOrderList;
    
    public void addExecutionOrder(ExecutionOrder exec){
        m_oExecOrderList.add(exec);
    }
    
    public ExecutionOrderRegistry(){
        this.m_oExecOrderList = new ArrayList<ExecutionOrder>();
    }
    /**
     * Method to obtain the list of execution orders by provider
     * @param sp related to the service provider
     * @return list of execution orders
     */
    public List<ExecutionOrder> getExecutionOrdersByServiceProvider(ServiceProvider sp){
        List<ExecutionOrder> execOrderList = new ArrayList<>();
        for (ExecutionOrder execOrd : m_oExecOrderList) {
            if (execOrd.getSp().equals(sp)) {
                execOrderList.add(execOrd);
            }
        }
        return execOrderList;
    }
    /**
     * Method to obtain the list of the unevaluated execution orders by provider
     * @param sp related to the service provider
     * @return list of unevaluated execution orders 
     */
    public List<ExecutionOrder> getUnevaluatedExecutionOrdersByServiceProvider(ServiceProvider sp){
        List<ExecutionOrder> UnevExecOrderList = new ArrayList<>();
        for (ExecutionOrder execOrd : m_oExecOrderList) {
            if ((execOrd.getSp().equals(sp))&& (execOrd.getRequestStatus().equals(RequestStatus.UNFINISHED))) {
                UnevExecOrderList.add(execOrd);
            }
        }
        return UnevExecOrderList;
    }
    /**
     * Method to obtain the list of the evaluated execution orders by provider
     * @param sp related to the service provider
     * @return list of evaluated execution orders 
     */
    public List<ExecutionOrder> getEvaluatedExecutionOrdersByServiceProvider(ServiceProvider sp){
        List<ExecutionOrder> EvExecOrderList = new ArrayList<>();
        for (ExecutionOrder execOrd : m_oExecOrderList) {
            if ((execOrd.getSp().equals(sp))&& !(execOrd.getRequestStatus().equals(RequestStatus.UNFINISHED))) {
                EvExecOrderList.add(execOrd);
            }
        }
        return EvExecOrderList;
    }
}
