package modelo;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    public void testClienteCreacionConCorreoValido() {
        Cliente cliente = new Cliente("1", "Franco", "franco@correo.com");
        assertEquals("Franco", cliente.getNombre());
        assertEquals(NivelFidelidad.BRONCE, cliente.getNivel());
    }

    @Test
    public void testCorreoInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cliente("2", "Juan", "juan.correo.com");
        });
    }

    @Test
    public void testCambioDeNivelPorPuntos() {
        Cliente cliente = new Cliente("3", "Ana", "ana@mail.com");
        cliente.agregarPuntos(1600);
        assertEquals(NivelFidelidad.ORO, cliente.getNivel());
    }

    @Test
    public void testCompraValida() {
        Cliente cliente = new Cliente("4", "Maria Jose", "mariajose@mail.com");
        Compra compra = new Compra("C001", cliente, 54350, LocalDate.now());
        compra.procesarCompra();
        assertEquals(NivelFidelidad.PLATA, cliente.getNivel());
    }

    @Test
    public void testSubeExactamenteANivelPlata() {
        Cliente cliente = new Cliente("5", "Luis", "luis@mail.com");
        Compra compra = new Compra("C002", cliente, 50000, LocalDate.now());
        compra.procesarCompra();
        assertEquals(NivelFidelidad.PLATA, cliente.getNivel());
    }

    @Test
    public void testCompraInsuficienteParaCambiarNivel() {
        Cliente cliente = new Cliente("6", "Ana", "ana@mail.com");
        Compra compra = new Compra("C003", cliente, 12000, LocalDate.now());
        compra.procesarCompra();
        assertEquals(NivelFidelidad.BRONCE, cliente.getNivel());
    }

    @Test
    public void testCompraConMultiplicadorOro() {
        Cliente cliente = new Cliente("7", "Carlos", "carlos@mail.com");
        cliente.agregarPuntos(1600);
        assertEquals(NivelFidelidad.ORO, cliente.getNivel());
        Compra compra = new Compra("C004", cliente, 10000, LocalDate.now());
        compra.procesarCompra();
        assertEquals(1750, cliente.getPuntos());
        assertEquals(NivelFidelidad.ORO, cliente.getNivel());
    }

    @Test
    public void testRedondeoDePuntos() {
        Cliente cliente = new Cliente("9", "Pedro", "pedro@mail.com");
        Compra compra = new Compra("C006", cliente, 9999, LocalDate.now());
        compra.procesarCompra();
        assertEquals(99, cliente.getPuntos());
    }

    @Test
    public void testStreakDias() {
        Cliente cliente = new Cliente("10", "Lucas", "lucas@mail.com");
        Compra compra1 = new Compra("C001", cliente, 10000, LocalDate.of(2025, 6, 18));
        compra1.procesarCompra();
        Compra compra2 = new Compra("C002", cliente, 20000, LocalDate.of(2025, 6, 18));
        compra2.procesarCompra();
        Compra compra3 = new Compra("C003", cliente, 15000, LocalDate.of(2025, 6, 18));
        compra3.procesarCompra();
        assertEquals(460, cliente.getPuntos());
    }

    @Test
    public void testReinicioStreak() {
        Cliente cliente = new Cliente("11", "Ana", "ana@mail.com");
        Compra compra1 = new Compra("C001", cliente, 10000, LocalDate.of(2025, 6, 18));
        compra1.procesarCompra();
        Compra compra2 = new Compra("C002", cliente, 20000, LocalDate.of(2025, 6, 19)); // día distinto
        compra2.procesarCompra();
        Compra compra3 = new Compra("C003", cliente, 15000, LocalDate.of(2025, 6, 19));
        compra3.procesarCompra();
        assertEquals(450, cliente.getPuntos());
    }

    @Test
    public void testNoBonoAntesDeTresCompras() {
        Cliente cliente = new Cliente("12", "Pedro", "pedro@mail.com");

        Compra compra1 = new Compra("C001", cliente, 10000, LocalDate.of(2025, 6, 18));
        compra1.procesarCompra();

        Compra compra2 = new Compra("C002", cliente, 20000, LocalDate.of(2025, 6, 18));
        compra2.procesarCompra();

        // Solo dos compras en el día → no bono aún
        int puntosEsperados = 100 + 200; // 300 puntos
        assertEquals(puntosEsperados, cliente.getPuntos());
    }

}
