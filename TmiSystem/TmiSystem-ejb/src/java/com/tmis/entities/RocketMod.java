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
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица модификаций РН
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "rocketMod.removeAll",
        query = "DELETE FROM RocketMod" ),
    @NamedQuery( name = "rocketMod.findByName",
        query = "SELECT rm "
              + "FROM RocketMod AS rm "
              + "WHERE rm.name = ?1" )
})
@Entity
public class RocketMod implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column( unique = true )
    private String name;
    private String description;
    
    @ManyToOne
    private RocketType rocketType;
    
    @OneToMany ( mappedBy = "rocketMod" )
    private List<Rocket> rockets;
    
    @OneToMany ( mappedBy = "rocketMod" )
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

    public List<Cell> getCells() { return cells; }
    public void setCells(List<Cell> cells) { this.cells = cells; }

    public RocketType getRocketType() { return rocketType; }
    public void setRocketType(RocketType rocketType) { 
        this.rocketType = rocketType;
    }

    public List<Rocket> getRockets() { return rockets; }
    public void setRockets(List<Rocket> rockets) { this.rockets = rockets; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public RocketMod( String name, String description ){
        this.name = name;
        this.description = description;
    }
    
    public RocketMod( ){ }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RocketMod)) {
            return false;
        }
        RocketMod other = (RocketMod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.RocketMod[ id=" + id + " ]";
    }
    
}
