CREATE TABLE IF NOT EXISTS itens_readmodel (
                                               id SERIAL PRIMARY KEY,
                                               pedido_id UUID NOT NULL,
                                               nome_produto VARCHAR(255),
    quantidade INT,
    preco_unitario NUMERIC(18,2),
    subtotal NUMERIC(18,2)
    );
CREATE INDEX IF NOT EXISTS idx_itens_pedido ON itens_readmodel(pedido_id);
