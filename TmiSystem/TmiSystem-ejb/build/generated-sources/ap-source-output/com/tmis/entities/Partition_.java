package com.tmis.entities;

import com.tmis.entities.ValueType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(Partition.class)
public class Partition_ { 

    public static volatile SingularAttribute<Partition, Long> id;
    public static volatile SingularAttribute<Partition, String> description;
    public static volatile SingularAttribute<Partition, String> name;
    public static volatile ListAttribute<Partition, ValueType> valueTypes;
    public static volatile SingularAttribute<Partition, Integer> version;

}