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
@ManagedBean( name = "subTypes" )
@ViewScoped
public class SubTypesBean {

    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @ManagedProperty( value = "#{mySession}" )
    private MySessionBean mySessionBean;
    
    private List<UnitType> unitTypes;
    private UnitType superType;
    private String field;

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }  

    public List<UnitType> getUnitTypes() {
        if ( unitTypes != null ){ return unitTypes; }
        unitTypes = unitTypeFacade.find( superType.getId() ).getSubTypes();
        return unitTypes;
    }
    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public UnitType getSuperType() { return superType; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
         
    public SubTypesBean() { }
    
    public void delete(){
        for ( UnitType ut : unitTypes ){
            if ( ut.isSelected() ) { unitTypeFacade.remove( ut.getId() ); }
        }
        unitTypes = null;
    }
    
    public String toNewUnitType(){
        mySessionBean.setCurrentSuperType( superType );
        return "newUnitType.xhtml?faces-redirect=true";
    }
    
    public String toAddUnitType(){
        mySessionBean.setCurrentSuperType( superType );
        return "addUnitType.xhtml?faces-redirect=true";
    }
    
    public String toEditUnitType( UnitType unitType ){
        mySessionBean.setCurrentUnitType( unitType );
        return "editUnitType.xhtml?faces-redirect=true";
    }
    
    public String toSubTypes( UnitType unitType ){
        mySessionBean.setCurrentSuperType( unitType );
        return "subTypeTable.xhtml?faces-redirect=true";
    }
    
    public String find() {
        if ( field == "" ) {
            unitTypes = unitTypeFacade.findAll();
        } else {
            unitTypes = unitTypeFacade.findByName( field );
        }
        return null;
    }
    
    public String toValueTypeTable( UnitType unitType ){
        mySessionBean.setCurrentUnitType( unitType );
        return "/SuperUser/ValueType/valueTypeTable.xhtml?faces-redirect=true";
    }
    
    public String toTmUnitTable( UnitType unitType ){
        mySessionBean.setCurrentUnitType( unitType );
        return "/SuperUser/TmUnit/unitsOfType.xhtml?faces-redirect=true";
    }
    
    public String returnStr(){
        superType = superType.getSuperType();
        mySessionBean.setCurrentSuperType( superType );
        if( superType != null ) { return "subTypeTable?faces-redirect=true"; } 
        return "unitTypeTable?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct(){
        superType = mySessionBean.getCurrentSuperType();
    }
    
}
