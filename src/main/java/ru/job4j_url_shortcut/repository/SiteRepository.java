package ru.job4j_url_shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j_url_shortcut.model.Site;

@Repository
public interface SiteRepository extends CrudRepository<Site, Long> {
}
