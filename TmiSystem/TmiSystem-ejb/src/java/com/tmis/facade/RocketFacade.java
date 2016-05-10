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
public class RocketFacade extends AbstractFacade<Rocket> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    @EJB
    CellFacade cellFacade;
    
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("rocket.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    /**
     * Поиск по имени
     * @param name
     * @return 
     */
    public List<Rocket> findByName( String name ){
        Query query = em.createNamedQuery("rocket.findByName");
        query.setParameter( 1, name );
        return query.getResultList();                
    }
    
    /**
     * Поиск по типу РН
     * @param name
     * @return 
     */
    public List<Rocket> findByType( RocketType rocketType ){
        Query query = em.createNamedQuery("rocket.findByType");
        query.setParameter( 1, rocketType.getId() );
        return query.getResultList();                
    }
    
    /**
     * Удаление записи
     * требует расширения
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ){ throw new EJBException(); }
        Rocket rocket = find( id );
        if ( rocket == null ){ throw  new EJBException(); }
        RocketMod rocketMod = rocket.getRocketMod();
        if ( rocketMod != null){ rocketMod.getRockets().remove( rocket ); }
        List<Cell> removedList = 
                new ArrayList<Cell>( rocket.getCells() );
        for ( Cell iter : removedList ){ cellFacade.remove( iter.getId() ); }
        em.remove( rocket );
    }
    
    /**
     * Добавление новой ячейки состава
     * @param rocket
     * @param cell 
     */
    public void addCell( Rocket rocket, Cell cell ){
        Rocket roc = em.find( Rocket.class, rocket.getId() );
        if ( roc == null ){ throw new EJBException(); }
        em.persist( cell );
        roc.getCells().add( cell );
        cell.setRocket( rocket );    
    }
    
    /**
     * Присоединение ячейки состава
     * @param rocket
     * @param cell 
     */
    public void connectCell( Rocket rocket, Cell cell ){
        Rocket roc = em.find( Rocket.class, rocket.getId() );
        if ( roc == null ){ throw new EJBException(); }
        em.merge( cell );
        roc.getCells().add( cell );
        cell.setRocket( rocket );  
        cellFacade.edit( cell );
    }
    
    /**
     * Сохранение в БД
     * @param rocket 
     */
    public void persist( Rocket rocket ){ em.persist( rocket ); }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RocketFacade() {
        super(Rocket.class);
    }    
}
