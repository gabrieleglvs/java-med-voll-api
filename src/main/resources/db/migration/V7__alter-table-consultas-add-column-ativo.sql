ALTER TABLE consultas ADD ativo tinyint;
UPDATE consultas SET ativo = 1;