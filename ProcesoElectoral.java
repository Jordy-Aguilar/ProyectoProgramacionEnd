import java.util.*;

class ProcesoElectoral {

    protected static final int HORA_CIERRE = 17;

    private Map<String, Votante> votantes;
    private Map<String, Candidato> candidatos;
    private List<String> partidosPoliticos;
    private int horaActual;
    public boolean procesoElectoralCerrado;

    public ProcesoElectoral() {
        votantes = new HashMap<>();
        candidatos = new HashMap<>();
        partidosPoliticos = new ArrayList<>();
        procesoElectoralCerrado = false;
        horaActual = 5; // Inicializando la hora en 05:00 Am
    }

    void mostrarInformacionProcesoElectoral() {
        System.out.println("=== Información del Proceso Electoral ===");

        // Mostrar candidatos y sus partidos políticos
        System.out.println("Candidatos y Partidos Políticos:");
        for (Candidato candidato : candidatos.values()) {
            System.out.println(candidato.getNombre() + " " + candidato.getApellido() + " - " + candidato.getPartidoPolitico());
        }

        // Mostrar el periodo electoral
        System.out.println("Periodo Electoral:");
        System.out.println("Desde las 05:00 AM hasta las " + HORA_CIERRE + ":00 PM");
    }

    void avanzarHora() {
        if (horaActual >= HORA_CIERRE) {
            System.out.println("Ya ha pasado la hora límite para emitir votos. El proceso electoral está cerrado.");
            procesoElectoralCerrado = true;
        } else {
            horaActual++;
            System.out.println("La hora ha avanzado a las " + horaActual + ":00.");
        }
    }

    void mostrarResultadosEleccion() {
        if (!procesoElectoralCerrado) {
            System.out.println("El proceso electoral aún no ha concluido. No hay resultados disponibles.");
            return;
        }

        System.out.println("=== Resultados Oficiales de las Elecciones ===");

        // Contadores
        int votosBlancos = 0;
        int votosNulos = 0;
        int votosValidos = 0;

        // Contadores de votos por candidato
        Map<Candidato, Integer> votosPorCandidato = new HashMap<>();

        // Contadores para encontrar el candidato más y menos votado
        int maxVotos = 0;
        int minVotos = Integer.MAX_VALUE;
        Candidato candidatoMasVotado = null;
        Candidato candidatoMenosVotado = null;

        // Contar votos nulos y en blanco y por cada candidato
        for (Votante votante : votantes.values()) {
            if (votante.getVotoEmitido()) {
                // Voto válido emitido
                if (votante.getOpcionVoto().equals(String.valueOf(candidatos.size() + 1))) {
                    votosBlancos++;
                } else if (votante.getOpcionVoto().equals(String.valueOf(candidatos.size() + 2))) {
                    votosNulos++;
                } else {
                    // Voto válido para un candidato
                    votosValidos++;
                    int numeroOpcion = Integer.parseInt(votante.getOpcionVoto());
                    Candidato candidato = candidatos.values().toArray(new Candidato[0])[numeroOpcion - 1];

                    // Actualizar contadores de votos por candidato
                    votosPorCandidato.put(candidato, votosPorCandidato.getOrDefault(candidato, 0) + 1);

                    // Actualizar candidato más votado
                    if (votosPorCandidato.get(candidato) > maxVotos) {
                        maxVotos = votosPorCandidato.get(candidato);
                        candidatoMasVotado = candidato;
                    }

                    // Actualizar candidato menos votado
                    if (votosPorCandidato.get(candidato) < minVotos) {
                        minVotos = votosPorCandidato.get(candidato);
                        candidatoMenosVotado = candidato;
                    }
                }
            }
        }

        // Calcular porcentajes
        double porcentajeVotosBlancos = (votosBlancos * 100.0) / votosValidos;
        double porcentajeVotosNulos = (votosNulos * 100.0) / votosValidos;
        double porcentajeVotosValidos = (votosValidos * 100.0) / (votosValidos + votosNulos + votosBlancos);

        // Mostrar resultados
        System.out.println("a) Cantidad de votos en blanco: " + votosBlancos);
        System.out.println("b) Cantidad de votos nulos: " + votosNulos);
        System.out.println("c) Cantidad de votos válidos: " + votosValidos);

        // Mostrar votos por cada candidato
        System.out.println("d) Cantidad de votos obtenidos por cada candidato:");
        for (Candidato candidato : votosPorCandidato.keySet()) {
            System.out.println("   " + candidato.getNombre() + " " + candidato.getApellido() + ": " + votosPorCandidato.get(candidato) + " votos");
        }

        // Mostrar candidato más votado
        System.out.println("e) Candidato más votado: " + candidatoMasVotado);

        // Mostrar candidato menos votado
        System.out.println("f) Candidato menos votado: " + candidatoMenosVotado);

        // Mostrar porcentajes
        System.out.println("g) Porcentaje de todos los candidatos:");
        for (Candidato candidato : votosPorCandidato.keySet()) {
            double porcentajeCandidato = (votosPorCandidato.get(candidato) * 100.0) / votosValidos;
            System.out.println("   " + candidato.getNombre() + " " + candidato.getApellido() + ": " + porcentajeCandidato + "%");
        }

        System.out.println("h) Porcentaje de votos en blanco: " + porcentajeVotosBlancos + "%");
        System.out.println("i) Porcentaje de votos nulos: " + porcentajeVotosNulos + "%");
        System.out.println("j) Porcentaje de votos válidos: " + porcentajeVotosValidos + "%");
    }

    public Map<String, Votante> getVotantes() {
        return votantes;
    }

    public void setVotantes(Map<String, Votante> votantes) {
        this.votantes = votantes;
    }

    public Map<String, Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(Map<String, Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public List<String> getPartidosPoliticos() {
        return partidosPoliticos;
    }

    public void setPartidosPoliticos(List<String> partidosPoliticos) {
        this.partidosPoliticos = partidosPoliticos;
    }

    public int getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(int horaActual) {
        this.horaActual = horaActual;
    }

    public boolean isProcesoElectoralCerrado() {
        return procesoElectoralCerrado;
    }

    public void setProcesoElectoralCerrado(boolean procesoElectoralCerrado) {
        this.procesoElectoralCerrado = procesoElectoralCerrado;
    }

}