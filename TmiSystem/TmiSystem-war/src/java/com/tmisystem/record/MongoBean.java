/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.record;

import com.tmis.entities.Cell;
import com.tmis.entities.Partition;
import com.tmis.entities.Record;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import com.tmis.facade.PartitionFacade;
import com.tmis.facade.RecordFacade;
import com.tmis.mongo.Elem;
import com.tmis.mongo.MongoDB;
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
@ManagedBean( name = "mongo" )
@ViewScoped
public class MongoBean {

    @EJB
    PartitionFacade partitionFacade;
    
    @EJB
    RecordFacade recordFacade;
            
    @EJB
    MongoDB mongoDB;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;

    private Cell cell;
    private Record record;
    private UnitType type;
    private List<Elem> elems;
    private List<Elem> params;
    private List<Partition> partitions;   
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }       

    public Record getRecord() { return record; }

    public List<Elem> getElems() { return elems; }
    public void setElems(List<Elem> elems) { this.elems = elems; }

    public List<Elem> getParams() {
        params = mongoDB.getParams( record );
        return params;
    }
    public void setParams(List<Elem> params) { this.params = params; }

    public List<Partition> getPartitions() {
        if ( partitions != null ){ return partitions; }
        partitions = partitionFacade.findAll();
        return partitions;
    }      
    
    public MongoBean() { }
    
    public void addMassElem( Elem e ){
        Double[] row = new Double[1];
        row[0] = e.getX();
        e.getTable().add( row );
    }
    
    public void delMassElem( Elem e ){
        if ( e.getTable().isEmpty() ){ return; }
        e.getTable().remove( e.getTable().size()-1 );
    }
    
    public void addRow( Elem e ){
        Double[] row = new Double[2];
        row[0] = e.getX();
        row[1] = e.getY();
        e.getTable().add( row );
    }
    
    public void delRow( Elem e ){
        if ( e.getTable().isEmpty() ){ return; }
        e.getTable().remove( e.getTable().size()-1 );
    }
    
    public boolean renderedParam( Partition partition, Elem elem ){
        if ( elem.getValueType().getPartition().getId() == partition.getId() ){
            return true;
        }
        return false;
    }
    
    public boolean renderedString( Elem elem ){
        String type = elem.getValueType().getType();
        if ( type.equals( "строка" ) ){ return true; }
        if ( type.equals( "число" ) ){ return true; }
        return false;
    }
    
    public boolean renderedDate( Elem elem ){
        String type = elem.getValueType().getType();
        if ( type.equals( "дата" ) ){ return true; }
        return false;
    }
    
    public boolean renderedText( Elem elem ){
        String type = elem.getValueType().getType();
        if ( type.equals( "текст" ) ){ return true; }
        return false;
    }
    
    public boolean renderedMas( Elem elem ){
        String type = elem.getValueType().getType();
        if ( type.equals( "массив" ) ){ return true; }
        return false;
    }

    public boolean renderedTab( Elem elem ){
        String type = elem.getValueType().getType();
        if ( type.equals( "таблица" ) ){ return true; }
        return false;
    }
    
    public String newDoc(){
        System.out.println("Params "+elems);
        if ( record != null ){ mongoDB.add( record, elems ); }
        return "showDoc.xhtml?faces-redirect=true";
    }
    
    public String returnStr(){
        if ( cell == null ) { 
            if ( type == null ){
                return "/SuperUser/TmUnit/tmUnitTable.xhtml?faces-redirect=true";
            }
            return "/SuperUser/TmUnit/unitsOfType.xhtml?faces-redirect=true"; 
        }
        if( cell.getSuperCell() != null ) { 
            return "/SuperUser/Cell/structSubCells?faces-redirect=true"; 
        } 
        return "/SuperUser/Cell/structTable?faces-redirect=true";
    }
    
    public String removeDoc(){
        mongoDB.remove( record );
        recordFacade.remove( record.getId() );
         if ( cell == null ) { 
            if ( type == null ){
                return "/SuperUser/TmUnit/tmUnitTable.xhtml?faces-redirect=true";
            }
            return "/SuperUser/TmUnit/unitsOfType.xhtml?faces-redirect=true"; 
        }
        if( cell.getSuperCell() != null ) { 
            return "/SuperUser/Cell/structSubCells?faces-redirect=true"; 
        } 
        return "/SuperUser/Cell/structTable?faces-redirect=true";
    }
    
    public String toChange( Elem param ){
        if ( param.getValueType().getEditing() ){
            mySessionBean.setCurrentParam( param );
            return "changeDoc?faces-redirect=true";
        }
        return null;
    }
    
    @PostConstruct
    public void postConstruct() {
        cell = mySessionBean.getCurrentCell();
        record = mySessionBean.getCurrentRecord();  
        elems = new ArrayList<Elem>();
        type = mySessionBean.getCurrentUnitType();
        UnitType unitType = record.getTmUnit().getUnitType();
        List<ValueType> valueTypes = 
                new ArrayList<ValueType>( unitType.getValueTypes() );
        for ( ValueType valueType : valueTypes ){
            elems.add( new Elem( valueType ) );
        }
    }
}
