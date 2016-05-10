package com.tmis.facade;

import com.tmis.entities.Record;
import com.tmis.entities.TmUnit;
import com.tmis.mongo.MongoDB;
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
public class RecordFacade extends AbstractFacade<Record> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    @EJB
    MongoDB mongoDB;
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("record.removeAll");
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
        if ( id == null ) { throw new EJBException(); }
        Record record = find ( id );
        if ( record == null) { throw new EJBException(); }
        TmUnit tmUnit = record.getTmUnit();
        if ( tmUnit != null ) { tmUnit.getRecords().remove( record ); }
        em.remove( record );
    }
    
    /**
     * поиск по имени
     * @param name
     * @return 
     */
    public List<TmUnit> findByName( String name ){
        Query query = em.createNamedQuery("record.findByName");
        query.setParameter( 1, name );
        return query.getResultList();
    }
    
    /**
     * Сохранение в БД
     * @param record 
     */
    public void persist( Record record ){ em.persist( record ); }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecordFacade() {
        super(Record.class);
    }
    
}
