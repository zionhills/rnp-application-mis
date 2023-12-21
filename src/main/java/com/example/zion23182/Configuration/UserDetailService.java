package com.example.zion23182.Configuration;

import com.example.zion23182.Model.User;
import com.example.zion23182.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailService implements UserDetailsService{
@Autowired private UserRepository userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        User User =userrepo.findByEmail(username).get();
        if(User == null)throw new UsernameNotFoundException("Unimplemented method  loadUserByUsername");
    return new UserDetailPrinciple(User);
    }
}
