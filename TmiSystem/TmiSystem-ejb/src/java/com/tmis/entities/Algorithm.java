package com.tmis.entities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Таблица для хранения алгоритмов
 * @author Sokolov Slava
 */
@NamedQueries({
    @NamedQuery( name = "algorithm.removeAll",
        query = "DELETE FROM Algorithm" ),
    @NamedQuery( name = "algorithm.clearNull",
         query = "SELECT a "
               + "FROM Algorithm AS a "
               + "LEFT JOIN FETCH a.cells "
               + "WHERE a.id IS NULL")
})
@Entity
public class Algorithm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
     // данные документа
    @OneToOne( fetch = FetchType.LAZY, optional = false,
             cascade = { CascadeType.PERSIST, CascadeType.REMOVE } )
    @JoinColumn( name = "DATA_ID" )
    private DocData docData;
  
    private String name;
    private String className;
        
    @OneToMany ( mappedBy = "algorithm" )
    private List<Cell> cells; 

    @Transient
    private boolean selected;
    
    @Version
    private Integer version;

    public DocData getDocData() { return docData; }
    public void setDocData(DocData docData) { this.docData = docData; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public List<Cell> getCells() { return cells; }
    public void setCells(List<Cell> cells) { this.cells = cells; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Algorithm() {}
    
    public Algorithm( String name ){ this.name = name; }
    
    public Algorithm( String name,String className, 
            String fileName )throws IOException {
        if ( name == null ||  name.isEmpty() ) {
            throw new IllegalArgumentException("Bad doc name:" + name );
        }
        if ( className == null ||  name.isEmpty() ) {
            throw new IllegalArgumentException("Bad doc className:" + className );
        }
        this.name = name;
        this.className = className;
        docData = new DocData( fileName ); 
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
        if (!(object instanceof Algorithm)) {
            return false;
        }
        Algorithm other = (Algorithm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tmis.entities.Algorithm[ id=" + id + " ]";
    }
    
    /**
    * Записывает документ в заданный файл
    * @param fileName имя файла для записи документа
    */
    public void writeToFile( String fileName ) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream( fileName );
            fos.write( docData.getDocData() );
        } finally {
            fos.close();
        }
    }
    
}
