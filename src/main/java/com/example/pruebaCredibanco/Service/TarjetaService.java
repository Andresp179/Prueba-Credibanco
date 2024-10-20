package com.example.pruebaCredibanco.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pruebaCredibanco.Models.Tarjeta;
import com.example.pruebaCredibanco.Repository.TarjetaRepository;

@Service
public class TarjetaService {

	@Autowired
	private TarjetaRepository tarjetaRepository;

	public Tarjeta emitirTarjeta(String idProducto, String titular) { // Metodo para crear tarjetas
		Tarjeta tarjetaNueva = new Tarjeta(idProducto, titular);
		return tarjetaRepository.save(tarjetaNueva);
		// Metodo para crear tarjetas
	}

	public boolean activarTarjeta(String numeroTarjeta) { // Metodo para activar tarjeta
		Optional<Tarjeta> tarjetaOpt = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);
		if (tarjetaOpt.isPresent()) {
			Tarjeta tarjeta = tarjetaOpt.get();
			tarjeta.setActiva(true);
			tarjetaRepository.save(tarjeta);
			return true;
		}
		return false;
	}

	public Tarjeta realizarCompra(Long id, double monto, LocalDate fechaTransaccion) {
		Tarjeta tarjeta = tarjetaRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Tarjeta no encontrada"));
		tarjeta.realizarCompra(monto, fechaTransaccion);
		return tarjetaRepository.save(tarjeta);
	}

	public boolean recargarSaldo(String numeroTarjeta, double monto) { // Recargar saldo
		Optional<Tarjeta> tarjetaOpt = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);
		if (tarjetaOpt.isPresent()) {
			Tarjeta tarjeta = tarjetaOpt.get();
			tarjeta.setSaldo(tarjeta.getSaldo() + monto);
			tarjetaRepository.save(tarjeta);
			return true;
		}
		return false;
	}

	public boolean bloquearTarjeta(String numeroTarjeta) { // Bloquear tarjeta
		Optional<Tarjeta> tarjetaOpt = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);
		if (tarjetaOpt.isPresent()) {
			Tarjeta tarjeta = tarjetaOpt.get();
			tarjeta.setBloqueada(true);
			tarjetaRepository.save(tarjeta);
			return true;
		}
		return false;
	}

	public double consultarSaldo(String numeroTarjeta) {
		Tarjeta tarjeta = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta)
				.orElseThrow(() -> new IllegalStateException("Tarjeta no encontrada"));
		return tarjeta.getSaldo();
	}

	public Boolean desbloquearTarjeta(String numeroTarjeta) {
		Optional<Tarjeta> tarjetaOpt = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta);
		if (tarjetaOpt.isPresent()) {
			Tarjeta tarjeta = tarjetaOpt.get();
			tarjeta.setBloqueada(false);
			tarjetaRepository.save(tarjeta);
			return true;
		}
		return false;
	}

	public boolean validarTarjetaParaCompra(String numeroTarjeta, Double montoCompra) {
		Tarjeta tarjeta = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta)
				.orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada"));

		// Verificar si la tarjeta está bloqueada
		if (tarjeta.isBloqueada()) {
			throw new IllegalStateException("La tarjeta está bloqueada");
		}

		// Verificar si la tarjeta está activada
		if (!tarjeta.isActiva()) {
			throw new IllegalStateException("La tarjeta no está activada");
		}

		// Verificar la fecha de vencimiento
		LocalDate hoy = LocalDate.now();
		if (hoy.isAfter(LocalDate.parse(tarjeta.getFechaVencimiento()))) {
			throw new IllegalStateException("La tarjeta está vencida");
		}

		// Verificar el saldo suficiente
		int comparisonResult = Double.compare(tarjeta.getSaldo(), montoCompra);
		if (comparisonResult < 0) {
			throw new IllegalStateException("Saldo insuficiente");
		}

		return true; // Si todas las validaciones pasan, la tarjeta está lista para compras
	}

	public void realizarCompra(String numeroTarjeta, Double montoCompra) {
		// Validar que la tarjeta esté lista para la compra
		validarTarjetaParaCompra(numeroTarjeta, montoCompra);

		Tarjeta tarjeta = tarjetaRepository.findByNumeroTarjeta(numeroTarjeta)
				.orElseThrow(() -> new IllegalArgumentException("Tarjeta no encontrada"));

		// Realizar la compra deduciendo el saldo
		int result = Double.compare(tarjeta.getSaldo(), montoCompra);
		tarjeta.setSaldo(result);

		// Guardar los cambios en la base de datos
		tarjetaRepository.save(tarjeta);

	}

}
