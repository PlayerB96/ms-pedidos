package com.examen.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEstadoDto {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "REGISTRADO|PAGADO|ENVIADO|CANCELADO",
            message = "El estado debe ser REGISTRADO, PAGADO, ENVIADO o CANCELADO")
    private String estado;
}
