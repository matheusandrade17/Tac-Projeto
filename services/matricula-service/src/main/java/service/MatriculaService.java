package com.seuprojeto.matricula.service;

import com.seuprojeto.contracts.ItemDto;
import com.seuprojeto.contracts.PedidoCriadoEvent;
import com.seuprojeto.matricula.EventoPublisher;
import com.seuprojeto.matricula.model.Matricula;
import com.seuprojeto.matricula.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MatriculaService {

    private final MatriculaRepository repo;
    private final EventoPublisher eventoPublisher;

    public MatriculaService(MatriculaRepository repo, EventoPublisher eventoPublisher) {
        this.repo = repo;
        this.eventoPublisher = eventoPublisher;
    }

    public Matricula criarMatricula(Matricula matricula) {
        // 1. persistir
        Matricula saved = repo.save(matricula);

        // 2. montar evento a partir da entidade persistida
        var itensDto = saved.getItens().stream()
                .map(i -> new ItemDto(i.getProdutoId(), i.getNomeProduto(), i.getQuantidade(), i.getPrecoUnitario()))
                .collect(Collectors.toList());

        var event = new PedidoCriadoEvent(
                saved.getId(),
                saved.getClienteId(),
                saved.getNomeCliente(),
                saved.getEmailCliente(),
                saved.getValorTotal(),
                saved.getCriadoEm(),
                itensDto
        );

        // 3. publicar evento
        eventoPublisher.publicar(event);

        // 4. retornar salvo
        return saved;
    }
}
