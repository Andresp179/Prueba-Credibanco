package com.example.pruebaCredibanco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import com.example.pruebaCredibanco.Models.Tarjeta;
import com.example.pruebaCredibanco.Repository.TarjetaRepository;
import com.example.pruebaCredibanco.Service.TarjetaService;

@SpringBootTest
public class TarjetaServiceTest {

	@Mock
	public TarjetaRepository tarjetaRepository;

	@InjectMocks
	public TarjetaService tarjetaService;

	public Tarjeta tarjeta;

	@BeforeEach
	void setUp() {
		// Inicializar la tarjeta para las pruebas
		tarjeta = new Tarjeta();
		tarjeta.setNumeroTarjeta("1234567890123456");
		tarjeta.setSaldo(Double.valueOf(500.00)); // Tarjeta con 500 dólares de saldo
		tarjeta.setActiva(true); // Tarjeta activada
		tarjeta.setBloqueada(false); // Tarjeta no está bloqueada
		// tarjeta.setFechaVencimiento(LocalDate.now().plusYears(2)); // Válida por 2
		// años
	}

	@Test
	public void testEmitirTarjeta() {

		String idProducto = "123456";// Datos de prueba
		String titular = "Juan Pérez";

		Tarjeta tarjetaEsperada = new Tarjeta(idProducto, titular); // Asegúrate de que no sea nulo Crear una tarjeta
																	// esperada

		when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(tarjetaEsperada); // Configurar el mock para que
																						// devuelva la tarjeta esperada

		Tarjeta tarjetaEmitida = tarjetaService.emitirTarjeta(idProducto, titular); // Llamar al método a probar

		assertNotNull(tarjetaEmitida); // Verificar que el resultado es el esperado
		assertEquals(tarjetaEsperada.getId(), tarjetaEmitida.getId());
		assertEquals(tarjetaEsperada.getNombreTitular(), tarjetaEmitida.getNombreTitular());

		verify(tarjetaRepository, times(1)).save(any(Tarjeta.class)); // Verificar que el método save del repositorio
																		// fue llamado una vez
	}

	@Test
	public void testConsultarSaldo_TarjetaEncontrada() {

		String numeroTarjeta = "1234567812345678"; // Datos de prueba
		double saldoEsperado = 100.0;

		Tarjeta tarjetaSimulada = new Tarjeta("123456", "Juan Pérez"); // Crear una tarjeta simulada
		tarjetaSimulada.setSaldo(saldoEsperado); // Asegúrate de que la clase Tarjeta tenga un método setSaldo

		when(tarjetaRepository.findByNumeroTarjeta(numeroTarjeta)).thenReturn(Optional.of(tarjetaSimulada)); // Configurar
																												// el
																												// mock
																												// para
																												// que
																												// devuelva
																												// la
																												// tarjeta
																												// simulada

		double saldoConsultado = tarjetaService.consultarSaldo(numeroTarjeta); // Llamar al método a probar

		assertEquals(saldoEsperado, saldoConsultado); // Verificar que el saldo devuelto es el esperado

		verify(tarjetaRepository, times(1)).findByNumeroTarjeta(numeroTarjeta); // Verificar que el método
																				// findByNumeroTarjeta fue llamado una
																				// vez
	}

	@Test
	public void testConsultarSaldo_TarjetaNoEncontrada() {

		String numeroTarjeta = "8765432187654321"; // Datos de prueba
		when(tarjetaRepository.findByNumeroTarjeta(numeroTarjeta)).thenReturn(Optional.empty()); // Configurar el mock
																									// para que devuelva
																									// un Optional vacío
		Exception exception = assertThrows(IllegalStateException.class, () -> { // Llamar al método a probar y verificar
																				// que lanza la excepción esperada
			tarjetaService.consultarSaldo(numeroTarjeta);
		});
		assertEquals("Tarjeta no encontrada", exception.getMessage()); // Verificar el mensaje de la excepción
		verify(tarjetaRepository, times(1)).findByNumeroTarjeta(numeroTarjeta); // Verificar que el método
																				// findByNumeroTarjeta fue llamado una
																				// vez
	}

}
