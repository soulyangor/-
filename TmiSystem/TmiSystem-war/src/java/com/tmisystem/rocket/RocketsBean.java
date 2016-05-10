/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.rocket;

import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.entities.RocketType;
import com.tmis.facade.RocketFacade;
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
@ManagedBean( name = "rockets" )
@ViewScoped
public class RocketsBean {
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @EJB
    RocketFacade rocketFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<Rocket> rockets;
    private RocketMod rocketMod;
    private RocketType rocketType;
    private String field;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }            

    public List<Rocket> getRockets() {
        if ( rockets != null ){ return rockets; }
        if ( rocketMod != null ){
            return rocketModFacade.find( rocketMod.getId() ).getRockets();
        }
        if ( rocketType != null){
            return rocketFacade.findByType( rocketType );
        }
        return rocketFacade.findAll();
    }
    public void setRockets(List<Rocket> rockets) { this.rockets = rockets; }

    public RocketMod getRocketMod() { return rocketMod; }
    public void setRocketMod(RocketMod rocketMod) { this.rocketMod = rocketMod; }

    public RocketType getRocketType() { return rocketType; }
    public void setRocketType(RocketType rocketType) {
        this.rocketType = rocketType;
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }   
        
    public RocketsBean() { }
    
    public String allRockets(){
        mySessionBean.setCurrentRocketMod( null );
        mySessionBean.setCurrentRocketType( null );
        rockets = rocketFacade.findAll();
        return null;
    }
    
    public String toAddRocket(){ return "newRocket.xhtml?faces-redirect=true"; }
    
    public String toEditRocket( Rocket rocket ){
        mySessionBean.setCurrentRocket( rocket );
        return "editRocket.xhtml?faces-redirect=true";
    }
    
    public String sortByType( Rocket rocket ){
        RocketType rt = rocket.getRocketMod().getRocketType();
        rockets = rocketFacade.findByType( rt );
        mySessionBean.setCurrentRocketType( rt );
        return null;
    }
    
    public String sortByMod( Rocket rocket ){
        RocketMod rm = rocketModFacade.find( rocket.getRocketMod().getId() );
        rockets = rm.getRockets();
        mySessionBean.setCurrentRocketMod( rm );
        return null;
    }
    
    public void delete(){
        for ( Rocket r : rockets ){
            if ( r.isSelected() ) { rocketFacade.remove( r.getId() ); }
        }
        rockets = null;
    }
    
    public String find() {
        if ( field == "" ) {
            rockets = rocketFacade.findAll();
        } else {
            rockets = rocketFacade.findByName( field );
        }
        return null;
    }
    
    public String toCellTable( Rocket rocket ){
        mySessionBean.setCurrentRocket( rocket );
        return "/SuperUser/Cell/structTable.xhtml?faces-redirect=true";
    }
    
    public String toRocketTable( RocketMod rocketMod ){
        mySessionBean.setCurrentRocketMod( rocketMod );
        return "/SuperUser/Rocket/rocketTable.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        rocketMod = mySessionBean.getCurrentRocketMod();
        rocketType = mySessionBean.getCurrentRocketType();
        if ( rocketMod == null ) { return; }
        if ( rocketType == null ) { return; }
    }
}
