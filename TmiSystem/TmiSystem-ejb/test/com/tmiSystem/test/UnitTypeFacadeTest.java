/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Algorithm;
import com.tmis.entities.TmUnit;
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
public class UnitTypeFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public UnitTypeFacadeTest() {
    }
    //---------------------BeforeMethod--------------------------------------------

    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods-------------------------------------------
    /**
     * Тест метода присоединения типа значения к типу оборудования
     *
     * @throws Exception
     */
    @Test
    public void connectValueTypeTest() throws Exception {
        logger.debug("Тест connectValueType unitType");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        // Создаём тип значения
        ValueType valueType = new ValueType("General", "Общее", "Строка", "null");
        // сохранение типа значения в БД
        FacadeTest.getValueTypeFacade().persist(valueType);
        // Присоединяем тип значения к типу оборудования
        FacadeTest.getUnitTypeFacade().connectValueType(unitType, valueType);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список типов значений
        long count = unitType.getValueTypes().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getValueTypeFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода добавления нового оборудования к типу оборудования
     *
     * @throws Exception
     */
    @Test
    public void addTmUnitTest() throws Exception {
        logger.debug("Тест addTmUnit unitType");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        // Создаём оборудование
        TmUnit tmUnit = new TmUnit("ОКФЕ", "Основной коммутатор");
        // Добавляем оборудование к типу оборудования
        FacadeTest.getUnitTypeFacade().addTmUnit(unitType, tmUnit);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список оборудования
        long count = unitType.getTmUnits().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getTmUnitFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода присоединения оборудования к типу оборудования
     *
     * @throws Exception
     */
    @Test
    public void connectTmUnitTest() throws Exception {
        logger.debug("Тест connectTmUnit unitType");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        // Создаём оборудование
        TmUnit tmUnit = new TmUnit("ОКФЕ", "Основной коммутатор");
        // Сохраняем оборудование в БД
        FacadeTest.getTmUnitFacade().persist(tmUnit);
        // Присоединяем оборудование к типу оборудования
        FacadeTest.getUnitTypeFacade().connectTmUnit(unitType, tmUnit);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список оборудования
        long count = unitType.getTmUnits().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть 1 запись
        count = FacadeTest.getTmUnitFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода добавления подтипа к типу оборудования
     *
     * @throws Exception
     */
    @Test
    public void addSubTypeTest() throws Exception {
        logger.debug("Тест addTmUnit unitType");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        // Создаём подтип
        UnitType subType = new UnitType("Соединитель");
        // Добавляем подтип к типу оборудования
        FacadeTest.getUnitTypeFacade().addSubType(unitType, subType);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список оборудования 
        long count = unitType.getSubTypes().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в таблице  UnitType должно быть 2 записи
        count = FacadeTest.getUnitTypeFacade().count();
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода присоединения подтипа к типу оборудования
     *
     * @throws Exception
     */
    @Test
    public void connectSubTypeTest() throws Exception {
        logger.debug("Тест connectTmUnit unitType");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        // Создаём подтип
        UnitType subType = new UnitType("Соединитель");
        // сохранение подтипа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(subType);
        // Присоединяем подтип к типу оборудования
        FacadeTest.getUnitTypeFacade().connectSubType(unitType, subType);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список оборудования 
        long count = unitType.getSubTypes().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в таблице  UnitType должно быть 2 записи
        count = FacadeTest.getUnitTypeFacade().count();
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска типа оборудования по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName rocketType");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        //Создание ещё 2-х типов оборудования
        FacadeTest.getUnitTypeFacade().create(new UnitType("Датчик"));
        FacadeTest.getUnitTypeFacade().create(new UnitType("Шина"));
        // поиск записи в таблице UnitType
        long count = FacadeTest.getUnitTypeFacade().findByName("Коммутатор").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы Cell
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        logger.debug("Тест remove cell");
        // новый тип оборудования
        UnitType unitType = new UnitType("Коммутатор");
        //создаём новый алгоритм
        Algorithm algorithm = new Algorithm("Алгоритм обхода");
        FacadeTest.getAlgorithmFacade().create(algorithm);
        // сохранение типа оборудования в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // получение id типа оорудования
        long idUnitType = unitType.getId();
        // Создаём тип значения
        ValueType valueType = new ValueType("General", "Общее", "Строка", "null");
        //Сохраняем тип значения в БД
        FacadeTest.getValueTypeFacade().persist(valueType);
        // Добавляем тип значения к типу оборудования
        FacadeTest.getUnitTypeFacade().connectValueType(unitType, valueType);
        // Создаём подтип
        UnitType subType = new UnitType("Соединитель");
        // Добавляем подтип к типу оборудования
        FacadeTest.getUnitTypeFacade().addSubType(unitType, subType);
        // Создаём оборудование
        TmUnit tmUnit = new TmUnit("ОКФЕ", "Основной коммутатор");
        // Добавляем оборудование к типу оборудования
        FacadeTest.getUnitTypeFacade().addTmUnit(unitType, tmUnit);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        //Создание ещё 2-х типов оборудования
        FacadeTest.getUnitTypeFacade().create(new UnitType("Шина"));
        FacadeTest.getUnitTypeFacade().create(new UnitType("Датчик"));
        // Удаляем запись по id
        FacadeTest.getUnitTypeFacade().remove(unitType.getId());
        long count = FacadeTest.getUnitTypeFacade().count();
        // в таблице UnitType должно быть 3 записи
        assertEquals(count, 3L, FacadeTest.ERROR_MESSAGE);
        // в таблице ValueType должно быть 0 записей
        count = FacadeTest.getValueTypeFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
        // в таблице TmUnit должно быть 0 записей
        count = FacadeTest.getTmUnitFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }
}
