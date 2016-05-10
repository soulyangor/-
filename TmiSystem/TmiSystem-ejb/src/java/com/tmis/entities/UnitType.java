package com.tmis.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица типов оборудования
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "unitType.removeAll",
        query = "DELETE FROM UnitType" ),
    @NamedQuery( name = "unitType.findByName",
        query = "SELECT ut "
              + "FROM UnitType AS ut "
              + "WHERE ut.name = ?1" )
})
@Entity
public class UnitType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column( unique = true )
    private String name;
    private String description;
    
    @ManyToOne
    private UnitType superType;
    
    @OneToMany ( mappedBy = "superType" )
    private List<UnitType> subTypes;
    
    @OneToMany ( mappedBy = "unitType" )
    private List<ValueType> valueTypes;
    
    @OneToMany ( mappedBy = "unitType" )
    private List<TmUnit> tmUnits;
    
    @ManyToMany 
    private List<Cell> cells;
    
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

    public UnitType getSuperType() { return superType; }
    public void setSuperType(UnitType superType) { this.superType = superType; }

    public List<UnitType> getSubTypes() { return subTypes; }
    public void setSubTypes(List<UnitType> subTypes) { this.subTypes = subTypes; }
    
    public List<ValueType> getValueTypes() { return valueTypes; }
    public void setValueTypes(List<ValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    public List<TmUnit> getTmUnits() { return tmUnits; }
    public void setTmUnits(List<TmUnit> tmUnits) { this.tmUnits = tmUnits; }

    public List<Cell> getCells() { return cells; }
    public void setCells(List<Cell> cells) { this.cells = cells; }    

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
      
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public UnitType(){}
    
    public UnitType( String name ){
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnitType)) {
            return false;
        }
        UnitType other = (UnitType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.UnitType[ id=" + id + " ]";
    }
    
}
