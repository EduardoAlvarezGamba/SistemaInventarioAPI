
package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.entity.Categoria;
import org.example.SistemaInventarioAPI.repository.CategoriaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void guardarCategoriaCorrectamente() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Electrónica");

        Categoria guardada = categoriaRepository.save(categoria);

        assertNotNull(guardada.getId());
        assertEquals("Electrónica", guardada.getNombre());
    }
}

