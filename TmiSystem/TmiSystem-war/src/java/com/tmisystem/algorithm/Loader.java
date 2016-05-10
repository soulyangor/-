/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Бин для загрузки файла
 *
 * @author Sokolov Slava
 */
@Stateless
@LocalBean
public class Loader {

    public Loader() {}

    /**
     * Сохранение потока в файл
     *
     * @param inputStream входной поток
     * @param fileName файл для сохранения
     */
    public static void inputStreamToFile( InputStream inputStream,
            String fileName ) {
        // создание выходного файла
        File file = new File( fileName );
        file.delete();
        try {
            if (!file.createNewFile()) {
                throw new IOException();
            }
        } catch (IOException ex) {
            System.out.println(
                    "Can't create output file" + fileName + " for write input stream" + ex);
        }
        // создание буфера
        final int BUFF_LENGTH = 65536;
        byte[] buf = new byte[BUFF_LENGTH];
        // запись потока в файл
        int b;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileName);
            while ((b = inputStream.read(buf, 0, BUFF_LENGTH)) > 0) {
                fos.write(buf, 0, b);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Can't find output file" + fileName
                    + " for write input stream" + ex);
        } catch (IOException ex) {
            System.out.println("Error process file" + fileName
                    + " for write input stream" + ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                }
            }
        }
    }
    
}
