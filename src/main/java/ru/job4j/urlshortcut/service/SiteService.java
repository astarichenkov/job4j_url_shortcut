package ru.job4j.urlshortcut.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository sites;

    public Optional<Site> findBySite(String site) {
        return sites.findBySite(site);
    }

    public Optional<Site> findByLogin(String login) {
        return sites.findByLogin(login);
    }

    public void save(Site site) {
        sites.save(site);
    }
}
