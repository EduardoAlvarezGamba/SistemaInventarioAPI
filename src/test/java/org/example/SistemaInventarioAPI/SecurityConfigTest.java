
package org.example.SistemaInventarioAPI;

import org.example.SistemaInventarioAPI.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY="
})
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void consultarProductosSinTokenDebeRetornar401() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void usuarioNoDebePoderRegistrarCategoria() throws Exception {
        UserDetails usuario =
                userDetailsService.loadUserByUsername("usuario");

        String token = jwtService.generarToken(usuario);

        mockMvc.perform(post("/categorias")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Categoria de prueba\"}"))
                .andExpect(status().isForbidden());
    }
}

