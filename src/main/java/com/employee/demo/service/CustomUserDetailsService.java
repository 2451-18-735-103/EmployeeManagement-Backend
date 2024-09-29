package com.employee.demo.service;


import com.employee.demo.jwt.CustomUserPrinciple;
import com.employee.demo.model.User;
import com.employee.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Optional<User> userOptional = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new User(
//                user.get().getUsername(),
//                user.get().getPassword(),
//                employee.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                        .collect(Collectors.toList())
//        );
//    }
//}
//        if (!userOptional.isPresent()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        User user = userOptional.get();
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                        .collect(Collectors.toList())
//        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return CustomUserPrinciple.create(user);
    }


}