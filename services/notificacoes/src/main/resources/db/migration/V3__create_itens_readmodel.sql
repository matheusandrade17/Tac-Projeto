CREATE TABLE IF NOT EXISTS itens_readmodel (
                                               id BIGSERIAL PRIMARY KEY,
                                               pedido_id UUID NOT NULL,
                                               nome_produto VARCHAR(255),
    quantidade INTEGER,
    preco_unitario NUMERIC(18,2),
    subtotal NUMERIC(18,2)
    );