package modelo;

/**
 * Clase modelo que representa la tabla Producto de la BD MercaFacil.
 * Campos: producto_id, codigo, nombre, unidad_medida, stock_minimo, stock_maximo, estado, categoria_id
 */
public class Producto {

    private int productoId;
    private String codigo;
    private String nombre;
    private String unidadMedida;
    private int stockMinimo;
    private int stockMaximo;
    private String estado;
    private int categoriaId;
    // Campo auxiliar para mostrar nombre de categoría en tabla
    private String categoriaNombre;

    public Producto() {}

    public Producto(String codigo, String nombre, String unidadMedida,
                    int stockMinimo, int stockMaximo, String estado, int categoriaId) {
        this.codigo       = codigo;
        this.nombre       = nombre;
        this.unidadMedida = unidadMedida;
        this.stockMinimo  = stockMinimo;
        this.stockMaximo  = stockMaximo;
        this.estado       = estado;
        this.categoriaId  = categoriaId;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public int getProductoId()                   { return productoId; }
    public void setProductoId(int productoId)     { this.productoId = productoId; }

    public String getCodigo()                    { return codigo; }
    public void setCodigo(String codigo)          { this.codigo = codigo; }

    public String getNombre()                    { return nombre; }
    public void setNombre(String nombre)          { this.nombre = nombre; }

    public String getUnidadMedida()              { return unidadMedida; }
    public void setUnidadMedida(String u)         { this.unidadMedida = u; }

    public int getStockMinimo()                  { return stockMinimo; }
    public void setStockMinimo(int stockMinimo)   { this.stockMinimo = stockMinimo; }

    public int getStockMaximo()                  { return stockMaximo; }
    public void setStockMaximo(int stockMaximo)   { this.stockMaximo = stockMaximo; }

    public String getEstado()                    { return estado; }
    public void setEstado(String estado)          { this.estado = estado; }

    public int getCategoriaId()                  { return categoriaId; }
    public void setCategoriaId(int categoriaId)   { this.categoriaId = categoriaId; }

    public String getCategoriaNombre()           { return categoriaNombre; }
    public void setCategoriaNombre(String n)      { this.categoriaNombre = n; }
}
