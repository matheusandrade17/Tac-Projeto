CREATE TABLE IF NOT EXISTS pedidos_readmodel (
                                                 pedido_id UUID PRIMARY KEY,
                                                 cliente_id UUID NOT NULL,
                                                 nome_cliente VARCHAR(255) NOT NULL,
    email_cliente VARCHAR(255),
    status VARCHAR(50),
    valor_total NUMERIC(18,2),
    criado_em TIMESTAMP,
    criado_em_formatado VARCHAR(100)
    );
CREATE INDEX IF NOT EXISTS idx_pedidos_cliente ON pedidos_readmodel(cliente_id);
CREATE INDEX IF NOT EXISTS idx_pedidos_criadoem ON pedidos_readmodel(criado_em);
