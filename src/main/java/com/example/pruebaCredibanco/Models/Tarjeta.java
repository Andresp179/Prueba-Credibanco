package com.example.pruebaCredibanco.Models;

import java.io.Serializable;
import java.time.LocalDate;

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
	private LocalDate fechaVencimiento;

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
	        this.fechaVencimiento = calcularFechaVencimiento();
	    }
	public Tarjeta(Long id, String numeroTarjeta, String nombreTitular, LocalDate fechaVencimiento, double saldo,
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

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
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

	private LocalDate calcularFechaVencimiento() {
		return LocalDate.now().plusYears(3);
	}

}
