package com.xupt.xiyoumobile.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * Spring Security User entity
 * @author : zengshuaizhi
 * @date : 2020-03-27 10:14
 */
@Data
public class SecurityUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 7003620264717866994L;

    private Long id;
    private String userAccount;
    private String userName;
    private String userPassword;
    private Integer ban = 0;


    /**
     * 用户角色
     */
    private Collection<GrantedAuthority> authorities;
    /**
     * 账户是否过期
     */
    private boolean isAccountNonExpired = false;
    /**
     * 账户是否被锁定
     */
    private boolean isAccountNonLocked = false;
    /**
     * 证书是否过期
     */
    private boolean isCredentialsNonExpired = false;
    /**
     * 账户是否有效
     */
    private boolean isEnabled = (ban == 0);


    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userAccount;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
