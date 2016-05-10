/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmiSystem.test;

import com.tmis.facade.AlgorithmFacade;
import com.tmis.facade.CellFacade;
import com.tmis.facade.PartitionFacade;
import com.tmis.facade.RecordFacade;
import com.tmis.facade.RocketFacade;
import com.tmis.facade.RocketModFacade;
import com.tmis.facade.RocketTypeFacade;
import com.tmis.facade.TmUnitFacade;
import com.tmis.facade.UnitTypeFacade;
import com.tmis.facade.ValueTypeFacade;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 *
 * @author Sokolov Slava
 */
public class FacadeTest {

    //---------------------Constants----------------------------------------------
    public static final String ERROR_MESSAGE = "Ошибка";
    private static final String APPLICATION_NAME = "TmiSystem-ejb";
    private static final String INSTANCE_ROOT =
            "org.glassfish.ejb.embedded.glassfish.instance.root";
    private static final String DOMAIN_PATH =
            "F:\\glassfish-3.1.2.2\\glassfish\\domains\\domain1";
    private static final String JNDI_NAME_UNIT_TYPE_FACADE =
            "java:global/TmiSystem-ejb/classes/UnitTypeFacade";
    private static final String JNDI_NAME_VALUE_TYPE_FACADE =
            "java:global/TmiSystem-ejb/classes/ValueTypeFacade";
    private static final String JNDI_NAME_TMUNIT_FACADE =
            "java:global/TmiSystem-ejb/classes/TmUnitFacade";
    private static final String JNDI_NAME_ROCKET_TYPE_FACADE =
            "java:global/TmiSystem-ejb/classes/RocketTypeFacade";
    private static final String JNDI_NAME_ROCKET_MOD_FACADE =
            "java:global/TmiSystem-ejb/classes/RocketModFacade";
    private static final String JNDI_NAME_ROCKET_FACADE =
            "java:global/TmiSystem-ejb/classes/RocketFacade";
    private static final String JNDI_NAME_CELL_FACADE =
            "java:global/TmiSystem-ejb/classes/CellFacade";
    private static final String JNDI_NAME_PARTITION_FACADE =
            "java:global/TmiSystem-ejb/classes/PartitionFacade";
    private static final String JNDI_NAME_ALGORITHM_FACADE =
            "java:global/TmiSystem-ejb/classes/AlgorithmFacade";
    private static final String JNDI_NAME_RECORD_FACADE =
            "java:global/TmiSystem-ejb/classes/RecordFacade";
    //---------------------Logger-------------------------------------------------
    private static final Logger logger =
            Logger.getLogger(FacadeTest.class.getName());
    //---------------------Fields-------------------------------------------------
    public static EJBContainer container;
    private static UnitTypeFacade unitTypeFacade;
    private static ValueTypeFacade valueTypeFacade;
    private static TmUnitFacade tmUnitFacade;
    private static RocketTypeFacade rocketTypeFacade;
    private static RocketModFacade rocketModFacade;
    private static RocketFacade rocketFacade;
    private static CellFacade cellFacade;
    private static PartitionFacade partitionFacade;
    private static AlgorithmFacade algorithmFacade;
    private static RecordFacade recordFacade;

    //---------------------Constructors-------------------------------------------
    public FacadeTest() {
    }

    //---------------------Getters and setters------------------------------------
    public static EJBContainer getContainer() {
        return container;
    }

    public static UnitTypeFacade getUnitTypeFacade() {
        return unitTypeFacade;
    }

    public static ValueTypeFacade getValueTypeFacade() {
        return valueTypeFacade;
    }

    public static TmUnitFacade getTmUnitFacade() {
        return tmUnitFacade;
    }

    public static RocketTypeFacade getRocketTypeFacade() {
        return rocketTypeFacade;
    }

    public static RocketModFacade getRocketModFacade() {
        return rocketModFacade;
    }

    public static RocketFacade getRocketFacade() {
        return rocketFacade;
    }

    public static CellFacade getCellFacade() {
        return cellFacade;
    }

    public static PartitionFacade getPartitionFacade() {
        return partitionFacade;
    }

    public static AlgorithmFacade getAlgorithmFacade() {
        return algorithmFacade;
    }

    public static RecordFacade getRecordFacade() {
        return recordFacade;
    }

    //---------------------Methods------------------------------------------------
    /**
     * Полная очистка базы данных
     */
    public static void clearDataBase() {
        logger.debug("Очистка базы данных");
        recordFacade.clearTable();
        valueTypeFacade.clearTable();
        partitionFacade.clearTable();
        tmUnitFacade.clearTable();
        unitTypeFacade.clearTable();
        cellFacade.clearTable();
        algorithmFacade.clearTable();
        rocketFacade.clearTable();
        rocketModFacade.clearTable();
        rocketTypeFacade.clearTable();
    }

    /**
     * Cоздание контейнера и получение ссылок на EJB
     */
    @BeforeSuite
    public static void beforeSuite() throws Exception {
        // инициализация log4j
        BasicConfigurator.configure();
        // настройка контейнера
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.APP_NAME, APPLICATION_NAME);
        properties.put(INSTANCE_ROOT, DOMAIN_PATH);
        // создание контейнера
        logger.debug("Создание контейнера");
        container = EJBContainer.createEJBContainer(properties);
        logger.debug("Контейнер создан");
        unitTypeFacade =
                (UnitTypeFacade) container.getContext().lookup(JNDI_NAME_UNIT_TYPE_FACADE);
        valueTypeFacade =
                (ValueTypeFacade) container.getContext().lookup(JNDI_NAME_VALUE_TYPE_FACADE);
        tmUnitFacade =
                (TmUnitFacade) container.getContext().lookup(JNDI_NAME_TMUNIT_FACADE);
        rocketTypeFacade =
                (RocketTypeFacade) container.getContext().lookup(JNDI_NAME_ROCKET_TYPE_FACADE);
        rocketModFacade =
                (RocketModFacade) container.getContext().lookup(JNDI_NAME_ROCKET_MOD_FACADE);
        rocketFacade =
                (RocketFacade) container.getContext().lookup(JNDI_NAME_ROCKET_FACADE);
        cellFacade =
                (CellFacade) container.getContext().lookup(JNDI_NAME_CELL_FACADE);
        partitionFacade =
                (PartitionFacade) container.getContext().lookup(JNDI_NAME_PARTITION_FACADE);
        algorithmFacade =
                (AlgorithmFacade) container.getContext().lookup(JNDI_NAME_ALGORITHM_FACADE);
        recordFacade =
                (RecordFacade) container.getContext().lookup(JNDI_NAME_RECORD_FACADE);

    }

    @AfterSuite
    public static void afterSuite() throws Exception {
        logger.debug("Closing container");
        if (container != null) {
            container.close();
        }
        logger.debug("Container closed");
    }
}
