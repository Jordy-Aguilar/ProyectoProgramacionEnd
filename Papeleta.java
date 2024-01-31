import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Papeleta {
    private Map<String, Candidato> candidatos;
    private List<String> partidosPoliticos;

    public Papeleta(Map<String, Candidato> candidatos, List<String> partidosPoliticos) {
        this.candidatos = candidatos;
        this.partidosPoliticos = partidosPoliticos;
    }

    public void mostrarCandidatos() {
        System.out.println("=== Candidatos y Partidos Políticos ===");
        for (Candidato candidato : candidatos.values()) {
            System.out.println(candidato.getNombre() + " " + candidato.getApellido() + " - " + candidato.getPartidoPolitico());
        }
    }

    public boolean votar(Scanner scanner, Votante votante, boolean procesoElectoralCerrado) {
        if (procesoElectoralCerrado) {
            System.out.println("El proceso electoral está cerrado. No se pueden emitir votos.");
            return false;
        }

        System.out.println("=== Emitir Voto ===");

        // Mostrar candidatos y partidos políticos con números
        mostrarCandidatosNumerados();

        // Opciones adicionales
        int opcionBlanco = candidatos.size() + 1;
        int opcionNulo = candidatos.size() + 2;
        System.out.println(opcionBlanco + ") Voto en blanco");
        System.out.println(opcionNulo + ") Voto nulo");

        System.out.print("Seleccione una opción: ");
        String opcionVoto = scanner.nextLine();

        try {
            int numeroOpcion = Integer.parseInt(opcionVoto);

            if (numeroOpcion >= 1 && numeroOpcion <= candidatos.size()) {
                // El usuario ha seleccionado un candidato válido
                Candidato candidatoElegido = candidatos.values().toArray(new Candidato[0])[numeroOpcion - 1];
                candidatoElegido.incrementarVotos();
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(numeroOpcion));
                System.out.println("Voto registrado exitosamente para " + candidatoElegido.getNombre() + " " + candidatoElegido.getApellido());
            } else if (numeroOpcion == opcionBlanco) {
                // Voto en blanco
                System.out.println("Voto en blanco registrado exitosamente.");
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(opcionBlanco));
            } else if (numeroOpcion == opcionNulo) {
                // Voto nulo
                System.out.println("Voto nulo registrado exitosamente.");
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(opcionNulo));
            } else {
                System.out.println("Opción no válida. Por favor, ingrese una opción válida y vuelva a intentarlo.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida. Por favor, ingrese un número.");
            return false;
        }

        return true;
    }

    private void mostrarCandidatosNumerados() {
        int i = 1;
        for (Candidato candidato : candidatos.values()) {
            System.out.println(i + ") " + candidato.getNombre() + " " + candidato.getApellido() + " - " + candidato.getPartidoPolitico());
            i++;
        }
    }
}