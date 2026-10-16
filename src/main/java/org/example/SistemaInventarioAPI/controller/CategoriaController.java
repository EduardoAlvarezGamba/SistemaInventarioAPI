package org.example.SistemaInventarioAPI.controller;

import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.service.CategoriaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }
    //POST
    @PostMapping
    public Categoria registrarCategoria(@RequestBody Categoria categoria){
        return categoriaService.registrarCategoria(categoria);
    }
    //GET
    @GetMapping
    public List<Categoria> listarCategorias(){
        return categoriaService.listarCategorias();
    }
    @GetMapping("/{id}")
    public Categoria buscarCategoriaPorId(@PathVariable Long id){
        return categoriaService.buscarCategoriaPorId(id);
    }
    //PUT
    @PutMapping("/{id}")
    public Categoria actualizarCategoria(
            @PathVariable Long id,
            @RequestBody Categoria categoriaActualizada){
        return categoriaService.actualizarCategoria(id,categoriaActualizada);

    }
    //DELETE
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
    }

}
