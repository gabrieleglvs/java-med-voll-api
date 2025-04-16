ALTER TABLE consultas ADD motivo_cancelamento VARCHAR(255);
UPDATE consultas SET motivo_cancelamento = "";