package com.tmis.entities;

import com.tmis.entities.Account.RoleType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2015-06-19T03:55:05")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, String> name;
    public static volatile SingularAttribute<Account, Boolean> blocked;
    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, String> descrb;
    public static volatile SingularAttribute<Account, RoleType> accRole;

}