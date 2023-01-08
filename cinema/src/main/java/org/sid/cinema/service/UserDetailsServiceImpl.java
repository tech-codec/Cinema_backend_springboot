package org.sid.cinema.service;


import org.sid.cinema.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser userApp = accountService.findUserByUsername(username);
        if(userApp==null) throw new UsernameNotFoundException(username);
        boolean enabled = !userApp.isAccountVerified();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        userApp.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });

        return new User(userApp.getUsername(), userApp.getPassword(),enabled, true, true,true, authorities);
    }
}
