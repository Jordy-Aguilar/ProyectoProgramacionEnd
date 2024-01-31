import java.util.Map;
import java.util.Scanner;

public class Votante extends Persona {
    private Voto voto;

    public Votante(String nombre, String apellido, String id) {
        super(nombre, apellido, id);
        this.voto = new Voto();
    }
    public String getOpcionVoto() {
        return voto.getOpcionVoto();
    }
    public Voto getVoto() {
        return voto;
    }


    public static void registroVotantes(Scanner scanner, Map<String, Votante> votantes, boolean procesoElectoralCerrado) {
        if (procesoElectoralCerrado) {
            System.out.println("El proceso electoral está cerrado. No se pueden registrar nuevos votantes.");
            return;
        }

        System.out.println("=== Registro de Votantes ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Número de identificación (cédula o pasaporte): ");
        String id = scanner.nextLine();

        // Verificar si la cédula es válida antes de agregar al votante
        if (!votantes.containsKey(id) && new Persona("", "", id).getId().equals(id)) {
            Votante votante = new Votante(nombre, apellido, id);
            votantes.put(id, votante);
            System.out.println("Votante registrado exitosamente.");
        } else {
            System.out.println("Este votante ya está registrado o la cédula es incorrecta. Registro fallido.");
        }
    }
}