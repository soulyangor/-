/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmisystem.user;

import com.tmis.entities.Account;
import com.tmis.entities.Account.RoleType;
import com.tmis.facade.AccountFacade;
import com.tmisystem.sessionbean.MySessionBean;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sokolov Slava
 */
@ManagedBean(name = "user")
@ViewScoped
public class UserBean {

    //---------------------Constants----------------------------------------------
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{mySession}")
    private MySessionBean mySessionBean;
    //---------------------EJB----------------------------------------------------
    @EJB
    private AccountFacade accountFacade;
    //---------------------Fields-------------------------------------------------
    // имя пользователя
    @Size( min = 1, max = Account.MAX_NAME_LENGTH )
    private String name;
    // роль
    @NotNull(message = "{com.nestos.user.badRole}")
    private RoleType role;
    // описание
    @Size( max = Account.MAX_NAME_LENGTH )
    private String descrb;
    // пароль
    @Size( min = 1 )
    private String password;
    // подтверждение пароля
    @Size( min = 1 )
    private String confirmPassword;

    //--------------------Constructors--------------------------------------------
    /**
     * Дефолтный конструктор
     */
    public UserBean() {}

    public void setMySessionBean(MySessionBean mySessionBean) {
        this.mySessionBean = mySessionBean;
    }   

    //--------------------PostConstruct-------------------------------------------
    @PostConstruct
    private void init() {
        String userName = mySessionBean.getCurrentUser();
        name = "";
        descrb = "";
        role = RoleType.USER;
        password = "";
        confirmPassword = "";
        // для создания нового пользователя
        if (userName == null) {
            return;
        }
        // для редактирования пользователя
        Account account = accountFacade.find(userName);
        if (account == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response =
                    (HttpServletResponse) context.getExternalContext().getResponse();
            try {
                response.sendRedirect("/TmiSystem-war/oldpage.xhtml");
            } catch (IOException ex) {}
        }
        name = account.getName();
        descrb = account.getAccDescribe();
        role = account.getAccRole();
    }

    //--------------------Getters and setters-------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getDescrb() {
        return descrb;
    }

    public void setDescrb(String descrb) {
        this.descrb = descrb;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    //--------------------Methods-------------------------------------------------
    /**
     * Возвращает список существующих ролей
     *
     * @return list of existing roles
     */
    public RoleType[] getRoleList() {
        return RoleType.values();
    }

    /**
     * Проверка на соответствие пароля и его подтверждения
     *
     * @param event
     */
    public void validateConfirmPassword(ComponentSystemEvent event) {
        UIComponent source = event.getComponent();
        UIInput passwordInput = (UIInput) source.findComponent("userPassword");
        UIInput confirmInput =
                (UIInput) source.findComponent("userConfirmPassword");
        String pass = (String) passwordInput.getLocalValue();
        String conf = (String) confirmInput.getLocalValue();
        if ((pass == null) || (conf == null) || !pass.equals(conf)) {
            FacesMessage message = new FacesMessage("Ошибка аутентификации");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(confirmInput.getClientId(), message);
            context.renderResponse();
        }
    }

    /**
     * Проверка на существование пользователя с заданным именем
     *
     * @param event
     */
    public void validateUserName(ComponentSystemEvent event) {
        UIComponent source = event.getComponent();
        UIInput userInput = (UIInput) source.findComponent("userName");
        String userName = (String) userInput.getLocalValue();
        if ((userName == null) || (userName.isEmpty())) {
            return;
        }
        Account account = accountFacade.find(userName);
        if (account != null) {
            FacesMessage message = new FacesMessage("Пользователя не существует");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(userInput.getClientId(), message);
            context.renderResponse();
        }
    }

    /**
     * Сохранение нового пользователя в базе данных, переход к таблице
     * пользователей
     */
    public String persistUser() {
        Account account = null;
        try {
            account = new Account(name, password, role, descrb, false);
        } catch (UnsupportedEncodingException ex) {
            return "error";
        } catch (NoSuchAlgorithmException ex) {
            return "error";
        }
        if (account != null) {
            accountFacade.create(account);
        }
        return "userTable.xhtml?faces-redirect=true";
    }

    /**
     * Редактирование данных пользователя, переход к таблице пользователей
     */
    public String editUser() {
        try {
            accountFacade.edit(name, descrb, role);
        } catch (EJBException ex) {
            return "error";
        }
        return "userTable.xhtml?faces-redirect=true";
    }

    /**
     * Изменение пароля пользователя
     *
     * @return
     */
    public String changePassword() {
        try {
            accountFacade.changePassword(name, password);
        } catch (EJBException ex) {
            return "error";
        }
        return null;
    }
}
