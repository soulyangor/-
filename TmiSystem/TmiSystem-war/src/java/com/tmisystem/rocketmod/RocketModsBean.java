/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.rocketmod;

import com.tmis.entities.RocketMod;
import com.tmis.entities.RocketType;
import com.tmis.facade.RocketModFacade;
import com.tmis.facade.RocketTypeFacade;
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
@ManagedBean( name = "rocketMods" )
@ViewScoped
public class RocketModsBean {

    @EJB
    RocketTypeFacade rocketTypeFacade;
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<RocketMod> rocketMods;
    private RocketType rocketType;
    private String field;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }    

    public List<RocketMod> getRocketMods() {
        if ( rocketMods != null ) { return rocketMods; }
        rocketMods = rocketTypeFacade.find(rocketType.getId()).getModifications();
        return rocketMods;
    }

    public void setRocketMods(List<RocketMod> rocketMods) {
        this.rocketMods = rocketMods;
    }

    public RocketType getRocketType() { return rocketType; }
    public void setRocketType(RocketType rocketType) {
        this.rocketType = rocketType;
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }        
    
    public RocketModsBean() { }
    
     public String toAddRocketMod(){
        if ( rocketType == null ) { return null; }
        return "newRocketMod.xhtml?faces-redirect=true";
    }
    
    public String toEditRocketMod( RocketMod rocketMod ){
        mySessionBean.setCurrentRocketMod( rocketMod );
        return "editRocketMod.xhtml?faces-redirect=true";
    }
    
    public void delete(){
        for ( RocketMod rm : rocketMods ){
            if ( rm.isSelected() ) { rocketModFacade.remove( rm.getId() ); }
        }
        rocketMods = null;
    }
    
    public String find() {
        if ( field == "" ) {
            rocketMods = rocketModFacade.findAll();
        } else {
            rocketMods = rocketModFacade.findByName( field );
        }
        return null;
    }
    
    public String toCellTable( RocketMod rocketMod ){
        mySessionBean.setCurrentRocketMod( rocketMod );
        return "/SuperUser/Cell/modelTable.xhtml?faces-redirect=true";
    }
    
    public String toRocketTable( RocketMod rocketMod ){
        mySessionBean.setCurrentRocketMod( rocketMod );
        return "/SuperUser/Rocket/rocketTable.xhtml?faces-redirect=true";
    }
    
    @PostConstruct
    public void postConstruct() {
        rocketType = mySessionBean.getCurrentRocketType();
        if ( rocketType == null ) { return; }
    }
}
