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
 * Таблица РН
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "rocket.removeAll",
        query = "DELETE FROM Rocket" ),
    @NamedQuery( name = "rocket.findByName",
        query = "SELECT r "
              + "FROM Rocket AS r "
              + "WHERE r.name = ?1" ),
    @NamedQuery( name = "rocket.findByType",
        query = "SELECT r "
              + "FROM Rocket AS r "
              + "WHERE r.rocketMod.rocketType.id = ?1" )
})
@Entity
public class Rocket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column( unique = true )
    private String name;
    
    private String description;
    
    @OneToMany ( mappedBy = "rocket")
    private List<Cell> cells; 
    
    @ManyToOne
    private RocketMod rocketMod;
    
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

    public List<Cell> getCells() { return cells;}
    public void setCells(List<Cell> cells) { this.cells = cells; }    

    public RocketMod getRocketMod() { return rocketMod; }
    public void setRocketMod(RocketMod rocketMod) { this.rocketMod = rocketMod; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Rocket( String name, String description ){
        this.name = name;
        this.description = description;
    }
    
    public Rocket( ){ }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rocket)) {
            return false;
        }
        Rocket other = (Rocket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.Rocket[ id=" + id + " ]";
    }
    
}
