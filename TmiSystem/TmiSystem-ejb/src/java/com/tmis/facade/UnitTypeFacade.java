package com.tmis.facade;

import com.tmis.entities.Cell;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
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
public class UnitTypeFacade extends AbstractFacade<UnitType> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;
    
    @EJB
    ValueTypeFacade valueTypeFacade;
    
    @EJB
    TmUnitFacade tmUnitFacade;
    
    @EJB
    CellFacade cellFacade;
    
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        em.createNativeQuery("DELETE FROM unittype_cell").executeUpdate();
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createNamedQuery("unitType.removeAll");
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
        UnitType unitType = find ( id );
        if ( unitType == null) { throw new EJBException(); }
        UnitType superType = unitType.getSuperType();
        if ( superType != null ) {
            em.merge( superType );
            superType.getSubTypes().remove( unitType );
        }        
        List<UnitType> subTypeList = 
                new ArrayList<UnitType>( unitType.getSubTypes() );
        for ( UnitType subType : subTypeList ){
            em.merge( subType );
            subType.setSuperType( null );
        }
        List<ValueType> removedValueTypeList =
                new ArrayList<ValueType>( unitType.getValueTypes() );
        for ( ValueType valueType : removedValueTypeList ){
            valueTypeFacade.remove( valueType.getId() );
        }
        List<TmUnit> removedTmUnitList = 
                new ArrayList<TmUnit>( unitType.getTmUnits() );
        for ( TmUnit tmUnit : removedTmUnitList ){
            tmUnitFacade.remove( tmUnit.getId() );
        }       
        List<Cell> fromRemove = new ArrayList<Cell>( unitType.getCells() );
        for ( Cell cell : fromRemove ){
            cellFacade.removeUnitType( cell, unitType );
        }
        em.remove( unitType );
    }
    
    /**
     * Удаление ячейки из списка типов
     * @param unitType
     * @param cell 
     */
    public void removeCell( UnitType  unitType, Cell cell ){
        UnitType uType = em.find( UnitType.class, unitType.getId() );
        if ( uType == null ) { throw new EJBException(); }
        em.persist( cell );
        uType.getCells().remove( cell );
        cell.getUnitTypes().remove( unitType );
    }
    
    /**
     * Поиск по имени типа
     * @param name
     * @return 
     */
    public List<UnitType> findByName( String name ){
        Query query = em.createNamedQuery("unitType.findByName");
        query.setParameter( 1, name );
        return query.getResultList();
    }
    
    /**
     * Добавление подтипа
     * @param superType
     * @param subType 
     */
    public void addSubType( UnitType superType, UnitType subType ){
        UnitType unitType = em.find( UnitType.class, superType.getId() );
        if ( unitType == null ) { throw new EJBException(); }
        em.persist( subType );
        unitType.getSubTypes().add( subType );
        subType.setSuperType( superType );
    }
    
    /**
     * Присоединение подтипа
     * @param superType
     * @param subType 
     */
    public void connectSubType( UnitType superType, UnitType subType ){
        UnitType unitType = em.find( UnitType.class, superType.getId() );
        if ( unitType == null ) { throw new EJBException(); }
        em.merge( subType );
        unitType.getSubTypes().add( subType );
        subType.setSuperType( superType );
    }
    
    /**
     * Присоединение типа значений
     * @param unitType
     * @param valueType 
     */
    public void connectValueType( UnitType unitType, ValueType valueType ){
        UnitType uType = em.find( UnitType.class, unitType.getId() );
        if ( uType == null ) { throw new EJBException(); }
        em.merge( valueType );
        uType.getValueTypes().add( valueType );
        valueType.setUnitType( unitType );
        valueTypeFacade.edit( valueType );
    }
    
    /**
     * Присоединение оборудования
     * @param unitType
     * @param tmUnit 
     */
    public void connectTmUnit( UnitType unitType, TmUnit tmUnit ){
        UnitType uType = em.find( UnitType.class, unitType.getId() );
        if ( uType == null ) { throw new EJBException(); }
        em.merge( tmUnit );
        uType.getTmUnits().add( tmUnit );
        tmUnit.setUnitType( unitType );
    }
    
    /**
     * Добавление оборудования
     * @param unitType
     * @param tmUnit 
     */
    public void addTmUnit( UnitType unitType, TmUnit tmUnit ){
        UnitType uType = em.find( UnitType.class, unitType.getId() );
        if ( uType == null ) { throw new EJBException(); }
        em.persist( tmUnit );
        uType.getTmUnits().add( tmUnit );
        tmUnit.setUnitType( unitType );
    }
    
    public void persist( UnitType unitType ){ em.persist( unitType ); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnitTypeFacade() {
        super(UnitType.class);
    }    
}
