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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "rocketMod" )
@ViewScoped
public class RocketModBean {

    @EJB
    RocketTypeFacade rocketTypeFacade;
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private RocketType rocketType;
    private RocketMod rocketMod;
    private String name;
    private String description;

    public RocketType getRocketType() { return rocketType; }
    public void setRocketType(RocketType rocketType) {
        this.rocketType = rocketType;
    }

    public RocketMod getRocketMod() { return rocketMod; }
    public void setRocketMod(RocketMod rocketMod) { this.rocketMod = rocketMod; }   
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }    

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public RocketModBean() {}

    public String newRocketMod() {
        try {
            rocketMod = new RocketMod( name, description );
            if( rocketType != null ){
                rocketTypeFacade.addRocketMod( rocketType , rocketMod );
            }
            return "rocketModTable?faces-redirect=true";
        } catch (Exception e) { return "newRocketMod?faces-redirect=true"; }        
    }

    public String edit() {
        if ( rocketType == null ) { return null; }
        rocketMod.setName( name );
        rocketMod.setDescription( description );
        rocketModFacade.edit( rocketMod );
        return "rocketModTable?faces-redirect=true";
    }  
    
    @PostConstruct
    public void postConstruct() {
        rocketType = mySessionBean.getCurrentRocketType();
        rocketMod = mySessionBean.getCurrentRocketMod();
        if (rocketMod == null) { return; }
        name = rocketMod.getName();
        description = rocketMod.getDescription();
    }
}
