
package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.dto.ProductoRequestDTO;
import org.example.SistemaInventarioAPI.dto.ProductoResponseDTO;
import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.entity.Producto;
import org.example.SistemaInventarioAPI.repository.CategoriaRepository;
import org.example.SistemaInventarioAPI.repository.ProductoRepository;
import org.example.SistemaInventarioAPI.service.ProductoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductoService productoService;


    @Test
    void registrarProductoCorrectamente() {
        // Arrange: preparar los datos de la prueba

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Teclado");
        request.setPrecio(new BigDecimal("50000"));
        request.setStock(10);
        request.setCategoriaId(1L);

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setNombre("Teclado");
        productoGuardado.setPrecio(new BigDecimal("50000"));
        productoGuardado.setStock(10);
        productoGuardado.setCategoria(categoria);

        when(categoriaRepository.findById(1L))
                .thenReturn(Optional.of(categoria));

        when(productoRepository.save(any(Producto.class)))
                .thenReturn(productoGuardado);

        // Act: ejecutar el método que queremos probar

        ProductoResponseDTO respuesta =
                productoService.registrarProducto(request);

        // Assert: comprobar el resultado

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Teclado", respuesta.getNombre());
        assertEquals(new BigDecimal("50000"), respuesta.getPrecio());
        assertEquals(10, respuesta.getStock());
        assertEquals(1L, respuesta.getCategoriaId());
        assertEquals("Electrónica", respuesta.getCategoriaNombre());

        verify(categoriaRepository).findById(1L);
        verify(productoRepository).save(any(Producto.class));
    }


    @Test
    void registrarProductoConCategoriaInexistente() {
        // Arrange: preparar una solicitud con una categoría inexistente

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Teclado");
        request.setPrecio(new BigDecimal("50000"));
        request.setStock(10);
        request.setCategoriaId(99L);

        when(categoriaRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act: ejecutar el método y capturar la excepción

        ResponseStatusException excepcion = assertThrows(
                ResponseStatusException.class,
                () -> productoService.registrarProducto(request)
        );

        // Assert: verificar que la categoría no encontrada genere un 404

        assertEquals(
                HttpStatus.NOT_FOUND,
                excepcion.getStatusCode()
        );

        verify(categoriaRepository).findById(99L);
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void registrarProductoSinCategoria() {
        // Arrange: preparar un producto sin categoría

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Mouse");
        request.setPrecio(new BigDecimal("30000"));
        request.setStock(5);
        request.setCategoriaId(null);

        // Act: comprobar que se lance la excepción

        ResponseStatusException excepcion = assertThrows(
                ResponseStatusException.class,
                () -> productoService.registrarProducto(request)
        );

        // Assert: verificar el error 400

        assertEquals(
                HttpStatus.BAD_REQUEST,
                excepcion.getStatusCode()
        );

        verifyNoInteractions(categoriaRepository);
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void buscarProductoInexistente() {
        // Arrange: simular que el producto no existe

        when(productoRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act: ejecutar la búsqueda y capturar la excepción

        ResponseStatusException excepcion = assertThrows(
                ResponseStatusException.class,
                () -> productoService.buscarProductoPorId(99L)
        );

        // Assert: verificar el error 404

        assertEquals(
                HttpStatus.NOT_FOUND,
                excepcion.getStatusCode()
        );

        verify(productoRepository).findById(99L);
    }

    @Test
    void actualizarProductoCorrectamente() {
        // Arrange: preparar el producto existente

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");

        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setNombre("Teclado antiguo");
        productoExistente.setPrecio(new BigDecimal("40000"));
        productoExistente.setStock(5);
        productoExistente.setCategoria(categoria);

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Teclado mecánico");
        request.setPrecio(new BigDecimal("80000"));
        request.setStock(15);
        request.setCategoriaId(2L);

        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setId(2L);
        nuevaCategoria.setNombre("Accesorios");

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(productoExistente));

        when(categoriaRepository.findById(2L))
                .thenReturn(Optional.of(nuevaCategoria));

        when(productoRepository.save(any(Producto.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        // Act: actualizar el producto

        ProductoResponseDTO respuesta =
                productoService.actualizarProducto(1L, request);

        // Assert: verificar los datos actualizados

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Teclado mecánico", respuesta.getNombre());
        assertEquals(new BigDecimal("80000"), respuesta.getPrecio());
        assertEquals(15, respuesta.getStock());
        assertEquals(2L, respuesta.getCategoriaId());
        assertEquals("Accesorios", respuesta.getCategoriaNombre());

        verify(productoRepository).findById(1L);
        verify(categoriaRepository).findById(2L);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void actualizarProductoCorrectamenteConCategoria() {
        // Arrange: preparar el producto existente

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Electrónica");

        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setNombre("Teclado antiguo");
        productoExistente.setPrecio(new BigDecimal("40000"));
        productoExistente.setStock(5);
        productoExistente.setCategoria(categoria);

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Teclado mecánico");
        request.setPrecio(new BigDecimal("80000"));
        request.setStock(15);
        request.setCategoriaId(2L);

        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setId(2L);
        nuevaCategoria.setNombre("Accesorios");

        when(productoRepository.findById(1L))
                .thenReturn(Optional.of(productoExistente));

        when(categoriaRepository.findById(2L))
                .thenReturn(Optional.of(nuevaCategoria));

        when(productoRepository.save(any(Producto.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        // Act: actualizar el producto

        ProductoResponseDTO respuesta =
                productoService.actualizarProducto(1L, request);

        // Assert: verificar los datos actualizados

        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Teclado mecánico", respuesta.getNombre());
        assertEquals(new BigDecimal("80000"), respuesta.getPrecio());
        assertEquals(15, respuesta.getStock());
        assertEquals(2L, respuesta.getCategoriaId());
        assertEquals("Accesorios", respuesta.getCategoriaNombre());

        verify(productoRepository).findById(1L);
        verify(categoriaRepository).findById(2L);
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void eliminarProductoCorrectamente() {
        // Act: ejecutar la eliminación

        productoService.eliminarProducto(1L);

        // Assert: verificar que se solicite la eliminación

        verify(productoRepository).deleteById(1L);
    }
}

