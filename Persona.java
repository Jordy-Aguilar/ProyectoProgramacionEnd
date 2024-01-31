class Persona {
    protected String nombre;
    protected String apellido;
    protected String id;

    public Persona(String nombre, String apellido, String id) {
        this.nombre = nombre;
        this.apellido = apellido;
        setId(id);
    }

    public boolean getVotoEmitido() {
        return Voto.isVotoEmitido();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (validarCedula(id)) {
            this.id = id;
        } else {
            System.out.println("La cédula debe tener 10 dígitos. Registro fallido.");
            this.id = "";  // Limpiar la cédula si no es válida
        }
    }

    private boolean validarCedula(String cedula) {
        return cedula.length() == 10;
    }
}