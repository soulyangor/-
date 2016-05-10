/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.algorithm;

import com.tmis.facade.AlgorithmFacade;
import java.io.File;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean( name = "algorithm")
@ViewScoped
public class AlgorithmBean {

    @EJB
    AlgorithmFacade algorithmFacade;
    
    private String name;
    private String className;
    
    // полное имя загруженного файла документа
    private String fileName;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public AlgorithmBean() { }

    /**
     * Обработка загруженного файла документа
     */
    public void listener(FileUploadEvent event) {
        final String TEMP_PATH_KEY = "org.richfaces.fileUpload.tempFilesDirectory";
        UploadedFile file = event.getUploadedFile();

        // путь к временному файлу
        FacesContext ctx = FacesContext.getCurrentInstance();
        fileName = ctx.getExternalContext().getInitParameter(TEMP_PATH_KEY)
                + File.separatorChar + file.getName();

        // сохранение InputStream в файл
        try {
            Loader.inputStreamToFile(file.getInputStream(), fileName);
        } catch (IOException ex) {
            System.out.println("Error copy stream to file" + ex);
        }
        name = file.getName();
        name = name.substring(0, name.lastIndexOf('.'));
    }

    /**
     * Сохраняет алгоритм
     *
     * @return переход к таблице алгоритмов
     */
    public String persistDoc() throws IOException {
        String ext = fileName.substring(fileName.lastIndexOf("."));
        try {
            algorithmFacade.addDoc( name, className, fileName );
        } catch (EJBException ex) {
            System.out.println("Error add new Doc to algorithm ");
            return "error";
        }
        return "algorithmTable.xhtml?faces-redirect=true";
    }

}
