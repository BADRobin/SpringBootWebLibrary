package oleg.bryl.springbootweblibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import oleg.bryl.springbootweblibrary.model.User;
import oleg.bryl.springbootweblibrary.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User: " + username + " not found"));
        return user;
    }
}
