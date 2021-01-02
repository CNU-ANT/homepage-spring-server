
    create table article (
       id bigint not null auto_increment,
        board_id integer,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        hit integer,
        is_deleted bit,
        subject varchar(255),
        tags varchar(255),
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table article_likes (
       post_id bigint not null,
        username varchar(255) not null,
        primary key (post_id, username)
    ) engine=InnoDB

    create table board (
       id integer not null,
        name varchar(255),
        type varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table comment (
       id integer not null,
        article_id bigint,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        like integer not null,
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table files_meta (
       id bigint not null auto_increment,
        article_id bigint,
        created_at datetime,
        file_type varchar(255),
        file_url varchar(255),
        filename varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table recommend (
       id integer not null,
        article_id integer,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table users (
       username varchar(255) not null,
        created_at datetime,
        email varchar(255),
        last_accessed datetime,
        name varchar(255),
        nickname varchar(255),
        password varchar(255),
        profile varchar(255),
        role varchar(255),
        student_id integer,
        primary key (username)
    ) engine=InnoDB

    alter table article 
       add constraint FK4y8m6ilvjatwv7xvdgdjviw13 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FKbylrsnh0ny69yoj1vuvjn2iyg 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FK5yx0uphgjc6ik6hb82kkw501y 
       foreign key (article_id) 
       references article (id)

    alter table files_meta 
       add constraint FKkquhq87g9qxi5o8f7msnq4fp7 
       foreign key (article_id) 
       references article (id)

    alter table recommend 
       add constraint FKmi45x3ewxh5i2cy7ji7gamhgo 
       foreign key (article_id) 
       references article (id)

    create table article (
       id bigint not null auto_increment,
        board_id integer,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        hit integer,
        is_deleted bit,
        subject varchar(255),
        tags varchar(255),
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table article_likes (
       post_id bigint not null,
        username varchar(255) not null,
        primary key (post_id, username)
    ) engine=InnoDB

    create table board (
       id integer not null,
        name varchar(255),
        type varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table comment (
       id integer not null,
        article_id bigint,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        like integer not null,
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table files_meta (
       id bigint not null auto_increment,
        article_id bigint,
        created_at datetime,
        file_type varchar(255),
        file_url varchar(255),
        filename varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table recommend (
       id integer not null,
        article_id integer,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table users (
       username varchar(255) not null,
        created_at datetime,
        email varchar(255),
        last_accessed datetime,
        name varchar(255),
        nickname varchar(255),
        password varchar(255),
        profile varchar(255),
        role varchar(255),
        student_id integer,
        primary key (username)
    ) engine=InnoDB

    alter table article 
       add constraint FK4y8m6ilvjatwv7xvdgdjviw13 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FKbylrsnh0ny69yoj1vuvjn2iyg 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FK5yx0uphgjc6ik6hb82kkw501y 
       foreign key (article_id) 
       references article (id)

    alter table files_meta 
       add constraint FKkquhq87g9qxi5o8f7msnq4fp7 
       foreign key (article_id) 
       references article (id)

    alter table recommend 
       add constraint FKmi45x3ewxh5i2cy7ji7gamhgo 
       foreign key (article_id) 
       references article (id)

    create table article (
       id bigint not null auto_increment,
        board_id integer,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        hit integer,
        is_deleted bit,
        subject varchar(255),
        tags varchar(255),
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table article_likes (
       post_id bigint not null,
        username varchar(255) not null,
        primary key (post_id, username)
    ) engine=InnoDB

    create table board (
       id integer not null,
        name varchar(255),
        type varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table comment (
       id integer not null,
        article_id bigint,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        like integer not null,
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table files_meta (
       id bigint not null auto_increment,
        article_id bigint,
        created_at datetime,
        file_type varchar(255),
        file_url varchar(255),
        filename varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table recommend (
       id integer not null,
        article_id integer,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table users (
       username varchar(255) not null,
        created_at datetime,
        email varchar(255),
        last_accessed datetime,
        name varchar(255),
        nickname varchar(255),
        password varchar(255),
        profile varchar(255),
        role varchar(255),
        student_id integer,
        primary key (username)
    ) engine=InnoDB

    alter table article 
       add constraint FK4y8m6ilvjatwv7xvdgdjviw13 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FKbylrsnh0ny69yoj1vuvjn2iyg 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FK5yx0uphgjc6ik6hb82kkw501y 
       foreign key (article_id) 
       references article (id)

    alter table files_meta 
       add constraint FKkquhq87g9qxi5o8f7msnq4fp7 
       foreign key (article_id) 
       references article (id)

    alter table recommend 
       add constraint FKmi45x3ewxh5i2cy7ji7gamhgo 
       foreign key (article_id) 
       references article (id)

    create table article (
       id bigint not null auto_increment,
        board_id integer,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        hit integer,
        is_deleted bit,
        subject varchar(255),
        tags varchar(255),
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table article_likes (
       post_id bigint not null,
        username varchar(255) not null,
        primary key (post_id, username)
    ) engine=InnoDB

    create table board (
       id integer not null,
        name varchar(255),
        type varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table comment (
       id integer not null,
        article_id bigint,
        content varchar(255),
        created_at datetime,
        depth integer,
        no integer,
        grpord integer,
        like integer not null,
        updated_at datetime,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table files_meta (
       id bigint not null auto_increment,
        article_id bigint,
        created_at datetime,
        file_type varchar(255),
        file_url varchar(255),
        filename varchar(255),
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table recommend (
       id integer not null,
        article_id integer,
        username varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table users (
       username varchar(255) not null,
        created_at datetime,
        email varchar(255),
        last_accessed datetime,
        name varchar(255),
        nickname varchar(255),
        password varchar(255),
        profile varchar(255),
        role varchar(255),
        student_id integer,
        primary key (username)
    ) engine=InnoDB

    alter table article 
       add constraint FK4y8m6ilvjatwv7xvdgdjviw13 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FKbylrsnh0ny69yoj1vuvjn2iyg 
       foreign key (username) 
       references users (username)

    alter table comment 
       add constraint FK5yx0uphgjc6ik6hb82kkw501y 
       foreign key (article_id) 
       references article (id)

    alter table files_meta 
       add constraint FKkquhq87g9qxi5o8f7msnq4fp7 
       foreign key (article_id) 
       references article (id)

    alter table recommend 
       add constraint FKmi45x3ewxh5i2cy7ji7gamhgo 
       foreign key (article_id) 
       references article (id)
