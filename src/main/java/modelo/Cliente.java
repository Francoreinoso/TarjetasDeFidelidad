package modelo;

import java.time.LocalDate;

public class Cliente {
    private String id;
    private String nombre;
    private String correo;
    private int puntos;
    private NivelFidelidad nivel;
    private int streakDias;
    private LocalDate fechaUltimaCompra;


    public Cliente(String id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.setCorreo(correo);
        this.puntos = 0;
        this.nivel = NivelFidelidad.BRONCE;
        this.streakDias = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getId() {
        return id;
    }

    public NivelFidelidad getNivel() {
        return nivel;
    }

    public void setCorreo(String correo) {
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("Correo inválido");
        }
        this.correo = correo;
    }

    public void agregarPuntos(int puntosGanados) {
        this.puntos += puntosGanados;
        this.nivel = NivelFidelidad.obtenerPorPuntos(this.puntos);
    }

    public void registrarCompra(LocalDate fechaCompra, int puntosGanados) {
        if (fechaUltimaCompra != null && fechaUltimaCompra.isEqual(fechaCompra)) {
            streakDias++;
        } else {
            streakDias = 1;
        }
        fechaUltimaCompra = fechaCompra;

        agregarPuntos(puntosGanados);

        if (streakDias % 3 == 0) {
            agregarPuntos(10); // bono cada 3 compras seguidas en el mismo día
        }
    }

}