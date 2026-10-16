package org.example.SistemaInventarioAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
		"jwt.secret=MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY="
})
class SistemaInventarioApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void sumarDosNumeros(){
		int resultado = 2 + 3;

		assertEquals(5,resultado);
	}

}
