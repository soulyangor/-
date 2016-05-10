/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmis.facade;

import com.tmis.entities.Account;
import com.tmis.entities.Account.RoleType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sokolov Slava
 */
@Stateless
@DeclareRoles({"ADMIN", "EUSER", "CUSER", "USER"})
@RolesAllowed({"ADMIN"})
public class AccountFacade extends AbstractFacade<Account> {
    //---------------------Entity manager-----------------------------------------

    @PersistenceContext(unitName = "tmiPU")
    private EntityManager em;

    //---------------------Constructors-------------------------------------------
    public AccountFacade() {
        super(Account.class);
    }

    //---------------------Methods------------------------------------------------
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // покрыт тестом
    @RolesAllowed({"ADMIN"})
    @Override
    public void create(Account account) {
        super.create(account);
    }

    // покрыт тестом
    @RolesAllowed({"ADMIN"})
    @Override
    public void edit(Account account) {
        super.edit(account);
    }
    
    // покрыт тестом
    @RolesAllowed({"ADMIN"})
    public void remove(String name) {
        if (name == null) {
            throw new EJBException();
        }
        Account account = find(name);
        if (account == null) {
            throw new EJBException();
        }
        super.remove(account);
    }

    // покрыт тестом
    @RolesAllowed({"ADMIN"})
    @Override
    public Account find(Object id) {
        return getEntityManager().find(Account.class, id);
    }

    // покрыт тестом
    @RolesAllowed({"ADMIN"})
    @Override
    public List<Account> findAll() {
        return super.findAll();
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public List<Account> findRange(int[] range) {
        return super.findRange(range);
    }

    @RolesAllowed({"ADMIN"})
    @Override
    public int count() {
        return super.count();
    }

    /**
     * Возвращает список всех пользователей, отсортированный по имени
     *
     * @return
     */
    @RolesAllowed({"ADMIN"})
    public List<Account> findAllOrderByName() {
        Query query = em.createNamedQuery("account.findAllOrderByName");
        return query.getResultList();
    }

    /**
     * Редактирует пользователя tested
     */
    public void edit(String name, String descrb, RoleType role) {
        Account account = find(name);
        if (account == null) {
            throw new EJBException();
        }
        account.setAccDescribe(descrb);
        account.setAccRole(role);
        edit(account);
    }

    @RolesAllowed({"ADMIN"})
    public void changePassword(String name, String password) {
        Account account = find(name);
        if (account == null) {
            throw new EJBException();
        }
        try {
            account.setPassword(password);
            edit(account);
        } catch (UnsupportedEncodingException ex) {
            throw new EJBException();
        } catch (NoSuchAlgorithmException ex) {
            throw new EJBException();
        }
    }

    @RolesAllowed({"ADMIN"})
    public void clearTable() {
        em.flush();
        em.clear();
        em.getEntityManagerFactory().getCache().evictAll();
        Query query = em.createNamedQuery("account.removeAll");
        query.executeUpdate();
    }
}
