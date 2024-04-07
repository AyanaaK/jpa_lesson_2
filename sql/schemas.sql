-- Categories

-- Комплектующие  (1, 10, 0 уровень глубины)
-- - Процессоры   (2, 7,  1 уровень глубины)
-- - - Intel      (3, 4,  2 уровень глубины)
-- - - AMD        (5, 6,  2 уровень глубины)
-- - ОЗУ          (8, 9,  1 уровень)

-- Аудиотехника      (11, 20, 0 уровень)
-- - Наушники        (12, 17, 1 уровень)
-- - - С микрофоном  (13, 14, 2 уровень)
-- - - Без микрофона (15, 16, 2 уровень)
-- - Колонки         (18, 19, 1 уровень)

--техника (21, 22, 0)

drop table if exists categories;

create table categories (
                            id serial8,
                            name varchar(30) not null,
                            left_key int4 not null,
                            right_key int4 not null,
                            depth int4 not null,
                            primary key(id),
                            unique(name)
);

insert into categories(name, left_key, right_key, depth)
values ('Комплектующие', 1, 10, 0),
       ('Процессоры', 2, 7, 1),
       ('Intel', 3, 4, 2),
       ('AMD', 5, 6, 2),
       ('ОЗУ', 8, 9, 1),
       ('Аудиотехника', 11, 20, 0),
       ('Наушники', 12, 17, 1),
       ('С микрофоном', 13, 14, 2),
       ('Без микрофона', 15, 16, 2),
       ('Колонки', 18, 19, 1);


select name, left_key, right_key, depth from categories