package br.com.alurafood.pagamentos.dto.adapters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDoPedidoDto {

    private Long id;
    private Long quantidade;
    private String descricao;
    
}
