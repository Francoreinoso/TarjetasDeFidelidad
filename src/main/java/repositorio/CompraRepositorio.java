package repositorio;

import modelo.Compra;

import java.util.*;

public class CompraRepositorio {
    private Map<String, Compra> compras = new HashMap<>();

    public void registrar(Compra compra) {
        compras.put(compra.getIdCompra(), compra);
    }

    public Compra obtener(String idCompra) {
        return compras.get(idCompra);
    }

    public List<Compra> listar() {
        return new ArrayList<>(compras.values());
    }

    public void eliminar(String idCompra) {
        compras.remove(idCompra);
    }

    public List<Compra> listarPorCliente(String idCliente) {
        List<Compra> resultado = new ArrayList<>();
        for (Compra c : compras.values()) {
            if (c.getCliente().getId().equals(idCliente)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}

