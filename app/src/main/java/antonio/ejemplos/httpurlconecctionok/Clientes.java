package antonio.ejemplos.httpurlconecctionok;

/**
 * Created by Susana on 30/09/2015.
 */
public class Clientes {

    private int id; //es autoincremental en BB.DD.
    private int androidID;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String email;
    private int idCategoria;
    private String observaciones;
    private String owner;

    public Clientes() {

    }

    public Clientes(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Clientes(String nombre) {
        this.nombre = nombre;
    }

    public Clientes(int id, int androidID, String nombre, String apellidos, String direccion, String telefono, String email, int idCategoria, String observaciones, String owner) {
        this.id = id;
        this.androidID = androidID;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.idCategoria = idCategoria;
        this.observaciones = observaciones;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public int getAndroidID() {
        return androidID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAndroidID(int androidID) {
        this.androidID = androidID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return
                this.nombre;
    }
}
