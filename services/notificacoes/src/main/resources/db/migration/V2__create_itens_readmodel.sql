CREATE TABLE IF NOT EXISTS itens_readmodel (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pedido_id VARCHAR(36) NOT NULL,
  nome_produto VARCHAR(255),
  quantidade INT,
  preco_unitario DECIMAL(19,2),
  subtotal DECIMAL(19,2),
  CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos_readmodel(pedido_id) ON DELETE CASCADE
);
