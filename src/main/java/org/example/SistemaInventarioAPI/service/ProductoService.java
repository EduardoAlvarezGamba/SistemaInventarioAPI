package org.example.SistemaInventarioAPI.service;

import org.example.SistemaInventarioAPI.repository.CategoriaRepository;
import org.example.SistemaInventarioAPI.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.entity.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.example.SistemaInventarioAPI.dto.ProductoResponseDTO;
import org.example.SistemaInventarioAPI.entity.Producto;
import org.example.SistemaInventarioAPI.dto.ProductoRequestDTO;

import java.util.List;


@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    private ProductoResponseDTO convertirAResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();

        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());

        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }

        return dto;
    }

    //Agregar
    public ProductoResponseDTO registrarProducto(ProductoRequestDTO requestDTO) {

        if (requestDTO.getCategoriaId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debes indicar una categoria para el producto"
            );
        }

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La categoria no existe"
                ));

        Producto producto = new Producto();
        producto.setNombre(requestDTO.getNombre());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());
        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);

        return convertirAResponseDTO(productoGuardado);
    }

    //Listar
    public List<ProductoResponseDTO> listarProductos(){
        return productoRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    public ProductoResponseDTO buscarProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El producto no existe"
                ));

        return convertirAResponseDTO(producto);
    }

    //Actualizar
    public ProductoResponseDTO actualizarProducto(
            Long id,
            ProductoRequestDTO requestDTO) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El producto no existe"
                ));

        producto.setNombre(requestDTO.getNombre());
        producto.setPrecio(requestDTO.getPrecio());
        producto.setStock(requestDTO.getStock());

        if (requestDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository
                    .findById(requestDTO.getCategoriaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "La categoria no existe"
                    ));

            producto.setCategoria(categoria);
        }

        Producto productoActualizado = productoRepository.save(producto);

        return convertirAResponseDTO(productoActualizado);
    }
    //Delete

    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "El producto no existe"
                ));

        productoRepository.delete(producto);
    }

}
