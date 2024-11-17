package com.auth.mfa.service;

import com.auth.mfa.persistence.model.Privilege;
import com.auth.mfa.persistence.model.Role;
import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.repository.RepositoryUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service @RequiredArgsConstructor
public class ServiceCustomUserDetails implements UserDetailsService {

    private final RepositoryUser repositoryUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repositoryUser.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return new org.springframework.security.core.userdetails.User(Objects.requireNonNull(user).getUsername(), user.getPassword(), getAuthorities(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }
    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getId().toString());
            collection.addAll(role.getPrivilege());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}