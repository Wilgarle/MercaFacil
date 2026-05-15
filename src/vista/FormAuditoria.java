package vista;

import static vista.EstiloMercaFacil.*;
import dao.AuditoriaDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormAuditoria extends JFrame {

    private final AuditoriaDAO dao = new AuditoriaDAO();
    private JTable tablaAuditoria, tablaInventario, tablaMasVendidos, tablaTotalVentas, tablaPedidosVista, tablaPedidos;
    private DefaultTableModel mAuditoria, mInventario, mMasVendidos, mTotalVentas, mPedidosVista, mPedidos;
    private JComboBox<String> cboEstadoPedido;

    public FormAuditoria() { initUI(); cargarAuditoria(); }

    private void initUI() {
        setTitle("MercaFacil – Auditoria del Sistema"); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 750); setLocationRelativeTo(null); aplicarIcono(this);
        JPanel p = new JPanel(new BorderLayout(8,8)); p.setBorder(new EmptyBorder(10,10,10,10)); p.setBackground(BG_PRINCIPAL); setContentPane(p);
        p.add(crearTituloForm("Auditoria del Sistema – MercaFacil"), BorderLayout.NORTH);

        // Panel de contenido principal con tamaño fijo vertical para que el scroll funcione
        JPanel contenido = new JPanel(new BorderLayout(8,8));
        contenido.setBackground(BG_PRINCIPAL);

        JPanel panelAudit = crearPanelAudit();
        panelAudit.setPreferredSize(new Dimension(0, 280));
        panelAudit.setMinimumSize(new Dimension(0, 200));
        contenido.add(panelAudit, BorderLayout.NORTH);

        JTabbedPane tabs = crearTabs();
        tabs.setPreferredSize(new Dimension(0, 400));
        tabs.setMinimumSize(new Dimension(0, 300));
        contenido.add(tabs, BorderLayout.CENTER);

        // Scroll general para todo el contenido
        JScrollPane scrollGeneral = new JScrollPane(contenido,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGeneral.setBorder(BorderFactory.createEmptyBorder());
        scrollGeneral.getVerticalScrollBar().setUnitIncrement(16);
        scrollGeneral.getViewport().setBackground(BG_PRINCIPAL);
        p.add(scrollGeneral, BorderLayout.CENTER);

        JPanel bot = new JPanel(new FlowLayout(FlowLayout.CENTER,10,6)); bot.setBackground(BG_PRINCIPAL);
        JButton bs = crearBotonPill("Salir", BTN_SALIR); bs.addActionListener(e->{dispose();FormMenu.getInstance().setVisible(true);}); bot.add(bs);
        p.add(bot, BorderLayout.SOUTH);
    }

    private JPanel crearPanelAudit() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL);
        panel.setBorder(crearBordeTitulado("Registros de Auditoria del Sistema"));
        mAuditoria = new DefaultTableModel(new String[]{"ID","Tabla Afectada","Ref. ID","Mensaje","Fecha","Nivel Alerta"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tablaAuditoria = new JTable(mAuditoria); estilizarTabla(tablaAuditoria);
        tablaAuditoria.getColumnModel().getColumn(0).setMaxWidth(50); tablaAuditoria.getColumnModel().getColumn(2).setMaxWidth(60); tablaAuditoria.getColumnModel().getColumn(3).setPreferredWidth(350);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,6,2)); top.setBackground(BG_PRINCIPAL);
        JButton br = crearBotonPill("Refrescar", BTN_REFRESCAR); br.addActionListener(e->cargarAuditoria()); top.add(br);
        panel.add(top, BorderLayout.NORTH); panel.add(new JScrollPane(tablaAuditoria), BorderLayout.CENTER); return panel;
    }

    private JTabbedPane crearTabs() {
        JTabbedPane tabs = new JTabbedPane(); tabs.setFont(FONT_TAB);
        tabs.addTab("Inventario", crearTabInv()); tabs.addTab("Productos Mas Vendidos", crearTabMV());
        tabs.addTab("Total Ventas Cliente", crearTabTV()); tabs.addTab("Pedidos", crearTabPed());
        tabs.addChangeListener(e->{switch(tabs.getSelectedIndex()){case 0:cargarInv();break;case 1:cargarMV();break;case 2:cargarTV();break;case 3:cargarPedV();cargarPedF();break;}});
        return tabs;
    }

    private JPanel crearTabInv() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL); panel.setBorder(new EmptyBorder(6,6,6,6));
        mInventario = new DefaultTableModel(new String[]{"ID","Producto","Almacen","Stock Actual","Ubicacion"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tablaInventario = new JTable(mInventario); estilizarTabla(tablaInventario); tablaInventario.getColumnModel().getColumn(0).setMaxWidth(50);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,6,2)); top.setBackground(BG_PRINCIPAL);
        JButton b = crearBotonPill("Refrescar", BTN_REFRESCAR); b.addActionListener(e->cargarInv()); top.add(b);
        panel.add(top, BorderLayout.NORTH); panel.add(new JScrollPane(tablaInventario), BorderLayout.CENTER); return panel;
    }

    private JPanel crearTabMV() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL); panel.setBorder(new EmptyBorder(6,6,6,6));
        mMasVendidos = new DefaultTableModel(new String[]{"Producto","Total Ventas"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tablaMasVendidos = new JTable(mMasVendidos); estilizarTabla(tablaMasVendidos);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,6,2)); top.setBackground(BG_PRINCIPAL);
        JButton b = crearBotonPill("Refrescar", BTN_REFRESCAR); b.addActionListener(e->cargarMV()); top.add(b);
        panel.add(top, BorderLayout.NORTH); panel.add(new JScrollPane(tablaMasVendidos), BorderLayout.CENTER); return panel;
    }

    private JPanel crearTabTV() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL); panel.setBorder(new EmptyBorder(6,6,6,6));
        mTotalVentas = new DefaultTableModel(new String[]{"Cliente","Total Comprado"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tablaTotalVentas = new JTable(mTotalVentas); estilizarTabla(tablaTotalVentas);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,6,2)); top.setBackground(BG_PRINCIPAL);
        JButton b = crearBotonPill("Refrescar", BTN_REFRESCAR); b.addActionListener(e->cargarTV()); top.add(b);
        panel.add(top, BorderLayout.NORTH); panel.add(new JScrollPane(tablaTotalVentas), BorderLayout.CENTER); return panel;
    }

    private JPanel crearTabPed() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL); panel.setBorder(new EmptyBorder(6,6,6,6));
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, crearPanelPedV(), crearPanelPedF());
        split.setDividerLocation(150); split.setResizeWeight(0.4); panel.add(split, BorderLayout.CENTER); return panel;
    }

    private JPanel crearPanelPedV() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL);
        panel.setBorder(crearBordeTitulado("Vista: Pedidos Pendientes"));
        mPedidosVista = new DefaultTableModel(new String[]{"Proveedor","Num. Factura","Fecha","Total Compra"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tablaPedidosVista = new JTable(mPedidosVista); estilizarTabla(tablaPedidosVista);
        panel.add(new JScrollPane(tablaPedidosVista), BorderLayout.CENTER); return panel;
    }

    private JPanel crearPanelPedF() {
        JPanel panel = new JPanel(new BorderLayout(4,4)); panel.setBackground(BG_PRINCIPAL);
        panel.setBorder(crearBordeTitulado("Tabla Pedidos – Filtrar por Estado"));
        JPanel filtro = new JPanel(new FlowLayout(FlowLayout.LEFT,8,4)); filtro.setBackground(BG_PRINCIPAL);
        filtro.add(crearLabel("Estado:")); cboEstadoPedido = new JComboBox<>(new String[]{"TODOS","PENDIENTE","RECIBIDO","CANCELADO"}); estilizarCombo(cboEstadoPedido); filtro.add(cboEstadoPedido);
        JButton bf = crearBotonPill("Filtrar", BTN_FILTRAR); bf.setPreferredSize(new Dimension(100,34)); bf.addActionListener(e->cargarPedF()); filtro.add(bf);
        mPedidos = new DefaultTableModel(new String[]{"ID","Num. Pedido","Fecha","Proveedor","Almacen","Estado","Observacion"},0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tablaPedidos = new JTable(mPedidos); estilizarTabla(tablaPedidos); tablaPedidos.getColumnModel().getColumn(0).setMaxWidth(50);
        panel.add(filtro, BorderLayout.NORTH); panel.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER); return panel;
    }

    private void cargarAuditoria(){mAuditoria.setRowCount(0);for(Object[] f:dao.listarAuditoriaSistema())mAuditoria.addRow(f);}
    private void cargarInv(){mInventario.setRowCount(0);for(Object[] f:dao.listarInventario())mInventario.addRow(f);}
    private void cargarMV(){mMasVendidos.setRowCount(0);for(Object[] f:dao.listarProductosMasVendidos())mMasVendidos.addRow(f);}
    private void cargarTV(){mTotalVentas.setRowCount(0);for(Object[] f:dao.listarTotalVentasCliente())mTotalVentas.addRow(f);}
    private void cargarPedV(){mPedidosVista.setRowCount(0);for(Object[] f:dao.listarPedidosPendientesVista())mPedidosVista.addRow(f);}
    private void cargarPedF(){String e=(String)cboEstadoPedido.getSelectedItem();mPedidos.setRowCount(0);for(Object[] f:dao.listarPedidosPorEstado(e))mPedidos.addRow(f);}
}
