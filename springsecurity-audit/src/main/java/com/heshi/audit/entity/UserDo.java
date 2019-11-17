package com.heshi.audit.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户对象
 */
@Entity
@Table(name = UserDo.TABLE_NAME)
public class UserDo implements Serializable {
    public static final String TABLE_NAME = "BASE_USERINFO";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USER_NAME", nullable = false)
    private String name;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "VALID", nullable = false)
    private Boolean valid;
    @Column(name = "PERMISSION")
    private String permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
