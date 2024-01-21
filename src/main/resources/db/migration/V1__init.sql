-- SET search_path TO pop;

create table book (
    id serial primary key,
    title varchar not null,
    author varchar not null,
    quantity int not null,
    publishing_house varchar not null,
    year_publishing int not null,
    description varchar not null,
    availableForRent boolean default true not null,
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

create table rent (
    id serial primary key,
    user_id int not null,
    book_id int not null,
    rented boolean default false not null,
    rented_start date not null,
    rented_end date not null,
    foreign key (user_id) references users (id),
    foreign key (book_id) references book (id)
);

create table review (
    id serial primary key,
    book_id int not null,
    user_id int not null,
    review_text varchar,
    review_rating int not null check ( review_rating >= 1 and review_rating <= 5),
    foreign key (book_id) references book (id),
    foreign key (user_id) references users (id)
);

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


INSERT INTO roles (name)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');


INSERT INTO book (title, author, publishing_house, quantity, year_publishing, description, age_limit, category)
VALUES (
           'Философия Java',
           'Брюс Эккель',
           'Питер',
            2,
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

INSERT INTO book (title, author, publishing_house, quantity, year_publishing, description, age_limit, category)
VALUES (
           '451 градус по Фаренгейту',
           'Рэй Брэдбери',
           'Шарм',
            5,
           1953,
           'Роман, в котором описывается антиутопическое общество, в котором книги считаются опасным оружием. ' ||
           'Автор затрагивает темы цензуры, подавления свободы мысли и интеллектуального развития. ' ||
           'Число "451" в названии относится к температуре, при которой бумага загорается и горит.',
           12,
           'Художественная'
       );

INSERT INTO book (title, author, publishing_house, quantity, year_publishing, description, age_limit, category)
VALUES (
           'Война и мир',
           'Лев Толстой',
           'Северная Пальмира',
            8,
           1869,
           'Величайшее произведение русской литературы. ' ||
           'Описывает события французско-русской войны 1812 года и влияние исторических событий ' ||
           'на судьбы нескольких аристократических семей.',
           16,
           'Художественная'
       );

INSERT INTO book (title, author, publishing_house, quantity, year_publishing, description, age_limit, category)
VALUES (
           'Все для рыбалки',
           'Леонид Павлович Сабанеев',
           'Печатник',
            5,
           1955,
           'Практическое руководство по рыбной ловле, включающее советы по выбору снастей, ' ||
           'различным видам рыбалки и подробное описание мест для успешного лова. ' ||
           'Автор дает советы как начинающим рыбакам, так и опытным любителям.',
           0,
           'Хобби'
       );

INSERT INTO book (title, author, publishing_house, quantity, year_publishing, description, age_limit, category)
VALUES (
           'Современное руководство по домашнему хозяйству',
           'Анна Иванова',
           'Дом и семья',
            3,
           2020,
           'Эта книга предлагает практические советы по эффективному ведению домашнего хозяйства. ' ||
           'Автор дает рекомендации по планированию бюджета, управлению временем, ' ||
           'организации пространства и поддержанию чистоты и порядка в доме. ' ||
           'Особое внимание уделяется рецептам быстрых и вкусных блюд для всей семьи.',
           0,
           'Домашнее хозяйство'
       );

INSERT INTO book (title, author, publishing_house, quantity, year_publishing, description, age_limit, category)
VALUES (
           'Изучаем SQL',
           'Линн Бейли',
           'O''Reilly Media',
            6,
           2020,
           'Книга "Изучаем SQL" написана Линн Бейли и предназначена для тех, ' ||
           'кто хочет освоить язык структурированных запросов. В ней подробно рассматриваются ' ||
           'основные концепции SQL, начиная от базовых запросов SELECT и заканчивая более сложными темами, ' ||
           'такими как проектирование баз данных и оптимизация запросов.',
           0,
           'Техническая литература'
       );


-- ROLE_ADMIN username: dima password: dima123
-- ROLE_USER username: sanya password: sanya123

insert into users (name, age, username, email, phone_number, password)
values ('Dmitry', 20, 'dima', 'dima@gmail.com', '+79123314526', '$2a$12$3GHx9Vu5TKYGtiLwd3p.aeD6s9Q4hvOcWwvBj3.6BwKnL//Gw2Yti'),
       ('Alexandr', 5, 'sanya', 'sasha@gmail.com', '+79265124421', '$2a$12$RfPmP9b1AD8zz4VdkW4jrevJSbvXxLf3hGR8O1X3UK5WTeD5RZEJW');

INSERT INTO user_role (user_id, role_id)
VALUES ((SELECT id FROM users WHERE username = 'dima'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')),
       ((SELECT id FROM users WHERE username = 'sanya'), (SELECT id FROM roles WHERE name = 'ROLE_USER'));


/*INSERT INTO rent (user_id, book_id, taken, rent_start, rent_end) VALUES
    ((SELECT id FROM users WHERE username = 'dima'),
     (SELECT id FROM book WHERE id = 1),
     false,
     CURRENT_DATE,
     CURRENT_DATE + 7);*/
