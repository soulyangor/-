/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import com.tmis.entities.UnitType;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class CellFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public CellFacadeTest() {
    }
    //---------------------BeforeMethod--------------------------------------------

    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods-------------------------------------------
    /**
     * Тест метода добавления нового типа оборудования к ячейке
     *
     * @throws Exception
     */
    @Test
    public void addUnitTypeTest() throws Exception {
        logger.debug("Тест addUnitType cell");
        // новая ячейка
        Cell cell = new Cell("ОК", "метка 1");
        // сохранение ячейки в БД
        FacadeTest.getCellFacade().persist(cell);
        // получение id ячейки
        long idCell = cell.getId();
        // Создаём тип оборудования
        UnitType unitType = new UnitType("коммутатор");
        // Добавляем тип оборудования к ячейке
        FacadeTest.getCellFacade().addUnitType(cell, unitType);
        // получение id вставленного типа
        long idUnitType = unitType.getId();
        // ищем ячейку по id
        cell = FacadeTest.getCellFacade().find(idCell);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список типов оборудования
        long count = cell.getUnitTypes().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getUnitTypeFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // проверяем не пустой ли список ячеек
        count = unitType.getCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода присоединения существующего типа оборудования к ячейке
     *
     * @throws Exception
     */
    @Test
    public void connectUnitTypeTest() throws Exception {
        logger.debug("Тест connectUnitType cell");
        // новая ячейка
        Cell cell = new Cell("ОК", "метка 1");
        // сохранение ячейки в БД
        FacadeTest.getCellFacade().persist(cell);
        // получение id ячейки
        long idCell = cell.getId();
        // Создаём тип оборудования
        UnitType unitType = new UnitType("коммутатор");
        //Сохраняем в БД
        FacadeTest.getUnitTypeFacade().persist(unitType);
        // Присоединяем тип оборудования к ячейке
        FacadeTest.getCellFacade().connectUnitType(cell, unitType);
        // получение id вставленного типа
        long idUnitType = unitType.getId();
        // ищем ячейку по id
        cell = FacadeTest.getCellFacade().find(idCell);
        // ищем тип оборудования по id
        unitType = FacadeTest.getUnitTypeFacade().find(idUnitType);
        // проверяем не пустой ли список типов оборудования
        long count = cell.getUnitTypes().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getUnitTypeFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // проверяем не пустой ли список ячеек
        count = unitType.getCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода добавления новой подъячейки к ячейке
     *
     * @throws Exception
     */
    @Test
    public void addSubCellTest() throws Exception {
        logger.debug("Тест addSubCell cell");
        // новая ячейка
        Cell cell = new Cell("ОК", "метка 1");
        // сохранение ячейки в БД
        FacadeTest.getCellFacade().persist(cell);
        // получение id ячейки
        long idCell = cell.getId();
        // Создаём подъячейку
        Cell subCell = new Cell("ЛК", "метка 2");
        // Добавляем подъячейку к ячейке
        FacadeTest.getCellFacade().addSubCell(cell, subCell);
        // ищем ячейку по id
        cell = FacadeTest.getCellFacade().find(idCell);
        // проверяем не пустой ли список подъячеек
        long count = cell.getSubCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в таблице Cell должно быть 2 записи
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест метода копирования
     *
     * @throws Exception
     */
    @Test
    public void CopyTest() throws Exception {
        logger.debug("Тест copy cell");
        // новая ячейка
        Cell cell = new Cell("ОК", "метка 1");
        // сохранение ячейки в БД
        FacadeTest.getCellFacade().persist(cell);
        // получение id ячейки
        long idCell = cell.getId();
        // Создаём подъячейку
        Cell subCell = new Cell("ЛК", "метка 2");
        // Добавляем подъячейку к ячейке
        FacadeTest.getCellFacade().addSubCell(cell, subCell);
        //Добавляем 3-й уровень структуры
        FacadeTest.getCellFacade().addSubCell(subCell, new Cell("БК", "метка 3"));
        // ищем ячейку по id 
        cell = FacadeTest.getCellFacade().find(idCell);
        //Копируем
        Cell copy = FacadeTest.getCellFacade().copy(cell);
        // Число записей в таблице Cell должно удвоится должно стать 6
        long count = FacadeTest.getCellFacade().count();
        assertEquals(count, 6L, FacadeTest.ERROR_MESSAGE);
        // Ищем копию
        cell = FacadeTest.getCellFacade().find(copy.getId());
        // Копия должна быть найдена
        assertNotEquals(cell, null, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска ячейки по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName cell");
        // новая ячейка
        Cell cell = new Cell("ОК", "метка 1");
        // сохранение ячейки в БД
        FacadeTest.getCellFacade().persist(cell);
        //Создание ещё 2-х ячеек
        FacadeTest.getCellFacade().create(new Cell("ЛК", "метка 2"));
        FacadeTest.getCellFacade().create(new Cell("БК", "метка 3"));
        // поиск записи в таблице Cell
        long count = FacadeTest.getCellFacade().findByName("ОК").size();
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
        // новую ячейку
        Cell cell = new Cell("ОК", "метка 1");
        //создаём новый алгоритм
        Algorithm algorithm = new Algorithm("Алгоритм обхода");
        FacadeTest.getAlgorithmFacade().create(algorithm);
        //Устанавливаем для данной ячейки алгоритм
        cell.setAlgorithm(algorithm);
        // сохранение ячейки в БД
        FacadeTest.getCellFacade().persist(cell);
        // получение id ячейки
        long idCell = cell.getId();
        // Создаём тип оборудования
        UnitType unitType = new UnitType("Соединитель");
        // Добавляем тип оборудования к ячнейке
        //FacadeTest.getCellFacade().addUnitType( cell , unitType );
        // Создаём подъячейку
        Cell subCell = new Cell("ЛК1", "метка 2");
        // Добавляем 2 подъячейке к ячейке
        FacadeTest.getCellFacade().addSubCell(cell, subCell);
        FacadeTest.getCellFacade().addSubCell(cell, new Cell("ЛК2", "метка 3"));
        //Добавляем 3-й уровень структуры
        FacadeTest.getCellFacade().addSubCell(subCell, new Cell("БК1", "метка 4"));
        FacadeTest.getCellFacade().addSubCell(subCell, new Cell("БК2", "метка 5"));
        //Ищем ячейку по id
        cell = FacadeTest.getCellFacade().find(idCell);
        // Удаляем запись по id
        FacadeTest.getCellFacade().remove(cell.getId());
        long count = FacadeTest.getCellFacade().count();
        // в таблице Cell должно быть 0 записей
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
        // в таблице UnitType должно быть 0 записей
        count = FacadeTest.getUnitTypeFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }
}
