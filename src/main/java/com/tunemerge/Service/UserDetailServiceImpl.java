package com.tunemerge.Service;

import com.tunemerge.Model.Client;
import com.tunemerge.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client=userRepository.findByUsername(username);
        if(client!=null){
            return User.builder().username(client.getUsername())
                    .password(client.getPassword())
                    .roles(client.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("User not found");

    }
}
