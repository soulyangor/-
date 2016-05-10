/*
 * Сущность для хранения и загрузки
 * документов и алгоритмов
 */
package com.tmis.entities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Таблица байт-кодов алгоритмов
 * @author Sokolov Slava
 */
@Entity
public class DocData implements Serializable {
    //---------------------Constants----------------------------------------------

    private static final long serialVersionUID = 1L;
    //---------------------Fields-------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // данные документа
    @Lob
    private byte[] docData;

    //--------------------Constructors--------------------------------------------
    /**
     * Дефолтный конструктор
  *
     */
    public DocData() { }
    public DocData(byte[] docData) { setDocData(docData); }
    public DocData(String fileName) throws IOException { setDocData(fileName); }

    //--------------------Getters and setters-------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public byte[] getDocData() {
        if (docData == null || docData.length == 0) {
            return docData;
        }
        final int BUF_LEN = 4096;
        byte[] buf = new byte[4096];
        ByteArrayInputStream bais = new ByteArrayInputStream(docData);
        Inflater inflater = new Inflater();
        InflaterInputStream iis =
                new InflaterInputStream(bais, inflater, docData.length);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int res;
        do {
            res = 0;
            try {
                res = iis.read(buf, 0, BUF_LEN);
                if (res > 0) {
                    bos.write(buf, 0, res);
                }
            } catch (IOException ex) {
            } // не случится никогда
        } while (res == BUF_LEN);
        try {
            iis.close();
        } catch (IOException ex) {
        } // не случится никогда
        return bos.toByteArray();
    }

    public void setDocData(String fileName) throws IOException,
            FileNotFoundException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            this.docData = data;
            zipDocData();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    public void setDocData(byte[] docData) {
        this.docData = docData;
        zipDocData();
    }

    //--------------------Methods-------------------------------------------------
    private void zipDocData() {
        if (docData == null || docData.length == 0) {
            return;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
            DeflaterOutputStream dfos = new DeflaterOutputStream(baos, compresser);
            dfos.write(docData, 0, docData.length);
            dfos.finish();
            docData = baos.toByteArray();
        } catch (IOException ex) {
        } // не случится никогда 
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DocData)) {
            return false;
        }
        DocData other = (DocData) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nestos.entity.DocData[ id=" + id + " ]";
    }
}
