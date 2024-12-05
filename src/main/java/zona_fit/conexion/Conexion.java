package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConexion() {
        Connection conexion = null;
        var baseDatos = "zona_fit_db";
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario = "root";
        var password = "admin";

        try {
            // Cargamos la clase de conexión a la BD. Driver es su nombre y el resto es el
            // paquete al que pertenece:
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Realizamos la conexión:
            conexion = DriverManager.getConnection(url, usuario, password);
        }catch(Exception e){
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    // Probamos si la conexión funciona:

    public static void main(String[] args) {
        var conexion = Conexion.getConexion();
        if(conexion != null) {
            System.out.println("Conexión realizada con éxito: " + conexion);
        } else {
            System.out.println("Error en la conexión");
        }
    }
}
