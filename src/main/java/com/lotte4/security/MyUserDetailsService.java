package com.lotte4.security;

import com.lotte4.entity.User;
import com.lotte4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {

        Optional<User> opt = userRepository.findByUid(uid);
        if(opt.isPresent()) {

            MyUserDetails myUserDetails = MyUserDetails.builder()
                                                .user(opt.get())
                                                .build();

            return myUserDetails;
        }
        return null;

    }
}
