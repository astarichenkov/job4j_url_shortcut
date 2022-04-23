package ru.job4j.urlshortcut.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlShortcut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "URL must be non null")
    private String url;
    private String code;
    private long count;

    @ManyToOne
    private Site site;

    public String returnCode() {
        return "{"
                + "\"code\": " + "\"" + code + "\""
                + "}";
    }
}