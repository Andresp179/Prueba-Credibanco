package com.example.pruebaCredibanco.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pruebaCredibanco.Models.Tarjeta;
import com.example.pruebaCredibanco.Service.TarjetaService;

@RestController
@RequestMapping("/card")
public class TarjetaController {

	@Autowired
	private TarjetaService tarjetaService;

	@PostMapping("/emanar")
	public ResponseEntity<Tarjeta> emitirTarjeta(@RequestParam String productId, @RequestParam String nombreTitular) {
		Tarjeta nuevaTarjeta = tarjetaService.emitirTarjeta(productId, nombreTitular);
		return ResponseEntity.ok(nuevaTarjeta);
	}

	@PostMapping("/activate")
	public ResponseEntity<String> activarTarjeta(@RequestParam String numeroTarjeta) {
		boolean activada = tarjetaService.activarTarjeta(numeroTarjeta);
		if (activada) {
			return ResponseEntity.ok("Tarjeta activada correctamente.");
		} else {
			return ResponseEntity.badRequest().body("No se pudo activar la tarjeta.");
		}
	}

	@PostMapping("/bloqueo")
	public ResponseEntity<String> bloquearTarjeta(@RequestParam String numeroTarjeta) {
		boolean bloqueada = tarjetaService.bloquearTarjeta(numeroTarjeta);
		if (bloqueada) {
			return ResponseEntity.ok("Tarjeta bloqueada correctamente.");
		} else {
			return ResponseEntity.badRequest().body("No se pudo bloquear la tarjeta.");
		}
	}
	@PostMapping("/desbloqueando")
	public ResponseEntity<String> desbloquearTarjeta(@RequestParam String numeroTarjeta) {		
		Boolean bloqueado = tarjetaService.desbloquearTarjeta(numeroTarjeta);
	  if(bloqueado) {
		  return ResponseEntity.ok("Desbloqueada correctamente");
	  } else {
		  return ResponseEntity.badRequest().body("No se pudo desbloquear");
	  }
	}

	@PostMapping("/recargaSaldo")
	public ResponseEntity<String> recargarSaldo(@RequestParam String numeroTarjeta, @RequestParam double monto) {
		String recargada = tarjetaService.recargarSaldo(numeroTarjeta, monto);
		if (recargada.contains("Tarjeta no encontrada..")) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(recargada);
		}
		return ResponseEntity.ok(recargada);
	}

	@PostMapping("/saldo")
	public String consultarSaldoPorNumero(@RequestParam String numeroTarjeta) {
		return tarjetaService.consultarSaldo(numeroTarjeta);
	}
	
	@PostMapping("/realizar")
    public ResponseEntity<String> realizarCompra(@RequestParam String numeroTarjeta,
                                                 @RequestParam Double montoCompra) {
        try {
            tarjetaService.realizarCompra(numeroTarjeta, montoCompra);
            return ResponseEntity.ok("Compra realizada con éxito");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
