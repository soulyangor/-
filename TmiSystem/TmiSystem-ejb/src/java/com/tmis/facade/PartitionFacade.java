package com.tmis.facade;

import com.tmis.entities.Partition;
import com.tmis.entities.ValueType;
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
public class PartitionFacade extends AbstractFacade<Partition> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    @EJB ValueTypeFacade valueTypeFacade;
    
    /**
     * Очистка таблицы 
     */
    public void clearTable(){
        Query query = em.createNamedQuery("partition.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    /**
     * Добавление типа значений
     * @param unitType
     * @param valueType 
     */
    public void addValueType( Partition partition, ValueType valueType ){
        Partition part = em.find( Partition.class, partition.getId() );
        if ( part == null ) { throw new EJBException(); }
        em.persist( valueType );
        part.getValueTypes().add( valueType );
        valueType.setPartition( partition );
    }
    
    /**
     * Удаление записи
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ) { throw new EJBException(); }
        Partition partition = find ( id );
        if ( partition == null) { throw new EJBException(); }
        List<ValueType> removedValueTypeList =
                new ArrayList<ValueType>( partition.getValueTypes() );
        for ( ValueType valueType : removedValueTypeList ){
            valueTypeFacade.remove( valueType.getId() );
        }
        em.remove( partition );
    }
    
    public void persist ( Partition partition ){ em.persist( partition ); }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartitionFacade() {
        super(Partition.class);
    }    
}
