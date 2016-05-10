package com.tmis.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица паспортов радиотелеметрического оборудования
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "record.removeAll",
        query = "DELETE FROM Record" ),
    @NamedQuery( name = "record.findByName",
        query = "SELECT rec "
              + "FROM Record AS rec "
              + "WHERE rec.name = ?1" )
})
@Entity
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateWriting;
    
    @ManyToOne
    private TmUnit tmUnit;
    
    @Transient
    private boolean selected;

    @Version
    private Integer version;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getDateWriting() { return dateWriting; }
    public void setDateWriting(Date dateWriting) { this.dateWriting = dateWriting; }

    public TmUnit getTmUnit() { return tmUnit; }
    public void setTmUnit(TmUnit tmUnit) { this.tmUnit = tmUnit; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Record(){}
    
    public Record( String name ){
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
        if (!(object instanceof Record)) {
            return false;
        }
        Record other = (Record) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.Record[ id=" + id + " ]";
    }
    
}
