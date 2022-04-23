package ru.job4j.urlshortcut.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SiteService sites;

    public UserDetailsServiceImpl(SiteService sites) {
        this.sites = sites;
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> opt = sites.findByLogin(login);
        if (opt.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        Site site = opt.get();
        return new User(site.getLogin(), site.getPassword(), emptyList());
    }
}