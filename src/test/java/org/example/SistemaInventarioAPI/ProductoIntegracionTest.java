package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.dto.ProductoRequestDTO;
import org.example.SistemaInventarioAPI.dto.ProductoResponseDTO;
import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.repository.CategoriaRepository;
import org.example.SistemaInventarioAPI.repository.ProductoRepository;
import org.example.SistemaInventarioAPI.service.ProductoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(properties = {
        "jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY="
})
@Transactional
class ProductoIntegracionTest {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void registrarProductoIntegrandoServicioYBaseDeDatos() {

        Categoria categoria = new Categoria();
        categoria.setNombre("Integracion");
        Categoria categoriaGuardada =
                categoriaRepository.save(categoria);

        ProductoRequestDTO request = new ProductoRequestDTO();
        request.setNombre("Monitor");
        request.setPrecio(new BigDecimal("750000"));
        request.setStock(6);
        request.setCategoriaId(categoriaGuardada.getId());

        ProductoResponseDTO respuesta =
                productoService.registrarProducto(request);

        assertNotNull(respuesta.getId());
        assertEquals("Monitor", respuesta.getNombre());
        assertEquals(6, respuesta.getStock());
        assertEquals(
                categoriaGuardada.getId(),
                respuesta.getCategoriaId()
        );

        assertTrue(
                productoRepository.findById(respuesta.getId()).isPresent()
        );
    }

}
