/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.partition;

import com.tmis.entities.Partition;
import com.tmis.facade.PartitionFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "partitions")
@ViewScoped
public class PartitionsBean {

    @EJB
    PartitionFacade partitionFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<Partition> partitions;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }

    public List<Partition> getPartitions() {
        if ( partitions != null ){ return partitions; }
        partitions = partitionFacade.findAll();
        return partitions;
    }  
    
    public PartitionsBean() {}
    
    public String toNewPartition(){
        return "newPartition.xhtml?faces-redirect=true";
    }
    
    public String toEditPartition( Partition partition ){
        mySessionBean.setCurrentPartition( partition );
        return "editPartition.xhtml?faces-redirect=true";
    }
    
    public void delete() {
        for ( Partition p : partitions ) {
            if ( p.isSelected() ) {
                partitionFacade.remove( p.getId() );
            }
        }
        partitions = null;
    }
}
