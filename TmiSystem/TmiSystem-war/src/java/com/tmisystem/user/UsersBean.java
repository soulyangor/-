/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.user;

import com.tmis.entities.Account;
import com.tmis.facade.AccountFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean(name = "users")
@ViewScoped
public class UsersBean {

    //---------------------Constants----------------------------------------------
    private static final long serialVersionUID = 1L;

    //---------------------EJB----------------------------------------------------
    @EJB
    private AccountFacade accountFacade;
    //---------------------Fields-------------------------------------------------
    // список всех пользователей
    private List<Account> accountList;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;

    //--------------------Constructors--------------------------------------------
    /**
     * Дефолтный конструктор
     */
    public UsersBean() {}
    
    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }

    //--------------------PostConstruct-------------------------------------------
    @PostConstruct
    private void init() {
    }

    //--------------------Navigation----------------------------------------------
    /**
     * К странице создания нового пользователя
     *
     * @return
     */
    public String toNewUser() {
        mySessionBean.setCurrentUser(null);
        return "newUser.xhtml?faces-redirect=true";
    }

    /**
     * К странице редактирования данных пользователя
     *
     * @param account
     * @return
     */
    public String toEditUser(Account account) {
        mySessionBean.setCurrentUser(account.getName());
        return "editUser.xhtml?faces-redirect=true";
    }

    //--------------------Methods-------------------------------------------------
    /**
     * Делает недействительным список пользователей
     */
    public void refreshAccounts() {
        accountList = null;
    }

    /**
     * Возвращает список всех пользователей
     *
     * @return list of accounts
     */
    public List<Account> getAccounts() {
        if (accountList != null) {
            return accountList;
        }
        accountList = accountFacade.findAllOrderByName();
        return accountList;
    }

    /**
     * Возвращает количество пользователей
     *
     * @return accounts count
     */
    public int getAccountCount() {
        if (accountList != null) {
            return accountList.size();
        }
        return accountFacade.count();
    }

    /**
     * Блокирует выбранных пользователей
     */
    public void blockChoosen() {
        if (accountList == null) {
            return;
        }
        for (Account acc : accountList) {
            if (acc.isChecked()) {
                acc.setBlocked(true);
                String password = acc.getPassword();
                if (!password.startsWith("z")) {
                    acc.setPasswordDirect('z' + password);
                }
                accountFacade.edit(acc);
            }
        }
        accountList = null;
    }

    /**
     * Разблокирует выбранных пользователей
     */
    public void unBlockChoosen() {
        if (accountList == null) {
            return;
        }
        for (Account acc : accountList) {
            if (acc.isChecked()) {
                acc.setBlocked(false);
                String password = acc.getPassword();
                if (password.startsWith("z")) {
                    password = password.substring(1, 64);
                    acc.setPasswordDirect(password);
                }
                accountFacade.edit(acc);
            }
        }
        accountList = null;
    }

    /**
     * Удаляет выбранных пользователей
     */
    public void deleteChoosen() {
        if (accountList == null) {
            return;
        }
        for (Account acc : accountList) {
            if (acc.isChecked()) {
                accountFacade.remove(acc.getName());
            }
        }
        accountList = null;
    }
}
