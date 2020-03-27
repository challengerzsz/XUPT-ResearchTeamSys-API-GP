package com.xupt.xiyoumobile.web.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 10:14
 */
@Data
public class User implements UserDetails {

    private int id;
    private String userAccount;
    @JsonIgnore
    private String userPassword;
    private String img;
    private String userName;
    private String major;
    private String classify;
    private int team;
    private int guideTeacherId;
    private String personalSignature;
    private String research_direction;
    private int type;
    private int sex;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
