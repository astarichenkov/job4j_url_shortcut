create table if not exists sites
(
    id       bigserial
        constraint sites_pkey
            primary key,
    login    varchar(255),
    password varchar(255),
    site     varchar(255)
);

create table if not exists url_shortcut
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

