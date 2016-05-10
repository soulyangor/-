/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.rockettype;

import com.tmis.entities.RocketType;
import com.tmis.facade.RocketTypeFacade;
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
@ManagedBean ( name = "rocketTypes")
@ViewScoped
public class RocketTypesBean {
    
    @EJB
    RocketTypeFacade rocketTypeFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<RocketType> rocketTypes;
    private String field;

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }    

    public List<RocketType> getRocketTypes() { 
        if ( rocketTypes != null ) { return rocketTypes; }
        rocketTypes = rocketTypeFacade.findAll();
        return rocketTypes; 
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }
    
    public RocketTypesBean() { }
    
    public String toNewRocketType(){
        return "newRocketType.xhtml?faces-redirect=true";
    }
    
    public String toEditRocketType( RocketType rocketType ){
        mySessionBean.setCurrentRocketType( rocketType );
        return "editRocketType.xhtml?faces-redirect=true";
    }
    
    public void delete() {
        for ( RocketType rt : rocketTypes ) {
            if ( rt.isSelected() ) {
                rocketTypeFacade.remove( rt.getId() );
            }
        }
        rocketTypes = null;
    }

    public String find() {
        if ( field == "" ) {
            rocketTypes = rocketTypeFacade.findAll();
        } else {
            rocketTypes = rocketTypeFacade.findByName( field );
        }
        return null;
    }
    
    public String toRocketModTable( RocketType rocketType ){
        mySessionBean.setCurrentRocketType( rocketType );
        return "/SuperUser/RocketMod/rocketModTable.xhtml?faces-redirect=true";
    }
    
    public String toRocketTable( RocketType rocketType ){
        mySessionBean.setCurrentRocketType( rocketType );
        mySessionBean.setCurrentRocketMod( null );
        return "/SuperUser/Rocket/rocketTable.xhtml?faces-redirect=true";
    }
}
