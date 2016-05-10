/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.record;

import com.tmis.entities.Record;
import com.tmis.mongo.Elem;
import com.tmis.mongo.MongoDB;
import com.tmisystem.sessionbean.MySessionBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean ( name = "changeMongo" )
@ViewScoped
public class EditMongoBean {
    
    @EJB
    MongoDB mongoDB;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private Elem param;
    private Record record;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    } 

    public Elem getParam() { return param; }

    public void setParam(Elem param) { this.param = param; }      
    
    public EditMongoBean() {}
    
    public boolean renderedString(){
        String type = param.getValueType().getType();
        if ( type.equals( "строка" ) ){ return true; }
        if ( type.equals( "число" ) ){ return true; }
        return false;
    }
    
    public boolean renderedDate(){
        String type = param.getValueType().getType();
        if ( type.equals( "дата" ) ){ return true; }
        return false;
    }
    
    public boolean renderedText(){
        String type = param.getValueType().getType();
        if ( type.equals( "текст" ) ){ return true; }
        return false;
    }
    
    public boolean renderedMas(){
        String type = param.getValueType().getType();
        if ( type.equals( "массив" ) ){ return true; }
        return false;
    }

    public boolean renderedTab(){
        String type = param.getValueType().getType();
        if ( type.equals( "таблица" ) ){ return true; }
        return false;
    }
    
    public void addMassElem(){
        Double[] row = new Double[1];
        row[0] = param.getX();
        param.getTable().add( row );
    }
    
    public void delMassElem(){
        if ( param.getTable().isEmpty() ){ return; }
        param.getTable().remove( param.getTable().size()-1 );
    }
    
    public void addRow(){
        Double[] row = new Double[2];
        row[0] = param.getX();
        row[1] = param.getY();
        param.getTable().add( row );
    }
    
    public void delRow(){
        if ( param.getTable().isEmpty() ){ return; }
        param.getTable().remove( param.getTable().size()-1 );
    }
    
    public String edit(){
        mongoDB.edit( record, param );
        return "showDoc.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {  
        record = mySessionBean.getCurrentRecord();  
        param = mySessionBean.getCurrentParam();
    }
}
