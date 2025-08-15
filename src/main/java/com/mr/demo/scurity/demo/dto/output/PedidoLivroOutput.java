package com.mr.demo.scurity.demo.dto.output;

import com.mr.demo.scurity.demo.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PedidoLivroOutput {

    private Long id;
    private StatusPedido statusPedido;
    private LocalDate dataPedido;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntregaEfetiva;
    private UsuarioOutput usuario;
    private LivroOutputSemUsuario livro;
}
