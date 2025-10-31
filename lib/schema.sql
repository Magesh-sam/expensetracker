drop TABLE IF EXISTS app_user;

drop TABLE IF EXISTS category;

drop TABLE IF EXISTS transaction_tracker;

drop TABLE IF EXISTS payment_method;

drop TYPE if EXISTS transaction_type;

CREATE TYPE transaction_type as ENUM ( 'expense', 'income' );

CREATE Table app_user (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255)
);

CREATE Table category (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

create Table payment_method (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE Table transaction_tracker (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount VARCHAR(255),
    category_id INT REFERENCES category (id),
    app_user_id INT REFERENCES app_user (id),
    payment_method_id INT REFERENCES payment_method (id),
    transaction_type VARCHAR(255),
    created_at TIMESTAMP
);

INSERT INTO
    payment_method (name)
VALUES ('Cash'),
    ('Credit Card'),
    ('Debit Card');

INSERT INTO
    category (name)
VALUES ('Groceries'),
    ('Restaurants'),
    ('Entertainment'),
    ('Clothing'),
    ('Health'),
    ('Travel'),
    ('Salary'),
    ('Freelance'),
    ('Bonus'),
    ('Other');
