drop TABLE IF EXISTS app_user;

drop TABLE IF EXISTS category;

drop TABLE IF EXISTS transaction_tracker;

drop TABLE IF EXISTS payment_method;

drop TYPE if EXISTS transaction_type;

ENUM transaction_type { expense, income }

CREATE Table app_user (
    id GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255)
);

CREATE Table category (
    id GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255)
);

CREATE Table transaction_tracker (
    id GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount VARCHAR(255),
    category_id INTEGER REFERENCES category (id),
    app_user_id INTEGER REFERENCES app_users (id),
    payment_method_id INTEGER REFERENCES payment_method (id),
    transaction_type VARCHAR(255),
    created_at TIMESTAMP,
);

create Table payment_method (
    id GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255)
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
    ('Other');