package ru.job4j.urlshortcut.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Statistic;
import ru.job4j.urlshortcut.model.UrlShortcut;
import ru.job4j.urlshortcut.service.SiteService;
import ru.job4j.urlshortcut.service.UrlShortcutService;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UrlShortcutController {
    private static final int LOGIN_LENGTH = 8;
    private static final int SHORT_ID_LENGTH = 8;
    private static final int PASSWORD_LENGTH = 12;
    private final PasswordEncoder encoder;
    private final SiteService sites;
    private final UrlShortcutService urls;

    @PostMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> convert(@Valid @RequestBody UrlShortcut urlShortcut) {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(urlShortcut.getUrl())) {
            throw new IllegalArgumentException("URL is not valid");
        }
        Optional<UrlShortcut> opt = urls.findByUrl(urlShortcut.getUrl());
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get().returnCode(), HttpStatus.OK);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Site site = sites.findByLogin((String) auth.getPrincipal()).get();
        String code = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
        urlShortcut.setCode(code);
        urlShortcut.setSite(site);
        urls.save(urlShortcut);
        return new ResponseEntity<>(urlShortcut.returnCode(), HttpStatus.OK);
    }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registration(@Valid @RequestBody Site site) {
        JSONObject response = new JSONObject();
        if (sites.findBySite(site.getSite()).isPresent()) {
            response.put("registration", false);
            response.put("message", "Site is already registered");
            return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
        }
        String password = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
        String encodedPassword = encoder.encode(password);
        String login = RandomStringUtils.randomAlphabetic(LOGIN_LENGTH);
        site.setLogin(login);
        site.setPassword(encodedPassword);
        sites.save(site);
        response.put("registration", true);
        response.put("login", login);
        response.put("password", password);
        return new ResponseEntity<>(response.toString(), HttpStatus.OK);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        Optional<UrlShortcut> opt = urls.findByCode(code);
        if (opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UrlShortcut urlShortcut = opt.get();
        urls.incrementCount(urlShortcut.getId());
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlShortcut.getUrl())).build();
    }

    @GetMapping(value = "/statistic", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Statistic> getStatistic() {
        List<Statistic> statistics = new ArrayList<>();
        List<UrlShortcut> shortcuts = urls.findAll();
        shortcuts.forEach(e -> {
            statistics.add(Statistic.of(e.getUrl(), e.getCount()));
        });
        return statistics;
    }

}
