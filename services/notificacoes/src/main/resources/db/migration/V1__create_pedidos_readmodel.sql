CREATE TABLE IF NOT EXISTS pedidos_readmodel (
  pedido_id VARCHAR(36) PRIMARY KEY,
  cliente_id VARCHAR(36),
  nome_cliente VARCHAR(255),
  email_cliente VARCHAR(255),
  status VARCHAR(50),
  valor_total DECIMAL(19,2),
  criado_em TIMESTAMP
);
