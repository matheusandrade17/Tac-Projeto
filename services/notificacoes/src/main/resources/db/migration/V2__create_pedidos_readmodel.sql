CREATE TABLE IF NOT EXISTS pedidos_readmodel (
                                                 pedido_id UUID PRIMARY KEY,
                                                 cliente_id UUID,
                                                 nome_cliente VARCHAR(255),
    email_cliente VARCHAR(255),
    status VARCHAR(50),
    valor_total NUMERIC(18,2),
    criado_em TIMESTAMP
    );