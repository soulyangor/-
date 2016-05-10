package com.tmis.entities;

import com.tmis.entities.Partition;
import com.tmis.entities.UnitType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(ValueType.class)
public class ValueType_ { 

    public static volatile SingularAttribute<ValueType, Long> id;
    public static volatile SingularAttribute<ValueType, String> dimension;
    public static volatile SingularAttribute<ValueType, Partition> partition;
    public static volatile SingularAttribute<ValueType, Boolean> editing;
    public static volatile SingularAttribute<ValueType, UnitType> unitType;
    public static volatile SingularAttribute<ValueType, String> description;
    public static volatile SingularAttribute<ValueType, String> name;
    public static volatile SingularAttribute<ValueType, String> type;
    public static volatile SingularAttribute<ValueType, String> nameId;
    public static volatile SingularAttribute<ValueType, Integer> version;

}