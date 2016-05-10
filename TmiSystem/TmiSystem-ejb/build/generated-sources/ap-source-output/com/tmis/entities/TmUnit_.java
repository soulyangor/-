package com.tmis.entities;

import com.tmis.entities.Cell;
import com.tmis.entities.Record;
import com.tmis.entities.Test;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(TmUnit.class)
public class TmUnit_ { 

    public static volatile SingularAttribute<TmUnit, Long> id;
    public static volatile ListAttribute<TmUnit, TmUnit> subUnits;
    public static volatile ListAttribute<TmUnit, Test> tests;
    public static volatile SingularAttribute<TmUnit, UnitType> unitType;
    public static volatile SingularAttribute<TmUnit, String> description;
    public static volatile SingularAttribute<TmUnit, String> name;
    public static volatile SingularAttribute<TmUnit, Cell> cell;
    public static volatile ListAttribute<TmUnit, Record> records;
    public static volatile SingularAttribute<TmUnit, String> fullname;
    public static volatile SingularAttribute<TmUnit, TmUnit> superUnit;
    public static volatile SingularAttribute<TmUnit, Integer> version;

}