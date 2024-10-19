package com.example.pruebaCredibanco.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarjetas")
public class Tarjeta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero_tarjeta", unique = true, nullable = false, length = 16)
	private String numeroTarjeta;

	@Column(name = "nombre_titular", nullable = false)
	private String nombreTitular;

	@Column(name = "fecha_vencimiento", nullable = false)
	private String fechaVencimiento;

	@Column(name = "saldo", nullable = false)
	private double saldo;

	@Column(name = "activa", nullable = false)
	private boolean activa;

	@Column(name = "bloqueada", nullable = false)
	private boolean bloqueada;

	public Tarjeta() {
		super();

	}

	public Tarjeta(String productId, String titular) {
		this.numeroTarjeta = generarNumeroTarjeta(productId);
		this.nombreTitular = titular;
		this.fechaVencimiento = generarFechaVencimiento();
	}

	public Tarjeta(Long id, String numeroTarjeta, String nombreTitular, String fechaVencimiento, double saldo,
			boolean activa, boolean bloqueada) {
		super();
		this.id = id;
		this.numeroTarjeta = numeroTarjeta;
		this.nombreTitular = nombreTitular;
		this.fechaVencimiento = fechaVencimiento;
		this.saldo = saldo;
		this.activa = activa;
		this.bloqueada = bloqueada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getNombreTitular() {
		return nombreTitular;
	}

	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}

	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	private String generarNumeroTarjeta(String productId) {
		String numeroAleatorio = String.format("%010d", (long) (Math.random() * 1_000_000_0000L));
		return productId + numeroAleatorio;
	}

	private String generarFechaVencimiento() {
		LocalDate hoy = LocalDate.now();
		LocalDate fechaVencimiento = hoy.plusYears(3);
		return fechaVencimiento.format(DateTimeFormatter.ofPattern("MM/yyyy"));
	}

	public void activarTarjeta() { // Activar la tarjeta
		if (!activa) {
			activa = true;
			System.out.println("La tarjeta ha sido activada.");
		} else {
			System.out.println("La tarjeta ya estaba activa.");
		}
	}

	public void recargarSaldo(double monto) { // Recargar saldo
		if (activa) {
			if (monto > 0) {
				saldo += monto;
				System.out.println("Se han recargado $" + monto + " a la tarjeta. Saldo actual: $" + saldo);
			} else {
				System.out.println("El monto de recarga debe ser mayor a cero.");
			}
		} else {
			System.out.println("No se puede recargar saldo en una tarjeta inactiva.");
		}
	}

	public boolean realizarCompra(double monto, LocalDate fechaTransaccion) { // compra
		if (!activa) {
			throw new IllegalStateException("La tarjeta no está activada.");
		}
		if (bloqueada) {
			throw new IllegalStateException("La tarjeta está bloqueada.");
		}
		LocalDate vencimiento = LocalDate.parse(fechaVencimiento, DateTimeFormatter.ofPattern("MM/yyyy"));
		if (fechaTransaccion.isAfter(vencimiento)) {
			throw new IllegalStateException("La tarjeta está vencida.");
		}
		if (saldo < monto) {
			throw new IllegalStateException("Saldo insuficiente.");
		}
		this.saldo -= monto;
		return true;
	}

}
