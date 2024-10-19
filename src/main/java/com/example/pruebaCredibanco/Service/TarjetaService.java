package com.example.pruebaCredibanco.Service;

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

}
