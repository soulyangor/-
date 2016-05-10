package com.tmis.entities;

import com.tmis.entities.TmUnit;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(Test.class)
public class Test_ { 

    public static volatile SingularAttribute<Test, Long> id;
    public static volatile SingularAttribute<Test, Date> testDate;
    public static volatile SingularAttribute<Test, TmUnit> tmUnit;
    public static volatile SingularAttribute<Test, String> name;
    public static volatile SingularAttribute<Test, Integer> version;

}