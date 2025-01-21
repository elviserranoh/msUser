package com.eimbee.ecommerce.user.infrastructure.security;

import com.eimbee.ecommerce.user.domain.model.User;
import com.eimbee.ecommerce.user.domain.ports.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserUseCase userUseCase;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userUseCase.findByEmail(email);
        return new UserDetailsAdapter(user);
    }

    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        User user = userUseCase.findById(id);
        return new UserDetailsAdapter(user);
    }

}
