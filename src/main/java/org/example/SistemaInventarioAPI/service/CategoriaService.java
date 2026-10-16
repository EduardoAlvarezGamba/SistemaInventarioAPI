package org.example.SistemaInventarioAPI.service;

import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    //Registrar
    public Categoria registrarCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }
    //Mostrar
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }
    public Categoria buscarCategoriaPorId(Long id){
        return categoriaRepository.findById(id).orElse(null);
    }
    //Actualizar
    public Categoria actualizarCategoria(Long id, Categoria categoriaActualizada){
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(categoriaActualizada.getNombre());
            return categoriaRepository.save(categoria);
        }).orElse(null);
    }
    //Eliminar
    public void eliminarCategoria(Long id){
        categoriaRepository.deleteById(id);
    }
}
