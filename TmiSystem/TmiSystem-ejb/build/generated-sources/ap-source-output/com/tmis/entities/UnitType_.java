package com.tmis.entities;

import com.tmis.entities.Cell;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(UnitType.class)
public class UnitType_ { 

    public static volatile SingularAttribute<UnitType, Long> id;
    public static volatile ListAttribute<UnitType, Cell> cells;
    public static volatile SingularAttribute<UnitType, String> description;
    public static volatile SingularAttribute<UnitType, UnitType> superType;
    public static volatile SingularAttribute<UnitType, String> name;
    public static volatile ListAttribute<UnitType, ValueType> valueTypes;
    public static volatile ListAttribute<UnitType, UnitType> subTypes;
    public static volatile ListAttribute<UnitType, TmUnit> tmUnits;
    public static volatile SingularAttribute<UnitType, Integer> version;

}