package br.com.alurafood.pagamentos.dto.adapters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.alurafood.pagamentos.model.PedidoStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDto {

    private Long id;
    private LocalDateTime dataHora;
    private PedidoStatus status;
    private List<ItemDoPedidoDto> itens = new ArrayList<>();
    
}
