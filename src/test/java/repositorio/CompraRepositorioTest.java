package repositorio;

import modelo.Cliente;
import modelo.Compra;
import org.junit.jupiter.api.Test;
import repositorio.CompraRepositorio;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompraRepositorioTest {

    @Test
    public void testRegistrarYObtenerCompra() {
        CompraRepositorio repo = new CompraRepositorio();
        Cliente cliente = new Cliente("10", "Carlos", "carlos@mail.com");
        Compra compra = new Compra("C001", cliente, 10000, LocalDate.now());

        repo.registrar(compra);
        Compra obtenida = repo.obtener("C001");

        assertNotNull(obtenida);
        assertEquals(10000, obtenida.getMonto());
        assertEquals("Carlos", obtenida.getCliente().getNombre());
    }

    @Test
    public void testEliminarCompra() {
        CompraRepositorio repo = new CompraRepositorio();
        Cliente cliente = new Cliente("11", "Laura", "laura@mail.com");
        Compra compra = new Compra("C002", cliente, 5000, LocalDate.now());

        repo.registrar(compra);
        assertNotNull(repo.obtener("C002"));

        repo.eliminar("C002");
        assertNull(repo.obtener("C002"));
    }

    @Test
    public void testListarTodasLasCompras() {
        CompraRepositorio repo = new CompraRepositorio();
        Cliente cliente1 = new Cliente("1", "Juan", "juan@mail.com");
        Cliente cliente2 = new Cliente("2", "Ana", "ana@mail.com");

        repo.registrar(new Compra("C001", cliente1, 1000, LocalDate.now()));
        repo.registrar(new Compra("C002", cliente2, 2000, LocalDate.now()));
        repo.registrar(new Compra("C003", cliente1, 3000, LocalDate.now()));

        List<Compra> todas = repo.listar();

        assertEquals(3, todas.size());
    }

    @Test
    public void testListarPorCliente() {
        CompraRepositorio repo = new CompraRepositorio();
        Cliente cliente1 = new Cliente("12", "Pablo", "pablo@mail.com");
        Cliente cliente2 = new Cliente("13", "Eva", "eva@mail.com");

        repo.registrar(new Compra("C003", cliente1, 2000, LocalDate.now()));
        repo.registrar(new Compra("C004", cliente1, 3000, LocalDate.now()));
        repo.registrar(new Compra("C005", cliente2, 1000, LocalDate.now()));

        List<Compra> comprasCliente1 = repo.listarPorCliente("12");
        assertEquals(2, comprasCliente1.size());

        List<Compra> comprasCliente2 = repo.listarPorCliente("13");
        assertEquals(1, comprasCliente2.size());
    }
}
