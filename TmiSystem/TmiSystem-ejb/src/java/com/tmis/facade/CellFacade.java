package com.tmis.facade;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.entities.UnitType;
import java.util.ArrayList;
import java.util.Collections;
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
public class CellFacade extends AbstractFacade<Cell> {
    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    @EJB
    UnitTypeFacade unitTypeFacade;
    
    @EJB
    AlgorithmFacade algorithmFacade;
    
    /**
     * Очистка таблицы
     */
    public void clearTable(){
        Query query = em.createNamedQuery("cell.removeAll");
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        query.executeUpdate();
    }
    
    // добавляет к списку id всех потомков cell 
    private void addSubCellsId( ArrayList<Long> cellsId, Cell cell ) {
        cellsId.add( cell.getId() );
	for ( Cell subCell : cell.getSubCells() ) { addSubCellsId( cellsId, subCell ); }
    }
    
    /**
     * Возващает список всех id элементов cell, 
     * начиная с текущего и всех его потомков (ветвь дерева)
     * @param id идентификатор cell
     * @return список id
     */
    public List<Long> getRemoveCellList( Long id ){
        if ( id == null ){ throw new EJBException(); }
        Cell cell = find( id );
        if ( cell == null ){ throw new EJBException(); }
        ArrayList<Long> list = new ArrayList<Long>();
        addSubCellsId( list, cell );
        Collections.reverse( list );
        return list;
    }
        
    /**
     * Удаление записи
     * @param id 
     */
    public void remove( Long id ){
        if ( id == null ) { throw new EJBException(); }
        Cell cell = find ( id );
        if ( cell == null ) { throw new EJBException(); }
        Cell superCell = cell.getSuperCell();
        if ( superCell != null ) {
            em.merge( superCell );
            superCell.getSubCells().remove( cell );
        }
        Algorithm algorithm = cell.getAlgorithm();
        if ( algorithm != null){ algorithm.getCells().remove( cell ); }
        RocketMod rocketMod = cell.getRocketMod();
        if ( rocketMod != null){ rocketMod.getCells().remove( cell ); }
        Rocket rocket = cell.getRocket();
        if ( rocket != null){ rocket.getCells().remove( cell ); }
        List<UnitType> fromRemove = new ArrayList<UnitType>( cell.getUnitTypes() );
        for ( UnitType unitType : fromRemove ){
            unitTypeFacade.removeCell( unitType, cell );
        }        
        Query query = em.createNamedQuery("cell.removeList");
        query.setParameter( 1, getRemoveCellList( id ) );
        query.executeUpdate();
    }
    
    /**
     * Удаление разрешённого типа оборудования
     * @param cell
     * @param unitType 
     */
    public void removeUnitType( Cell cell, UnitType  unitType ){
        Cell c = em.find( Cell.class, cell.getId() );
        if ( c == null ) { throw new EJBException(); }
        em.merge( unitType );
        c.getUnitTypes().remove( unitType );
        unitType.getCells().remove( cell );
    }
    
    /**
     * Поиск по имени
     * @param name
     * @return 
     */
    public List<Cell> findByName( String name ){
        Query query = em.createNamedQuery("cell.findByName");
        query.setParameter( 1, name );
        return query.getResultList();                
    }
    
    /**
     * Добавление нового типа
     * @param mainCell
     * @param unitType 
     */
    public void addUnitType( Cell mainCell, UnitType unitType ){
        Cell cell = em.find( Cell.class, mainCell.getId() );
        if ( cell == null ) { throw new EJBException(); }
        em.persist( unitType );
        cell.getUnitTypes().add( unitType );
        List<Cell> cells = new ArrayList<Cell>();
        cells.add( mainCell );
        unitType.setCells( cells );
    }
    
    /**
     * Присоединение уже существующего типа
     * @param mainCell
     * @param unitType 
     */
    public void connectUnitType( Cell mainCell, UnitType unitType ){
        Cell cell = em.find( Cell.class, mainCell.getId() );
        if ( cell == null ) { throw new EJBException(); }
        em.merge( unitType );
        cell.getUnitTypes().add( unitType );
        List<Cell> cells = new ArrayList<Cell>( unitType.getCells() );
        cells.add( mainCell );
        unitType.setCells( cells );
        unitTypeFacade.edit( unitType );
    }
    
    /**
     * Добавление подъячейки
     * @param mainCell
     * @param subCell 
     */
    public void addSubCell ( Cell mainCell, Cell subCell ){
        Cell cell = em.find( Cell.class, mainCell.getId() );
        if ( cell == null ){ throw new EJBException(); }
        em.persist( subCell );
        cell.getSubCells().add( subCell );
        subCell.setSuperCell( mainCell );
    }
    
    /**
     * Поиск оргинала с которого снята копия
     * @param copy
     * @return 
     */
    public Cell getOrigin( Cell copy ){
        Cell cell = em.find( Cell.class, copy.getId() );
        if ( cell == null ){ throw new EJBException(); }
        Cell origin = em.find( Cell.class, copy.getOriginId() );
        return origin;
    }
   
    /**
     * Формирование дерева для копирования
     * @param cell
     * @return 
     */
    public Cell formTree( Cell cell ){
        Cell c = em.find( Cell.class, cell.getId() );
        if ( c == null ){ throw new EJBException(); }
        Cell copy = new Cell();
        copy.setName( c.getName() );
        copy.setOriginId( c.getId() );
        if ( c.getChannels() != null ){
            List<Integer> channels = new ArrayList<Integer>( c.getChannels() );
            copy.setChannels( channels );
        } else { 
            List<Integer> channels = new ArrayList<Integer>();
            copy.setChannels( channels );
        }
        copy.setDescription( c.getDescription() );
        if ( c.getSubCells() != null ){
            List<Cell> cells = new ArrayList<Cell>( c.getSubCells() );
            for ( Cell subCell : cells ){
                if ( copy.getSubCells() != null ){
                    List<Cell> subCells = new ArrayList<Cell>( copy.getSubCells() );
                    Cell brunch = formTree( subCell );
                    brunch.setSuperCell( copy );
                    subCells.add( brunch );
                    copy.setSubCells( subCells );
                } else {
                    List<Cell> subCells = new ArrayList<Cell>( );
                    Cell brunch = formTree( subCell );
                    brunch.setSuperCell( copy );
                    subCells.add( brunch );
                    copy.setSubCells( subCells );
                }
            }
        }
        return copy;
    }
    
    /**
     * Сохранение дерева начиная с вершины
     * @param cell 
     */
    public void persistBrunch( Cell cell ){
        if ( cell.getSubCells() != null ){
            for ( Cell subCell :cell.getSubCells() ){ persistBrunch( subCell ); }
        }
        em.persist( cell );
        Long originId = cell.getOriginId();
        if ( originId != null ){
            Algorithm algorithm = find( originId ).getAlgorithm();
            if ( algorithm != null ){ 
                algorithmFacade.connectCell( algorithm, cell);
            }
        }
    }
    
    /**
     * Копирование шаблона
     * @param cell - оригинал
     * @return скопированный элемент
     */
    public Cell copy ( Cell cell ){
        Cell copy = formTree( cell );
        persistBrunch( copy );
        return copy;
    }
    
    /**
     * Сохрнение в БД
     * @param cell
     */
    public void persist( Cell cell ){ em.persist( cell ); }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CellFacade() {
        super(Cell.class);
    }    
}
