package com.tmis.entities;

import com.tmis.entities.Cell;
import com.tmis.entities.Rocket;
import com.tmis.entities.RocketType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(RocketMod.class)
public class RocketMod_ { 

    public static volatile SingularAttribute<RocketMod, Long> id;
    public static volatile ListAttribute<RocketMod, Cell> cells;
    public static volatile SingularAttribute<RocketMod, RocketType> rocketType;
    public static volatile SingularAttribute<RocketMod, String> description;
    public static volatile SingularAttribute<RocketMod, String> name;
    public static volatile ListAttribute<RocketMod, Rocket> rockets;
    public static volatile SingularAttribute<RocketMod, Integer> version;

}