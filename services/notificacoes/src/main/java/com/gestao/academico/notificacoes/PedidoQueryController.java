package com.gestao.academico.notificacoes;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoQueryController {
    private final JdbcTemplate jdbc;

    public PedidoQueryController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping
    public List<Map<String, Object>> list() {
        return jdbc.queryForList("SELECT pedido_id, cliente_id, nome_cliente, email_cliente, status, valor_total, criado_em FROM pedidos_readmodel");
    }

    @GetMapping("/{id}")
    public Map<String, Object> get(@PathVariable("id") String id) {
        try {
            Map<String, Object> pedido = jdbc.queryForMap("SELECT pedido_id, cliente_id, nome_cliente, email_cliente, status, valor_total, criado_em FROM pedidos_readmodel WHERE pedido_id = ?", id);
            List<Map<String, Object>> itens = jdbc.queryForList("SELECT nome_produto, quantidade, preco_unitario, subtotal FROM itens_readmodel WHERE pedido_id = ?", id);
            pedido.put("itens", itens);
            return pedido;
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido not found");
        }
    }
}
