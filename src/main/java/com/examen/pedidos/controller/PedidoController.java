package com.examen.pedidos.controller;

import com.examen.pedidos.dto.PedidoEstadoDto;
import com.examen.pedidos.dto.PedidoRequestDto;
import com.examen.pedidos.dto.PedidoResponseDto;
import com.examen.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDto crear(@Valid @RequestBody PedidoRequestDto dto) {
        return pedidoService.crear(dto);
    }

    @GetMapping
    public List<PedidoResponseDto> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public PedidoResponseDto obtener(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public PedidoResponseDto actualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequestDto dto) {
        return pedidoService.actualizar(id, dto);
    }

    @PatchMapping("/{id}/estado")
    public PedidoResponseDto actualizarEstado(
            @PathVariable Long id, @Valid @RequestBody PedidoEstadoDto dto) {
        return pedidoService.actualizarEstado(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
    }
}
