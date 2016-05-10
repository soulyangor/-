/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.valuetype;

import com.tmis.entities.Partition;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import com.tmis.facade.PartitionFacade;
import com.tmis.facade.UnitTypeFacade;
import com.tmis.facade.ValueTypeFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "valueTypes" )
@ViewScoped
public class ValueTypesBean {
    
    @EJB
    PartitionFacade partitionFacade;
    
    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    ValueTypeFacade valueTypeFacade; 
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private UnitType unitType;
    private List<ValueType> valueTypes;
    private List<Partition> partitions;
    private String field;
    private Partition partition;
    private Partition activePartition;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }  

    public UnitType getUnitType() { return unitType; }
    public void setUnitType(UnitType unitType) { this.unitType = unitType; }

    public List<ValueType> getValueTypes() {
        valueTypes = valueTypeFacade.findByPartitionAndUnitType( partition , unitType );
        if( valueTypes == null ){ return valueTypeFacade.findAll(); }
        return valueTypes; 
    }
    public void setValueTypes(List<ValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    public List<Partition> getPartitions() {
        if ( partitions != null ){ return partitions; }
        partitions = partitionFacade.findAll();
        return partitions;
    }
    
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public void setPartition(Partition partition) {
        this.partition = partition;
        getValueTypes();
    }
    public Partition getPartition() { return partition; }

    public Partition getActivePartition() { return activePartition; }
    public void setActivePartition(Partition activePartition) {
        this.activePartition = activePartition;
    }

    public ValueTypesBean() {}
    
    public String toNewValueType( Partition partition ){
        mySessionBean.setCurrentPartition( partition );
        return "newValueType.xhtml?faces-redirect=true";
    }
    
    public String toEditValueType( ValueType valueType ){
        mySessionBean.setCurrentValueType( valueType );
        return "editValueType.xhtml?faces-redirect=true";        
    }
    
    public void delete() {
        for ( ValueType vt : valueTypes ) {
            if ( vt.isSelected() ) {
                valueTypeFacade.remove( vt.getId() );
            }
        }
        valueTypes = null;
    }
    
    public String find() {
        if ( field == "" ) {
            valueTypes = valueTypeFacade.findAll();
        } else {
            valueTypes = valueTypeFacade.findByName( field );
            activePartition = valueTypes.get( 0 ).getPartition();
        }
        return null;
    }
    
    @PostConstruct
    public void postConstruct() {
        unitType = mySessionBean.getCurrentUnitType();
        activePartition = mySessionBean.getCurrentPartition();
        if ( activePartition == null ) { 
            activePartition = partitionFacade.findAll().get(0); 
        }
    }
}
