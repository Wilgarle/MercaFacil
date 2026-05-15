package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Clase utilitaria centralizada con el Design System "MercaFacil Pastel Glass".
 * Estilo: Liquid Glass + Moderno con paleta pastel.
 * Fuente: Inter (Segoe UI como fallback en sistemas sin Inter instalado).
 */
public final class EstiloMercaFacil {

    private EstiloMercaFacil() {} // no instanciable

    // ═══════════════════════════════════════════════════════════════════════════
    // ── PALETA DE COLORES ──────────────────────────────────────────────────────
    // ═══════════════════════════════════════════════════════════════════════════

    // Fondos
    public static final Color BG_PRINCIPAL    = new Color(240, 244, 250);  // #F0F4FA
    public static final Color BG_CARD         = new Color(255, 255, 255, 165); // glass fill
    public static final Color BG_CARD_OPAQUE  = new Color(255, 255, 255);
    public static final Color BG_INPUT        = new Color(245, 247, 252);
    public static final Color BG_INPUT_DISABLED = new Color(235, 238, 245);

    // Header gradient
    public static final Color HEADER_START    = new Color(124, 159, 227); // #7C9FE3
    public static final Color HEADER_END      = new Color(91, 127, 204);  // #5B7FCC

    // Módulos (botones del menú)
    public static final Color MOD_ALMACENES   = new Color(124, 159, 227); // #7C9FE3
    public static final Color MOD_PROVEEDORES = new Color(126, 203, 161); // #7ECBA1
    public static final Color MOD_PRODUCTOS   = new Color(184, 169, 232); // #B8A9E8
    public static final Color MOD_VENTAS      = new Color(114, 188, 212); // #72BCD4
    public static final Color MOD_CLIENTES    = new Color(94, 174, 172);  // #5EAEAC
    public static final Color MOD_AUDITORIA   = new Color(232, 168, 124); // #E8A87C
    public static final Color MOD_SALIR       = new Color(232, 139, 139); // #E88B8B

    // Botones de acción
    public static final Color BTN_INSERTAR    = new Color(126, 203, 161); // mint
    public static final Color BTN_ACTUALIZAR  = new Color(240, 194, 122); // amber
    public static final Color BTN_ELIMINAR    = new Color(232, 139, 139); // coral
    public static final Color BTN_BUSCAR      = new Color(114, 188, 212); // sky blue
    public static final Color BTN_LISTAR      = new Color(124, 159, 227); // primary
    public static final Color BTN_LIMPIAR     = new Color(156, 163, 175); // muted gray
    public static final Color BTN_SALIR       = new Color(156, 163, 175); // muted gray
    public static final Color BTN_REFRESCAR   = new Color(124, 159, 227); // primary
    public static final Color BTN_FILTRAR     = new Color(126, 203, 161); // mint

    // Texto
    public static final Color TEXT_DARK       = new Color(45, 55, 72);    // #2D3748
    public static final Color TEXT_SECONDARY  = new Color(90, 107, 128);  // #5A6B80
    public static final Color TEXT_MUTED      = new Color(150, 170, 200);
    public static final Color TEXT_WHITE      = Color.WHITE;

    // Tabla
    public static final Color TABLE_HEADER_BG = new Color(124, 159, 227); // #7C9FE3
    public static final Color TABLE_HEADER_FG = Color.WHITE;
    public static final Color TABLE_ROW_ALT   = new Color(245, 247, 251); // #F5F7FB
    public static final Color TABLE_SELECTION = new Color(214, 228, 255); // #D6E4FF
    public static final Color TABLE_GRID      = new Color(220, 225, 235);

    // Bordes
    public static final Color BORDER_CARD     = new Color(200, 210, 228, 120);
    public static final Color BORDER_INPUT    = new Color(200, 210, 228);
    public static final Color BORDER_FOCUS    = new Color(124, 159, 227);

    // ═══════════════════════════════════════════════════════════════════════════
    // ── FUENTES ────────────────────────────────────────────────────────────────
    // ═══════════════════════════════════════════════════════════════════════════

    private static final String FONT_NAME = "Segoe UI"; // fallback si Inter no está instalado

    public static final Font FONT_TITLE       = new Font(FONT_NAME, Font.BOLD, 20);
    public static final Font FONT_TITLE_LARGE = new Font(FONT_NAME, Font.BOLD, 28);
    public static final Font FONT_SUBTITLE    = new Font(FONT_NAME, Font.PLAIN, 13);
    public static final Font FONT_LABEL       = new Font(FONT_NAME, Font.PLAIN, 12);
    public static final Font FONT_INPUT       = new Font(FONT_NAME, Font.PLAIN, 13);
    public static final Font FONT_BUTTON      = new Font(FONT_NAME, Font.BOLD, 13);
    public static final Font FONT_BUTTON_MENU = new Font(FONT_NAME, Font.BOLD, 14);
    public static final Font FONT_TABLE       = new Font(FONT_NAME, Font.PLAIN, 12);
    public static final Font FONT_TABLE_HEADER= new Font(FONT_NAME, Font.BOLD, 12);
    public static final Font FONT_FOOTER      = new Font(FONT_NAME, Font.PLAIN, 11);
    public static final Font FONT_SECTION     = new Font(FONT_NAME, Font.BOLD, 13);
    public static final Font FONT_TAB         = new Font(FONT_NAME, Font.BOLD, 12);

    // ═══════════════════════════════════════════════════════════════════════════
    // ── DIMENSIONES ────────────────────────────────────────────────────────────
    // ═══════════════════════════════════════════════════════════════════════════

    public static final int BORDER_RADIUS     = 20;
    public static final int CARD_RADIUS       = 16;
    public static final int INPUT_RADIUS      = 10;
    public static final int ROW_HEIGHT        = 32;

    // ═══════════════════════════════════════════════════════════════════════════
    // ── FACTORY METHODS ────────────────────────────────────────────────────────
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Crea un botón pill con el estilo Liquid Glass.
     */
    public static JButton crearBotonPill(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra suave
                g2.setColor(new Color(0, 0, 0, 20));
                g2.fill(new RoundRectangle2D.Float(2, 3, getWidth() - 4, getHeight() - 4, BORDER_RADIUS, BORDER_RADIUS));
                // Fondo del botón
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 2, BORDER_RADIUS, BORDER_RADIUS));
                g2.dispose();
                // Pintar texto
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BUTTON);
        btn.setBackground(color);
        btn.setForeground(TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 38));
        return btn;
    }

    /**
     * Crea un botón pill para el menú principal (más grande).
     */
    public static JButton crearBotonMenuPill(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fill(new RoundRectangle2D.Float(2, 3, getWidth() - 4, getHeight() - 4, BORDER_RADIUS, BORDER_RADIUS));
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 2, BORDER_RADIUS, BORDER_RADIUS));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BUTTON_MENU);
        btn.setBackground(color);
        btn.setForeground(TEXT_DARK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        return btn;
    }

    /**
     * Estiliza un JTextField con bordes redondeados y colores pastel.
     */
    public static void estilizarInput(JTextField txt, boolean editable) {
        txt.setFont(FONT_INPUT);
        txt.setBackground(editable ? BG_INPUT : BG_INPUT_DISABLED);
        txt.setForeground(TEXT_DARK);
        txt.setCaretColor(HEADER_START);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_INPUT, 1, true),
                new EmptyBorder(5, 10, 5, 10)));
        txt.setEditable(editable);
    }

    /**
     * Estiliza un JComboBox.
     */
    public static void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(FONT_INPUT);
        combo.setBackground(BG_INPUT);
        combo.setForeground(TEXT_DARK);
    }

    /**
     * Estiliza una JTable con el diseño Pastel Glass.
     */
    public static void estilizarTabla(JTable tabla) {
        tabla.setFont(FONT_TABLE);
        tabla.setForeground(TEXT_DARK);
        tabla.setRowHeight(ROW_HEIGHT);
        tabla.setSelectionBackground(TABLE_SELECTION);
        tabla.setSelectionForeground(TEXT_DARK);
        tabla.setGridColor(TABLE_GRID);
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new Dimension(1, 1));
        tabla.setFillsViewportHeight(true);

        // Header – renderer personalizado para forzar colores
        JTableHeader header = tabla.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(TABLE_HEADER_BG);
                lbl.setForeground(TEXT_DARK);
                lbl.setFont(FONT_TABLE_HEADER);
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                lbl.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, TABLE_GRID),
                        new EmptyBorder(0, 6, 0, 6)));
                lbl.setOpaque(true);
                return lbl;
            }
        });

        // Alternating rows
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : TABLE_ROW_ALT);
                }
                c.setForeground(TEXT_DARK);
                setBorder(new EmptyBorder(0, 6, 0, 6));
                return c;
            }
        });
    }

    /**
     * Crea un panel con efecto glass (fondo semi-transparente + bordes suaves).
     */
    public static JPanel crearPanelGlass() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sombra suave
                g2.setColor(new Color(0, 0, 0, 12));
                g2.fill(new RoundRectangle2D.Float(3, 4, getWidth() - 6, getHeight() - 6, CARD_RADIUS, CARD_RADIUS));
                // Fondo glass
                g2.setColor(BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, CARD_RADIUS, CARD_RADIUS));
                // Borde suave
                g2.setColor(BORDER_CARD);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, CARD_RADIUS, CARD_RADIUS));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Crea un panel con gradiente para headers.
     */
    public static JPanel crearPanelGradiente() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, HEADER_START, getWidth(), getHeight(), HEADER_END);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Crea un JLabel de título para formularios.
     */
    public static JLabel crearTituloForm(String texto) {
        JLabel lbl = new JLabel(texto, JLabel.CENTER);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_DARK);
        lbl.setBorder(new EmptyBorder(0, 0, 8, 0));
        return lbl;
    }

    /**
     * Crea un JLabel estándar para formularios.
     */
    public static JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }

    /**
     * Crea un borde titulado con el estilo glass.
     */
    public static Border crearBordeTitulado(String titulo) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_INPUT, 1, true),
                        titulo,
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        FONT_SECTION,
                        HEADER_START),
                new EmptyBorder(8, 10, 8, 10));
    }

    /**
     * Envuelve un panel de formulario en un JScrollPane con ancho fijo
     * para que todos los campos y botones sean siempre accesibles.
     */
    public static JScrollPane wrapFormEnScroll(JPanel panelForm, int ancho) {
        JScrollPane scroll = new JScrollPane(panelForm,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(ancho, 0));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(BG_PRINCIPAL);
        scroll.getViewport().setBackground(BG_PRINCIPAL);
        return scroll;
    }

    /**
     * Aplica el logo de la aplicacion como favicon al JFrame especificado.
     */
    public static void aplicarIcono(JFrame frame) {
        try {
            URL resource = EstiloMercaFacil.class.getResource("/recursos/logo.png");
            if (resource != null) {
                Image icon = new ImageIcon(resource).getImage();
                frame.setIconImage(icon);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + e.getMessage());
        }
    }

    /**
     * Retorna el logo de la aplicacion escalado y con bordes redondeados.
     */
    public static ImageIcon obtenerIconoLogo(int width, int height) {
        try {
            URL resource = EstiloMercaFacil.class.getResource("/recursos/logo.png");
            if (resource != null) {
                Image sourceImg = new ImageIcon(resource).getImage();
                
                // Crear una imagen con soporte de transparencia
                BufferedImage roundedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = roundedImg.createGraphics();
                
                // Activar maxima calidad de renderizado
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                
                // Crear el clip con bordes redondeados
                int arc = (int)(width * 0.25);
                g2.setClip(new RoundRectangle2D.Float(0, 0, width, height, arc, arc));
                
                // Dibujar la imagen escalada con alta calidad
                g2.drawImage(sourceImg, 0, 0, width, height, null);
                g2.dispose();
                
                return new ImageIcon(roundedImg);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono redondeado: " + e.getMessage());
        }
        return null;
    }
}
