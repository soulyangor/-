/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Record;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class RecordFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public RecordFacadeTest() {
    }
    //---------------------BeforeMethod--------------------------------------------

    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods-------------------------------------------
    /**
     * Тест поиска документа по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName record");
        // новый документ
        Record record = new Record("Док1");
        // сохранение документа в БД
        FacadeTest.getRecordFacade().persist(record);
        //Создание ещё 2-х документов
        FacadeTest.getRecordFacade().create(new Record("Док2"));
        FacadeTest.getRecordFacade().create(new Record("Док3"));
        // поиск записи в таблице Record
        long count = FacadeTest.getRecordFacade().findByName("Док1").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы Record
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        // новый документ
        Record record = new Record("Док1");
        // сохранение документа в БД
        FacadeTest.getRecordFacade().persist(record);
        // получение id документа
        long idRecord = record.getId();
        // ищем документ по id
        record = FacadeTest.getRecordFacade().find(idRecord);
        //Создание ещё 2-х документов
        FacadeTest.getRecordFacade().create(new Record("Док2"));
        FacadeTest.getRecordFacade().create(new Record("Док3"));
        // Удаляем запись по id
        FacadeTest.getRecordFacade().remove(record.getId());
        long count = FacadeTest.getRecordFacade().count();
        // в таблице Record должно быть 2 записи
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
    }
}
