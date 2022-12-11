package br.com.alurafood.pagamentos.dto;

import java.math.BigDecimal;

import br.com.alurafood.pagamentos.dto.adapters.PedidoDto;
import br.com.alurafood.pagamentos.model.PagamentoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDto {
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private PagamentoStatus status;
    private Long formaDePagamentoId;
    private Long pedidoId;
    private PedidoDto pedido;


}
