CREATE DATABASE IF NOT EXISTS promptvault;
CREATE USER IF NOT EXISTS 'promptvault'@'localhost' IDENTIFIED BY 'promptvault';
GRANT ALL PRIVILEGES ON promptvault.* TO 'promptvault'@'localhost';
FLUSH PRIVILEGES;

USE promptvault;

create table policy_keywords
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    text       varchar(255) null
);

create table prompt_categories
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)  not null,
    updated_at datetime(6)  not null,
    name       varchar(255) null
);

create table prompt_vault_users
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)                  not null,
    updated_at    datetime(6)                  not null,
    email         varchar(255)                 not null,
    first_name    varchar(255)                 null,
    last_name     varchar(255)                 null,
    password_hash varchar(255)                 null,
    role          enum ('admin', 'user')       null,
    status        enum ('disabled', 'enabled') null,
    username      varchar(255)                 not null,
    constraint UKeg5sdlactqw51dbniqyr2qrin
        unique (email),
    constraint UKh1dfjy1dthq9b8vyi4mtlhewq
        unique (username)
);

create table prompt_history_items
(
    id            bigint auto_increment
        primary key,
    created_at    datetime(6)  not null,
    updated_at    datetime(6)  not null,
    prompt_text   varchar(255) not null,
    response_text text         not null,
    fk_account_id bigint       null,
    constraint FKs7irxny1a5qly2f5p892ufop2
        foreign key (fk_account_id) references prompt_vault_users (id)
);

create index IDXeg5sdlactqw51dbniqyr2qrin
    on prompt_vault_users (email);

create index IDXh1dfjy1dthq9b8vyi4mtlhewq
    on prompt_vault_users (username);

create table prompts
(
    id             bigint auto_increment
        primary key,
    created_at     datetime(6)                 not null,
    updated_at     datetime(6)                 not null,
    prompt_text    varchar(255)                null,
    title          varchar(255)                null,
    visibility     enum ('personal', 'shared') null,
    fk_account_id  bigint                      null,
    fk_category_id bigint                      null,
    constraint FKi0yxbrv2hqxjlgfx8u060qdjd
        foreign key (fk_account_id) references prompt_vault_users (id),
    constraint FKkklvbny4e9flq6cvj7n9wi2r0
        foreign key (fk_category_id) references prompt_categories (id)
);

create table flagged_prompts
(
    id           bigint auto_increment
        primary key,
    created_at   datetime(6) not null,
    updated_at   datetime(6) not null,
    fk_prompt_id bigint      null,
    constraint UK4hdd3fqj5px4rf5bm0r42mwrs
        unique (fk_prompt_id),
    constraint FKtbw8lyh0t7rm881wcnqo3gsd8
        foreign key (fk_prompt_id) references prompts (id)
);

create table flagged_prompt_policy_keywords
(
    flagged_prompt_id bigint not null,
    policy_keyword_id bigint not null,
    constraint FK7c73lgltfwyx7nvve59li86eg
        foreign key (flagged_prompt_id) references flagged_prompts (id),
    constraint FKndhimtns0n9v8feaddlw4tgb1
        foreign key (policy_keyword_id) references policy_keywords (id)
);

