package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase Singleton para gestionar la conexión JDBC con MySQL.
 * 
 * Requisito: mysql-connector-j-8.x.x.jar en el classpath del proyecto.
 */
public class Conexion {

    // ── Datos de conexión ────────────────────────────────────────────────────
    private static final String HOST     = "localhost";
    private static final String PUERTO   = "3306";
    private static final String BD       = "mercafacil";
    private static final String USUARIO  = "root";      // Cambia si usas otro usuario
    private static final String PASSWORD = "123456";           // Cambia por tu contraseña

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BD
            + "?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true";

    private static Connection instancia = null;

    // Constructor privado (patrón Singleton)
    private Conexion() {}

    /**
     * Retorna la única instancia de conexión activa.Si no existe o fue cerrada, crea una nueva.
     * @return
     */
    public static Connection getInstancia() {
        try {
            if (instancia == null || instancia.isClosed()) {
                // Cargar driver (obligatorio en versiones antiguas de JDBC)
                Class.forName("com.mysql.cj.jdbc.Driver");
                instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("✔ Conexión establecida con la BD: " + BD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
        }
        return instancia;
    }

    /** Cierra la conexión si está abierta. */
    public static void cerrar() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
                System.out.println("✔ Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
        }
    }
}
