/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.tmunit;

import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.facade.TmUnitFacade;
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
@ManagedBean( name = "tmUnit" )
@ViewScoped
public class TmUnitBean {

    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    TmUnitFacade tmUnitFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;

    private List<UnitType> unitTypes;
    private TmUnit superUnit;
    private UnitType unitType;
    private TmUnit tmUnit;
    private String name;
    private String fullname;
    private String description; 
    private String typeName;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }    

    public List<UnitType> getUnitTypes() { return unitTypeFacade.findAll(); }
    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeName() {
        if ( unitType == null ) { return typeName; }
        return unitType.getName(); 
    }
    public void setTypeName(String typeName) {  this.typeName = typeName; }
    
    
    public TmUnitBean() { }
    
    public String newTmUnit() {
        try {
            tmUnit = new TmUnit( name , fullname );        
            tmUnit.setDescription( description );
            unitType = unitTypeFacade.findByName( typeName ).get( 0 );
            if ( superUnit != null ){ 
                tmUnitFacade.addSubUnit( superUnit, tmUnit );
                if ( unitType != null ){
                unitTypeFacade.connectTmUnit( unitType, tmUnit );
                return "subUnitTable?faces-redirect=true";
                }
            }else { 
            if ( unitType != null ){
                unitTypeFacade.addTmUnit( unitType , tmUnit );
                unitType = mySessionBean.getCurrentUnitType();
                if( unitType != null ){ return "unitsOfType?faces-redirect=true"; }
            } 
        }        
        return "tmUnitTable?faces-redirect=true";
        } catch (Exception e) { return "newTmUnit?faces-redirect=true"; }        
    }

    public String edit() {
        if ( tmUnit == null ) { return null; }
        tmUnit.setName( name );
        tmUnit.setFullname( fullname );
        tmUnit.setDescription( description );
        tmUnitFacade.edit( tmUnit );
        if( unitType != null ) { return "unitsOfType?faces-redirect=true"; }
        if( superUnit != null ) { return "subUnitTable?faces-redirect=true"; } 
        return "tmUnitTable?faces-redirect=true";
    }
    
    public String returnStr(){
        if( superUnit !=null ){ superUnit = superUnit.getSuperUnit(); }
        mySessionBean.setCurrentSuperUnit( superUnit );
        if( unitType != null ) { return "unitsOfType?faces-redirect=true"; }
        if( superUnit != null ) { return "subUnitTable?faces-redirect=true"; } 
        return "tmUnitTable?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        unitType = mySessionBean.getCurrentUnitType();
        superUnit = mySessionBean.getCurrentSuperUnit();
        tmUnit = mySessionBean.getCurrenTmUnit();
        if ( tmUnit == null ) { return; }
        name = tmUnit.getName();
        description = tmUnit.getDescription();
        fullname = tmUnit.getFullname();
    }
}
