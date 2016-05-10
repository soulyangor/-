package com.tmis.entities;

import com.tmis.entities.Cell;
import com.tmis.entities.RocketMod;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(Rocket.class)
public class Rocket_ { 

    public static volatile SingularAttribute<Rocket, Long> id;
    public static volatile ListAttribute<Rocket, Cell> cells;
    public static volatile SingularAttribute<Rocket, RocketMod> rocketMod;
    public static volatile SingularAttribute<Rocket, String> description;
    public static volatile SingularAttribute<Rocket, String> name;
    public static volatile SingularAttribute<Rocket, Integer> version;

}