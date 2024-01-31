import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;


class Candidato extends Persona {
    private String fechaNacimiento;
    private String genero;
    private String telefono;
    private String correo;
    private String partidoPolitico;
    private int votos;

    public Candidato(String nombre, String apellido, String fechaNacimiento, String id, String genero,
                     String telefono, String correo, String partidoPolitico) {
        super(nombre, apellido, id);
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.telefono = telefono;
        this.correo = correo;
        this.partidoPolitico = partidoPolitico;
        this.votos = 0;
    }

    public static boolean registrarCandidatura(Scanner scanner, Map<String, Candidato> candidatosRegistrados, List<String> partidosPoliticos) {

        System.out.println("=== Registrar Candidatura ===");
        String nombre = validarNombre(scanner);
        String apellido = validarApellido(scanner);
        System.out.print("Fecha de nacimiento (Dia/Mes/Año): ");
        String fechaNacimiento = scanner.nextLine();
        System.out.print("Número de identificación (cédula o pasaporte): ");
        String id = scanner.nextLine();
        System.out.print("Género (Masculino, Femenino): ");
        String genero = scanner.nextLine();
        System.out.print("Número de teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();

        // Solicitar al candidato que ingrese el nombre del partido político
        System.out.print("Ingrese el nombre del partido político: ");
        String partidoPolitico = scanner.nextLine();

        // Verificar si el partido político ya existe en la lista de candidatos registrados, si no, agregarlo
        if (!candidatosRegistrados.values().stream().anyMatch(c -> c.getPartidoPolitico().equals(partidoPolitico))) {
            System.out.println("Partido político registrado exitosamente.");
        }

        Candidato candidato = new Candidato(nombre, apellido, fechaNacimiento, id, genero, telefono, correo, partidoPolitico);

        if (validarCandidato(candidato, candidatosRegistrados)) {
            candidatosRegistrados.put(id, candidato);
            System.out.println("Su registro se ha completado con éxito.");
            return true;
        }
        return false;
    }

    private static String validarNombre(Scanner scanner) {
        String texto;
        do {
            System.out.print("Nombre: ");
            texto = scanner.nextLine().trim();

            if (!esNombreValido(texto)) {
                System.out.println("Error: El nombre no puede contener números. Ingrese nuevamente.");
            }
        } while (!esNombreValido(texto));

        return texto;
    }

    private static String validarApellido(Scanner scanner) {
        String texto;
        do {
            System.out.print("Apellido: ");
            texto = scanner.nextLine().trim();

            if (!esNombreValido(texto)) {
                System.out.println("Error: El apellido no puede contener números. Ingrese nuevamente.");
            }
        } while (!esNombreValido(texto));

        return texto;
    }

    // Función para validar que el nombre o apellido no contenga números
    private static boolean esNombreValido(String texto) {
        // Patrón que permite solo letras y espacios
        String patron = "^[a-zA-Z\\s]+$";
        return Pattern.matches(patron, texto);
    }


    public static boolean validarCandidato(Candidato candidato, Map<String, Candidato> candidatosRegistrados) {
        if (!esMayorDe17Anios(candidato.getFechaNacimiento())) {
            System.out.println("Lamentablemente, no cumple con los requisitos necesarios (menor de 17 años).");
            return false;
        }

        if (!tieneCedulaValida(candidato.getId())) {
            System.out.println("La cédula debe tener 10 dígitos. Registro de candidatura fallido.");
            return false;
        }

        if (!tieneCorreoValido(candidato.getCorreo())) {
            System.out.println("El correo electrónico debe contener '@' y terminar con '.com'. Registro de candidatura fallido.");
            return false;
        }

        if (!tieneTelefonoValido(candidato.getTelefono())) {
            System.out.println("El número de teléfono debe empezar con '09' y tener 10 dígitos. Registro de candidatura fallido.");
            return false;
        }

        if (!esGeneroValido(candidato.getGenero())) {
            System.out.println("El género debe ser 'Masculino' o 'Femenino'. Registro de candidatura fallido.");
            return false;
        }

        if (yaRegistradoEnOtroPartido(candidato, candidatosRegistrados)) {
            System.out.println("El candidato ya está registrado para otro partido político. Registro de candidatura fallido.");
            return false;
        }

        return true;
    }

    private static boolean esMayorDe17Anios(String fechaNacimiento) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
            LocalDate fechaActual = LocalDate.now();

            long edad = ChronoUnit.YEARS.between(fechaNac, fechaActual);
            return edad > 17;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean tieneCedulaValida(String cedula) {
        return cedula.length() == 10;
    }

    private static boolean tieneCorreoValido(String correo) {
        String regex = "^(.+)@(.+).com$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(correo).matches();
    }

    private static boolean tieneTelefonoValido(String telefono) {
        return telefono.startsWith("09") && telefono.length() == 10;
    }

    private static boolean esGeneroValido(String genero) {
        return genero.equalsIgnoreCase("Masculino") || genero.equalsIgnoreCase("Femenino");
    }

    private static boolean yaRegistradoEnOtroPartido(Candidato candidato, Map<String, Candidato> candidatosRegistrados) {
        return candidatosRegistrados.values().stream()
                .anyMatch(c -> c.getPartidoPolitico().equals(candidato.getPartidoPolitico()));
    }

    public static boolean validarCandidato(Candidato candidato) {
        return false;
    }


    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPartidoPolitico(String partidoPolitico) {
        this.partidoPolitico = partidoPolitico;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public String getPartidoPolitico() {
        return partidoPolitico;
    }

    public void incrementarVotos() {
        this.votos++;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + partidoPolitico;
    }
}