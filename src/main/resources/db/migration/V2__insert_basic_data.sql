-- Rollerin eklenmesi
INSERT INTO roles (name) VALUES 
('ROLE_ADMIN'),
('ROLE_USER'),
('ROLE_MANAGER');

-- Admin kullanıcısının eklenmesi
INSERT INTO users (username, password, email, first_name, last_name) 
VALUES (
    'admin',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', -- şifre: admin123
    'admin@yevmie.com',
    'Admin',
    'User'
);

-- Admin kullanıcısına ROLE_ADMIN rolünün atanması
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id 
FROM users u, roles r 
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN'; 