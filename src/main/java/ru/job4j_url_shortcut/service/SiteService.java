package ru.job4j_url_shortcut.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j_url_shortcut.model.Site;
import ru.job4j_url_shortcut.repository.SiteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository sites;

    public List<Site> findAll() {
        return (List<Site>) sites.findAll();
    }
}
