/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import org.apache.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sokolov Slava
 */
public class AlgorithmFacadeTest {
    //------------------------Logger-------------------------------------------------

    private static Logger logger =
            Logger.getLogger(RocketTypeFacadeTest.class.getName());

    public AlgorithmFacadeTest() {
    }

    //---------------------BeforeMethod--------------------------------------------
    @BeforeMethod
    public static void beforeTest() throws Exception {
        FacadeTest.clearDataBase();
    }

    //---------------------Test methods--------------------------------------------
    /**
     * Тест добавления нового блока структуры к алгоритму
     *
     * @throws Exception
     */
    @Test
    public void addCellTest() throws Exception {
        logger.debug("Тест addCell algorithm");
        // новый алгоритм
        Algorithm algorithm = new Algorithm("Для ОК");
        // сохранение алгоритма в БД
        FacadeTest.getAlgorithmFacade().persist(algorithm);
        // получение id алгоритма
        long idAlgorithm = algorithm.getId();
        // Создаём блок (начало структуры)
        Cell cell = new Cell("1-я ступень", "метка");
        // Добавляем блок к алгоритму
        FacadeTest.getAlgorithmFacade().addCell(algorithm, cell);
        // ищем алгоритм по id
        algorithm = FacadeTest.getAlgorithmFacade().find(idAlgorithm);
        // проверяем не пустой ли список блоков
        long count = algorithm.getCells().size();
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
        logger.debug("Тест addCell algorithm");
        // новый алгоритм
        Algorithm algorithm = new Algorithm("Для ОК");
        // сохранение алгоритма в БД
        FacadeTest.getAlgorithmFacade().persist(algorithm);
        // получение id алгоритма
        long idAlgorithm = algorithm.getId();
        // Создаём блок (начало структуры)
        Cell cell = new Cell("1-я ступень", "метка");
        // Сохраняем блок в БД
        FacadeTest.getCellFacade().persist(cell);
        // Присоединяем блок к алгоритму
        FacadeTest.getAlgorithmFacade().connectCell(algorithm, cell);
        // ищем алгоритм по id
        algorithm = FacadeTest.getAlgorithmFacade().find(idAlgorithm);
        // проверяем не пустой ли список блоков
        long count = algorithm.getCells().size();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
        // в зависимой таблице должна быть одна запись
        count = FacadeTest.getCellFacade().count();
        assertEquals(count, 1L, FacadeTest.ERROR_MESSAGE);
    }
}
