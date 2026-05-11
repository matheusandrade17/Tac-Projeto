package com.seuprojeto.contracts;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDto(UUID produtoId, String nomeProduto, int quantidade, BigDecimal precoUnitario) {}