-- -- init.sql

-- -- Create the table ONLY if it doesn't exist
-- -- Adjust column types/constraints if they differ in your User entity
-- CREATE TABLE IF NOT EXISTS app_user (
--     id BIGSERIAL PRIMARY KEY,  
--     username VARCHAR(255) UNIQUE NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     role VARCHAR(255) NOT NULL
--     -- Add other columns here if your User entity has them (e.g., email, enabled)
-- );

-- -- Attempt to insert the user, ignore if the username already exists
-- INSERT INTO app_user (username, password, role) VALUES ('user', 'HasshedPasswordFromPasswordEncoder', 'ROLE_USER') ON CONFLICT (username) DO NOTHING;
