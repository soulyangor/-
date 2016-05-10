/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.record;

import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import com.tmis.facade.TmUnitFacade;
import com.tmis.facade.UnitTypeFacade;
import com.tmis.facade.ValueTypeFacade;
import com.tmis.mongo.Elem;
import com.tmis.mongo.MongoDB;
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
@ManagedBean(name = "find")
@ViewScoped
public class findBean {
    
    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    ValueTypeFacade valueTypeFacade;
    
    @EJB
    TmUnitFacade tmUnitFacade;
    
    @EJB
    MongoDB mongoDB;
    
    private List<TmUnit> tmUnits;
    private List<UnitType> unitTypes;
    private List<ValueType> valueTypes;
    private String utName;
    private String vtName;
    private Elem elem;
    private ValueType valueType;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    public findBean() {}
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }

    public List<TmUnit> getTmUnits() {
        return tmUnits;
    }
    public void setTmUnits(List<TmUnit> tmUnits) {
        this.tmUnits = tmUnits;
    }

    public List<UnitType> getUnitTypes() { return unitTypeFacade.findAll(); }
    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }

    public List<ValueType> getValueTypes() { 
        if( valueTypes == null ) { return valueTypeFacade.findAll(); }
        return valueTypes;
    }
    public void setValueTypes(List<ValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    public String getUtName() { return utName; }
    public void setUtName(String utName) { this.utName = utName; }

    public String getVtName() { return vtName; }
    public void setVtName(String vtName) { this.vtName = vtName; }

    public Elem getElem() { return elem; }
    public void setElem(Elem elem) { this.elem = elem; }

    public ValueType getValueType() { return valueType; }
    public void setValueType(ValueType valueType) { this.valueType = valueType; }
    
    public String rocket( TmUnit tmUnit ){
        return tmUnitFacade.getRocket( tmUnit );
    }
    
    public String setUt(){
        UnitType unitType = unitTypeFacade.findByName( utName ).get( 0 );
        valueTypes = unitType.getValueTypes();
        return null;
    }
    
    public String setP(){
        valueType = valueTypeFacade.findByNameId( vtName ).get( 0 );
        elem = new Elem( valueType );
        return null;
    }
    
    public boolean rendered(){
        if( elem == null ){ return false; }
        return true;
    } 
    
    public boolean renderedString(){
        String type = elem.getValueType().getType();
        if ( type.equals( "строка" ) ){ return true; }
        if ( type.equals( "число" ) ){ return true; }
        return false;
    }
    
    public boolean renderedDate(){
        String type = elem.getValueType().getType();
        if ( type.equals( "дата" ) ){ return true; }
        return false;
    }
    
    public boolean renderedText(){
        String type = elem.getValueType().getType();
        if ( type.equals( "текст" ) ){ return true; }
        return false;
    }
    
    public boolean renderedMas(){
        String type = elem.getValueType().getType();
        if ( type.equals( "массив" ) ){ return true; }
        return false;
    }

    public boolean renderedTab(){
        String type = elem.getValueType().getType();
        if ( type.equals( "таблица" ) ){ return true; }
        return false;
    }
    
    public void addMassElem(){
        Double[] row = new Double[1];
        row[0] = elem.getX();
        elem.getTable().add( row );
    }
    
    public void delMassElem(){
        if ( elem.getTable().isEmpty() ){ return; }
        elem.getTable().remove( elem.getTable().size()-1 );
    }
    
    public void addRow(){
        Double[] row = new Double[2];
        row[0] = elem.getX();
        row[1] = elem.getY();
        elem.getTable().add( row );
    }
    
    public void delRow(){
        if ( elem.getTable().isEmpty() ){ return; }
        elem.getTable().remove( elem.getTable().size()-1 );
    }
    
    public String find() {
        tmUnits = mongoDB.find( elem );
        return null;
    }
    
    public String toRecordList( TmUnit tmUnit ){
        mySessionBean.setCurrentTmUnit( tmUnit );
        mySessionBean.setCurrentCell( null );
        mySessionBean.setCurrentRecord( tmUnit.getRecords().get( 0 ) );
        return "/SuperUser/Record/showDoc.xhtml?faces-redirect=true";
    }
}
