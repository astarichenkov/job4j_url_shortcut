create table sites
(
    id       bigserial
        constraint sites_pkey
            primary key,
    login    varchar(255),
    password varchar(255),
    site     varchar(255)
);

alter table sites
    owner to postgres;

create table url_shortcut
(
    id      bigserial
        constraint url_shortcut_pkey
            primary key,
    code    varchar(255),
    count   bigint       not null,
    url     varchar(255) not null,
    site_id bigint
        constraint fkme529jd16uaewkrdfr3fu2duj
            references sites
);

alter table url_shortcut
    owner to postgres;

