package com.wotos.wotosuserservice.security;

import com.wotos.wotosuserservice.dao.UserRepo;
import com.wotos.wotosuserservice.model.CustomUserDetails;
import com.wotos.wotosuserservice.model.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Encoder encoder = new Encoder();

    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> localUser = repo.findLocalUserByUsername(username); // use jpa

        localUser.orElseThrow(() -> new UsernameNotFoundException("User:" + username + "Not Found"));

        return localUser.map(CustomUserDetails::new).get();
    }

}
