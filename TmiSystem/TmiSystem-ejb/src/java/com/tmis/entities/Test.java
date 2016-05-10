/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "test.removeAll",
        query = "DELETE FROM Test" ),
    @NamedQuery( name = "test.findByName",
        query = "SELECT t "
              + "FROM Test AS t "
              + "WHERE t.name = ?1" )
})
@Entity
public class Test implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    
    @ManyToOne
    private TmUnit tmUnit;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date testDate;
    
    @Transient
    private boolean selected;
    
    @Version
    private Integer version;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getTestDate() { return testDate; }
    public void setTestDate(Date testDate) { this.testDate = testDate; }

    public TmUnit getTmUnit() { return tmUnit; }
    public void setTmUnit(TmUnit tmUnit) { this.tmUnit = tmUnit; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.Test[ id=" + id + " ]";
    }
    
}
