package com.tmis.entities;

import com.tmis.entities.RocketMod;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(RocketType.class)
public class RocketType_ { 

    public static volatile SingularAttribute<RocketType, Long> id;
    public static volatile SingularAttribute<RocketType, String> description;
    public static volatile SingularAttribute<RocketType, String> name;
    public static volatile ListAttribute<RocketType, RocketMod> modifications;
    public static volatile SingularAttribute<RocketType, Integer> version;

}