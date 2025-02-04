INSERT INTO users(first_name, username, password, email, age, is_active)
VALUES ('Admin', 'admin', 'f95d600a99d805f10a7d133729122fcc09e3c0a53e6fe64cba3f969f081d3d565b604a265ca9a8a7e2c197f75165363f', 'admin@mail.com', 33, true);
-- ('petar', '$2a$12$pp/k7HzJ2E86kPfX7YA8Oe7tjHgNMhGv8pVSOS3Pz0ppAwIfSbR1C', 'petar@abv.bg', 23);

INSERT INTO users_roles(user_id, role_id)
VALUES(1, 1),
      (1, 2);
--       (2, 2);