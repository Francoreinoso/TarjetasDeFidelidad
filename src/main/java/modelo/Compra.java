package modelo;

import java.time.LocalDate;

public class Compra {
    private String idCompra;
    private Cliente cliente;
    private double monto;
    private LocalDate fecha;


    public Compra(String idCompra, Cliente cliente, double monto, LocalDate fecha) {
        this.idCompra = idCompra;
        this.cliente = cliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public double getMonto() {
        return monto;
    }

    public Cliente getCliente() {
        return cliente;
    }


    public void procesarCompra() {
        double puntos = Math.floor(monto/100);
        double multiplicador = switch (cliente.getNivel()) {
            case BRONCE -> 1.0;
            case PLATA -> 1.2;
            case ORO -> 1.5;
            case PLATINO -> 2.0;
        };
        int puntosTotales = (int) Math.floor(puntos * multiplicador);
        cliente.registrarCompra(fecha, puntosTotales);
    }

}
