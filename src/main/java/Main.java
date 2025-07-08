import modelo.Cliente;
import modelo.Compra;
import repositorio.ClienteRepositorio;
import repositorio.CompraRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClienteRepositorio clienteRepo = new ClienteRepositorio();
    private static final CompraRepositorio compraRepo = new CompraRepositorio();

    public static void main(String[] args) {
        boolean salir = false;
        Scanner sc = new Scanner(System.in);

        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: agregarCliente(); break;
                case 2: listarClientes(); break;
                case 3: editarCliente(); break;
                case 4: eliminarCliente(); break;
                case 5: menuCompras(); break;  // Volvemos acá tras salir del submenú
                case 6: mostrarPuntosYNivel(); break;
                case 7: salir = true; break;
                default: System.out.println("Opción inválida."); break;
            }
        }

        System.out.println("¡Hasta luego!");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==== MENÚ PRINCIPAL ====");
        System.out.println("1. Agregar Cliente");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Editar Cliente");
        System.out.println("4. Eliminar Cliente");
        System.out.println("5. Gestión de compras");
        System.out.println("6. Mostrar Puntos y Nivel de un Cliente");
        System.out.println("7. Salir");
    }

    private static void menuCompras() {
        int opcion;
        do {
            System.out.println("\n== GESTIÓN DE COMPRAS ==");
            System.out.println("1. Registrar Compra");
            System.out.println("2. Listar Compras de un Cliente");
            System.out.println("3. Eliminar Compra");
            System.out.println("4. Volver");

            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1: registrarCompra(); break;
                case 2: listarComprasCliente(); break;
                case 3: eliminarCompra(); break;
                case 4: System.out.println("Volviendo al menú principal..."); break;
                default: System.out.println("Opción inválida."); break;
            }
        } while (opcion != 4);
    }

    private static void agregarCliente() {
        System.out.println("\n== Agregar Cliente ==");
        String id = leerTexto("ID: ");
        if (clienteRepo.existe(id)) {
            System.out.println("Ya existe un cliente con ese ID.");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        String correo = leerTexto("Correo: ");
        try {
            Cliente cliente = new Cliente(id, nombre, correo);
            clienteRepo.agregar(cliente);
            System.out.println("Cliente agregado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarClientes() {
        System.out.println("\n== Lista de Clientes ==");
        List<Cliente> clientes = clienteRepo.listar();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.printf("- %s (%s) | Puntos: %d | Nivel: %s%n",
                    c.getNombre(), c.getId(), c.getPuntos(), c.getNivel());
        }
    }

    private static void editarCliente() {
        System.out.println("\n== Editar Cliente ==");
        String id = leerTexto("ID del cliente a editar: ");
        Cliente cliente = clienteRepo.obtener(id);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        String nuevoNombre = leerTexto("Nuevo nombre (" + cliente.getNombre() + "): ");
        String nuevoCorreo = leerTexto("Nuevo correo (" + cliente.getCorreo() + "): ");

        try {
            Cliente actualizado = new Cliente(id, nuevoNombre, nuevoCorreo);
            actualizado.agregarPuntos(cliente.getPuntos()); // mantener puntos actuales
            clienteRepo.actualizar(id, actualizado);
            System.out.println("Cliente actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarCliente() {
        System.out.println("\n== Eliminar Cliente ==");
        String id = leerTexto("ID del cliente a eliminar: ");
        Cliente cliente = clienteRepo.obtener(id);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        clienteRepo.eliminar(id);
        System.out.println("Cliente eliminado correctamente.");
    }

    private static void registrarCompra() {
        System.out.println("\n== Registrar Compra ==");
        String idCliente = leerTexto("ID Cliente: ");
        Cliente cliente = clienteRepo.obtener(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        String idCompra = leerTexto("ID Compra: ");
        double monto = leerDouble("Monto de la compra: ");
        LocalDate fecha = LocalDate.now();

        try {
            Compra compra = new Compra(idCompra, cliente, monto, fecha);
            compra.procesarCompra();
            compraRepo.registrar(compra);
            System.out.println("Compra registrada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarComprasCliente() {
        System.out.println("\n== Listar Compras de un Cliente ==");
        String idCliente = leerTexto("ID Cliente: ");
        Cliente cliente = clienteRepo.obtener(idCliente);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        List<Compra> compras = compraRepo.listarPorCliente(idCliente);
        if (compras.isEmpty()) {
            System.out.println("Este cliente no tiene compras registradas.");
            return;
        }

        for (Compra c : compras) {
            System.out.printf("- ID: %s | Fecha: %s | Monto: $%.2f%n",
                    c.getIdCompra(), c.getFecha(), c.getMonto());
        }
    }

    private static void eliminarCompra() {
        System.out.println("\n== Eliminar Compra ==");
        String idCompra = leerTexto("ID de la compra a eliminar: ");
        Compra compra = compraRepo.obtener(idCompra);

        if (compra == null) {
            System.out.println("Compra no encontrada.");
            return;
        }

        compraRepo.eliminar(idCompra);
        System.out.println("Compra eliminada correctamente.");
    }

    private static void mostrarPuntosYNivel() {
        System.out.println("\n== Consultar Cliente ==");
        String idCliente = leerTexto("ID Cliente: ");
        Cliente cliente = clienteRepo.obtener(idCliente);
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.printf("Cliente: %s | Puntos: %d | Nivel: %s%n",
                cliente.getNombre(), cliente.getPuntos(), cliente.getNivel());
    }

    // Utilidades
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debes ingresar un número decimal válido.");
            }
        }
    }
}

