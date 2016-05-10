package com.tmis.facade;

import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.entities.RocketType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sokolov Slava
 */
@Stateless
@DeclareRoles({"ADMIN", "EUSER", "CUSER", "USER"})
@RolesAllowed({"ADMIN", "EUSER", "CUSER", "USER"})
public class RocketModFacade extends AbstractFacade<RocketMod> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;
    
    @EJB
    CellFacade cellFacade;
    
    @EJB
    RocketFacade rocketFacade;
    
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("rocketMod.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    /**
     * Удаление записи
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ){ throw new EJBException(); }
        RocketMod rocketMod = find( id );
        if ( rocketMod == null ){ throw new EJBException(); }
        RocketType rocketType = rocketMod.getRocketType();
        if ( rocketType != null ){ rocketType.getModifications().remove( rocketMod ); }
        List<Cell> removedCellList = 
                new ArrayList<Cell>( rocketMod.getCells() );
        for ( Cell cell : removedCellList ){
            cellFacade.remove( cell.getId() );
        }
        List<Rocket> removedRocketList = 
                new ArrayList<Rocket>( rocketMod.getRockets() );
        for ( Rocket rocket : removedRocketList ){
            rocketFacade.remove( rocket.getId() );
        }
        em.remove( rocketMod );
    }
    
    /**
     * Поиск по имени
     * @param name
     * @return 
     */
    public List<RocketMod> findByName( String name ){
        Query query = em.createNamedQuery("rocketMod.findByName");
        query.setParameter( 1, name );
        return query.getResultList();  
    }
    
    /**
     * Добавление РН
     * @param rocketMod
     * @param rocket 
     */
    public void addRocket( RocketMod rocketMod, Rocket rocket ){
        RocketMod mod = em.find( RocketMod.class, rocketMod.getId() );
        if ( mod == null ){ throw new EJBException(); }
        em.persist( rocket );
        mod.getRockets().add( rocket );
        rocket.setRocketMod( rocketMod );
    }
    
    /**
     * Добавление блока
     * @param rocketMod
     * @param cell 
     */
    public void addCell ( RocketMod rocketMod, Cell cell ){
        RocketMod mod = em.find( RocketMod.class, rocketMod.getId() );
        if ( mod == null ){ throw new EJBException(); }
        em.persist( cell );
        mod.getCells().add( cell );
        cell.setRocketMod( rocketMod ); 
    }
    
    /**
     * Сохранение в БД
     * @param rocketMod 
     */
    public void persist( RocketMod rocketMod ){ em.persist( rocketMod ); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RocketModFacade() {
        super(RocketMod.class);
    }    
}
