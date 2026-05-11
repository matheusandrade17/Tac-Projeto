package com.seuprojeto.contracts;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ItemDto(UUID produtoId, String nomeProduto, int quantidade, BigDecimal precoUnitario) {}

public record PedidoCriadoEvent(
        UUID pedidoId,
        UUID clienteId,
        String nomeCliente,
        String emailCliente,
        BigDecimal valorTotal,
        Instant criadoEm,
        List<ItemDto> itens
) {}
