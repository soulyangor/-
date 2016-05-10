/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.entities.RocketType;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class RocketFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public RocketFacadeTest() {
    }

    //---------------------BeforeMethod--------------------------------------------
    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods--------------------------------------------
    /**
     * Тест добавления нового блока структуры к РН
     *
     * @throws Exception
     */
    @Test
    public void addCellTest() throws Exception {
        logger.debug("Тест addCell rocket");
        // новый РН
        Rocket rocket = new Rocket("Агат", "метка");
        // сохранение РН в БД
        FacadeTest.getRocketFacade().persist(rocket);
        // получение id РН
        long idRocket = rocket.getId();
        // Создаём блок (начало структуры)
        Cell cell = new Cell("1-я ступень", "метка");
        // Добавляем блок к РН
        FacadeTest.getRocketFacade().addCell(rocket, cell);
        // ищем РН по id
        rocket = FacadeTest.getRocketFacade().find(idRocket);
        // проверяем не пустой ли список блоков
        long count = rocket.getCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест присоединения блока структуры к РН
     *
     * @throws Exception
     */
    @Test
    public void connectCellTest() throws Exception {
        logger.debug("Тест connectCell rocket");
        // новый РН
        Rocket rocket = new Rocket("Агат", "метка");
        // сохранение РН в БД
        FacadeTest.getRocketFacade().persist(rocket);
        // получение id РН
        long idRocket = rocket.getId();
        // Создаём блок (начало структуры)
        Cell cell = new Cell("1-я ступень", "метка");
        //Сохраняем блок в БД
        FacadeTest.getCellFacade().persist(cell);
        // Присоединяем блок к РН
        FacadeTest.getRocketFacade().connectCell(rocket, cell);
        // ищем РН по id
        rocket = FacadeTest.getRocketFacade().find(idRocket);
        // проверяем не пустой ли список блоков
        long count = rocket.getCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска РН по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName rocket");
        // новый РН
        Rocket rocket = new Rocket("511 М", "метка");
        // сохранение РН в БД
        FacadeTest.getRocketFacade().persist(rocket);
        //Создание ещё 2-х модификаций РН
        FacadeTest.getRocketFacade().create(new Rocket("412", "метка"));
        FacadeTest.getRocketFacade().create(new Rocket("511", "метка"));
        // поиск записи в таблице Rocket
        long count = FacadeTest.getRocketFacade().findByName("412").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска РН по типу
     *
     * @throws Exception
     */
    @Test
    public void testFindByType() throws Exception {
        logger.debug("Тест findByType rocket");
        // новый тип РН
        RocketType rocketType = new RocketType("Союз", "метка");
        // сохранение типа РН в БД
        FacadeTest.getRocketTypeFacade().persist(rocketType);
        //новая модификация РН
        RocketMod rocketMod = new RocketMod("Союз-2", "метка");
        // Добавляем модификацию РН к типу РН
        FacadeTest.getRocketTypeFacade().addRocketMod(rocketType, rocketMod);
        // новый РН
        Rocket rocket = new Rocket("511 М", "метка");
        // добавляем РН к модификации
        FacadeTest.getRocketModFacade().addRocket(rocketMod, rocket);
        //Создание ещё 2-х модификаций РН
        FacadeTest.getRocketFacade().create(new Rocket("412", "метка"));
        FacadeTest.getRocketFacade().create(new Rocket("511", "метка"));
        // поиск записи в таблице Rocket
        long count = FacadeTest.getRocketFacade().findByType(rocketType).size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы Rocket
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        logger.debug("Тест remove rocketMod");
        // новый РН
        Rocket rocket = new Rocket("511М", "метка");
        // сохранение РН в БД
        FacadeTest.getRocketFacade().persist(rocket);
        // получение id РН
        long idRocket = rocket.getId();
        // Создаём блок (начало структуры)
        Cell cell = new Cell("1-я ступень", "метка");
        // Добавляем блок к РН
        FacadeTest.getRocketFacade().addCell(rocket, cell);
        // ищем РН по id
        rocket = FacadeTest.getRocketFacade().find(idRocket);
        //Создание ещё 2-х РН
        FacadeTest.getRocketFacade().create(new Rocket("412", "метка"));
        FacadeTest.getRocketFacade().create(new Rocket("511", "метка"));
        // Удаляем запись по id
        FacadeTest.getRocketFacade().remove(rocket.getId());
        long count = FacadeTest.getRocketFacade().count();
        // в таблице Rocket должно быть 2 записи
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
        // в таблице Cell должно быть 0 записей
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }
}
