package repositorio;

import modelo.Cliente;

import java.util.*;

public class ClienteRepositorio {
    private Map<String, Cliente> clientes = new HashMap<>();

    public void agregar(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    public Cliente obtener(String id) {
        return clientes.get(id);
    }

    public List<Cliente> listar() {
        return new ArrayList<>(clientes.values());
    }

    public void actualizar(String id, Cliente nuevo) {
        if (clientes.containsKey(id)) {
            clientes.put(id, nuevo);
        }
    }

    public void eliminar(String id) {
        clientes.remove(id);
    }

    public boolean existe(String id) {
        return clientes.containsKey(id);
    }
}
