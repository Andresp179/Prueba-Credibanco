package com.example.pruebaCredibanco.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacciones")
public class Transaccion implements Serializable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private double valor;
    private LocalDateTime fecha;
    
    @Enumerated(EnumType.STRING)
    private EstadoTransaccion estado;

    // Getters y Setters

    public EstadoTransaccion getEstado() {
		return estado;
	}
	public void setEstado(EstadoTransaccion estado) {
		this.estado = estado;
	}

	public enum EstadoTransaccion {
        REALIZADA,
        ANULADA
    }
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public Transaccion(Long id, double valor, LocalDateTime fecha) {
		super();
		this.id = id;
		this.valor = valor;
		this.fecha = fecha;
	}
	
}
