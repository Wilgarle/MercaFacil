package modelo;

/**
 * Clase modelo que representa la tabla Almacen de la BD MercaFacil
 * Campos: almacen_id, codigo, nombre, direccion, gerente, estado
 */
public class Almacen {

    private int almacenId;
    private String codigo;
    private String nombre;
    private String direccion;
    private String gerente;
    private String estado;

    // Constructor vacío
    public Almacen() {}

    // Constructor con todos los campos
    public Almacen(int almacenId, String codigo, String nombre,
                   String direccion, String gerente, String estado) {
        this.almacenId = almacenId;
        this.codigo    = codigo;
        this.nombre    = nombre;
        this.direccion = direccion;
        this.gerente   = gerente;
        this.estado    = estado;
    }

    // Constructor sin ID (para INSERT)
    public Almacen(String codigo, String nombre,
                   String direccion, String gerente, String estado) {
        this.codigo    = codigo;
        this.nombre    = nombre;
        this.direccion = direccion;
        this.gerente   = gerente;
        this.estado    = estado;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public int getAlmacenId()            { return almacenId; }
    public void setAlmacenId(int id)     { this.almacenId = id; }

    public String getCodigo()            { return codigo; }
    public void setCodigo(String c)      { this.codigo = c; }

    public String getNombre()            { return nombre; }
    public void setNombre(String n)      { this.nombre = n; }

    public String getDireccion()         { return direccion; }
    public void setDireccion(String d)   { this.direccion = d; }

    public String getGerente()           { return gerente; }
    public void setGerente(String g)     { this.gerente = g; }

    public String getEstado()            { return estado; }
    public void setEstado(String e)      { this.estado = e; }

    @Override
    public String toString() {
        return "Almacen{id=" + almacenId + ", codigo='" + codigo +
               "', nombre='" + nombre + "', estado='" + estado + "'}";
    }
}
