/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.algorithm;

import com.tmis.entities.Algorithm;
import com.tmis.facade.AlgorithmFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.io.File;
import java.io.IOException;
import java.util.List;
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
@ManagedBean( name = "algorithms")
@ViewScoped
public class AlgorithmsBean {

    @EJB
    AlgorithmFacade algorithmFacade;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    
    private List<Algorithm> algorithms;
    private String currentAlgorithmName;

    public AlgorithmsBean() { }

    public void setMySessionBean( MySessionBean mySessionBean ) {
        this.mySessionBean = mySessionBean;
    }

    public List<Algorithm> getAlgorithms() {
        if ( algorithms != null ) { return algorithms; }
        algorithms = algorithmFacade.findAll();
        return algorithms;
    }
    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

    public void delete() {
        for (Algorithm a : algorithms) {
            if (a.isSelected()) {
                algorithmFacade.remove(a.getId());
            }
        }
        algorithms = null;
    }

    /**
     * Создает файл алгоритма и скачивает его
     *
     * @param algorithm алгоритм
     */
    public void makeDocFile( Algorithm algorithm ) {
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
    
    public String toNewAlgorithm(){
        return "uploadAlgorithm.xhtml?faces-redirect=true"; 
    }

}
