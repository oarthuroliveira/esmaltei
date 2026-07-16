ALTER TABLE usuarios
DROP COLUMN imagem;

ALTER TABLE usuarios
ADD COLUMN imagem TEXT;

ALTER TABLE postagens
DROP COLUMN foto;

ALTER TABLE postagens
ADD COLUMN foto TEXT;

ALTER TABLE esmaltes
DROP COLUMN imagem;

ALTER TABLE esmaltes
ADD COLUMN imagem TEXT;