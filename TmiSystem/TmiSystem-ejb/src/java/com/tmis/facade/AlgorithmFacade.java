package com.tmis.facade;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import java.io.IOException;
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
@DeclareRoles({"ADMIN", "MODER", "MUSER", "USER"})
@RolesAllowed({"ADMIN"})
@Stateless
public class AlgorithmFacade extends AbstractFacade<Algorithm> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;
    
    @EJB
    CellFacade cellFacade;
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("algorithm.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    /**
     * Добавление ячейки
     * @param algorithm
     * @param cell 
     */
    public void addCell( Algorithm algorithm, Cell cell ){
        Algorithm a = em.find( Algorithm.class, algorithm.getId() );
        if ( a == null ){ throw new EJBException(); }
        em.persist( cell );
        a.getCells().add( cell );
        cell.setAlgorithm( algorithm );                
    }
    
    /**
     * Присоединение ячейки
     * @param algorithm
     * @param cell 
     */
    public void connectCell( Algorithm algorithm, Cell cell ){
        Algorithm a = em.find( Algorithm.class, algorithm.getId() );
        if ( a == null ){ throw new EJBException(); }
        em.merge( cell );
        a.getCells().add( cell );
        cell.setAlgorithm( algorithm ); 
        cellFacade.edit(cell);
    }
    
    public void addDoc( String name, String className, 
            String fileName ) throws IOException {
        Algorithm algorithm = new Algorithm( name, className, fileName );
        em.persist( algorithm );
    }
    
    /**
     * Удаление
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ){ throw new EJBException(); }
        Algorithm algorithm = find( id );
        if ( algorithm == null ){ throw  new EJBException(); }
        List<Cell> cells = 
                new ArrayList<Cell>( algorithm.getCells() );
        for ( Cell cell : cells ){ 
            em.merge( cell );
            cell.setAlgorithm( null );
        }
        em.remove( algorithm );
    }
    
    /**
     * Сохранение записи в БД
     * @param algorithm 
     */
    public void persist( Algorithm algorithm ){ em.persist( algorithm ); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlgorithmFacade() {
        super(Algorithm.class);
    }    
}
