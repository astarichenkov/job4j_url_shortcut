package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.model.UrlShortcut;

import java.util.Optional;

@Repository
public interface UrlShortcutRepository extends CrudRepository<UrlShortcut, Long> {
    Optional<UrlShortcut> findByUrl(String url);

    Optional<UrlShortcut> findByCode(String code);

    @Transactional
    @Modifying
    @Query("update UrlShortcut u set u.count = u.count + 1 where u.id = ?1")
    void incrementCount(long urlShortcutId);
}
