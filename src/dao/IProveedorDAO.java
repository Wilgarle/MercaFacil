package dao;

import java.util.List;
import modelo.Proveedor;

public interface IProveedorDAO {
    boolean insertar(Proveedor p);
    boolean actualizar(Proveedor p);
    boolean eliminar(int proveedorId);
    List<Proveedor> listarTodos();
    List<Proveedor> buscarPorNombreONit(String texto);
}
