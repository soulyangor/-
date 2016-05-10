/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.rockettype;

import com.tmis.entities.RocketType;
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
@ManagedBean( name = "rocketType" )
@ViewScoped
public class RocketTypeBean {

    @EJB
    RocketTypeFacade rocketTypeFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private RocketType rocketType;
    private String name;
    private String description;

    public RocketType getRocketType() { return rocketType; }
    public void setRocketType(RocketType rocketType) {
        this.rocketType = rocketType;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }    

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }
    
    public RocketTypeBean() { }

    public String newRocketType() {
        try {
            rocketType = new RocketType( name, description );
            rocketTypeFacade.create( rocketType );
            return "rocketTypeTable?faces-redirect=true";
        } catch (Exception e) { return "newRocketType?faces-redirect=true"; }        
    }

    public String edit() {
        if ( rocketType == null ) { return null; }
        rocketType.setName( name );
        rocketType.setDescription( description );
        rocketTypeFacade.edit( rocketType );
        return "rocketTypeTable?faces-redirect=true";
    }  
    
    @PostConstruct
    public void postConstruct() {
        rocketType = mySessionBean.getCurrentRocketType();
        if (rocketType == null) { return; }
        name = rocketType.getName();
        description = rocketType.getDescription();
    }
}
