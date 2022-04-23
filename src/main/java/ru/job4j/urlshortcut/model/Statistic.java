package ru.job4j.urlshortcut.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Statistic {
    private String url;
    private long total;

    public static Statistic of(String url, long total) {
        return new Statistic(url, total);
    }
}
