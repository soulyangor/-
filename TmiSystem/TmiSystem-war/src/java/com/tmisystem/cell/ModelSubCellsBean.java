/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.cell;

import com.tmis.entities.Cell;
import com.tmis.entities.UnitType;
import com.tmis.facade.CellFacade;
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
@ManagedBean( name = "modelSubCells" )
@ViewScoped
public class ModelSubCellsBean {
    
    @EJB
    CellFacade cellFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<Cell> cells;
    private Cell superCell;
    private String field;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }            

    public List<Cell> getCells() {
        if ( cells != null ){ return cells; }
        cells = cellFacade.find( superCell.getId() ).getSubCells();
        return cells;
    }
    public void setCells(List<Cell> cells) { this.cells = cells; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public Cell getSuperCell() { return superCell; }

    public ModelSubCellsBean() { }
            
    public void delete(){
        for ( Cell c : cells ){
            if ( c.isSelected() ) { cellFacade.remove( c.getId() ); }
        }
        cells = null;
    }
    
    public void deleteUnitType( Cell cell ){
        for ( UnitType ut : cell.getUnitTypes() ){
            if ( ut.isSelected() ) { cellFacade.removeUnitType( cell, ut ); }
        } 
        cells = null;
    }
    
    public String toAddUnitType( Cell cell ){
        mySessionBean.setCurrentCell( cell );
        return "cellAddUnitType.xhtml?faces-redirect=true"; 
    }
    
    public String toSubCells( Cell cell ){
        mySessionBean.setCurrentSuperCell( cell );
        return "modelSubCells.xhtml?faces-redirect=true";
    }
    
    public String toNewCell(){ 
        mySessionBean.setCurrentSuperCell( superCell );
        return "newCell.xhtml?faces-redirect=true"; 
    }
    
    public String toEditCell( Cell cell ){
        mySessionBean.setCurrentCell( cell );
        return "editCell.xhtml?faces-redirect=true";
    }
        
    public String find() {
        if ( field == "" ) {
            cells = cellFacade.findAll();
        } else {
            cells = cellFacade.findByName( field );
        }
        return null;
    }
    
    public String returnStr(){
        superCell = superCell.getSuperCell();
        mySessionBean.setCurrentSuperCell( superCell );
        if( superCell != null ) { return "modelSubCells?faces-redirect=true"; } 
        return "modelTable?faces-redirect=true";
    }
    
    public String toAlgorithmTable( Cell cell ){
        mySessionBean.setCurrentCell( cell );
        return "setAlgorithm.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        superCell = mySessionBean.getCurrentSuperCell();
        if ( superCell == null ) { return; }
    }
}
