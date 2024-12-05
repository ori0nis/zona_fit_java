package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO{

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        // Metemos los objetos que nos permiten conectarnos a la BD y utilizar sentencias
        PreparedStatement ps; // Con esto preparamos la sentencia
        ResultSet rs; // Esto nos permite recibir la info de la consulta
        Connection con = Conexion.getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try {
            ps = con.prepareStatement(sql); // Pasamos la sentencia
            rs = ps.executeQuery(); // La ejecutamos

            while(rs.next()){ // El método next() nos coloca en el primer registro a iterar
                // Generamos los clientes y los metemos en el array:
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch(Exception e){
            System.out.println("Error al listar clientes: "+ e.getMessage());
        } finally {
            // Es buena práctica cerrar el objeto de conexión, pero lo envolvemos por si acaso:
            try {
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = Conexion.getConexion();
        var sql = "SELECT * FROM cliente WHERE id = ?"; // Creamos un valor de parámetro pendiente
        // con la ?
        try{
            ps = con.prepareStatement(sql);
            // Necesitamos pasar un parámetro además de la cadena de la sentencia:
            ps.setInt(1, cliente.getId()); // Rellenamos el valor de parámetro pendiente.
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch(Exception e){
            System.out.println("Error al recuperar cliente por id: " + e.getMessage());
        } finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexión " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        // No necesitamos ResultSet porque no vamos a recuperar info, sino introducirla.
        Connection con = Conexion.getConexion();
        var sql = "INSERT INTO cliente(nombre, apellido, membresia)"
                + " " + "VALUES(?, ?, ?)"; // No incluimos el id porque es autoincrementable. Esto
        // significa que los valores de VALUES serán 1, 2 y 3, ya que el 0 es el id.
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute(); // Guardamos cambios en la BD.
            return true;
        }catch(Exception e){
            System.out.println("Error al agregar cliente: " + e.getMessage());
        } finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexión: "+ e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        var sql = "UPDATE cliente SET nombre=?, apellido=?, membresia=? " +
                " WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }finally {
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = Conexion.getConexion();
        var sql = "DELETE FROM cliente WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al eliminar el cliente: " + e.getMessage());
        }finally {
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Hacemos una prueba para ver si la lista de clientes funciona:
        System.out.println("*** Lista de clientes ***");
        IClienteDAO clienteDao = new ClienteDAO();
        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);

        // MANTENGO EL RESTO DEL CÓDIGO COMENTADO PORQUE SON LLAMADAS DE PRUEBA QUE SALEN RARAS SI SE STACKEAN:

        // Hacemos la prueba de buscar por id:
//        var cliente1 = new Cliente(2);
//        System.out.println("Cliente antes de la búsqueda");
//        var encontrado = clienteDao.buscarClientePorId(cliente1);
//        if(encontrado){
//            System.out.println("Cliente encontrado: " + cliente1);
//        } else {
//            System.out.println("No se encontró el cliente: " + cliente1.getId());
//        }

        // Hacemos la prueba de agregar cliente:
//        var nuevoCliente = new Cliente("Daniel", "Ortiz", 300);
//        var agregado = clienteDao.agregarCliente(nuevoCliente);
//        if(agregado){
//            System.out.println("Cliente agregado: " + nuevoCliente);
//        } else {
//            System.out.println("No se pudo agregar el cliente: " + nuevoCliente);
//        }

        // Hacemos la prueba de modificar cliente:
//        var clienteModificado = new Cliente(3, "Carlos Daniel", "Ortiz", 300);
//        var modificado = clienteDao.modificarCliente(clienteModificado);
//        if(modificado){
//            System.out.println("Cliente modificado: " + clienteModificado);
//        } else {
//            System.out.println("Error al modificar cliente: " + clienteModificado);
//        }

        // Hacemos la prueba de eliminar cliente:
//        var clienteAEliminar = new Cliente(3);
//        var eliminado = clienteDao.eliminarCliente(clienteAEliminar);
//        if(eliminado){
//            System.out.println("El cliente ha sido eliminado: " + clienteAEliminar);
//        } else {
//            System.out.println("Error al eliminar cliente " + clienteAEliminar.getId());
//        }
    }
}
