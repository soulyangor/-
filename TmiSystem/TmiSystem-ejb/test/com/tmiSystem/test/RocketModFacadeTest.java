/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class RocketModFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public RocketModFacadeTest() {
    }

    //---------------------BeforeMethod--------------------------------------------
    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods--------------------------------------------
    /**
     * Тест добавления нового РН к модификации РН
     *
     * @throws Exception
     */
    @Test
    public void addRocketTest() throws Exception {
        logger.debug("Тест addRocket rocket");
        // новая модификация РН
        RocketMod rocketMod = new RocketMod("511М", "метка");
        // сохранение модификации РН в БД
        FacadeTest.getRocketModFacade().persist(rocketMod);
        // получение id модификации РН
        long idRocketMod = rocketMod.getId();
        // Создаём РН
        Rocket rocket = new Rocket("Агат", "метка");
        // Добавляем РН к модификации РН
        FacadeTest.getRocketModFacade().addRocket(rocketMod, rocket);
        // ищем модификацию РН по id
        rocketMod = FacadeTest.getRocketModFacade().find(idRocketMod);
        // проверяем не пустой ли список РН
        long count = rocketMod.getRockets().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getRocketFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест добавления нового блока шаблона к модификации РН
     *
     * @throws Exception
     */
    @Test
    public void addCellTest() throws Exception {
        logger.debug("Тест addCell rocketMod");
        // новая модификация РН
        RocketMod rocketMod = new RocketMod("511М", "метка");
        // сохранение модификации РН в БД
        FacadeTest.getRocketModFacade().persist(rocketMod);
        // получение id модификации РН
        long idRocketMod = rocketMod.getId();
        // Создаём блок (начало шаблона)
        Cell cell = new Cell("1-я ступень", "метка");
        // Добавляем блок к модификации РН
        FacadeTest.getRocketModFacade().addCell(rocketMod, cell);
        // ищем модификацию РН по id
        rocketMod = FacadeTest.getRocketModFacade().find(idRocketMod);
        // проверяем не пустой ли список блоков
        long count = rocketMod.getCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска модификации РН по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName rocketMod");
        // новая модификация РН
        RocketMod rocketMod = new RocketMod("511 М", "метка");
        // сохранение модификации РН в БД
        FacadeTest.getRocketModFacade().persist(rocketMod);
        //Создание ещё 2-х модификаций РН
        FacadeTest.getRocketModFacade().create(new RocketMod("412", "метка"));
        FacadeTest.getRocketModFacade().create(new RocketMod("511", "метка"));
        // поиск записи в таблице RocketMod
        long count = FacadeTest.getRocketModFacade().findByName("412").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы RocketMod
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        logger.debug("Тест remove rocketMod");
        // новая модификация РН
        RocketMod rocketMod = new RocketMod("511М", "метка");
        // сохранение модификации РН в БД
        FacadeTest.getRocketModFacade().persist(rocketMod);
        // получение id модификации РН
        long idRocketMod = rocketMod.getId();
        // Создаём блок (начало шаблона)
        Cell cell = new Cell("1-я ступень", "метка");
        // Добавляем блок к модификации РН
        FacadeTest.getRocketModFacade().addCell(rocketMod, cell);
        // Создаём РН
        Rocket rocket = new Rocket("Агат", "метка");
        // Добавляем РН к модификации РН
        FacadeTest.getRocketModFacade().addRocket(rocketMod, rocket);
        // ищем модификацию РН по id
        rocketMod = FacadeTest.getRocketModFacade().find(idRocketMod);
        //Создание ещё 2-х модификаций РН
        FacadeTest.getRocketModFacade().create(new RocketMod("412", "метка"));
        FacadeTest.getRocketModFacade().create(new RocketMod("511", "метка"));
        // Удаляем запись по id
        FacadeTest.getRocketModFacade().remove(rocketMod.getId());
        long count = FacadeTest.getRocketModFacade().count();
        // в таблице RocketMod должно быть 2 записи
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
        // в таблице Rocket должно быть 0 записей
        count = FacadeTest.getRocketFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
        // в таблице Cell должно быть 0 записей
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }
}
