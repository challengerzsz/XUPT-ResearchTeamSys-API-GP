package com.xupt.xiyoumobile.security.user;

import com.xupt.xiyoumobile.security.entity.SecurityUser;

import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private IUserService userService;

    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.selectUserByUserAccount(username);

        if (user != null) {
            SecurityUser securityUser = new SecurityUser();
//        securityUser.setId(user.getId());
//        securityUser.setUserAccount(user.getUserAccount());
//        securityUser.setUserPassword(user.getUserPassword());
            BeanUtils.copyProperties(user, securityUser);
            return securityUser;
        }
        return null;
    }

}
