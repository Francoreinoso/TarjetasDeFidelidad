package repositorio;
import modelo.Cliente;
import modelo.NivelFidelidad;
import org.junit.jupiter.api.Test;
import repositorio.ClienteRepositorio;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteRepositorioTest {

    @Test
    public void testAgregarYObtenerCliente() {
        ClienteRepositorio repo = new ClienteRepositorio();
        Cliente cliente = new Cliente("1", "Ana", "ana@mail.com");
        repo.agregar(cliente);

        Cliente obtenido = repo.obtener("1");
        assertNotNull(obtenido);
        assertEquals("Ana", obtenido.getNombre());
    }

    @Test
    public void testActualizarCliente() {
        ClienteRepositorio repo = new ClienteRepositorio();
        Cliente original = new Cliente("2", "Luis", "luis@mail.com");
        repo.agregar(original);

        Cliente modificado = new Cliente("2", "Luis Modificado", "luisnuevo@mail.com");
        repo.actualizar("2", modificado);

        Cliente actualizado = repo.obtener("2");
        assertEquals("Luis Modificado", actualizado.getNombre());
        assertEquals("luisnuevo@mail.com", actualizado.getCorreo());
    }

    @Test
    public void testEliminarCliente() {
        ClienteRepositorio repo = new ClienteRepositorio();
        Cliente cliente = new Cliente("3", "Sofía", "sofia@mail.com");
        repo.agregar(cliente);
        assertTrue(repo.existe("3"));

        repo.eliminar("3");
        assertFalse(repo.existe("3"));
        assertNull(repo.obtener("3"));
    }

    @Test
    public void testListarClientes() {
        ClienteRepositorio repo = new ClienteRepositorio();
        repo.agregar(new Cliente("4", "Pedro", "pedro@mail.com"));
        repo.agregar(new Cliente("5", "Lucía", "lucia@mail.com"));

        List<Cliente> lista = repo.listar();
        assertEquals(2, lista.size());
    }
}

