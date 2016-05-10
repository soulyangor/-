/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.tmunit;

import com.tmis.entities.TmUnit;
import com.tmis.facade.TmUnitFacade;
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
@ManagedBean( name = "subUnits" )
@ViewScoped
public class SubUnitsBean {

    @EJB
    TmUnitFacade tmUnitFacade;
    
    @ManagedProperty( value = "#{mySession}" )
    private MySessionBean mySessionBean;
    
    private List<TmUnit> tmUnits;
    private TmUnit superUnit;
    private String field;

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }

    public List<TmUnit> getTmUnits() {
        if ( tmUnits != null ){ return tmUnits; }
        tmUnits = tmUnitFacade.find( superUnit.getId() ).getSubUnits();
        return tmUnits;
    }
    public void setTmUnits(List<TmUnit> tmUnits) { this.tmUnits = tmUnits; }

    public TmUnit getSuperUnit() { return superUnit; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
        
    public SubUnitsBean() {}
    
    public String find() {
        if ( field == "" ) {
            tmUnits = tmUnitFacade.findAll();
        } else {
            tmUnits = tmUnitFacade.findByName( field );
        }
        return null;
    }
    
    public String toNewTmUnit(){
        mySessionBean.setCurrentSuperUnit( superUnit );
        return "newTmUnit.xhtml?faces-redirect=true";
    }
    
    public String toEditTmUnit( TmUnit tmUnit ){
        mySessionBean.setCurrentTmUnit( tmUnit );
        return "editTmUnit.xhtml?faces-redirect=true";
    }
    
    public String toSubUnits( TmUnit tmUnit ){
        mySessionBean.setCurrentSuperUnit( tmUnit );
        tmUnits = tmUnit.getSubUnits();
        return "subUnitTable.xhtml?faces-redirect=true";
    }
    
    public void delete() {
        for ( TmUnit tm : tmUnits ) {
            if ( tm.isSelected() ) {
                tmUnitFacade.remove( tm.getId() );
            }
        }
        tmUnits = null;
    }
    
    public String returnStr(){
        superUnit = superUnit.getSuperUnit();
        mySessionBean.setCurrentSuperUnit( superUnit );
        if( superUnit != null ) { return "subUnitTable?faces-redirect=true"; } 
        return "tmUnitTable?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        superUnit = mySessionBean.getCurrentSuperUnit();
    }
}
