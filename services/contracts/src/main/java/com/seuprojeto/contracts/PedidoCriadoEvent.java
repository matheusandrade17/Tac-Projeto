package com.seuprojeto.contracts;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PedidoCriadoEvent(
        UUID pedidoId,
        UUID clienteId,
        String nomeCliente,
        String emailCliente,
        BigDecimal valorTotal,
        Instant criadoEm,
        List<ItemDto> itens
) {}
