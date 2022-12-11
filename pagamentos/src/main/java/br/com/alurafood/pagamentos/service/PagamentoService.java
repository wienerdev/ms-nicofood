package br.com.alurafood.pagamentos.service;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alurafood.pagamentos.dto.PagamentoDto;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.PagamentoStatus;
import br.com.alurafood.pagamentos.repository.PagamentoRepositoy;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepositoy repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PedidoClient pedido;

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        Page<PagamentoDto> response = repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDto.class));

        response.forEach(pagamentoDto -> {
            pagamentoDto = recuperarPedidos(pagamentoDto);
        });

        return response;
    }

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return recuperarPedidos(modelMapper.map(pagamento, PagamentoDto.class));
    }

    private PagamentoDto recuperarPedidos(PagamentoDto pagamentoDto) {
        pagamentoDto.setPedido(pedido.listarPorId(pagamentoDto.getPedidoId()));
        return pagamentoDto;
    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(PagamentoStatus.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }

    public void confirmarPagamento(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        pagamento.setStatus(PagamentoStatus.CONFIRMADO);
        repository.save(pagamento);
        pedido.atualizaPagamento(id);
    }

    public void confirmarPagamentoOffline(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        pagamento.setStatus(PagamentoStatus.OFFLINE);
        repository.save(pagamento);
    }

}
