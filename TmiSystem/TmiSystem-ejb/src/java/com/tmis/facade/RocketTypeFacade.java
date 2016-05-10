package com.tmis.facade;

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
public class RocketTypeFacade extends AbstractFacade<RocketType> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    @EJB
    RocketModFacade rocketModFacade;
    
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("rocketType.removeAll");
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
        RocketType rocketType = find( id );
        if ( rocketType == null ){ throw new EJBException(); }
        List<RocketMod> removedList = 
                new ArrayList<RocketMod>( rocketType.getModifications() );
        for ( RocketMod rocketMod : removedList ){
            rocketModFacade.remove( rocketMod.getId() );
        }
        em.remove( rocketType );
    }
    
    /**
     * Добавление модификации
     * @param rocketType
     * @param rocketMod 
     */
    public void addRocketMod( RocketType rocketType, RocketMod rocketMod ){
        RocketType type = em.find( RocketType.class, rocketType.getId() );
        if ( type == null ){ throw new EJBException(); }
        em.persist( rocketMod );
        type.getModifications().add( rocketMod );
        rocketMod.setRocketType( rocketType );
    }
    
    /**
     * Поиск по имени
     * @param name
     * @return 
     */
    public List<RocketType> findByName( String name ){
        Query query = em.createNamedQuery("rocketType.findByName");
        query.setParameter( 1, name );
        return query.getResultList();  
    }
    
    /**
     * Сохранение в БД
     * @param rocketType 
     */
    public void persist( RocketType rocketType ){ em.persist( rocketType ); }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RocketTypeFacade() {
        super(RocketType.class);
    }
    
}
