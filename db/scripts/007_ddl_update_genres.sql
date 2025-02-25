DROP FOREIGN TABLE genres CASCADE;
CREATE TYPE genres AS ENUM('Ужасы', 'Комедия', 'Боевик', 'Драма', 'Фэнтэзи');

ALTER TABLE films RENAME COLUMN genre_id TO genre;

ALTER TABLE films ALTER COLUMN genre type varchar(25);

ALTER TABLE films ALTER COLUMN genre type genres;
-- DROP FOREIGN TABLE genres RESTRICT ;