package org.example.SistemaInventarioAPI.controller;

import org.example.SistemaInventarioAPI.service.ProductoService;
import org.example.SistemaInventarioAPI.entity.Producto;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.example.SistemaInventarioAPI.dto.ProductoRequestDTO;
import org.example.SistemaInventarioAPI.dto.ProductoResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    //POST
    @PostMapping
    public ProductoResponseDTO registrarProducto(
            @Valid @RequestBody ProductoRequestDTO requestDTO){
        return productoService.registrarProducto(requestDTO);
    }
    //GET
    @GetMapping
    public List<ProductoResponseDTO> listarProductos(){
        return productoService.listarProductos();
    }
    @GetMapping("/{id}")
    public ProductoResponseDTO buscarProductoPorId(@PathVariable Long id){
        return productoService.buscarProductoPorId(id);
    }
    //PUT
    @PutMapping("/{id}")
    public ProductoResponseDTO actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO requestDTO){
        return productoService.actualizarProducto(id,requestDTO);
    }
    //DELETE
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
    }

}
