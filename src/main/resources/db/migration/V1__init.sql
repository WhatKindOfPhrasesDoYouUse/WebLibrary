-- SET search_path TO pop;

create table book (
    id serial primary key,
    title varchar not null,
    author varchar not null,
    publishing_house varchar not null,
    year_publishing int not null,
    description varchar not null,
    age_limit int not null check ( age_limit in (0, 6, 12, 16, 18)),
    category varchar not null check ( category in ('Техническая литература', 'Хобби', 'Домашнее хозяйство',
                                                  'Художественная'))
);

create table users (
    id serial primary key,
    name varchar not null,
    age int not null,
    username varchar not null unique,
    email varchar not null unique,
    phone_number varchar not null,
    password varchar not null
);

/*create table users_books (
    id serial primary key,
    user_id int references users (id),
    book_id int references book (id),
    borrow_date timestamp,
    return_date timestamp
);*/

create table roles (
    id serial primary key,
    name varchar unique not null
);

CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

/*create table review (
    id serial primary key,
    book_id int not null,
    user_id int not null,
    rating int not null check (rating between 1 and 5),
    comment varchar,
    created_at timestamp default CURRENT_TIMESTAMP,
    foreign key (book_id) references book (id),
    foreign key (user_id) references users (id)
);*/

INSERT INTO book (title, author, publishing_house, year_publishing, description, age_limit, category)
VALUES (
           'Философия Java',
           'Брюс Эккель',
           'Питер',
           2022,
           'Книга, написанная известным программистом и создателем языка программирования Java Брюсом Эккелем. ' ||
           'В этой книге автор делится своими мыслями и философией, лежащей в основе разработки и использования Java. ' ||
           'Книга рассматривает принципы дизайна языка, его философию безопасности, переносимости и производительности. ' ||
           'Эккель поднимает вопросы о том, как писать надежный и эффективный код, ' ||
           'а также обсуждает преимущества и недостатки различных подходов к программированию. ' ||
           'Эта книга стала своеобразным руководством для разработчиков, ' ||
           'стремящихся понять не только технические аспекты Java, ' ||
           'но и ее общую философию и принципы разработки программного обеспечения.',
           0,
           'Техническая литература'
       );

INSERT INTO book (title, author, publishing_house, year_publishing, description, age_limit, category)
VALUES (
           '451 градус по Фаренгейту',
           'Рэй Брэдбери',
           'Шарм',
           1953,
           'Роман, в котором описывается антиутопическое общество, в котором книги считаются опасным оружием. ' ||
           'Автор затрагивает темы цензуры, подавления свободы мысли и интеллектуального развития. ' ||
           'Число "451" в названии относится к температуре, при которой бумага загорается и горит.',
           12,
           'Художественная'
       );

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');

-- ROLE_ADMIN username: dima password: dima123
-- ROLE_USER username: sanya password: sanya123


insert into users (name, age, username, email, phone_number, password)
values ('Dmitry', 20, 'dima', 'dima@gmail.com', '+79123314526', '$2a$12$3GHx9Vu5TKYGtiLwd3p.aeD6s9Q4hvOcWwvBj3.6BwKnL//Gw2Yti'),
       ('Alexandr', 5, 'sanya', 'sasha@gmail.com', '+79265124421', '$2a$12$RfPmP9b1AD8zz4VdkW4jrevJSbvXxLf3hGR8O1X3UK5WTeD5RZEJW');

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'dima'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE username = 'sanya'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));
