package vista;

import static vista.EstiloMercaFacil.*;
import dao.ProveedorDAO;
import modelo.Proveedor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Formulario CRUD para Proveedor – Estilo Liquid Glass Pastel.
 */
public class FormProveedor extends JFrame {

    private final ProveedorDAO dao = new ProveedorDAO();

    private JTextField txtId, txtNit, txtNombre, txtDireccion, txtTelefono, txtCorreo, txtBuscar;
    private JComboBox<String> cboEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private static final String[] COLUMNAS =
            {"ID", "NIT", "Nombre", "Direccion", "Telefono", "Correo", "Estado"};

    public FormProveedor() {
        initUI();
        cargarTabla(dao.listarTodos());
    }

    private void initUI() {
        setTitle("MercaFacil – CRUD Proveedor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 600);
        setLocationRelativeTo(null);
        aplicarIcono(this);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(12, 12, 12, 12));
        panelPrincipal.setBackground(BG_PRINCIPAL);
        setContentPane(panelPrincipal);

        panelPrincipal.add(crearTituloForm("Gestion de Proveedores – MercaFacil"), BorderLayout.NORTH);
        panelPrincipal.add(wrapFormEnScroll(crearPanelFormulario(), 300), BorderLayout.WEST);
        panelPrincipal.add(crearPanelTabla(),      BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(),    BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_CARD_OPAQUE);
        panel.setBorder(crearBordeTitulado("Datos del Proveedor"));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4); gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL; gc.weightx = 1.0; gc.gridx = 0;

        gc.gridy = 0;  panel.add(crearLabel("ID:"), gc);
        gc.gridy = 1;  txtId = new JTextField(); estilizarInput(txtId, false); panel.add(txtId, gc);
        gc.gridy = 2;  panel.add(crearLabel("NIT:"), gc);
        gc.gridy = 3;  txtNit = new JTextField(); estilizarInput(txtNit, true); panel.add(txtNit, gc);
        gc.gridy = 4;  panel.add(crearLabel("Nombre:"), gc);
        gc.gridy = 5;  txtNombre = new JTextField(); estilizarInput(txtNombre, true); panel.add(txtNombre, gc);
        gc.gridy = 6;  panel.add(crearLabel("Direccion:"), gc);
        gc.gridy = 7;  txtDireccion = new JTextField(); estilizarInput(txtDireccion, true); panel.add(txtDireccion, gc);
        gc.gridy = 8;  panel.add(crearLabel("Telefono:"), gc);
        gc.gridy = 9;  txtTelefono = new JTextField(); estilizarInput(txtTelefono, true); panel.add(txtTelefono, gc);
        gc.gridy = 10; panel.add(crearLabel("Correo:"), gc);
        gc.gridy = 11; txtCorreo = new JTextField(); estilizarInput(txtCorreo, true); panel.add(txtCorreo, gc);
        gc.gridy = 12; panel.add(crearLabel("Estado:"), gc);
        gc.gridy = 13; cboEstado = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"}); estilizarCombo(cboEstado); panel.add(cboEstado, gc);

        gc.gridy = 14; panel.add(new JSeparator(), gc);
        gc.gridy = 15; panel.add(crearLabel("Buscar (Nombre/NIT):"), gc);
        gc.gridy = 16; txtBuscar = new JTextField(); estilizarInput(txtBuscar, true); panel.add(txtBuscar, gc);

        gc.gridy = 17;
        JButton btnBuscar = crearBotonPill("Buscar", BTN_BUSCAR);
        btnBuscar.addActionListener(e -> accionBuscar()); panel.add(btnBuscar, gc);
        gc.gridy = 18;
        JButton btnLimpiar = crearBotonPill("Limpiar", BTN_LIMPIAR);
        btnLimpiar.addActionListener(e -> limpiarCampos()); panel.add(btnLimpiar, gc);

        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_PRINCIPAL);
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        estilizarTabla(tabla);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { cargarFilaSeleccionada(); }
        });
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_INPUT, 1, true));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        panel.setBackground(BG_PRINCIPAL);
        JButton btnIns = crearBotonPill("Insertar", BTN_INSERTAR);
        JButton btnUpd = crearBotonPill("Actualizar", BTN_ACTUALIZAR);
        JButton btnDel = crearBotonPill("Eliminar", BTN_ELIMINAR);
        JButton btnList = crearBotonPill("Listar Todo", BTN_LISTAR);
        JButton btnSalir = crearBotonPill("Salir", BTN_SALIR);
        btnIns.addActionListener(e -> accionInsertar());
        btnUpd.addActionListener(e -> accionActualizar());
        btnDel.addActionListener(e -> accionEliminar());
        btnList.addActionListener(e -> cargarTabla(dao.listarTodos()));
        btnSalir.addActionListener(e -> accionSalir());
        panel.add(btnIns); panel.add(btnUpd); panel.add(btnDel); panel.add(btnList); panel.add(btnSalir);
        return panel;
    }

    private void accionInsertar() {
        if (!validarCampos()) return;
        Proveedor p = obtenerProveedorDesdeFormulario();
        if (dao.insertar(p)) { mostrarMensaje("Proveedor insertado.", "Exito", JOptionPane.INFORMATION_MESSAGE); cargarTabla(dao.listarTodos()); limpiarCampos(); }
        else { mostrarMensaje("No se pudo insertar. Verifique el NIT.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void accionActualizar() {
        if (txtId.getText().isEmpty()) { mostrarMensaje("Seleccione un registro.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        if (!validarCampos()) return;
        Proveedor p = obtenerProveedorDesdeFormulario(); p.setProveedorId(Integer.parseInt(txtId.getText()));
        if (dao.actualizar(p)) { mostrarMensaje("Proveedor actualizado.", "Exito", JOptionPane.INFORMATION_MESSAGE); cargarTabla(dao.listarTodos()); limpiarCampos(); }
        else { mostrarMensaje("No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE); }
    }

    private void accionEliminar() {
        if (txtId.getText().isEmpty()) { mostrarMensaje("Seleccione un registro.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int c = JOptionPane.showConfirmDialog(this, "Eliminar el proveedor?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (c == JOptionPane.YES_OPTION) {
            if (dao.eliminar(Integer.parseInt(txtId.getText()))) { mostrarMensaje("Eliminado.", "Exito", JOptionPane.INFORMATION_MESSAGE); cargarTabla(dao.listarTodos()); limpiarCampos(); }
            else { mostrarMensaje("No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE); }
        }
    }

    private void accionBuscar() { String t = txtBuscar.getText().trim(); cargarTabla(t.isEmpty() ? dao.listarTodos() : dao.buscarPorNombreONit(t)); }
    private void accionSalir() { this.dispose(); FormMenu.getInstance().setVisible(true); }

    private void cargarTabla(List<Proveedor> lista) {
        modeloTabla.setRowCount(0);
        for (Proveedor p : lista) { modeloTabla.addRow(new Object[]{p.getProveedorId(), p.getNit(), p.getNombre(), p.getDireccion(), p.getTelefono(), p.getCorreo(), p.getEstado()}); }
    }

    private void cargarFilaSeleccionada() {
        int f = tabla.getSelectedRow(); if (f < 0) return;
        txtId.setText(val(f,0)); txtNit.setText(val(f,1)); txtNombre.setText(val(f,2));
        txtDireccion.setText(val(f,3)); txtTelefono.setText(val(f,4)); txtCorreo.setText(val(f,5));
        cboEstado.setSelectedItem(val(f,6));
    }
    private String val(int r, int c) { Object v = modeloTabla.getValueAt(r, c); return v != null ? v.toString() : ""; }

    private Proveedor obtenerProveedorDesdeFormulario() {
        return new Proveedor(txtNit.getText().trim(), txtNombre.getText().trim(), txtDireccion.getText().trim(),
                txtTelefono.getText().trim(), txtCorreo.getText().trim(), (String) cboEstado.getSelectedItem());
    }

    private boolean validarCampos() {
        if (txtNit.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
            txtDireccion.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("NIT, Nombre, Direccion y Telefono son obligatorios.", "Validacion", JOptionPane.WARNING_MESSAGE); return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText(""); txtNit.setText(""); txtNombre.setText(""); txtDireccion.setText("");
        txtTelefono.setText(""); txtCorreo.setText(""); cboEstado.setSelectedIndex(0); txtBuscar.setText(""); tabla.clearSelection();
    }

    private void mostrarMensaje(String msg, String titulo, int tipo) { JOptionPane.showMessageDialog(this, msg, titulo, tipo); }
}
