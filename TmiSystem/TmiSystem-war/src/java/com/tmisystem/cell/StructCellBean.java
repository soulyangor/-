/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.cell;

import com.tmis.entities.Cell;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.facade.CellFacade;
import com.tmis.facade.TmUnitFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.util.ArrayList;
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
@ManagedBean( name = "structCell" )
@ViewScoped
public class StructCellBean {

    @EJB
    CellFacade cellFacade;
    
    @EJB
    TmUnitFacade tmUnitFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private Cell cell;
    private Cell superCell;
    private List<TmUnit> tmUnits;
    private String field;

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public List<TmUnit> getTmUnits() {
        if ( tmUnits != null ){ return tmUnits; }
        Cell origin = cellFacade.getOrigin( cell );
        List<UnitType> unitTypes = new ArrayList<UnitType>( origin.getUnitTypes() );
        tmUnits = tmUnitFacade.findByTypes( unitTypes );
        return tmUnits;
    }
    public void setTmUnits(List<TmUnit> tmUnits) { this.tmUnits = tmUnits; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
    
     
    public String returnStr(){
        superCell = superCell.getSuperCell();
        mySessionBean.setCurrentSuperCell( superCell );
        if( superCell != null ) { return "structSubCells?faces-redirect=true"; } 
        return "structTable?faces-redirect=true";
    }
    
    public String setTmUnit( TmUnit tmUnit ){
        cell = cellFacade.find( cell.getId() );
        cell.setTmUnit( tmUnit );
        tmUnit = tmUnitFacade.find( tmUnit.getId() );
        tmUnit.setCell( cell );
        tmUnitFacade.edit( tmUnit );
        cellFacade.edit( cell );
        if( superCell != null ) { return "structSubCells?faces-redirect=true"; } 
        return "structTable?faces-redirect=true";
    }   
    
    public String find() {
        if ( field == "" ) {
            tmUnits = tmUnitFacade.findAll();
        } else {
            Cell origin = cellFacade.getOrigin( cell );
            List<UnitType> unitTypes = new ArrayList<UnitType>( origin.getUnitTypes() );
            tmUnits = tmUnitFacade.findByNameAndTypes( field, unitTypes );
        }
        return null;
    }
    
    public StructCellBean() {}
    
    @PostConstruct
    public void postConstruct() {
        cell = mySessionBean.getCurrentCell();
        superCell = mySessionBean.getCurrentSuperCell();
    }
}
