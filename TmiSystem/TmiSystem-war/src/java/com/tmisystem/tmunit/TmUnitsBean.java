/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.tmunit;

import com.tmis.entities.Record;
import com.tmis.entities.TmUnit;
import com.tmis.facade.RecordFacade;
import com.tmis.facade.TmUnitFacade;
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
@ManagedBean ( name = "tmUnits")
@ViewScoped
public class TmUnitsBean {

    @EJB
    TmUnitFacade tmUnitFacade;
    
    @EJB
    RecordFacade recordFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private String field;
    private List<TmUnit> tmUnits;

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }

    public List<TmUnit> getTmUnits() { 
        mySessionBean.setCurrentUnitType( null );
        if ( tmUnits != null ) { return tmUnits; }
        tmUnits = tmUnitFacade.findAll();
        return tmUnits;
    }
    public void setTmUnits(List<TmUnit> tmUnits) { this.tmUnits = tmUnits; } 

    public String getField() { return field; }
    public void setField(String field) { this.field = field; } 
    
    public TmUnitsBean() { }
    
    public String find() {
        if ( field == "" ) {
            tmUnits = tmUnitFacade.findAll();
        } else {
            tmUnits = tmUnitFacade.findByName( field );
        }
        return null;
    }
    
    public String toNewTmUnit(){
        mySessionBean.setCurrentSuperUnit( null );
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
    
    public String toRecordList( TmUnit tmUnit ){
        mySessionBean.setCurrentTmUnit( tmUnit );
        mySessionBean.setCurrentCell( null );
        mySessionBean.setCurrentUnitType( null );
        if( tmUnit.getRecords().isEmpty() ){
            Record record = new Record( tmUnit.getName() ); 
            tmUnit = tmUnitFacade.find( tmUnit.getId() );
            tmUnitFacade.addRecord( tmUnit, record );
            mySessionBean.setCurrentRecord( record );
            return "/SuperUser/Record/addDoc.xhtml?faces-redirect=true";
        }
        mySessionBean.setCurrentRecord( tmUnit.getRecords().get( 0 ) );
        return "/SuperUser/Record/showDoc.xhtml?faces-redirect=true";
    }
    
    public void delete() {
        for ( TmUnit tm : tmUnits ) {
            if ( tm.isSelected() ) {
                tmUnitFacade.remove( tm.getId() );
            }
        }
        tmUnits = null;
    }    
}
