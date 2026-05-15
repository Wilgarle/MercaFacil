package dao;

import conexion.Conexion;
import modelo.Almacen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO que implementa las operaciones CRUD sobre la tabla Almacen.
 * Usa PreparedStatement para evitar SQL Injection.
 */
public class AlmacenDAO implements IAlmacenDAO<Almacen, Integer> {

    // ── Sentencias SQL ───────────────────────────────────────────────────────
    private static final String SQL_INSERT =
            "INSERT INTO Almacen (codigo, nombre, direccion, gerente, estado) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE Almacen SET codigo=?, nombre=?, direccion=?, gerente=?, estado=? " +
            "WHERE almacen_id=?";

    private static final String SQL_DELETE =
            "DELETE FROM Almacen WHERE almacen_id=?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM Almacen WHERE almacen_id=?";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM Almacen ORDER BY almacen_id";

    private static final String SQL_SEARCH_NOMBRE =
            "SELECT * FROM Almacen WHERE nombre LIKE ? ORDER BY nombre";

    // ── INSERT ───────────────────────────────────────────────────────────────
    @Override
    public boolean insertar(Almacen a) {
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setString(1, a.getCodigo());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getDireccion());
            ps.setString(4, a.getGerente());
            ps.setString(5, a.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error INSERT Almacen: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    public boolean actualizar(Almacen a) {
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, a.getCodigo());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getDireccion());
            ps.setString(4, a.getGerente());
            ps.setString(5, a.getEstado());
            ps.setInt(6, a.getAlmacenId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error UPDATE Almacen: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    @Override
    public boolean eliminar(Integer id) {
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error DELETE Almacen: " + e.getMessage());
            return false;
        }
    }

    // ── SELECT POR ID ────────────────────────────────────────────────────────
    @Override
    public Almacen buscarPorId(Integer id) {
        Connection con = Conexion.getInstancia();
        Almacen almacen = null;
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    almacen = mapearFila(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SELECT Almacen por ID: " + e.getMessage());
        }
        return almacen;
    }

    // ── SELECT ALL ───────────────────────────────────────────────────────────
    @Override
    public List<Almacen> listarTodos() {
        Connection con = Conexion.getInstancia();
        List<Almacen> lista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error SELECT ALL Almacen: " + e.getMessage());
        }
        return lista;
    }

    // ── BUSCAR POR NOMBRE (LIKE) ─────────────────────────────────────────────
    @Override
    public List<Almacen> buscarPorNombre(String nombre) {
        Connection con = Conexion.getInstancia();
        List<Almacen> lista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_SEARCH_NOMBRE)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error BUSCAR NOMBRE Almacen: " + e.getMessage());
        }
        return lista;
    }

    // ── Mapeo ResultSet → objeto ─────────────────────────────────────────────
    private Almacen mapearFila(ResultSet rs) throws SQLException {
        return new Almacen(
                rs.getInt("almacen_id"),
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("gerente"),
                rs.getString("estado")
        );
    }
}
