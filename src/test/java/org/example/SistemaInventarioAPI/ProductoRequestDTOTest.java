
package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.dto.ProductoRequestDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductoRequestDTOTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void configurarValidador() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void cerrarValidador() {
        factory.close();
    }

    private ProductoRequestDTO crearProductoValido() {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Monitor");
        dto.setPrecio(new BigDecimal("500000"));
        dto.setStock(5);
        dto.setCategoriaId(1L);
        return dto;
    }

    @Test
    void productoValidoNoDebeTenerErrores() {
        ProductoRequestDTO dto = crearProductoValido();

        Set<ConstraintViolation<ProductoRequestDTO>> errores =
                validator.validate(dto);

        assertTrue(errores.isEmpty());
    }

    @Test
    void nombreVacioDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setNombre(" ");

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void nombreMayorA150CaracteresDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setNombre("A".repeat(151));

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void precioCeroDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setPrecio(BigDecimal.ZERO);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void precioNuloDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setPrecio(null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void stockCeroDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setStock(0);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void categoriaNulaDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setCategoriaId(null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void categoriaConIdCeroDebeGenerarError() {
        ProductoRequestDTO dto = crearProductoValido();
        dto.setCategoriaId(0L);

        assertFalse(validator.validate(dto).isEmpty());
    }
}

