package com.tmis.entities;

import com.tmis.entities.Cell;
import com.tmis.entities.DocData;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(Algorithm.class)
public class Algorithm_ { 

    public static volatile SingularAttribute<Algorithm, Long> id;
    public static volatile ListAttribute<Algorithm, Cell> cells;
    public static volatile SingularAttribute<Algorithm, String> name;
    public static volatile SingularAttribute<Algorithm, String> className;
    public static volatile SingularAttribute<Algorithm, DocData> docData;
    public static volatile SingularAttribute<Algorithm, Integer> version;

}