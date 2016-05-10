package com.tmis.entities;

import com.tmis.entities.Algorithm;
import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketMod;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(Cell.class)
public class Cell_ { 

    public static volatile SingularAttribute<Cell, Long> id;
    public static volatile SingularAttribute<Cell, Cell> superCell;
    public static volatile SingularAttribute<Cell, TmUnit> tmUnit;
    public static volatile SingularAttribute<Cell, RocketMod> rocketMod;
    public static volatile SingularAttribute<Cell, Rocket> rocket;
    public static volatile SingularAttribute<Cell, Long> originId;
    public static volatile SingularAttribute<Cell, String> description;
    public static volatile SingularAttribute<Cell, String> name;
    public static volatile SingularAttribute<Cell, List> channels;
    public static volatile ListAttribute<Cell, Cell> subCells;
    public static volatile SingularAttribute<Cell, Algorithm> algorithm;
    public static volatile ListAttribute<Cell, UnitType> unitTypes;
    public static volatile SingularAttribute<Cell, Integer> version;

}