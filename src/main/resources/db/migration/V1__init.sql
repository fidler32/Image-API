CREATE TABLE IF NOT EXISTS images(
                        id integer PRIMARY KEY,
                        label VARCHAR not null,
                        objects VARCHAR[],
                        metadata TEXT,
                        image TEXT
);