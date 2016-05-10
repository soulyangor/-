/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.rocket;

import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.facade.CellFacade;
import com.tmis.facade.RocketFacade;
import com.tmis.facade.RocketModFacade;
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
@ManagedBean( name = "rocket" )
@ViewScoped
public class RocketBean {
    
    @EJB
    RocketFacade rocketFacade;
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @EJB
    CellFacade cellFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private RocketMod rocketMod;
    private Rocket rocket;
    private String name;
    private String description;
    private List<RocketMod> rocketMods;
    private String modName;
    
    public RocketMod getRocketMod() { return rocketMod; }
    public void setRocketMod(RocketMod rocketMod) { this.rocketMod = rocketMod; }   

    public Rocket getRocket() { return rocket; }
    public void setRocket(Rocket rocket) { this.rocket = rocket; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<RocketMod> getRocketMods() {
        return rocketModFacade.findAll(); 
    }
    public void setRocketMods(List<RocketMod> rocketMods) {
        this.rocketMods = rocketMods;
    }    

    public String getModName() { 
        if ( rocketMod == null ) { return modName; }
        return rocketMod.getName(); 
    }
    public void setModName(String modName) { this.modName = modName; }

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public RocketBean() {}

    public String newRocket() {
        try {
            rocket = new Rocket( name, description );
            rocketMod = rocketModFacade.findByName( modName ).get( 0 );
            if( rocketMod != null ){
                rocketModFacade.addRocket( rocketMod , rocket );
                if( rocketMod.getCells() != null ){
                    List<Cell> cellList = new ArrayList<Cell>( rocketMod.getCells() );
                    for( Cell c : cellList ){
                        c = cellFacade.find( c.getId() );
                        rocketFacade.connectCell( rocket , cellFacade.copy( c ) );
                    }                    
                }
            }
            return "rocketTable?faces-redirect=true";
        } catch (Exception e) { return "newRocket?faces-redirect=true"; }        
    }

    public String edit() {
        if ( rocket == null ) { return null; }
        rocket.setName( name );
        rocket.setDescription( description );
        rocketFacade.edit( rocket );
        return "rocketTable?faces-redirect=true";
    }  
    
    @PostConstruct
    public void postConstruct() {
        rocketMod = mySessionBean.getCurrentRocketMod();
        rocket = mySessionBean.getCurrentRocket();
        if (rocket == null) { return; }
        name = rocket.getName();
        description = rocket.getDescription();
        rocketMod = rocket.getRocketMod();
    }
}
