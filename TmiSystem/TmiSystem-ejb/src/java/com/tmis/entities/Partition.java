package com.tmis.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица разделов паспортов
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "partition.removeAll",
        query = "DELETE FROM Partition" ),
    @NamedQuery( name = "partition.findByName",
        query = "SELECT p "
              + "FROM Partition AS p "
              + "WHERE p.name = ?1" )
})
@Entity
public class Partition implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column( unique = true )
    private String name;
    private String description;
    
    @OneToMany ( mappedBy="partition" )
    private List<ValueType> valueTypes;
    
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
    
    public List<ValueType> getValueTypes() { return valueTypes; }
    public void setValueTypes(List<ValueType> valueTypes) {
        this.valueTypes = valueTypes;
    }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Partition(){}
    
    public Partition( String name ){ this.name = name; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partition)) {
            return false;
        }
        Partition other = (Partition) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.Partition[ id=" + id + " ]";
    }
    
}
