package dao;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones CRUD.
 * T  = tipo del modelo (Almacen)
 * ID = tipo de la clave primaria (Integer)
 */
public interface IAlmacenDAO<T, ID> {

    /** Inserta un nuevo registro en la tabla. */
    boolean insertar(T objeto);

    /** Actualiza un registro existente por su ID. */
    boolean actualizar(T objeto);

    /** Elimina un registro por su ID. */
    boolean eliminar(ID id);

    /** Busca un registro por su ID. */
    T buscarPorId(ID id);

    /** Retorna todos los registros de la tabla. */
    List<T> listarTodos();

    /** Busca registros cuyo nombre contenga el texto indicado. */
    List<T> buscarPorNombre(String nombre);
}
