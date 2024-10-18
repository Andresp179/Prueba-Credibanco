package com.example.pruebaCredibanco.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "tarjeta_id", nullable = false)
	    private Tarjeta tarjeta;

	    @Column(name = "monto", nullable = false)
	    private double monto;

	    @Column(name = "fecha_transaccion", nullable = false)
	    private LocalDateTime fechaTransaccion;

	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Tarjeta getTarjeta() {
			return tarjeta;
		}

		public void setTarjeta(Tarjeta tarjeta) {
			this.tarjeta = tarjeta;
		}

		public double getMonto() {
			return monto;
		}

		public void setMonto(double monto) {
			this.monto = monto;
		}

		public LocalDateTime getFechaTransaccion() {
			return fechaTransaccion;
		}

		public void setFechaTransaccion(LocalDateTime fechaTransaccion) {
			this.fechaTransaccion = fechaTransaccion;
		}

		public boolean isAnulada() {
			return anulada;
		}

		public void setAnulada(boolean anulada) {
			this.anulada = anulada;
		}

		public Transaccion() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Transaccion(Long id, Tarjeta tarjeta, double monto, LocalDateTime fechaTransaccion, boolean anulada) {
			super();
			this.id = id;
			this.tarjeta = tarjeta;
			this.monto = monto;
			this.fechaTransaccion = fechaTransaccion;
			this.anulada = anulada;
		}

		@Column(name = "anulada", nullable = false)
	    private boolean anulada;

}
