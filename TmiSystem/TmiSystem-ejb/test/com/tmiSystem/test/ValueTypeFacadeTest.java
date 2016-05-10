/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Partition;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class ValueTypeFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public ValueTypeFacadeTest() {
    }
    //---------------------BeforeMethod--------------------------------------------

    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods-------------------------------------------
    /**
     * Тест поиска типа значения по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName valueType");
        // новый тип значения
        ValueType valueType = new ValueType("TAH", "ТАХ", "таблица", "null");
        // сохранение типа ззначения в БД
        FacadeTest.getValueTypeFacade().persist(valueType);
        //Создание ещё 2-х типов значений
        FacadeTest.getValueTypeFacade().create(new ValueType("General", "Общее", "Строка", "null"));
        FacadeTest.getValueTypeFacade().create(new ValueType("Consist", "Состав", "Строка", "null"));
        // поиск записи в таблице ValueType
        long count = FacadeTest.getValueTypeFacade().findByName("ТАХ").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска типа значения по разделу и типу оборудования
     *
     * @throws Exception
     */
    @Test
    public void testFindByPartitionAndUnitType() throws Exception {
        logger.debug("Тест findByPartitionAndUnitType valueType");
        // новый тип значения
        ValueType valueType = new ValueType("TAH", "ТАХ", "таблица", "null");
        //новый раздел
        Partition partition = new Partition(" Раздел 1 ");
        // сохранение раздела в БД
        FacadeTest.getPartitionFacade().persist(partition);
        // получение id раздела
        long idPartition = partition.getId();
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        //добавляем к разделу тип значения
        FacadeTest.getPartitionFacade().addValueType(partition, valueType);
        //пррисоединяем к типу оборудования тип значения
        FacadeTest.getUnitTypeFacade().connectValueType(unitType, valueType);
        //Создание ещё 2-х типов значений
        FacadeTest.getValueTypeFacade().create(new ValueType("General", "Общее", "Строка", "null"));
        FacadeTest.getValueTypeFacade().create(new ValueType("Consist", "Состав", "Строка", "null"));
        // ищем раздел по id
        partition = FacadeTest.getPartitionFacade().find(idPartition);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // поиск записи в таблице ValueType
        long count = FacadeTest.getValueTypeFacade().findByPartitionAndUnitType(partition, unitType).size();
        // должны быть найдены записи
        System.out.println(count);
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска типа значения по nameId
     *
     * @throws Exception
     */
    @Test
    public void testFindByNameId() throws Exception {
        logger.debug("Тест findByNameId valueType");
        // новый тип значения
        ValueType valueType = new ValueType("TAH", "ТАХ", "таблица", "null");
        // сохранение типа ззначения в БД
        FacadeTest.getValueTypeFacade().persist(valueType);
        //Создание ещё 2-х типов значений
        FacadeTest.getValueTypeFacade().create(new ValueType("General", "Общее", "Строка", "null"));
        FacadeTest.getValueTypeFacade().create(new ValueType("Consist", "Состав", "Строка", "null"));
        // поиск записи в таблице ValueType
        long count = FacadeTest.getValueTypeFacade().findByNameId("TAH").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы ValueType
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        logger.debug("Тест remove valueType");
        // новый тип значения
        ValueType valueType = new ValueType("TAH", "ТАХ", "таблица", "null");
        // сохранение типа ззначения в БД
        FacadeTest.getValueTypeFacade().persist(valueType);
        // получение id типа значения
        long idValueType = valueType.getId();
        // ищем тип значения по id
        valueType = FacadeTest.getValueTypeFacade().find(idValueType);
        //Создание ещё 2-х типов значений
        FacadeTest.getValueTypeFacade().create(new ValueType("General", "Общее", "Строка", "null"));
        FacadeTest.getValueTypeFacade().create(new ValueType("Consist", "Состав", "Строка", "null"));
        // Удаляем запись по id
        FacadeTest.getValueTypeFacade().remove(valueType.getId());
        long count = FacadeTest.getValueTypeFacade().count();
        // в таблице ValueType должно быть 2 записи
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
    }
}
