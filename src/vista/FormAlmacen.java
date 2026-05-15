package vista;

import static vista.EstiloMercaFacil.*;
import dao.AlmacenDAO;
import modelo.Almacen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Formulario CRUD para Almacen – Estilo Liquid Glass Pastel.
 */
public class FormAlmacen extends JFrame {

    private final AlmacenDAO dao = new AlmacenDAO();

    private JTextField txtId, txtCodigo, txtNombre, txtDireccion, txtGerente, txtBuscar;
    private JComboBox<String> cboEstado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private static final String[] COLUMNAS =
            {"ID", "Codigo", "Nombre", "Direccion", "Gerente", "Estado"};

    public FormAlmacen() {
        initUI();
        cargarTabla(dao.listarTodos());
    }

    private void initUI() {
        setTitle("MercaFacil – CRUD Almacen");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        aplicarIcono(this);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(12, 12, 12, 12));
        panelPrincipal.setBackground(BG_PRINCIPAL);
        setContentPane(panelPrincipal);

        panelPrincipal.add(crearTituloForm("Gestion de Almacenes – MercaFacil"), BorderLayout.NORTH);
        panelPrincipal.add(wrapFormEnScroll(crearPanelFormulario(), 300), BorderLayout.WEST);
        panelPrincipal.add(crearPanelTabla(),      BorderLayout.CENTER);
        panelPrincipal.add(crearPanelBotones(),    BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_CARD_OPAQUE);
        panel.setBorder(crearBordeTitulado("Datos del Almacen"));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets  = new Insets(4, 4, 4, 4);
        gc.anchor  = GridBagConstraints.WEST;
        gc.fill    = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1.0;
        gc.gridx   = 0;

        gc.gridy = 0;  panel.add(crearLabel("ID:"), gc);
        gc.gridy = 1;
        txtId = new JTextField(); estilizarInput(txtId, false);
        panel.add(txtId, gc);

        gc.gridy = 2;  panel.add(crearLabel("Codigo:"), gc);
        gc.gridy = 3;  txtCodigo = new JTextField(); estilizarInput(txtCodigo, true); panel.add(txtCodigo, gc);

        gc.gridy = 4;  panel.add(crearLabel("Nombre:"), gc);
        gc.gridy = 5;  txtNombre = new JTextField(); estilizarInput(txtNombre, true); panel.add(txtNombre, gc);

        gc.gridy = 6;  panel.add(crearLabel("Direccion:"), gc);
        gc.gridy = 7;  txtDireccion = new JTextField(); estilizarInput(txtDireccion, true); panel.add(txtDireccion, gc);

        gc.gridy = 8;  panel.add(crearLabel("Gerente:"), gc);
        gc.gridy = 9;  txtGerente = new JTextField(); estilizarInput(txtGerente, true); panel.add(txtGerente, gc);

        gc.gridy = 10; panel.add(crearLabel("Estado:"), gc);
        gc.gridy = 11;
        cboEstado = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});
        estilizarCombo(cboEstado); panel.add(cboEstado, gc);

        gc.gridy = 12; panel.add(new JSeparator(), gc);

        gc.gridy = 13; panel.add(crearLabel("Buscar por nombre:"), gc);
        gc.gridy = 14; txtBuscar = new JTextField(); estilizarInput(txtBuscar, true); panel.add(txtBuscar, gc);

        gc.gridy = 15;
        JButton btnBuscar = crearBotonPill("Buscar", BTN_BUSCAR);
        btnBuscar.addActionListener(e -> accionBuscar());
        panel.add(btnBuscar, gc);

        gc.gridy = 16;
        JButton btnLimpiar = crearBotonPill("Limpiar", BTN_LIMPIAR);
        btnLimpiar.addActionListener(e -> limpiarCampos());
        panel.add(btnLimpiar, gc);

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

        JButton btnInsertar   = crearBotonPill("Insertar",    BTN_INSERTAR);
        JButton btnActualizar = crearBotonPill("Actualizar",  BTN_ACTUALIZAR);
        JButton btnEliminar   = crearBotonPill("Eliminar",    BTN_ELIMINAR);
        JButton btnListar     = crearBotonPill("Listar Todo", BTN_LISTAR);
        JButton btnSalir      = crearBotonPill("Salir",       BTN_SALIR);

        btnInsertar.addActionListener(e   -> accionInsertar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnEliminar.addActionListener(e   -> accionEliminar());
        btnListar.addActionListener(e     -> cargarTabla(dao.listarTodos()));
        btnSalir.addActionListener(e      -> accionSalir());

        panel.add(btnInsertar); panel.add(btnActualizar);
        panel.add(btnEliminar); panel.add(btnListar); panel.add(btnSalir);
        return panel;
    }

    // ── ACCIONES CRUD ────────────────────────────────────────────────────────

    private void accionInsertar() {
        if (!validarCampos()) return;
        Almacen a = obtenerAlmacenDesdeFormulario();
        if (dao.insertar(a)) {
            mostrarMensaje("Almacen insertado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(dao.listarTodos()); limpiarCampos();
        } else {
            mostrarMensaje("No se pudo insertar. Verifique el codigo unico.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar() {
        if (txtId.getText().isEmpty()) { mostrarMensaje("Seleccione un registro.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        if (!validarCampos()) return;
        Almacen a = obtenerAlmacenDesdeFormulario();
        a.setAlmacenId(Integer.parseInt(txtId.getText()));
        if (dao.actualizar(a)) {
            mostrarMensaje("Almacen actualizado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(dao.listarTodos()); limpiarCampos();
        } else {
            mostrarMensaje("No se pudo actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        if (txtId.getText().isEmpty()) { mostrarMensaje("Seleccione un registro.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Eliminar el almacen seleccionado?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(Integer.parseInt(txtId.getText()))) {
                mostrarMensaje("Almacen eliminado.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla(dao.listarTodos()); limpiarCampos();
            } else {
                mostrarMensaje("No se pudo eliminar. Puede tener registros relacionados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void accionBuscar() {
        String texto = txtBuscar.getText().trim();
        cargarTabla(texto.isEmpty() ? dao.listarTodos() : dao.buscarPorNombre(texto));
    }

    private void accionSalir() { this.dispose(); FormMenu.getInstance().setVisible(true); }

    // ── UTILIDADES ───────────────────────────────────────────────────────────

    private void cargarTabla(List<Almacen> lista) {
        modeloTabla.setRowCount(0);
        for (Almacen a : lista) {
            modeloTabla.addRow(new Object[]{
                a.getAlmacenId(), a.getCodigo(), a.getNombre(),
                a.getDireccion(), a.getGerente(), a.getEstado()
            });
        }
    }

    private void cargarFilaSeleccionada() {
        int fila = tabla.getSelectedRow(); if (fila < 0) return;
        txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtCodigo.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtDireccion.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtGerente.setText(modeloTabla.getValueAt(fila, 4).toString());
        cboEstado.setSelectedItem(modeloTabla.getValueAt(fila, 5).toString());
    }

    private Almacen obtenerAlmacenDesdeFormulario() {
        return new Almacen(txtCodigo.getText().trim(), txtNombre.getText().trim(),
                txtDireccion.getText().trim(), txtGerente.getText().trim(),
                (String) cboEstado.getSelectedItem());
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
            txtDireccion.getText().trim().isEmpty() || txtGerente.getText().trim().isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios.", "Validacion", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.setText(""); txtCodigo.setText(""); txtNombre.setText("");
        txtDireccion.setText(""); txtGerente.setText("");
        cboEstado.setSelectedIndex(0); txtBuscar.setText(""); tabla.clearSelection();
    }

    private void mostrarMensaje(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }
}
