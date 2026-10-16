package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.entity.Producto;
import org.example.SistemaInventarioAPI.repository.CategoriaRepository;
import org.example.SistemaInventarioAPI.repository.ProductoRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void guardarProductoCorrectamente() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Tecnología");

        Categoria categoriaGuardada =
                categoriaRepository.save(categoria);

        Producto producto = new Producto();
        producto.setNombre("Teclado");
        producto.setPrecio(new java.math.BigDecimal("120000"));
        producto.setStock(10);
        producto.setCategoria(categoriaGuardada);

        Producto productoGuardado =
                productoRepository.save(producto);

        assertNotNull(productoGuardado.getId());
        assertEquals("Teclado", productoGuardado.getNombre());
        assertEquals(10, productoGuardado.getStock());
        assertEquals(
                categoriaGuardada.getId(),
                productoGuardado.getCategoria().getId()
        );
    }

    @Test
    void buscarProductoPorIdCorrectamente() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Hogar");

        Categoria categoriaGuardada =
                categoriaRepository.save(categoria);

        Producto producto = new Producto();
        producto.setNombre("Lámpara");
        producto.setPrecio(new java.math.BigDecimal("85000"));
        producto.setStock(5);
        producto.setCategoria(categoriaGuardada);

        Producto productoGuardado =
                productoRepository.save(producto);

        var productoEncontrado =
                productoRepository.findById(productoGuardado.getId());

        assertTrue(productoEncontrado.isPresent());
        assertEquals(
                "Lámpara",
                productoEncontrado.get().getNombre()
        );
        assertEquals(
                categoriaGuardada.getId(),
                productoEncontrado.get().getCategoria().getId()
        );
    }

    @Test
    void actualizarProductoCorrectamente() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Accesorios");

        Categoria categoriaGuardada =
                categoriaRepository.save(categoria);

        Producto producto = new Producto();
        producto.setNombre("Mouse");
        producto.setPrecio(new java.math.BigDecimal("50000"));
        producto.setStock(8);
        producto.setCategoria(categoriaGuardada);

        Producto productoGuardado =
                productoRepository.save(producto);

        productoGuardado.setNombre("Mouse inalámbrico");
        productoGuardado.setStock(12);

        productoRepository.save(productoGuardado);

        var productoActualizado =
                productoRepository.findById(productoGuardado.getId());

        assertTrue(productoActualizado.isPresent());
        assertEquals(
                "Mouse inalámbrico",
                productoActualizado.get().getNombre()
        );
        assertEquals(12, productoActualizado.get().getStock());
    }

    @Test
    void eliminarProductoCorrectamente() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Periféricos");

        Categoria categoriaGuardada =
                categoriaRepository.save(categoria);

        Producto producto = new Producto();
        producto.setNombre("Webcam");
        producto.setPrecio(new java.math.BigDecimal("95000"));
        producto.setStock(4);
        producto.setCategoria(categoriaGuardada);

        Producto productoGuardado =
                productoRepository.save(producto);

        Long idProducto = productoGuardado.getId();

        productoRepository.deleteById(idProducto);

        var productoEliminado =
                productoRepository.findById(idProducto);

        assertTrue(productoEliminado.isEmpty());
    }

}
