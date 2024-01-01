package com.sitesstorageproject.security;

import com.sitesstorageproject.entities.User;
import com.sitesstorageproject.repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCreditsService implements UserDetailsService {
    private final UserRepository userRepo;

    public UserCreditsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username.toLowerCase());
        if (user == null) {
            user = userRepo.findByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("Not found: " + username);
            }
        }

        return new UserCredits(user);
    }
}
