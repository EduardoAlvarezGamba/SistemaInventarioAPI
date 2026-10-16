package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.repository.CategoriaRepository;
import org.example.SistemaInventarioAPI.service.CategoriaService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;


    @Test
    void registrarCategoriaCorrectamente() {
        // Arrange: preparar una categoría

        Categoria categoria = new Categoria();
        categoria.setNombre("Electrónica");

        Categoria categoriaGuardada = new Categoria();
        categoriaGuardada.setId(1L);
        categoriaGuardada.setNombre("Electrónica");

        when(categoriaRepository.save(categoria))
                .thenReturn(categoriaGuardada);

        // Act: registrar la categoría

        Categoria respuesta = categoriaService.registrarCategoria(categoria);

        // Assert: comprobar el resultado

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Electrónica", respuesta.getNombre());

        verify(categoriaRepository).save(categoria);
    }


    @Test
    void listarCategoriasCorrectamente() {
        Categoria categoria1 = new Categoria();
        categoria1.setId(1L);
        categoria1.setNombre("Electrónica");

        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Hogar");

        when(categoriaRepository.findAll())
                .thenReturn(List.of(categoria1, categoria2));

        List<Categoria> respuesta = categoriaService.listarCategorias();

        assertEquals(2, respuesta.size());
        assertEquals("Electrónica", respuesta.get(0).getNombre());
        assertEquals("Hogar", respuesta.get(1).getNombre());

        verify(categoriaRepository).findAll();
    }


    @Test
    void buscarCategoriaPorIdCorrectamente() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        Categoria respuesta = categoriaService.buscarCategoriaPorId(1L);

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Electrónica", respuesta.getNombre());

        verify(categoriaRepository).findById(1L);
    }


    @Test
    void actualizarCategoriaCorrectamente() {
        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(1L);
        categoriaExistente.setNombre("Electrónica");

        Categoria categoriaActualizada = new Categoria();
        categoriaActualizada.setNombre("Tecnología");

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoriaExistente));

        when(categoriaRepository.save(categoriaExistente))
                .thenReturn(categoriaExistente);

        Categoria respuesta = categoriaService.actualizarCategoria(
                1L, categoriaActualizada
        );

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Tecnología", respuesta.getNombre());

        verify(categoriaRepository).findById(1L);
        verify(categoriaRepository).save(categoriaExistente);
    }


    @Test
    void eliminarCategoriaCorrectamente() {
        categoriaService.eliminarCategoria(1L);

        verify(categoriaRepository).deleteById(1L);
    }


}

