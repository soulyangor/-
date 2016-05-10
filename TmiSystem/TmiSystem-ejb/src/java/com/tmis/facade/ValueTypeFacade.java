package com.tmis.facade;

import com.tmis.entities.Partition;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
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
public class ValueTypeFacade extends AbstractFacade<ValueType> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;
    
    /**
     * Очистка таблицы 
     */
    public void clearTable(){
        Query query = em.createNamedQuery("valueType.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    /**
     * Удаление записи
     * @return 
     */
    public void remove( Long id ){
        if ( id == null ) { throw new EJBException(); }
        ValueType valueType = find ( id );
        if ( valueType == null) { throw new EJBException(); }
        UnitType unitType = valueType.getUnitType();
        Partition partition = valueType.getPartition(); 
        if ( unitType != null ) { unitType.getValueTypes().remove( valueType ); }
        if ( partition != null ) { partition.getValueTypes().remove( valueType ); }
        em.remove( valueType );
    }
    
    /**
     * Поиск по полю name
     * @param name
     * @return 
     */
    public List<ValueType> findByName( String name ){
        Query query = em.createNamedQuery("valueType.findByName");
        query.setParameter( 1, name );
        return query.getResultList();
    }    
    
    /**
     * Поиск по полю nameId (неизменяемое поле)
     * @param nameId
     * @return 
     */
    public List<ValueType> findByNameId( String nameId ){
        Query query = em.createNamedQuery("valueType.findByNameId");
        query.setParameter( 1, nameId );
        return query.getResultList();
    }  
    
    /**
     * Поиск по разделу и типу
     * @param partition
     * @param unitType
     * @return 
     */
    public List<ValueType> findByPartitionAndUnitType( Partition partition, 
            UnitType unitType ){
        if ( partition == null ){ return null; }
        if ( unitType == null ){ return null; }
      /*  Query query = em.createNamedQuery("valueType.findByPartitionAndUnitType");
        query.setParameter( 1, partition.getName() );
        query.setParameter( 2, unitType.getName() );*/
        List<ValueType> result = new ArrayList<ValueType>();
        for( ValueType valueType : findAll() ){
           System.out.println(valueType);
           if( ( valueType.getPartition() != null ) &&
                ( valueType.getUnitType() != null ) ){
               if( ( valueType.getPartition().getId() == partition.getId() ) &&
                   ( valueType.getUnitType().getId() == unitType.getId() ) ){
                   System.out.println("есть "+valueType);
                   result.add( valueType );
               }
           }
        }
        return result; //query.getResultList();
    }
    
    /**
     * Сохранение записи в БД
     * @param valueType 
     */
    public void persist( ValueType valueType ){ em.persist( valueType ); }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValueTypeFacade() {
        super(ValueType.class);
    }
    
}
