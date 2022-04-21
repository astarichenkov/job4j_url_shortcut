package ru.job4j_url_shortcut.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j_url_shortcut.model.Site;
import ru.job4j_url_shortcut.service.SiteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final SiteService sites;

    @GetMapping("/sites")
    public List<Site> findAll() {
        return sites.findAll();
    }


}
