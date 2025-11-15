drop TABLE IF EXISTS app_user;

drop TABLE IF EXISTS category;

drop TABLE IF EXISTS transaction_tracker;

drop TYPE if EXISTS transaction_type;

CREATE TYPE transaction_type as ENUM ( 'expense', 'income' );

CREATE Table app_user (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE category (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app_user_id INT REFERENCES app_user (id) ON DELETE CASCADE DEFAULT NULL,
    name VARCHAR(255) NOT NULL,
    transaction_type transaction_type NOT NULL
);

CREATE TABLE transaction_tracker (
    name VARCHAR(255) NOT NULL,
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount DECIMAL(10, 2),
    category_id INT REFERENCES category (id) ON DELETE SET NULL,
    app_user_id INT REFERENCES app_user (id) ON DELETE CASCADE,
    transaction_type transaction_type NOT NULL,
    created_at TIMESTAMP NOT NULL
);

INSERT INTO
    payment_method (name)
VALUES ('Cash'),
    ('Credit Card'),
    ('Debit Card'),
    ('Net Banking'),
    ('UPI'),
    ('Other');

INSERT INTO
    category (name, transaction_type)
VALUES ('Groceries', 'expense'),
    ('Restaurants', 'expense'),
    ('Entertainment', 'expense'),
    ('Clothing', 'expense'),
    ('Health', 'expense'),
    ('Travel', 'expense'),
    ('Salary', 'income'),
    ('Freelance', 'income'),
    ('Bonus', 'income'),
    ('Other', 'income'),
    ('Other', 'expense');