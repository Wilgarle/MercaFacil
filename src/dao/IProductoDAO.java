package dao;

import java.util.List;
import modelo.Producto;

/**
 * Interfaz que define las operaciones CRUD sobre la tabla Producto.
 */
public interface IProductoDAO {
    boolean insertar(Producto p);
    boolean actualizar(Producto p);
    boolean eliminar(int productoId);
    List<Producto> listarTodos();
    List<Producto> buscarPorNombre(String nombre);
}
