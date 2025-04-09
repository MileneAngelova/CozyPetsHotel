INSERT INTO roles VALUES (1, 'ADMIN'), (2, 'USER');


INSERT INTO users(id, first_name, username, password, email, is_active, uuid)
VALUES (1, 'Admin', 'admin', 'f95d600a99d805f10a7d133729122fcc09e3c0a53e6fe64cba3f969f081d3d565b604a265ca9a8a7e2c197f75165363f', 'admin@mail.com', true, '8dda5916-f8dd-40ca-80f7-a9cdacc444f8');


INSERT INTO users_roles(user_id, role_id)
VALUES  (1, 1),
        (1, 2);
