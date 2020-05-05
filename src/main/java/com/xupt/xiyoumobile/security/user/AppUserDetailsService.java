package com.xupt.xiyoumobile.security.user;

import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.pojo.Role;
import com.xupt.xiyoumobile.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:44
 */
@Component
public class AppUserDetailsService implements UserDetailsService {

    private IUserMapper userMapper;

    @Autowired
    public AppUserDetailsService(IUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = userMapper.findByUsername(name);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
        }
        Role role = userMapper.findRoleByUserId(user.getId());
        user.setRole(role);
        return user;
    }
}
