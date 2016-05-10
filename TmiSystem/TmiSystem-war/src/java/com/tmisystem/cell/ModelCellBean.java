/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.cell;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import com.tmis.entities.RocketMod;
import com.tmis.entities.UnitType;
import com.tmis.facade.AlgorithmFacade;
import com.tmis.facade.CellFacade;
import com.tmis.facade.RocketModFacade;
import com.tmis.facade.UnitTypeFacade;
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
@ManagedBean( name = "modelCell" )
@ViewScoped
public class ModelCellBean {

    @EJB
    CellFacade cellFacade;
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    AlgorithmFacade algorithmFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private RocketMod rocketMod;
    private Cell superCell;
    private Cell cell;
    private String name;
    private String description;
    private List<UnitType> unitTypes;
    private List<Integer> channels;
    private String channel;
    private String outputChannels;
    private List<Algorithm> algorithms;
    private String field;

    public RocketMod getRocketMod() { return rocketMod; }
    public void setRocketMod(RocketMod rocketMod) {
        this.rocketMod = rocketMod;   
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }    

    public List<UnitType> getUnitTypes() {
        if ( unitTypes != null ){ return unitTypes; }
        unitTypes = unitTypeFacade.findAll();
        return unitTypes;
    }
    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public List<Integer> getChannels() { return channels; }
    
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public String getOutputChannels() {
        if ( channels != null ){
            outputChannels = "";
            for( Integer ch : channels ) { outputChannels +="  " + ch.toString(); }
        }
        return outputChannels;
    }
    public void setOutputChannels(String outputChannels) {
        this.outputChannels = outputChannels;
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public List<Algorithm> getAlgorithms() {
        if ( algorithms != null ){ return algorithms; }
        algorithms = algorithmFacade.findAll();
        return algorithms;
    }
    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public ModelCellBean() {}

    public boolean isRendered(){
        if ( superCell != null ) { return true; }
        return false;
    }
    
    public void addChanel(){
        if( channels == null ){ channels = new ArrayList<Integer>(); }
        channels.add( new Integer( channel ) );
    }
    
    public String newCell() {
        cell = new Cell( name, description );
        if( superCell != null ){
            cell.setChannels( channels );
            cellFacade.addSubCell( superCell, cell );
            return "modelSubCells?faces-redirect=true";
        }
        if( rocketMod != null ){
            rocketModFacade.addCell( rocketMod, cell );
        }
        return "modelTable?faces-redirect=true";
    }
    
    public String returnStr(){
        if( superCell != null ) { return "modelSubCells?faces-redirect=true"; } 
        return "modelTable?faces-redirect=true";
    }

    public String edit() {
        if ( cell == null ) { return null; }
        cell.setName( name );
        cell.setDescription( description );
        cellFacade.edit( cell );
        if( superCell != null ) { return "modelSubCells?faces-redirect=true"; } 
        return "modelTable?faces-redirect=true";
    }
    
    public String addUnitType() {
        for ( UnitType ut : unitTypes ){
            if ( ut.isSelected() ){ cellFacade.connectUnitType( cell, ut ); }
        }
        if( superCell != null ) { return "modelSubCells?faces-redirect=true"; } 
        return "modelTable?faces-redirect=true";
    }
    
    public String find() {
        if ( field == "" ) {
            unitTypes = unitTypeFacade.findAll();
        } else {
            unitTypes = unitTypeFacade.findByName( field );
        }
        return null;
    }
    
    public String setAlgorithm( Algorithm algorithm ){
        algorithmFacade.connectCell( algorithm, cell );
        if( superCell != null ) { return "modelSubCells?faces-redirect=true"; } 
        return "modelTable?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        cell = mySessionBean.getCurrentCell();
        superCell = mySessionBean.getCurrentSuperCell();
        rocketMod = mySessionBean.getCurrentRocketMod();
        if ( cell == null ) { return; }
        name = cell.getName();
        description = cell.getDescription();
    }
}
