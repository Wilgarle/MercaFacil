package vista;

import static vista.EstiloMercaFacil.*;
import dao.ProductoDAO;
import modelo.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

public class FormProducto extends JFrame {

    private final ProductoDAO dao = new ProductoDAO();
    private JTextField txtId, txtCodigo, txtNombre, txtUnidad, txtStockMin, txtStockMax, txtBuscar;
    private JComboBox<String> cboEstado, cboCategoria;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private Map<Integer, String> mapCategorias;
    private static final String[] COLS = {"ID","Codigo","Nombre","Unidad","Stock Min","Stock Max","Estado","Categoria"};

    public FormProducto() { mapCategorias = dao.listarCategorias(); initUI(); cargarTabla(dao.listarTodos()); }

    private void initUI() {
        setTitle("MercaFacil – CRUD Producto"); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 640); setLocationRelativeTo(null); aplicarIcono(this);
        JPanel p = new JPanel(new BorderLayout(10,10)); p.setBorder(new EmptyBorder(12,12,12,12)); p.setBackground(BG_PRINCIPAL); setContentPane(p);
        p.add(crearTituloForm("Gestion de Productos – MercaFacil"), BorderLayout.NORTH);
        p.add(wrapFormEnScroll(crearPanelForm(), 300), BorderLayout.WEST); p.add(crearPanelTabla(), BorderLayout.CENTER); p.add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelForm() {
        JPanel panel = new JPanel(new GridBagLayout()); panel.setBackground(BG_CARD_OPAQUE);
        panel.setBorder(crearBordeTitulado("Datos del Producto"));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets=new Insets(3,4,3,4); gc.anchor=GridBagConstraints.WEST; gc.fill=GridBagConstraints.HORIZONTAL; gc.weightx=1.0; gc.gridx=0;

        gc.gridy=0; panel.add(crearLabel("ID:"),gc); gc.gridy=1; txtId=new JTextField(); estilizarInput(txtId,false); panel.add(txtId,gc);
        gc.gridy=2; panel.add(crearLabel("Codigo:"),gc); gc.gridy=3; txtCodigo=new JTextField(); estilizarInput(txtCodigo,true); panel.add(txtCodigo,gc);
        gc.gridy=4; panel.add(crearLabel("Nombre:"),gc); gc.gridy=5; txtNombre=new JTextField(); estilizarInput(txtNombre,true); panel.add(txtNombre,gc);
        gc.gridy=6; panel.add(crearLabel("Unidad de Medida:"),gc); gc.gridy=7; txtUnidad=new JTextField(); estilizarInput(txtUnidad,true); panel.add(txtUnidad,gc);
        gc.gridy=8; panel.add(crearLabel("Stock Minimo:"),gc); gc.gridy=9; txtStockMin=new JTextField(); estilizarInput(txtStockMin,true); panel.add(txtStockMin,gc);
        gc.gridy=10; panel.add(crearLabel("Stock Maximo:"),gc); gc.gridy=11; txtStockMax=new JTextField(); estilizarInput(txtStockMax,true); panel.add(txtStockMax,gc);
        gc.gridy=12; panel.add(crearLabel("Estado:"),gc); gc.gridy=13; cboEstado=new JComboBox<>(new String[]{"ACTIVO","INACTIVO"}); estilizarCombo(cboEstado); panel.add(cboEstado,gc);
        gc.gridy=14; panel.add(crearLabel("Categoria:"),gc); gc.gridy=15; cboCategoria=new JComboBox<>(); estilizarCombo(cboCategoria);
        for(Map.Entry<Integer,String> e:mapCategorias.entrySet()) cboCategoria.addItem(e.getKey()+" - "+e.getValue()); panel.add(cboCategoria,gc);
        gc.gridy=16; panel.add(new JSeparator(),gc);
        gc.gridy=17; panel.add(crearLabel("Buscar por nombre:"),gc); gc.gridy=18; txtBuscar=new JTextField(); estilizarInput(txtBuscar,true); panel.add(txtBuscar,gc);
        gc.gridy=19; JButton bb=crearBotonPill("Buscar",BTN_BUSCAR); bb.addActionListener(e->accionBuscar()); panel.add(bb,gc);
        gc.gridy=20; JButton bl=crearBotonPill("Limpiar",BTN_LIMPIAR); bl.addActionListener(e->limpiarCampos()); panel.add(bl,gc);
        return panel;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout()); panel.setBackground(BG_PRINCIPAL);
        modeloTabla = new DefaultTableModel(COLS,0){@Override public boolean isCellEditable(int r,int c){return false;}};
        tabla = new JTable(modeloTabla); estilizarTabla(tabla); tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        tabla.addMouseListener(new MouseAdapter(){@Override public void mouseClicked(MouseEvent e){cargarFila();}});
        JScrollPane sc = new JScrollPane(tabla); sc.setBorder(BorderFactory.createLineBorder(BORDER_INPUT,1,true)); panel.add(sc,BorderLayout.CENTER); return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,6)); panel.setBackground(BG_PRINCIPAL);
        JButton b1=crearBotonPill("Insertar",BTN_INSERTAR), b2=crearBotonPill("Actualizar",BTN_ACTUALIZAR), b3=crearBotonPill("Eliminar",BTN_ELIMINAR), b4=crearBotonPill("Listar Todo",BTN_LISTAR), b5=crearBotonPill("Salir",BTN_SALIR);
        b1.addActionListener(e->accionInsertar()); b2.addActionListener(e->accionActualizar()); b3.addActionListener(e->accionEliminar()); b4.addActionListener(e->cargarTabla(dao.listarTodos())); b5.addActionListener(e->accionSalir());
        panel.add(b1); panel.add(b2); panel.add(b3); panel.add(b4); panel.add(b5); return panel;
    }

    private void accionInsertar(){if(!validar())return;Producto p=fromForm();if(dao.insertar(p)){m("Producto insertado.","Exito",1);cargarTabla(dao.listarTodos());limpiarCampos();}else m("No se pudo insertar.","Error",0);}
    private void accionActualizar(){if(txtId.getText().isEmpty()){m("Seleccione un registro.","Aviso",2);return;}if(!validar())return;Producto p=fromForm();p.setProductoId(Integer.parseInt(txtId.getText()));if(dao.actualizar(p)){m("Producto actualizado.","Exito",1);cargarTabla(dao.listarTodos());limpiarCampos();}else m("No se pudo actualizar.","Error",0);}
    private void accionEliminar(){if(txtId.getText().isEmpty()){m("Seleccione un registro.","Aviso",2);return;}int c=JOptionPane.showConfirmDialog(this,"Eliminar?","Confirmar",JOptionPane.YES_NO_OPTION);if(c==0){if(dao.eliminar(Integer.parseInt(txtId.getText()))){m("Eliminado.","Exito",1);cargarTabla(dao.listarTodos());limpiarCampos();}else m("No se pudo eliminar.","Error",0);}}
    private void accionBuscar(){String t=txtBuscar.getText().trim();cargarTabla(t.isEmpty()?dao.listarTodos():dao.buscarPorNombre(t));}
    private void accionSalir(){dispose();FormMenu.getInstance().setVisible(true);}

    private void cargarTabla(List<Producto> l){modeloTabla.setRowCount(0);for(Producto p:l)modeloTabla.addRow(new Object[]{p.getProductoId(),p.getCodigo(),p.getNombre(),p.getUnidadMedida(),p.getStockMinimo(),p.getStockMaximo(),p.getEstado(),p.getCategoriaNombre()});}
    private void cargarFila(){int f=tabla.getSelectedRow();if(f<0)return;txtId.setText(v(f,0));txtCodigo.setText(v(f,1));txtNombre.setText(v(f,2));txtUnidad.setText(v(f,3));txtStockMin.setText(v(f,4));txtStockMax.setText(v(f,5));cboEstado.setSelectedItem(v(f,6));String cn=v(f,7);for(int i=0;i<cboCategoria.getItemCount();i++)if(cboCategoria.getItemAt(i).contains(cn)){cboCategoria.setSelectedIndex(i);break;}}
    private String v(int r,int c){Object o=modeloTabla.getValueAt(r,c);return o!=null?o.toString():"";}
    private int catId(){String s=(String)cboCategoria.getSelectedItem();if(s!=null&&s.contains(" - "))return Integer.parseInt(s.split(" - ")[0].trim());return 0;}
    private Producto fromForm(){return new Producto(txtCodigo.getText().trim(),txtNombre.getText().trim(),txtUnidad.getText().trim(),Integer.parseInt(txtStockMin.getText().trim()),Integer.parseInt(txtStockMax.getText().trim()),(String)cboEstado.getSelectedItem(),catId());}
    private boolean validar(){if(txtCodigo.getText().trim().isEmpty()||txtNombre.getText().trim().isEmpty()||txtUnidad.getText().trim().isEmpty()||txtStockMin.getText().trim().isEmpty()||txtStockMax.getText().trim().isEmpty()){m("Campos obligatorios vacios.","Validacion",2);return false;}try{Integer.parseInt(txtStockMin.getText().trim());Integer.parseInt(txtStockMax.getText().trim());}catch(NumberFormatException e){m("Stock debe ser numerico.","Validacion",2);return false;}return true;}
    private void limpiarCampos(){txtId.setText("");txtCodigo.setText("");txtNombre.setText("");txtUnidad.setText("");txtStockMin.setText("");txtStockMax.setText("");cboEstado.setSelectedIndex(0);if(cboCategoria.getItemCount()>0)cboCategoria.setSelectedIndex(0);txtBuscar.setText("");tabla.clearSelection();}
    private void m(String msg,String t,int tipo){JOptionPane.showMessageDialog(this,msg,t,tipo);}
}
