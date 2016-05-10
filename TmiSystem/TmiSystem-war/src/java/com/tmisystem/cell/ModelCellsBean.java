/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.cell;

import com.tmis.entities.Cell;
import com.tmis.entities.RocketMod;
import com.tmis.entities.UnitType;
import com.tmis.facade.CellFacade;
import com.tmis.facade.RocketModFacade;
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
@ManagedBean( name = "modelCells" )
@ViewScoped
public class ModelCellsBean {
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @EJB
    CellFacade cellFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<Cell> cells;
    private RocketMod rocketMod;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }            

    public List<Cell> getCells() {
        if ( cells != null ){ return cells; }
        cells = rocketModFacade.find( rocketMod.getId() ).getCells();
        return cells;
    }
    public void setCells(List<Cell> cells) { this.cells = cells; }

    public ModelCellsBean() { }
            
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
        mySessionBean.setCurrentSuperCell( null );
        return "newCell.xhtml?faces-redirect=true"; 
    }
    
    public String toEditCell( Cell cell ){
        mySessionBean.setCurrentCell( cell );
        return "editCell.xhtml?faces-redirect=true";
    }
    
    public String toAlgorithmTable( Cell cell ){
        mySessionBean.setCurrentCell( cell );
        return "setAlgorithm.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        rocketMod = mySessionBean.getCurrentRocketMod();
        if ( rocketMod == null ) { return; }
    }
}
