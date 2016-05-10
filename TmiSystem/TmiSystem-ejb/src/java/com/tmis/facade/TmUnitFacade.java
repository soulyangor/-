package com.tmis.facade;

import com.tmis.entities.Cell;
import com.tmis.entities.Record;
import com.tmis.entities.Test;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.mongo.MongoDB;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
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
public class TmUnitFacade extends AbstractFacade<TmUnit> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;
    
    @EJB
    RecordFacade recordFacade;
    
    @EJB
    MongoDB mongoDB;
    
    @EJB
    TestFacade testFacade;
    
    @EJB
    CellFacade cellFacade;
    
    @PermitAll
    @Override
    public List<TmUnit> findAll(){
        return super.findAll();
    }
    
    /**
     * очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("tmUnit.removeAll");
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
    public List<TmUnit> findByName( String name ){
        Query query = em.createNamedQuery("tmUnit.findByName");
        query.setParameter( 1, name );
        return query.getResultList();
    }
    
    /**
     * Поиск по имени и типу
     * @param name
     * @return 
     */
    public List<TmUnit> findByNameAndTypes( String name, 
            List<UnitType> unitTypes ){
        if( unitTypes == null ) { return null; }
        Query query = em.createNamedQuery("tmUnit.findByTypes");
        List<Long> typeIds = new ArrayList<Long>();
        for ( UnitType unitType : unitTypes ){ typeIds.add( unitType.getId() ); }
        query.setParameter( 1, typeIds );
        List<TmUnit> result = new ArrayList<TmUnit>();
        List<TmUnit> qList = new ArrayList<TmUnit>( query.getResultList() );
        for ( TmUnit tmUnit : qList ){
            if ( ( tmUnit.getName().equals( name ) ) && 
                    ( tmUnit.getCell() == null ) ) { 
                result.add( tmUnit ); 
            }
        }
        return result;
    }
    
    /**
     * Поиск по типам оборудования
     * @param unitTypes
     * @return 
     */
    public List<TmUnit> findByTypes( List<UnitType> unitTypes ){
        if( unitTypes == null ) { return null; }
        Query query = em.createNamedQuery("tmUnit.findByTypes");
        List<Long> typeIds = new ArrayList<Long>();
        for ( UnitType unitType : unitTypes ){ typeIds.add( unitType.getId() ); }
        query.setParameter( 1, typeIds );
        List<TmUnit> result = new ArrayList<TmUnit>();
        List<TmUnit> qList = new ArrayList<TmUnit>( query.getResultList() );
        for ( TmUnit tmUnit : qList ){
            if ( tmUnit.getCell() == null ) { result.add( tmUnit ); }
        }
        return result;
    }
   
    // добавляет к списку id всех потомков tmUnit 
    private void addSubUnitsId( ArrayList<Long> tmUnitsId, TmUnit tmUnit ) {
        tmUnitsId.add( tmUnit.getId() );
	for ( TmUnit subUnit : tmUnit.getSubUnits() ) { 
            addSubUnitsId( tmUnitsId, subUnit ); 
        }
    }
    
    /**
     * Возващает список всех id элементов tmUnit, 
     * начиная с текущего и всех его потомков (ветвь дерева)
     * @param id идентификатор tmUnit
     * @return список id
     */
    public List<Long> getRemoveTmUnitList( Long id ){
        if ( id == null ){ throw new EJBException(); }
        TmUnit tmUnit = find( id );
        if ( tmUnit == null ){ throw new EJBException(); }
        ArrayList<Long> list = new ArrayList<Long>();
        addSubUnitsId( list, tmUnit );
        Collections.reverse( list );
        return list;
    }
    
    /**
     * Удаление записи
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ) { throw new EJBException(); }
        TmUnit tmUnit = find ( id );
        if ( tmUnit == null ) { throw new EJBException(); }
        Cell cell = tmUnit.getCell();
        if( cell!=null ) { 
            cell.setTmUnit( null ); 
            cellFacade.edit( cell );
        }
        TmUnit superUnit = tmUnit.getSuperUnit();
        if ( superUnit != null ) {
            em.merge( superUnit );
            superUnit.getSubUnits().remove( tmUnit );
        }  
        UnitType unitType = tmUnit.getUnitType();
        if ( unitType != null ){ unitType.getTmUnits().remove( tmUnit ); }
        List<Record> removedRecordList = 
                new ArrayList<Record>( tmUnit.getRecords() );
        for ( Record record : removedRecordList ){
            mongoDB.remove( record );
            recordFacade.remove( record.getId() );
        }
        List<Test> removedTestList =
                new ArrayList<Test>( tmUnit.getTests() );
        for ( Test test : removedTestList ){ testFacade.remove( test.getId() ); }
        Query query = em.createNamedQuery("tmUnit.removeList");
        query.setParameter( 1, getRemoveTmUnitList( id ) );
        query.executeUpdate();
    }
    
    /**
     * Добавление оборудования входящего в состав
     * @param superUnit
     * @param subUnit 
     */
    public void addSubUnit( TmUnit superUnit, TmUnit subUnit ){
        TmUnit tmUnit = em.find( TmUnit.class, superUnit.getId() );
        if ( tmUnit == null ) { throw new EJBException(); }
        em.persist( subUnit );
        tmUnit.getSubUnits().add( subUnit );
        subUnit.setSuperUnit( superUnit );
    }
    
    /**
     * Добавление нового документа 
     * @param tmUnit
     * @param record 
     */
    public void addRecord( TmUnit tmUnit, Record record ){
        TmUnit unit = em.find( TmUnit.class, tmUnit.getId() );
        if ( unit == null ) { throw new EJBException(); }
        em.persist( record );
        unit.getRecords().add( record );
        record.setTmUnit( tmUnit );
    }
    
    /**
     * Добавление проверки
     * @param tmUnit
     * @param test 
     */
    public void addTest( TmUnit tmUnit, Test test){
        TmUnit unit = em.find( TmUnit.class, tmUnit.getId() );
        if ( unit == null ) { throw new EJBException(); }
        em.persist( test );
        unit.getTests().add( test );
        test.setTmUnit( tmUnit );
    }
    
    /**
     * Определяет имя РН на котором установлен
     * @param tmUnit
     * @return 
     */
    public String getRocket( TmUnit tmUnit ){
        TmUnit unit = em.find( TmUnit.class, tmUnit.getId() );
        if ( unit == null ) { throw new EJBException(); }
        Cell cell = unit.getCell();
        if( cell == null ){ return "Не установлен"; }
        while( cell.getSuperCell() != null ){
            cell = cell.getSuperCell();
        }
        return cell.getRocket().getName();
    }
    
    /**
     * Сохранение записи в БД
     * @param tmUnit 
     */
    public void persist( TmUnit tmUnit ){ em.persist( tmUnit ); }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TmUnitFacade() {
        super(TmUnit.class);
    }    
}
