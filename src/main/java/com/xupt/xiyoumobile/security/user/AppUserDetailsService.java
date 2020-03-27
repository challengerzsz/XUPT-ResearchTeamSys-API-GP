package com.xupt.xiyoumobile.security.user;

import com.xupt.xiyoumobile.web.dao.IUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:44
 */
@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("UserAccount check failed, UserAccount can not be null or empty");
        }

        return userMapper.getUserAccountPassword(username);
    }
}
