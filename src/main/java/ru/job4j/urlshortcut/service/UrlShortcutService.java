package ru.job4j.urlshortcut.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.repository.UrlShortcutRepository;
import ru.job4j.urlshortcut.model.UrlShortcut;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlShortcutService {
    private final UrlShortcutRepository urls;

    public void save(UrlShortcut urlShortcut) {
        this.urls.save(urlShortcut);
    }

    public Optional<UrlShortcut> findByUrl(String url) {
        return this.urls.findByUrl(url);
    }

    public Optional<UrlShortcut> findByCode(String code) {
        return this.urls.findByCode(code);
    }

    public void incrementCount(long urlShortcutId) {
        this.urls.incrementCount(urlShortcutId);
    }

    public List<UrlShortcut> findAll() {
        return (List<UrlShortcut>) this.urls.findAll();
    }
}
