package com.mcb.users.impli;

import com.mcb.users.model.User;
import com.mcb.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername method");
        User user = repository.findByEmail(username);
        if (!Objects.isNull(user)) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User Not Found");
        }
    }
}
