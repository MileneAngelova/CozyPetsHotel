-- INSERT INTO users(username, password, email, age)
-- VALUES ('admin', '$2a$12$PHmYvNAlTL08tYVEMjMTK.7Uc20VcxVznHXQyqhV9PNRwY7bNXF0i', 'admin@mail.com', 33);
-- ('petar', '$2a$12$pp/k7HzJ2E86kPfX7YA8Oe7tjHgNMhGv8pVSOS3Pz0ppAwIfSbR1C', 'petar@abv.bg', 23);

INSERT INTO users_roles(user_id, roles_id)
VALUES(1, 1),
      (1, 2);
--       (2, 2);