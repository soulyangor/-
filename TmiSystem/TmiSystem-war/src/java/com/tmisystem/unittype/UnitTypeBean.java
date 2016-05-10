/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.unittype;

import com.tmis.entities.UnitType;
import com.tmis.facade.UnitTypeFacade;
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
@ManagedBean(name = "unitType")
@ViewScoped
public class UnitTypeBean {

    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
   
    private List<UnitType> unitTypes;
    private UnitType unitType;
    private UnitType superType;
    private String name;
    private String description;

    public List<UnitType> getUnitTypes() {
        if ( unitTypes != null ){ return unitTypes; }
        unitTypes = unitTypeFacade.findAll();
        return unitTypes;
    }
    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }  
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public UnitTypeBean() {}
    
    public String newUnitType() {
        try {
            unitType = new UnitType( name );
            unitType.setDescription( description );
            if ( superType != null ){ 
                unitTypeFacade.addSubType( superType, unitType );
                return "subTypeTable?faces-redirect=true";
        } 
        unitTypeFacade.create( unitType );
        return "unitTypeTable?faces-redirect=true";
        } catch (Exception e) {
            return "newUnitType?faces-redirect=true";
        }
    }

    public String edit() {
        if ( unitType == null ) { return null; }
        unitType.setName( name );
        unitType.setDescription( description );
        unitTypeFacade.edit( unitType );
        if( superType != null ) { return "subTypeTable?faces-redirect=true"; } 
        return "unitTypeTable?faces-redirect=true";
    }
    
    public String connectUnitTypes() {
        if ( superType != null ){
            for ( UnitType ut : unitTypes ) {
                if ( ut.isSelected() ) {
                    unitTypeFacade.connectSubType( superType, ut );
                }
            }
        }
        return "subTypeTable?faces-redirect=true";
    }
    
    public String returnStr(){
        if( superType != null ) { return "subTypeTable?faces-redirect=true"; } 
        return "unitTypeTable?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        unitType = mySessionBean.getCurrentUnitType();
        superType = mySessionBean.getCurrentSuperType();
        if ( unitType == null ) { return; }
        name = unitType.getName();
        description = unitType.getDescription();
    }
}
