package ru.vat78.homeMoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vat78.homeMoney.dao.UsersDao;
import ru.vat78.homeMoney.model.User;

import java.util.List;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    UsersDao usersDao;

    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        UserDetails user;
        List<SimpleGrantedAuthority> auths = new java.util.ArrayList<SimpleGrantedAuthority>();
        auths.add(new SimpleGrantedAuthority("ROLE_USER"));

        try {
            User client = usersDao.findByName(name);
            if (client.isAdmin()) {
                auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                user = new org.springframework.security.core.userdetails.User(client.getName(),
                        client.getPassword(), auths);
            } else {
                user = new org.springframework.security.core.userdetails.User(client.getName(),
                        client.getPassword(), auths);
            }

        } catch (Exception problem) {
            throw new InternalAuthenticationServiceException(problem.getMessage(),problem);
        }

        return user;
    }

    public void checkDefaultAdmin() {

        List<User> admins = usersDao.getAllAdmins();
        if (admins == null || admins.size() == 0){
            User admin = new User();
            admin.setName("admin");
            admin.setPassword("admin");
            admin.setAdmin(true);
            usersDao.save(admin);
        }
    }
}
