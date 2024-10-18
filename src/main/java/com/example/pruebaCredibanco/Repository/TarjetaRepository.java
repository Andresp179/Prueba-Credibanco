package com.example.pruebaCredibanco.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pruebaCredibanco.Models.Tarjeta;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
	Optional<Tarjeta> findByNumeroTarjeta(String numeroTarjeta);
}
