package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;
import java.util.Scanner;

public class AppZonaFit {
    public static void main(String[] args) {
        appZonaFit();
    }

    public static void appZonaFit() {
        Scanner consola = new Scanner(System.in);
        IClienteDAO clienteDAO = new ClienteDAO();
        boolean salir = false;

        System.out.println("*** Bienvenido a Zona Fit ***");
        System.out.println();

        while (!salir) {
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarMenu(opcion, consola, clienteDAO);
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }
    }

    private static int mostrarMenu(Scanner consola) {
        System.out.print("""
                Menú de opciones:
                \t1. Mostrar la lista de clientes
                \t2. Agregar un nuevo cliente
                \t3. Modificar los datos de un cliente
                \t4. Eliminar los datos de un cliente
                \t5. Salir
                \tElige la acción que deseas realizar:\s
                """);
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarMenu(int opcion, Scanner consola, IClienteDAO clienteDAO) {
        var salir = false;
        switch (opcion) {
            case 1 -> mostrarClientes(clienteDAO);
            case 2 -> agregarCliente(consola, clienteDAO);
            case 3 -> modificarCliente(consola, clienteDAO);
            case 4 -> eliminarCliente(consola, clienteDAO);
            case 5 -> {
                System.out.println("Nos vemos!");
                salir = true;
            }
            default -> System.out.println("Opción inválida " + opcion);
        }
        return salir;
    }

    private static void mostrarClientes(IClienteDAO clienteDAO) {
        System.out.println("Lista de clientes: ");
        var clientes = clienteDAO.listarClientes();
        clientes.forEach(System.out::println);
    }

    private static void agregarCliente(Scanner consola, IClienteDAO clienteDAO) {
        System.out.print("Introduce el nombre del cliente: ");
        var nombreCliente = consola.nextLine();
        System.out.print("Introduce el apellido del cliente: ");
        var apellidoCliente = consola.nextLine();
        System.out.print("Introduce su número de membresía: ");
        var numeroMembresia = Integer.parseInt(consola.nextLine());
        var agregar = new Cliente(nombreCliente, apellidoCliente, numeroMembresia);
        var nuevoCliente = clienteDAO.agregarCliente(agregar);
        if (nuevoCliente) {
            System.out.println("Cliente agregado con éxito: " + agregar);
        } else {
            System.out.println("Error al agregar el cliente: " + agregar);
        }
    }

    private static void modificarCliente(Scanner consola, IClienteDAO clienteDAO) {
        System.out.println("Introduce el id del cliente que quieres modificar: ");
        var idCliente = Integer.parseInt(consola.nextLine());
        var clientePorId = new Cliente(idCliente);
        var encontrado = clienteDAO.buscarClientePorId(clientePorId);

        while (!encontrado) {
            System.out.println("ID de cliente inválido. Por favor, introduce un ID válido: ");
            var nuevoIdCliente = Integer.parseInt(consola.nextLine());
            var nuevoClientePorId = new Cliente(nuevoIdCliente);
            encontrado = clienteDAO.buscarClientePorId(nuevoClientePorId);
            if (encontrado) {
                idCliente = nuevoIdCliente;
            }
        }
        System.out.print("Introduce el nuevo nombre del cliente: ");
        var nuevoNombre = consola.nextLine();
        System.out.print("Introduce el nuevo apellido del cliente: ");
        var nuevoApellido = consola.nextLine();
        System.out.print("Introduce su nuevo número de membresía: ");
        var nuevoNumero = Integer.parseInt(consola.nextLine());
        var clienteModificado = new Cliente(idCliente, nuevoNombre, nuevoApellido, nuevoNumero);
        if (clienteDAO.modificarCliente(clienteModificado)) {
            System.out.println("Cliente modificado con éxito: " + clienteModificado);
        } else {
            System.out.println("No se pudo modificar el cliente: " + clienteModificado);
        }
    }

    private static void eliminarCliente(Scanner consola, IClienteDAO clienteDAO) {
        System.out.print("Introduce el id del cliente que deseas eliminar: ");
        var idCliente = Integer.parseInt(consola.nextLine());
        var clienteAEliminar = new Cliente(idCliente);
        if (clienteDAO.eliminarCliente(clienteAEliminar)) {
            System.out.println("Cliente eliminado con éxito: " + clienteAEliminar);
        } else {
            System.out.println("Error al eliminar el cliente: " + clienteAEliminar);
        }
    }
}
