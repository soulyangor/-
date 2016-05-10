/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Partition;
import com.tmis.entities.ValueType;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class PartitionFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public PartitionFacadeTest() {
    }
    //---------------------BeforeMethod--------------------------------------------

    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods-------------------------------------------
    /**
     * Тест метода добавления нового типа значения к разделу
     *
     * @throws Exception
     */
    @Test
    public void addValueTypeTest() throws Exception {
        logger.debug("Тест addValueType partition");
        // новый раздел
        Partition partition = new Partition("Общее");
        // сохранение раздела в БД
        FacadeTest.getPartitionFacade().persist(partition);
        // получение id раздела
        long idPartition = partition.getId();
        // Создаём новый тип значения
        ValueType valueType = new ValueType("General", "Общее", "Строка", "null");
        // Добавляем тип значения к разделу
        FacadeTest.getPartitionFacade().addValueType(partition, valueType);
        // ищем раздел по id
        partition = FacadeTest.getPartitionFacade().find(idPartition);
        // проверяем не пустой ли список типов значений
        long count = partition.getValueTypes().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getValueTypeFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы Partition
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        logger.debug("Тест remove partition");
        // новый раздел
        Partition partition = new Partition("Общее");
        // сохранение раздела в БД
        FacadeTest.getPartitionFacade().persist(partition);
        // получение id раздела
        long idPartition = partition.getId();
        // Создаём новый тип значения
        ValueType valueType = new ValueType("General", "Общее", "Строка", "null");
        // Добавляем тип значения к разделу
        FacadeTest.getPartitionFacade().addValueType(partition, valueType);
        // ищем раздел по id
        partition = FacadeTest.getPartitionFacade().find(idPartition);
        //Создание ещё 2-х разделов
        FacadeTest.getPartitionFacade().create(new Partition("Состав"));
        FacadeTest.getPartitionFacade().create(new Partition("ТХ"));
        // Удаляем запись по id
        FacadeTest.getPartitionFacade().remove(partition.getId());
        long count = FacadeTest.getPartitionFacade().count();
        // в таблице Partition должно быть 2 записи
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
        // в таблице ValueType должно быть 0 записей
        count = FacadeTest.getValueTypeFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }
}
