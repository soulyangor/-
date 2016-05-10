/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

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
public class RocketTypeFacadeTest {

    //------------------------Logger-------------------------------------------------
    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public RocketTypeFacadeTest() {
    }
    //---------------------BeforeMethod--------------------------------------------

    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods-------------------------------------------
    /**
     * Тест метода добавления новой модификации к типу РН
     *
     * @throws Exception
     */
    @Test
    public void addRocketModTest() throws Exception {
        logger.debug("Тест addRocketMod rocketType");
        // новый тип РН
        RocketType rocketType = new RocketType("Союз", "метка");
        // сохранение типа РН в БД
        FacadeTest.getRocketTypeFacade().persist(rocketType);
        // получение id типа РН
        long idRocketType = rocketType.getId();
        // Создаём модификацию РН
        RocketMod rocketMod = new RocketMod("М 511", "метка");
        // Добавляем модификацию РН к типу РН
        FacadeTest.getRocketTypeFacade().addRocketMod(rocketType, rocketMod);
        // ищем тип РН по id
        rocketType = FacadeTest.getRocketTypeFacade().find(idRocketType);
        // проверяем не пустой ли список модификаций
        long count = rocketType.getModifications().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getRocketModFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест поиска типа РН по имени
     *
     * @throws Exception
     */
    @Test
    public void testFindByName() throws Exception {
        logger.debug("Тест findByName rocketType");
        // новый тип РН
        RocketType rocketType = new RocketType("Союз", "метка");
        // сохранение типа РН в БД
        FacadeTest.getRocketTypeFacade().persist(rocketType);
        //Создание ещё 2-х типов РН
        FacadeTest.getRocketTypeFacade().create(new RocketType("Зенит", "метка"));
        FacadeTest.getRocketTypeFacade().create(new RocketType("Протон", "метка"));
        // поиск записи в таблице RocketType
        long count = FacadeTest.getRocketTypeFacade().findByName("Протон").size();
        // должны быть найдены записи
        assertNotEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }

    /**
     * Тест удаления записи из таблицы RocketType
     *
     * @throws Exception
     */
    @Test
    public void testRemove() throws Exception {
        logger.debug("Тест remove rocketType");
        // новый тип РН
        RocketType rocketType = new RocketType("Союз", "метка");
        // сохранение типа РН в БД
        FacadeTest.getRocketTypeFacade().persist(rocketType);
        // получение id типа РН
        long idRocketType = rocketType.getId();
        // Создаём модификацию РН
        RocketMod rocketMod = new RocketMod("М 511", "метка");
        // Добавляем модификацию РН к типу РН
        FacadeTest.getRocketTypeFacade().addRocketMod(rocketType, rocketMod);
        // ищем тип РН по id
        rocketType = FacadeTest.getRocketTypeFacade().find(idRocketType);
        //Создание ещё 2-х типов РН
        FacadeTest.getRocketTypeFacade().create(new RocketType("Зенит", "метка"));
        FacadeTest.getRocketTypeFacade().create(new RocketType("Протон", "метка"));
        // Удаляем запись по id
        FacadeTest.getRocketTypeFacade().remove(rocketType.getId());
        long count = FacadeTest.getRocketTypeFacade().count();
        // в таблице RocketType должно быть 2 записи
        assertEquals(count, 2L, FacadeTest.ERROR_MESSAGE);
        // в таблице RocketMod должно быть 0 записей
        count = FacadeTest.getRocketModFacade().count();
        assertEquals(count, 0L, FacadeTest.ERROR_MESSAGE);
    }
}
