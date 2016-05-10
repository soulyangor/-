package com.tmis.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Хранит блоки шаблона или структуры
 * телеметрической системы
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "cell.removeAll",
        query = "DELETE FROM Cell" ),
    @NamedQuery( name = "cell.findByName",
        query = "SELECT c "
              + "FROM Cell AS c "
              + "WHERE c.name = ?1" ),
    @NamedQuery( name = "cell.removeList",
        query = "DELETE FROM Cell c "
              + "WHERE c.id IN ?1" )
})
@Entity
public class Cell implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String description;
    private Long originId;
    private List<Integer> channels;
    
    @OneToOne
    private TmUnit tmUnit;
    
    @OneToMany ( mappedBy = "superCell" )
    private List<Cell> subCells;
    
    @ManyToOne
    private Cell superCell;
    
    @ManyToOne 
    private Algorithm algorithm;
    
    @ManyToOne
    private RocketMod rocketMod;
    
    @ManyToOne
    private Rocket rocket;
    
    @ManyToMany ( mappedBy = "cells" )
    @JoinTable( name = "cell_unittype",
        joinColumns = @JoinColumn(name = "cell_fk"),
        inverseJoinColumns = @JoinColumn(name = "unittype_fk") )
    private List<UnitType> unitTypes;
    
    @Transient
    private boolean selected;

    @Version
    private Integer version;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOriginId() { return originId; }
    public void setOriginId(Long originId) { this.originId = originId; }

    public List<Integer> getChannels() { return channels; }
    public void setChannels(List<Integer> channels) { this.channels = channels; }    

    public TmUnit getTmUnit() { return tmUnit; }
    public void setTmUnit(TmUnit tmUnit) { this.tmUnit = tmUnit; }

    public List<Cell> getSubCells() { return subCells; }
    public void setSubCells(List<Cell> subCells) { this.subCells = subCells; }

    public Cell getSuperCell() { return superCell; }
    public void setSuperCell(Cell superCell) { this.superCell = superCell; }

    public RocketMod getRocketMod() { return rocketMod; }
    public void setRocketMod(RocketMod rocketMod) { this.rocketMod = rocketMod; }

    public Rocket getRocket() { return rocket; }
    public void setRocket(Rocket rocket) { this.rocket = rocket; }
        
    public Algorithm getAlgorithm() { return algorithm; }
    public void setAlgorithm(Algorithm algorithm) { this.algorithm = algorithm; }
    
    public List<UnitType> getUnitTypes() { return unitTypes; }
    public void setUnitTypes(List<UnitType> unitTypes) {
        this.unitTypes = unitTypes;
    }
    
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Cell( String name, String description ){
        this.name = name;
        this.description = description;
    }
    
    public Cell( ){ }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cell)) {
            return false;
        }
        Cell other = (Cell) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.Cell[ id=" + id + " ]";
    }
    
}
