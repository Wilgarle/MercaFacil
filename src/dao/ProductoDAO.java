package dao;

import conexion.Conexion;
import modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * DAO que implementa las operaciones CRUD sobre la tabla Producto.
 * Incluye un método auxiliar para listar categorías (combo en el formulario).
 */
public class ProductoDAO implements IProductoDAO {

    private static final String SQL_INSERT =
            "INSERT INTO producto (codigo, nombre, unidad_medida, stock_minimo, stock_maximo, estado, categoria_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE producto SET codigo=?, nombre=?, unidad_medida=?, stock_minimo=?, stock_maximo=?, estado=?, categoria_id=? " +
            "WHERE producto_id=?";

    private static final String SQL_DELETE =
            "DELETE FROM producto WHERE producto_id=?";

    private static final String SQL_SELECT_ALL =
            "SELECT p.*, c.nombre AS categoria_nombre FROM producto p " +
            "LEFT JOIN categoria c ON p.categoria_id = c.categoria_id ORDER BY p.producto_id";

    private static final String SQL_SEARCH_NOMBRE =
            "SELECT p.*, c.nombre AS categoria_nombre FROM producto p " +
            "LEFT JOIN categoria c ON p.categoria_id = c.categoria_id " +
            "WHERE p.nombre LIKE ? ORDER BY p.nombre";

    // ── INSERT ───────────────────────────────────────────────────────────────
    @Override
    public boolean insertar(Producto p) {
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getUnidadMedida());
            ps.setInt(4, p.getStockMinimo());
            ps.setInt(5, p.getStockMaximo());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getCategoriaId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error INSERT Producto: " + e.getMessage());
            return false;
        }
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    public boolean actualizar(Producto p) {
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getUnidadMedida());
            ps.setInt(4, p.getStockMinimo());
            ps.setInt(5, p.getStockMaximo());
            ps.setString(6, p.getEstado());
            ps.setInt(7, p.getCategoriaId());
            ps.setInt(8, p.getProductoId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error UPDATE Producto: " + e.getMessage());
            return false;
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    @Override
    public boolean eliminar(int id) {
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error DELETE Producto: " + e.getMessage());
            return false;
        }
    }

    // ── SELECT ALL ───────────────────────────────────────────────────────────
    @Override
    public List<Producto> listarTodos() {
        Connection con = Conexion.getInstancia();
        List<Producto> lista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearFila(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error SELECT ALL Producto: " + e.getMessage());
        }
        return lista;
    }

    // ── BUSCAR POR NOMBRE ────────────────────────────────────────────────────
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        Connection con = Conexion.getInstancia();
        List<Producto> lista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SQL_SEARCH_NOMBRE)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearFila(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error BUSCAR NOMBRE Producto: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Retorna un mapa ordenado de categoria_id → nombre para poblar el combo.
     */
    public Map<Integer, String> listarCategorias() {
        Map<Integer, String> map = new LinkedHashMap<>();
        String sql = "SELECT categoria_id, nombre FROM categoria ORDER BY nombre";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("categoria_id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error listar categorías: " + e.getMessage());
        }
        return map;
    }

    // ── Mapeo ResultSet → objeto ─────────────────────────────────────────────
    private Producto mapearFila(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setProductoId(rs.getInt("producto_id"));
        p.setCodigo(rs.getString("codigo"));
        p.setNombre(rs.getString("nombre"));
        p.setUnidadMedida(rs.getString("unidad_medida"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setStockMaximo(rs.getInt("stock_maximo"));
        p.setEstado(rs.getString("estado"));
        p.setCategoriaId(rs.getInt("categoria_id"));
        p.setCategoriaNombre(rs.getString("categoria_nombre"));
        return p;
    }
}
