package com.gestao.academico.dto;

public class PagamentoDto {

    private String pedidoId;
    private String status;
    private String detalhes;

    public PagamentoDto() {}

    public PagamentoDto(String pedidoId, String status, String detalhes) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.detalhes = detalhes;
    }

    public static PagamentoDto pending(String pedidoId) {
        return new PagamentoDto(pedidoId, "PENDENTE", "fallback");
    }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
}