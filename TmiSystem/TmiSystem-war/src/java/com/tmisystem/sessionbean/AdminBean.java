/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.sessionbean;

import com.tmis.facade.CellFacade;
import com.tmis.facade.PartitionFacade;
import com.tmis.facade.RecordFacade;
import com.tmis.facade.RocketFacade;
import com.tmis.facade.RocketModFacade;
import com.tmis.facade.RocketTypeFacade;
import com.tmis.facade.TmUnitFacade;
import com.tmis.facade.UnitTypeFacade;
import com.tmis.facade.ValueTypeFacade;
import com.tmis.mongo.MongoDB;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "admin" )
@ViewScoped
public class AdminBean {

    @EJB
    CellFacade cellFacade;
    
    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    TmUnitFacade tmUnitFacade;
    
    @EJB
    RocketFacade rocketFacade;
    
    @EJB
    RocketModFacade rocketModFacade;
    
    @EJB
    RocketTypeFacade rocketTypeFacade;
    
    @EJB
    PartitionFacade partitionFacade;
    
    @EJB
    ValueTypeFacade valueTypeFacade;
    
    @EJB
    RecordFacade recordFacade;
    
    @EJB
    MongoDB mongoDB;
    
    public AdminBean() {}
    
    public void clearCell(){ cellFacade.clearTable(); }
    public void clearUnitType(){ unitTypeFacade.clearTable(); }
    public void clearTmUnit(){ tmUnitFacade.clearTable(); }
    public void clearRocket(){ rocketFacade.clearTable(); }
    public void clearRocketMod(){ rocketModFacade.clearTable(); }
    public void clearRocketType(){ rocketTypeFacade.clearTable(); }
    public void clearPartition(){ partitionFacade.clearTable(); }
    public void clearValueType(){ valueTypeFacade.clearTable(); }
    public void clearRecord(){ recordFacade.clearTable(); }
    public void clearMongo(){ mongoDB.removeAll(); }    
}
