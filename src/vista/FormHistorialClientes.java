package vista;

import static vista.EstiloMercaFacil.*;
import dao.AuditoriaDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormHistorialClientes extends JFrame {
    private final AuditoriaDAO dao = new AuditoriaDAO();
    private JTable tabla; private DefaultTableModel modeloTabla;
    private static final String[] COLS = {"Hist. ID","Cliente ID","Documento","Nombre","Direccion","Telefono","Tipo Cliente","Accion","Fecha Accion","Usuario"};

    public FormHistorialClientes() { initUI(); cargarTabla(); }

    private void initUI() {
        setTitle("MercaFacil – Historial de Clientes"); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1150, 560); setLocationRelativeTo(null); aplicarIcono(this);
        JPanel p = new JPanel(new BorderLayout(10,10)); p.setBorder(new EmptyBorder(12,12,12,12)); p.setBackground(BG_PRINCIPAL); setContentPane(p);
        p.add(crearTituloForm("Historial de Clientes – Auditoria"), BorderLayout.NORTH);
        p.add(crearPanelTabla(), BorderLayout.CENTER); p.add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout()); panel.setBackground(BG_PRINCIPAL);
        modeloTabla = new DefaultTableModel(COLS,0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tabla = new JTable(modeloTabla); estilizarTabla(tabla); tabla.getColumnModel().getColumn(0).setMaxWidth(70);
        JScrollPane sc = new JScrollPane(tabla); sc.setBorder(BorderFactory.createLineBorder(BORDER_INPUT,1,true));
        panel.add(sc, BorderLayout.CENTER); return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,6)); panel.setBackground(BG_PRINCIPAL);
        JButton b1 = crearBotonPill("Refrescar", BTN_REFRESCAR); b1.addActionListener(e->cargarTabla());
        JButton b2 = crearBotonPill("Salir", BTN_SALIR); b2.addActionListener(e->{dispose();FormMenu.getInstance().setVisible(true);});
        panel.add(b1); panel.add(b2); return panel;
    }

    private void cargarTabla() { modeloTabla.setRowCount(0); for(Object[] f:dao.listarClientesHistorial()) modeloTabla.addRow(f); }
}
