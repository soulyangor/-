package com.tmis.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица характеристик оборудования
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "valueType.removeAll",
        query = "DELETE FROM ValueType " ),
    @NamedQuery( name = "valueType.findByName",
        query = "SELECT vt "
              + "FROM ValueType AS vt "
              + "WHERE vt.name = ?1" ),
    @NamedQuery( name = "valueType.findByNameId",
        query = "SELECT vt "
              + "FROM ValueType AS vt "
              + "WHERE vt.nameId = ?1" ),
    @NamedQuery( name = "valueType.findByPartitionAndUnitType",
        query = "SELECT vt "
              + "FROM ValueType AS vt "
              + "WHERE vt.partition.name = ?1 AND vt.unitType.name = ?2" )
})
@Entity
public class ValueType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column( unique = true )
    private String nameId;
    private String name;
    private String type;
    private String dimension;
    private String description;
    private Boolean editing;
    
    @ManyToOne
    private Partition partition;
    
    @ManyToOne
    private UnitType unitType; 
    
    @Transient
    private boolean selected;
    
    @Version
    private Integer version;

    public String getNameId() { return nameId; }
    public void setNameId(String nameId) { this.nameId = nameId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDimension() { return dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }

    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description; 
    }

    public Boolean getEditing() { return editing; }
    public void setEditing(Boolean editing) { this.editing = editing; }
    
    public Partition getPartition() { return partition; }
    public void setPartition(Partition partition) { this.partition = partition; }

    public UnitType getUnitType() { return unitType; }
    public void setUnitType(UnitType unitType) { this.unitType = unitType; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ValueType() { }
    
    public ValueType( String nameId, String name, String type, String dimension ){
        this.nameId = nameId;
        this.name = name;
        this.type = type;
        this.dimension = dimension;
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
        if (!(object instanceof ValueType)) {
            return false;
        }
        ValueType other = (ValueType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.ValueType[ id=" + id + " ]";
    }
    
}
