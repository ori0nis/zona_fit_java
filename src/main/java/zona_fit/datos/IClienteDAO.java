package zona_fit.datos;

import zona_fit.dominio.Cliente;
import java.util.List;

// DAO (Data Access Object) es la interfaz/clase que nos permitirá acceder a los datos de la BD.

public interface IClienteDAO {
    List<Cliente> listarClientes();
    boolean buscarClientePorId(Cliente cliente);
    boolean agregarCliente(Cliente cliente);
    boolean modificarCliente(Cliente cliente);
    boolean eliminarCliente(Cliente cliente);
}
