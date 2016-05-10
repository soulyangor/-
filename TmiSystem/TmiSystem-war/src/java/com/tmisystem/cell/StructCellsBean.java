/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.cell;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.TmUnit;
import com.tmis.facade.CellFacade;
import com.tmis.facade.RocketFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "structCells" )
@ViewScoped
public class StructCellsBean {

    @EJB
    RocketFacade rocketFacade;
    
    @EJB
    CellFacade cellFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<Cell> cells;
    private Rocket rocket;
    private String currentAlgorithmName;
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }            

    public List<Cell> getCells() {
        if ( cells != null ){ return cells; }
        cells = rocketFacade.find( rocket.getId() ).getCells();
        return cells;
    }
    public void setCells(List<Cell> cells) { this.cells = cells; }

    public StructCellsBean() { }
            
    public String toSubCells( Cell cell ){
        mySessionBean.setCurrentSuperCell( cell );
        return "structSubCells.xhtml?faces-redirect=true";
    }
    
    public String toSetUnit( Cell cell ){
        mySessionBean.setCurrentSuperCell( cell );
        mySessionBean.setCurrentCell( cell );
        return "setUnit.xhtml?faces-redirect=true";
    }
    
    public String toPassport( Cell cell ){
        mySessionBean.setCurrentRecord( cell.getTmUnit().getRecords().get( 0 ) );
        mySessionBean.setCurrentCell( cell );
        return "/SuperUser/Record/showDoc.xhtml?faces-redirect=true";
    }
    
    /**
     * Создает файл алгоритма и скачивает его
     *
     * @param algorithm алгоритм
     */
    public void makeDocFile( Cell cell ) {
        Algorithm algorithm = cell.getAlgorithm();
        final String TEMP_PATH_KEY = "org.richfaces.fileUpload.tempFilesDirectory";
        // путь к временному файлу
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext external = ctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) external.getRequest();
        currentAlgorithmName =
                external.getInitParameter(TEMP_PATH_KEY)
                + File.separatorChar + request.getRemoteUser() + "_"
                + algorithm.getName()+algorithm.getId();
        try {
            ( new File( currentAlgorithmName ) ).createNewFile();
        } catch (IOException ex) {
            System.out.println("Can't create doc file: " + currentAlgorithmName);
        }
        try {
            algorithm.writeToFile(currentAlgorithmName);
        } catch (IOException ex) {
            System.out.println("Can't write doc file: " + currentAlgorithmName);
        }
        mySessionBean.downloadFile(currentAlgorithmName);
    }
    
    public boolean disabled( Cell cell ){
        TmUnit tmUnit = cell.getTmUnit();
        if( tmUnit == null ) { return false; }
        if( tmUnit.getRecords() != null ) { return true; }
        return false;
    }
    
    @PostConstruct
    public void postConstruct() {
        rocket = mySessionBean.getCurrentRocket();
        if ( rocket == null ) { return; }
    }
}
