import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Voto {
    private static boolean votoEmitido;
    private String opcionVoto;


    public Voto() {
        this.votoEmitido = false;
        this.opcionVoto = "";
    }

    public static boolean isVotoEmitido() {
        return votoEmitido;
    }

    public void setVotoEmitido(boolean votoEmitido) {
        this.votoEmitido = votoEmitido;
    }

    public String getOpcionVoto() {
        return opcionVoto;
    }

    public void setOpcionVoto(String opcionVoto) {
        this.opcionVoto = opcionVoto;
    }

    static void emitirVoto(Scanner scanner, Map<String, Votante> votantes, Map<String, Candidato> candidatos, List<String> partidosPoliticos, boolean procesoElectoralCerrado) {
        if (procesoElectoralCerrado) {
            System.out.println("El proceso electoral está cerrado. No se pueden emitir votos.");
            return;
        }

        System.out.println("=== Emitir Voto ===");
        System.out.print("Número de identificación (cédula o pasaporte): ");
        String id = scanner.nextLine();

        if (!votantes.containsKey(id)) {
            System.out.println("El votante no está registrado.");
            return;
        }

        Votante votante = votantes.get(id);

        if (votante.getVoto().isVotoEmitido()) {
            System.out.println("Este votante ya ha ejercido su voto.");
            return;
        }

        // Mostrar candidatos y partidos políticos con números
        System.out.println("Candidatos disponibles:");
        int i = 1;
        for (Candidato candidato : candidatos.values()) {
            System.out.println(i + ") " + candidato.getNombre() + " " + candidato.getApellido() + " - " + candidato.getPartidoPolitico());
            i++;
        }

        // Opciones adicionales
        System.out.println(i + ") Voto en blanco");
        i++;
        System.out.println(i + ") Voto nulo");

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
            } else if (numeroOpcion == i - 1) {
                // Voto en blanco
                System.out.println("Voto en blanco registrado exitosamente.");
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(i - 1));
            } else if (numeroOpcion == i) {
                // Voto nulo
                System.out.println("Voto nulo registrado exitosamente.");
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(i));
            } else {
                System.out.println("Opción no válida. Por favor, ingrese una opción válida y vuelva a intentarlo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida. Por favor, ingrese un número.");
        }
    }
}