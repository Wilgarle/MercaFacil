package vista;

import static vista.EstiloMercaFacil.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Menu principal de MercaFacil.
 * Patron Singleton: solo existe una instancia del menu.
 * Estilo: Liquid Glass + Moderno con paleta pastel.
 */
public class FormMenu extends JFrame {

    private static FormMenu instancia;

    public static FormMenu getInstance() {
        if (instancia == null) {
            instancia = new FormMenu();
        }
        return instancia;
    }

    private FormMenu() {
        initUI();
    }

    private void initUI() {
        setTitle("MercaFacil – Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        aplicarIcono(this);

        // Panel principal con fondo pastel
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 0));
        panelPrincipal.setBackground(BG_PRINCIPAL);
        setContentPane(panelPrincipal);

        // ── Encabezado con gradiente ─────────────────────────────────────────
        JPanel panelHeader = crearPanelGradiente();
        panelHeader.setLayout(new GridLayout(2, 1));
        panelHeader.setBorder(new EmptyBorder(30, 20, 20, 20));

        JLabel lblTitulo = new JLabel("MercaFácil", JLabel.CENTER);
        lblTitulo.setFont(FONT_TITLE_LARGE);
        lblTitulo.setForeground(TEXT_WHITE);
        ImageIcon logo = obtenerIconoLogo(48, 48);
        if (logo != null) lblTitulo.setIcon(logo);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestion de Inventarios", JLabel.CENTER);
        lblSubtitulo.setFont(FONT_SUBTITLE);
        lblSubtitulo.setForeground(TEXT_DARK);

        panelHeader.add(lblTitulo);
        panelHeader.add(lblSubtitulo);
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ── Botones del menu (dentro de un panel glass) ──────────────────────
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBackground(BG_PRINCIPAL);
        wrapper.setBorder(new EmptyBorder(15, 30, 15, 30));

        JPanel panelGlass = crearPanelGlass();
        panelGlass.setLayout(new GridLayout(9, 1, 0, 8));
        panelGlass.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblSeccion = new JLabel("Seleccione un modulo:", JLabel.CENTER);
        lblSeccion.setFont(FONT_SECTION);
        lblSeccion.setForeground(TEXT_SECONDARY);
        panelGlass.add(lblSeccion);

        JButton btnAlmacen = crearBotonMenuPill("Gestion de Almacenes", MOD_ALMACENES);
        btnAlmacen.addActionListener(e -> abrirFormulario("almacen"));
        panelGlass.add(btnAlmacen);

        JButton btnProveedor = crearBotonMenuPill("Gestion de Proveedores", MOD_PROVEEDORES);
        btnProveedor.addActionListener(e -> abrirFormulario("proveedor"));
        panelGlass.add(btnProveedor);

        JButton btnProducto = crearBotonMenuPill("Gestion de Productos", MOD_PRODUCTOS);
        btnProducto.addActionListener(e -> abrirFormulario("producto"));
        panelGlass.add(btnProducto);

        JButton btnHistVentas = crearBotonMenuPill("Historial de Ventas", MOD_VENTAS);
        btnHistVentas.addActionListener(e -> abrirFormulario("historial_ventas"));
        panelGlass.add(btnHistVentas);

        JButton btnHistClientes = crearBotonMenuPill("Historial de Clientes", MOD_CLIENTES);
        btnHistClientes.addActionListener(e -> abrirFormulario("historial_clientes"));
        panelGlass.add(btnHistClientes);

        JButton btnAuditoria = crearBotonMenuPill("Auditoria del Sistema", MOD_AUDITORIA);
        btnAuditoria.addActionListener(e -> abrirFormulario("auditoria"));
        panelGlass.add(btnAuditoria);

        panelGlass.add(new JLabel()); // spacer

        JButton btnSalir = crearBotonMenuPill("Salir del Sistema", MOD_SALIR);
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Desea salir del sistema?", "Confirmar salida",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        panelGlass.add(btnSalir);

        wrapper.add(panelGlass, BorderLayout.CENTER);
        panelPrincipal.add(wrapper, BorderLayout.CENTER);

        // ── Pie de pagina ────────────────────────────────────────────────────
        JLabel lblVersion = new JLabel("v2.0 – MercaFacil CRUD", JLabel.CENTER);
        lblVersion.setFont(FONT_FOOTER);
        lblVersion.setForeground(TEXT_MUTED);
        lblVersion.setBorder(new EmptyBorder(5, 0, 12, 0));
        panelPrincipal.add(lblVersion, BorderLayout.SOUTH);
    }

    private void abrirFormulario(String cual) {
        this.setVisible(false);
        switch (cual) {
            case "almacen":          new FormAlmacen().setVisible(true);          break;
            case "proveedor":        new FormProveedor().setVisible(true);        break;
            case "producto":         new FormProducto().setVisible(true);         break;
            case "historial_ventas": new FormHistorialVentas().setVisible(true);  break;
            case "historial_clientes": new FormHistorialClientes().setVisible(true); break;
            case "auditoria":        new FormAuditoria().setVisible(true);        break;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> FormMenu.getInstance().setVisible(true));
    }
}
