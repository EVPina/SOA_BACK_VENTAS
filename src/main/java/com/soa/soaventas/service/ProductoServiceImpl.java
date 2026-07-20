package com.soa.soaventas.service;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ProductoResponse;
import com.soa.soaventas.exception.BusinessException;
import com.soa.soaventas.exception.ResourceNotFoundException;
import com.soa.soaventas.mapper.ProductoMapper;
import com.soa.soaventas.model.Producto;
import com.soa.soaventas.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponse crearProducto(ProductoRequest request) {
        log.info("Creando nuevo producto: {}", request.getNombre());

        if (productoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new BusinessException("Ya existe un producto con el nombre: " + request.getNombre());
        }

        Producto producto = productoMapper.toEntity(request);
        
        if (producto.getDisponible() == null) {
            producto.setDisponible(true);
        }

        Producto savedProducto = productoRepository.save(producto);
        log.info("Producto creado exitosamente con ID: {}", savedProducto.getId());
        
        return productoMapper.toResponse(savedProducto);
    }

    @Override
    public ProductoResponse actualizarProducto(UUID id, ProductoRequest request) {
        log.info("Actualizando producto con ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        if (!producto.getNombre().equalsIgnoreCase(request.getNombre()) &&
                productoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new BusinessException("Ya existe otro producto con el nombre: " + request.getNombre());
        }

        productoMapper.updateEntity(request, producto);
        Producto updatedProducto = productoRepository.save(producto);
        
        log.info("Producto actualizado exitosamente: {}", id);
        return productoMapper.toResponse(updatedProducto);
    }

    @Override
    public void eliminarProducto(UUID id) {
        log.info("Eliminando producto con ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        producto.setDisponible(false);
        productoRepository.save(producto);
        
        log.info("Producto marcado como no disponible: {}", id);
    }

    @Override
    public ProductoResponse obtenerProductoPorId(UUID id) {
        log.debug("Buscando producto por ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        return productoMapper.toResponse(producto);
    }

    @Override
    public List<ProductoResponse> listarProductosDisponibles() {
        log.debug("Listando productos disponibles");
        
        return productoRepository.findByDisponibleTrue()
                .stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductoResponse> listarProductosPaginados(Pageable pageable) {
        log.debug("Listando productos paginados");
        
        return productoRepository.findAll(pageable)
                .map(productoMapper::toResponse);
    }

    @Override
    public List<ProductoResponse> buscarPorNombre(String nombre) {
        log.debug("Buscando productos por nombre: {}", nombre);
        
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> buscarPorCategoria(String categoria) {
        log.debug("Buscando productos por categoría: {}", categoria);
        
        return productoRepository.findByCategoria(categoria)
                .stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductoResponse> buscarDisponiblesPorRangoPrecio(BigDecimal min, BigDecimal max) {
        log.debug("Buscando productos por rango de precio: {} - {}", min, max);
        
        return productoRepository.findDisponiblesByPrecioRange(min, max)
                .stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void actualizarDisponibilidad(UUID id, Boolean disponible) {
        log.info("Actualizando disponibilidad del producto {} a: {}", id, disponible);
        
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        
        producto.setDisponible(disponible);
        productoRepository.save(producto);
    }

    @Override
    public long contarDisponibles() {
        log.debug("Contando productos disponibles");
        return productoRepository.countByDisponibleTrue();
    }
}
