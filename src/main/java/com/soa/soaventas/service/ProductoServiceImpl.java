package com.soa.soaventas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soa.soaventas.dto.request.ProductoRequest;
import com.soa.soaventas.dto.response.ProductoResponse;
import com.soa.soaventas.exception.ResourceNotFoundException;
import com.soa.soaventas.mapper.ProductoMapper;
import com.soa.soaventas.model.Producto;
import com.soa.soaventas.repository.ProductoRepository;

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
        log.info("Creando producto: {}", request.getNombre());
        Producto producto = productoMapper.toEntity(request);
        Producto saved = productoRepository.save(producto);
        return productoMapper.toResponse(saved);
    }

    @Override
    public ProductoResponse actualizarProducto(UUID id, ProductoRequest request) {
        log.info("Actualizando producto: {}", id);
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        productoMapper.updateEntity(request, producto);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Override
    public void eliminarProducto(UUID id) {
        log.info("Eliminando producto: {}", id);
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    @Override
    public ProductoResponse obtenerProductoPorId(UUID id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return productoMapper.toResponse(producto);
    }

    @Override
    public List<ProductoResponse> listarProductosDisponibles() {
        return productoRepository.findByDisponibleTrue()
                .stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
    }
}