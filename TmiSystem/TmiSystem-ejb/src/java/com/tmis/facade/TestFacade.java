/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmis.facade;

import com.tmis.entities.Test;
import com.tmis.entities.TmUnit;
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
public class TestFacade extends AbstractFacade<Test> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("test.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    /**
     * Удаление записи
     * требует расширения
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ){ throw new EJBException(); }
        Test test = find( id );
        if ( test == null ){ throw new EJBException(); }
        TmUnit tmUnit = test.getTmUnit();
        if ( tmUnit != null ){ tmUnit.getTests().remove( test ); }
        em.remove( test );
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TestFacade() {
        super(Test.class);
    }
    
}
