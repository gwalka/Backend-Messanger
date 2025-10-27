CREATE TABLE chats (
    id BIGSERIAL PRIMARY KEY,
    firstUser_id BIGINT,
    secondUser_id BIGINT,
    FOREIGN KEY (firstUser_id) REFERENCES users(id),
    FOREIGN KEY (secondUser_id) REFERENCES users(id)
);

CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    text TEXT,
    author_id BIGINT,
    chat_id BIGINT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (chat_id) REFERENCES chats(id)
);