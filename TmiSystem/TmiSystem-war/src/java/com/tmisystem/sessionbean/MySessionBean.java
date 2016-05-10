/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.sessionbean;

import com.tmis.entities.Account;
import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import com.tmis.entities.Partition;
import com.tmis.entities.Record;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.entities.RocketType;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import com.tmis.mongo.Elem;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "mySession")
@SessionScoped
public class MySessionBean {

    private static final int DEFAULT_BUFFER_SIZE = 10240;
    private RocketType currentRocketType;
    private RocketMod currentRocketMod;
    private Rocket currentRocket;
    private Partition currentPartition;
    private UnitType currentUnitType;
    private UnitType currentSuperType;
    private ValueType currentValueType;
    private Cell currentCell;
    private Cell currentSuperCell;
    private TmUnit currentTmUnit;
    private TmUnit currentSuperUnit;
    private Record currentRecord;
    private Algorithm currentAlgorithm;
    private Elem currentParam;
    private String currentUser;

    public MySessionBean() {}

    public RocketType getCurrentRocketType() { return currentRocketType; }
    public void setCurrentRocketType(RocketType currentRocketType) {
        this.currentRocketType = currentRocketType;
    }

    public RocketMod getCurrentRocketMod() { return currentRocketMod; }
    public void setCurrentRocketMod(RocketMod currentRocketMod) {
        this.currentRocketMod = currentRocketMod;
    }

    public Rocket getCurrentRocket() { return currentRocket; }
    public void setCurrentRocket(Rocket currentRocket) {
        this.currentRocket = currentRocket;
    }

    public Partition getCurrentPartition() { return currentPartition; }
    public void setCurrentPartition(Partition currentPartition) {
        this.currentPartition = currentPartition;
    }

    public UnitType getCurrentUnitType() { return currentUnitType; }
    public void setCurrentUnitType(UnitType currentUnitType) {
        this.currentUnitType = currentUnitType;
    }

    public UnitType getCurrentSuperType() { return currentSuperType; }
    public void setCurrentSuperType(UnitType currentSuperType) {
        this.currentSuperType = currentSuperType;
    }

    public ValueType getCurrentValueType() { return currentValueType; }
    public void setCurrentValueType(ValueType currentValueType) {
        this.currentValueType = currentValueType;
    }

    public Cell getCurrentCell() { return currentCell; }
    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public Cell getCurrentSuperCell() { return currentSuperCell; }
    public void setCurrentSuperCell(Cell currentSuperCell) {
        this.currentSuperCell = currentSuperCell;
    }

    public TmUnit getCurrenTmUnit() { return currentTmUnit; }
    public void setCurrentTmUnit(TmUnit currentTmUnit) {
        this.currentTmUnit = currentTmUnit;
    }

    public TmUnit getCurrentSuperUnit() { return currentSuperUnit; }
    public void setCurrentSuperUnit(TmUnit currentSuperUnit) {
        this.currentSuperUnit = currentSuperUnit;
    }

    public Record getCurrentRecord() { return currentRecord; }
    public void setCurrentRecord(Record currentRecord) {
        this.currentRecord = currentRecord;
    }

    public Algorithm getCurrentAlgorithm() { return currentAlgorithm; }
    public void setCurrentAlgorithm(Algorithm currentAlgorithm) {
        this.currentAlgorithm = currentAlgorithm;
    }    

    public Elem getCurrentParam() { return currentParam; }
    public void setCurrentParam(Elem currentParam) {
        this.currentParam = currentParam;
    } 

    public String getCurrentUser() { return currentUser; }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Скачивает файл
     */
    public void downloadFile(String fileName) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context
                .getExternalContext().getResponse();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            } catch (IOException ex) {
            }
        }
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment;filename=\""
                + file.getName() + "\"");
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file),
                    DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(),
                    DEFAULT_BUFFER_SIZE);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (IOException ex) {
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException ex) {
            }
        }
        context.responseComplete();
    }
    
    /**
     * Прекращение сессии
     */
    public void logout() throws IOException {  
        ExternalContext context =
        FacesContext.getCurrentInstance().getExternalContext();
        context.invalidateSession();
        context.redirect("/TmiSystem-war");
    }
}
