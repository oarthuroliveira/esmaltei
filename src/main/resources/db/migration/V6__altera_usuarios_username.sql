ALTER TABLE usuarios
ADD COLUMN username VARCHAR(100);

UPDATE usuarios
SET username = email
WHERE username IS NULL;

ALTER TABLE usuarios
ALTER COLUMN username SET NOT NULL;

ALTER TABLE usuarios
ADD CONSTRAINT uk_usuario_username UNIQUE (username);