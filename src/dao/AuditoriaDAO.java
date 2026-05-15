package dao;

import conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de solo lectura para las consultas de auditoría.
 * Agrupa acceso a: auditoria_sistema, clientes_historial,
 * vw_ventas_detalladas, inventario, vw_producto_masventas,
 * vw_totalventas_cliente, vw_pedidos_pendientes y pedido.
 */
public class AuditoriaDAO {

    // ── auditoria_sistema ────────────────────────────────────────────────────
    public List<Object[]> listarAuditoriaSistema() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT auditoria_id, tabla_afectada, referencia_id, mensaje, fecha, nivel_alerta " +
                     "FROM auditoria_sistema ORDER BY fecha DESC";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("auditoria_id"),
                    rs.getString("tabla_afectada"),
                    rs.getInt("referencia_id"),
                    rs.getString("mensaje"),
                    rs.getTimestamp("fecha"),
                    rs.getString("nivel_alerta")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar auditoria_sistema: " + e.getMessage());
        }
        return lista;
    }

    // ── clientes_historial ───────────────────────────────────────────────────
    public List<Object[]> listarClientesHistorial() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT historial_id, cliente_id, documento, nombre, direccion, telefono, " +
                     "tipo_cliente, accion, fecha_accion, usuario_accion " +
                     "FROM clientes_historial ORDER BY fecha_accion DESC";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("historial_id"),
                    rs.getInt("cliente_id"),
                    rs.getString("documento"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("tipo_cliente"),
                    rs.getString("accion"),
                    rs.getTimestamp("fecha_accion"),
                    rs.getString("usuario_accion")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar clientes_historial: " + e.getMessage());
        }
        return lista;
    }

    // ── vw_ventas_detalladas ─────────────────────────────────────────────────
    public List<Object[]> listarVentasDetalladas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT venta_id, numero_factura, fecha, cliente, almacen, producto, " +
                     "tipo_precio, cantidad, precio_unitario, descuento, subtotal " +
                     "FROM vw_ventas_detalladas ORDER BY fecha DESC";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("venta_id"),
                    rs.getString("numero_factura"),
                    rs.getDate("fecha"),
                    rs.getString("cliente"),
                    rs.getString("almacen"),
                    rs.getString("producto"),
                    rs.getString("tipo_precio"),
                    rs.getInt("cantidad"),
                    rs.getBigDecimal("precio_unitario"),
                    rs.getBigDecimal("descuento"),
                    rs.getBigDecimal("subtotal")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar vw_ventas_detalladas: " + e.getMessage());
        }
        return lista;
    }

    // ── inventario ───────────────────────────────────────────────────────────
    public List<Object[]> listarInventario() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT i.inventario_id, p.nombre AS producto, a.nombre AS almacen, " +
                     "i.stock_actual, i.ubicacion " +
                     "FROM inventario i " +
                     "JOIN producto p ON i.producto_id = p.producto_id " +
                     "JOIN almacen a ON i.almacen_id = a.almacen_id " +
                     "ORDER BY p.nombre";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("inventario_id"),
                    rs.getString("producto"),
                    rs.getString("almacen"),
                    rs.getInt("stock_actual"),
                    rs.getString("ubicacion")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar inventario: " + e.getMessage());
        }
        return lista;
    }

    // ── vw_producto_masventas ────────────────────────────────────────────────
    public List<Object[]> listarProductosMasVendidos() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT producto, total_ventas FROM vw_producto_masventas ORDER BY total_ventas DESC";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("producto"),
                    rs.getBigDecimal("total_ventas")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar vw_producto_masventas: " + e.getMessage());
        }
        return lista;
    }

    // ── vw_totalventas_cliente ───────────────────────────────────────────────
    public List<Object[]> listarTotalVentasCliente() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT cliente, total_comprado FROM vw_totalventas_cliente ORDER BY total_comprado DESC";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("cliente"),
                    rs.getBigDecimal("total_comprado")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar vw_totalventas_cliente: " + e.getMessage());
        }
        return lista;
    }

    // ── vw_pedidos_pendientes ────────────────────────────────────────────────
    public List<Object[]> listarPedidosPendientesVista() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT proveedor, numero_factura, fecha, total_compra " +
                     "FROM vw_pedidos_pendientes ORDER BY fecha DESC";
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getString("proveedor"),
                    rs.getString("numero_factura"),
                    rs.getDate("fecha"),
                    rs.getBigDecimal("total_compra")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error listar vw_pedidos_pendientes: " + e.getMessage());
        }
        return lista;
    }

    // ── pedido (tabla completa con filtro por estado) ─────────────────────────
    public List<Object[]> listarPedidosPorEstado(String estado) {
        List<Object[]> lista = new ArrayList<>();
        String sql;
        if (estado == null || estado.equalsIgnoreCase("TODOS")) {
            sql = "SELECT pe.pedido_id, pe.numero_pedido, pe.fecha, pr.nombre AS proveedor, " +
                  "a.nombre AS almacen, pe.estado, pe.observacion " +
                  "FROM pedido pe " +
                  "JOIN proveedor pr ON pe.proveedor_id = pr.proveedor_id " +
                  "JOIN almacen a ON pe.almacen_id = a.almacen_id " +
                  "ORDER BY pe.fecha DESC";
        } else {
            sql = "SELECT pe.pedido_id, pe.numero_pedido, pe.fecha, pr.nombre AS proveedor, " +
                  "a.nombre AS almacen, pe.estado, pe.observacion " +
                  "FROM pedido pe " +
                  "JOIN proveedor pr ON pe.proveedor_id = pr.proveedor_id " +
                  "JOIN almacen a ON pe.almacen_id = a.almacen_id " +
                  "WHERE pe.estado = ? ORDER BY pe.fecha DESC";
        }
        Connection con = Conexion.getInstancia();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            if (estado != null && !estado.equalsIgnoreCase("TODOS")) {
                ps.setString(1, estado);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("pedido_id"),
                        rs.getString("numero_pedido"),
                        rs.getDate("fecha"),
                        rs.getString("proveedor"),
                        rs.getString("almacen"),
                        rs.getString("estado"),
                        rs.getString("observacion")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listar pedidos por estado: " + e.getMessage());
        }
        return lista;
    }
}
