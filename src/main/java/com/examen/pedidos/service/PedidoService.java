package com.examen.pedidos.service;

import com.examen.pedidos.dto.PedidoEstadoDto;
import com.examen.pedidos.dto.PedidoRequestDto;
import com.examen.pedidos.dto.PedidoResponseDto;
import com.examen.pedidos.entity.Pedido;
import com.examen.pedidos.exception.RecursoNoEncontradoException;
import com.examen.pedidos.repository.PedidoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private static final String ESTADO_INICIAL = "REGISTRADO";
    private static final String ESTADO_CANCELADO = "CANCELADO";

    private final PedidoRepository pedidoRepository;

    @Transactional
    public PedidoResponseDto crear(PedidoRequestDto dto) {
        BigDecimal total = dto.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(dto.getCantidad()))
                .setScale(2, RoundingMode.HALF_UP);

        Pedido entity = Pedido.builder()
                .cliente(dto.getCliente())
                .correoCliente(dto.getCorreoCliente())
                .productoId(dto.getProductoId())
                .nombreProducto(dto.getNombreProducto())
                .cantidad(dto.getCantidad())
                .precioUnitario(dto.getPrecioUnitario())
                .total(total)
                .estado(ESTADO_INICIAL)
                .fechaPedido(LocalDateTime.now())
                .build();

        return toResponse(pedidoRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listar() {
        return pedidoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponseDto obtenerPorId(Long id) {
        return toResponse(buscarEntidad(id));
    }

    @Transactional
    public PedidoResponseDto actualizar(Long id, PedidoRequestDto dto) {
        Pedido entity = buscarEntidad(id);
        BigDecimal total = dto.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(dto.getCantidad()))
                .setScale(2, RoundingMode.HALF_UP);

        entity.setCliente(dto.getCliente());
        entity.setCorreoCliente(dto.getCorreoCliente());
        entity.setProductoId(dto.getProductoId());
        entity.setNombreProducto(dto.getNombreProducto());
        entity.setCantidad(dto.getCantidad());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setTotal(total);

        return toResponse(pedidoRepository.save(entity));
    }

    @Transactional
    public PedidoResponseDto actualizarEstado(Long id, PedidoEstadoDto dto) {
        Pedido entity = buscarEntidad(id);
        entity.setEstado(dto.getEstado());
        return toResponse(pedidoRepository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        Pedido entity = buscarEntidad(id);
        entity.setEstado(ESTADO_CANCELADO);
        pedidoRepository.save(entity);
    }

    private Pedido buscarEntidad(Long id) {
        return pedidoRepository
                .findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Pedido no encontrado", "No existe un pedido con el ID " + id));
    }

    private PedidoResponseDto toResponse(Pedido p) {
        return PedidoResponseDto.builder()
                .id(p.getId())
                .cliente(p.getCliente())
                .correoCliente(p.getCorreoCliente())
                .productoId(p.getProductoId())
                .nombreProducto(p.getNombreProducto())
                .cantidad(p.getCantidad())
                .precioUnitario(p.getPrecioUnitario())
                .total(p.getTotal())
                .estado(p.getEstado())
                .fechaPedido(p.getFechaPedido())
                .build();
    }
}
