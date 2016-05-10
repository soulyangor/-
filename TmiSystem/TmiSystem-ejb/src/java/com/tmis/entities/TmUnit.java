package com.tmis.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица радиотелеметрического оборудования
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "tmUnit.removeAll",
        query = "DELETE FROM TmUnit" ),
    @NamedQuery( name = "tmUnit.findByName",
        query = "SELECT tu "
              + "FROM TmUnit AS tu "
              + "WHERE tu.name = ?1" ),
    @NamedQuery( name = "tmUnit.removeList",
        query = "DELETE FROM TmUnit tu "
              + "WHERE tu.id IN ?1" ),
    @NamedQuery( name = "tmUnit.findByTypes",
        query = "SELECT tu "
              + "FROM TmUnit AS tu "
              + "WHERE tu.unitType.id IN ?1" )
})
@Entity
public class TmUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column( unique = true )
    private String name;
    private String fullname;
    private String description;
    
    @OneToOne ( mappedBy = "tmUnit" )
    private Cell cell;
    
    @OneToMany ( mappedBy = "tmUnit" )
    private List<Record> records;
    
    @OneToMany ( mappedBy = "superUnit" )
    private List<TmUnit> subUnits;
    
    @OneToMany ( mappedBy = "tmUnit" )
    private List<Test> tests;
    
    @ManyToOne
    private TmUnit superUnit;
    
    @ManyToOne
    private UnitType unitType;
    
    @Transient
    private boolean selected;
    
    @Version
    private Integer version;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }

    public Cell getCell() { return cell; }
    public void setCell(Cell cell) { this.cell = cell; }

    public List<Record> getRecords() { return records; }
    public void setRecords(List<Record> records) { this.records = records; }

    public List<TmUnit> getSubUnits() { return subUnits; }
    public void setSubUnits(List<TmUnit> subUnits) { this.subUnits = subUnits; }

    public List<Test> getTests() { return tests; }
    public void setTests(List<Test> tests) { this.tests = tests; }

    public TmUnit getSuperUnit() { return superUnit; }
    public void setSuperUnit(TmUnit superUnit) { this.superUnit = superUnit; }

    public UnitType getUnitType() { return unitType; }
    public void setUnitType(UnitType unitType) { this.unitType = unitType; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public TmUnit(){}
    
    public TmUnit( String name, String fullname ){
        this.name = name;
        this.fullname = fullname;
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
        if (!(object instanceof TmUnit)) {
            return false;
        }
        TmUnit other = (TmUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.TmUnit[ id=" + id + " ]";
    }
    
}
