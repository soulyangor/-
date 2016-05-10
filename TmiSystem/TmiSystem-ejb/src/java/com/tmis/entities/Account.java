/*
 * Используется для описание 
 * таблицы пользователей
 */
package com.tmis.entities;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;

/**
 * Таблица для хранения пользователей
 * @author Sokolov Slava
 */
@Entity // таблица ACCOUNT
@SecondaryTable( name = "accountGroup") // таблица GROUP
//---------------------Queries--------------------------------------------------
@NamedQueries({
    @NamedQuery( name = "account.findAllOrderByName",
    query = "SELECT u "
    + "FROM Account AS u "
    + "ORDER BY u.name"),
    @NamedQuery( name = "account.removeAll",
    query = "DELETE FROM Account")
})
public class Account implements Serializable {
    
    public static enum RoleType{
        ADMIN, USER, EUSER, CUSER
    }

    //-------------------Constants------------------------------------------------
    private static final long serialVersionUID = 1L;
    public static final int MAX_NAME_LENGTH = 20;
    public static final int MAX_DESCRB_LENGTH = 50;
    //-------------------Fields---------------------------------------------------
    // имя пользователя, первичный ключ
    @Id
    @Column( length = MAX_NAME_LENGTH)
    private String name;
    // пароль
    @Column( nullable = false, length = 2048 )
    private String password;
    // роль - ADMIN, MODER, MUSER, USER
    @Column( nullable = false, length = 5, table = "accountGroup")
    @Enumerated( EnumType.STRING)
    private RoleType accRole;
    // описание
    @Column( length = MAX_DESCRB_LENGTH)
    private String descrb;
    // признак блокирования аккаунта
    //@Column( columnDefinition = "CHAR(1) NOT NULL")
    private boolean blocked;

    @Transient
    private boolean checked;

    //-------------------Constructors---------------------------------------------
    /**
     * Дефолтный конструктор
     */
    public Account() {
    }

    /**
     * Конструктор
     *
     * @param name имя пользователя
     * @param password пароль пользователя
     * @param accRole роль пользователя
     * @param descrb описание пользователя
     * @param blocked признак блокирования аккаунта
     */
    public Account(String name, String password, RoleType accRole,
            String descrb, boolean blocked) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        // проверка name
        if (name == null) {
            throw new IllegalArgumentException("Account name must not be null");
        }
        if (name.isEmpty() || (name.length() > MAX_NAME_LENGTH)) {
            throw new IllegalArgumentException(
                    "Account name length must be greater then zero and lesser or equal "
                    + MAX_NAME_LENGTH);
        }
        this.name = name;

        // проверка password
        if (password == null) {
            throw new IllegalArgumentException("Account password must not be null");
        }
        this.password = strToPassword(password);

        // проверка role
        if (accRole == null) {
            throw new IllegalArgumentException("Account role must not be null");
        }
        this.accRole = accRole;

        // проверка descrb
        if ((descrb != null) && (descrb.length() > MAX_DESCRB_LENGTH)) {
            throw new IllegalArgumentException(
                    "Account describe length must be lesser or equal "
                    + MAX_DESCRB_LENGTH);
        }
        this.descrb = descrb;

        this.blocked = blocked;
    }

    //--------------------Getters and setters-------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        // проверка name
        if (name == null) {
            throw new IllegalArgumentException("Account name must not be null");
        }
        if (name.isEmpty() || (name.length() > MAX_NAME_LENGTH)) {
            throw new IllegalArgumentException(
                    "Account name length must be greater then zero and lesser or equal "
                    + MAX_NAME_LENGTH);
        }
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль, предварительно пребразовав его в HEX SHA-256
     *
     * @param password пароль
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public void setPassword(String password) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        // проверка password
        if (password == null) {
            throw new IllegalArgumentException("Account password must not be null");
        }
        this.password = strToPassword(password);
    }

    /**
     * Непосредственно устанавливает пароль в том виде, в котором он дан
     *
     * @param password пароль
     */
    public void setPasswordDirect(String password) {
        // проверка password
        if (password == null) {
            throw new IllegalArgumentException("Account password must not be null");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException(
                    "Account password should not be empty");
        }
        this.password = password;
    }

    public String getAccDescribe() {
        return descrb;
    }

    public void setAccDescribe(String descrb) {
        // проверка descrb
        if ((descrb != null) && (descrb.length() > MAX_DESCRB_LENGTH)) {
            throw new IllegalArgumentException(
                    "Account describe length must be lesser or equal "
                    + MAX_DESCRB_LENGTH);
        }
        this.descrb = descrb;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public RoleType getAccRole() {
        return accRole;
    }

    public void setAccRole(RoleType accRole) {
        // проверка role
        if (accRole == null) {
            throw new IllegalArgumentException("Account role must not be null");
        }
        this.accRole = accRole;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    //--------------------Methods-------------------------------------------------
    // преобразование массива байт в HEX-строку
    private static String bytesToHex(byte[] digest) {
        if (digest == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            String s = Integer.toHexString(digest[i] & 0xFF);
            if (s.length() == 1) {
                s = '0' + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }

    // шифрование строки алгоритмом SHA-256
    // и преобразование результата в HEX-строку  
    private static String strToPassword(String str) throws
            UnsupportedEncodingException, NoSuchAlgorithmException {
        if (str == null) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(str.getBytes("UTF-16"));
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.name == null && other.name != null)
                || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Account[ name=" + name + " ]";
    }
}
