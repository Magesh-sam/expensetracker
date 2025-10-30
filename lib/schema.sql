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
    created_at TIMESTAMP
);

create Table payment_method (
    id GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255)
);