drop TABLE IF EXISTS app_user;

drop TABLE IF EXISTS category;

drop TABLE IF EXISTS transaction_tracker;

drop TABLE IF EXISTS payment_method;

drop TYPE if EXISTS transaction_type;

CREATE TYPE transaction_type as ENUM ( 'expense', 'income' );

CREATE Table app_user (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(10) UNIQUE NOT NULL CHECK (mobile_number ~ '^[0-9]{10}$')
);

CREATE TABLE category (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app_user_id INT REFERENCES app_user (id) ON DELETE CASCADE DEFAULT NULL,
    name VARCHAR(255) NOT NULL,
    transaction_type transaction_type NOT NULL
);
CREATE TABLE user_category (
    app_user_id INT REFERENCES app_user (id) ON DELETE CASCADE,
    category_id INT REFERENCES category (id) ON DELETE CASCADE,
    PRIMARY KEY (app_user_id, category_id)
);

create Table payment_method (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE transaction_tracker (
    name VARCHAR(255) NOT NULL,
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount DECIMAL(10, 2),
    category_id INT REFERENCES category (id) ON DELETE SET NULL,
    app_user_id INT REFERENCES app_user (id) ON DELETE CASCADE,
    payment_method_id INT REFERENCES payment_method (id) ON DELETE SET NULL,
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
    category (name,transaction_type)
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