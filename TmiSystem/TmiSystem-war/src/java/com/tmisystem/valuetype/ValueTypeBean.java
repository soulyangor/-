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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "valueType" )
@ViewScoped
public class ValueTypeBean {
    
    @EJB
    PartitionFacade partitionFacade;
    
    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    ValueTypeFacade valueTypeFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private UnitType unitType;
    private Partition partition;
    private ValueType valueType;
    private String nameId;
    private String name;
    private String dimension;
    private String type;
    private String description;
    private boolean editing;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }         

    public String getNameId() { return nameId; }
    public void setNameId(String nameId) { this.nameId = nameId; }

    public String getDimension() { return dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isEditing() { return editing; }
    public void setEditing( boolean editing ) { this.editing = editing; }
    
    public ValueTypeBean() {}

    public String newValueType() {
        try {
            valueType = new ValueType( nameId, name, type, dimension );
            valueType.setDescription( description );
            valueType.setEditing( editing );
            if( valueType != null ){
                partitionFacade.addValueType( partition, valueType );
                unitTypeFacade.connectValueType( unitType, valueType );
            }
            return "valueTypeTable?faces-redirect=true";
        } catch (Exception e) { return "newValueType?faces-redirect=true"; }        
    }

    public String edit() {
        valueType.setName( name );
        valueType.setDimension( dimension );
        valueType.setType( type );
        valueType.setDescription( description );
        valueType.setEditing( editing );
        valueTypeFacade.edit( valueType );
        return "valueTypeTable?faces-redirect=true";
    }  
    
    @PostConstruct
    public void postConstruct() {
        partition = mySessionBean.getCurrentPartition();
        unitType = mySessionBean.getCurrentUnitType();
        valueType = mySessionBean.getCurrentValueType();
        if ( valueType == null ) { return; }
        name = valueType.getName();
        nameId = valueType.getNameId();
        dimension = valueType.getDimension();
        type = valueType.getType();
        description = valueType.getDescription();
        editing = valueType.getEditing();
    }
}
