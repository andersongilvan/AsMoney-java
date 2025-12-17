CREATE TABLE transactions(

    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    amount NUMERIC(10, 3) NOT NULL,
    description TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)

);