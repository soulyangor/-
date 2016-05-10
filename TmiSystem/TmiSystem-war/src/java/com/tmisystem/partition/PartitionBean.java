/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.partition;

import com.tmis.entities.Partition;
import com.tmis.facade.PartitionFacade;
import com.tmisystem.sessionbean.MySessionBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "partition")
@ViewScoped
public class PartitionBean {

    @EJB
    PartitionFacade partitionFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private Partition partition;
    private String name;
    private String description;

    public Partition getPartition() { return partition; }
    public void setPartition(Partition partition) { this.partition = partition; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public PartitionBean() {}
    
    public String newPartition() {
        try {
            partition = new Partition( name );
            partition.setDescription( description );
            partitionFacade.create( partition );
            return "partitionTable?faces-redirect=true";
        } catch (Exception e) { return "newPartition?faces-redirect=true"; }
        
    }

    public String edit() {
        if ( partition == null ) { return null; }
        partition.setName( name );
        partition.setDescription( description );
        partitionFacade.edit( partition );
        return "partitionTable?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        partition = mySessionBean.getCurrentPartition();
        if ( partition == null ) { return; }
        name = partition.getName();
        description = partition.getDescription();
    }
}
