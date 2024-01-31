import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;

public class InterfazProcesoElectoral extends JFrame {

    private ProcesoElectoral procesoElectoral;

    public InterfazProcesoElectoral() {
        procesoElectoral = new ProcesoElectoral();

        setTitle("Proceso Electoral");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JButton btnRegistrarCandidatura = crearBoton("Registrar Candidatura", Color.BLUE);
        JButton btnEmitirVoto = crearBoton("Emitir Voto", Color.GREEN);
        JButton btnRegistrarVotante = crearBoton("Registrar Votante", Color.YELLOW);
        JButton btnInfoProcesoElectoral = crearBoton("Información del Proceso Electoral", Color.RED);
        JButton btnAvanzarHora = crearBoton("Avanzar Hora", Color.PINK);
        JButton btnResultadosEleccion = crearBoton("Resultados Oficiales", Color.MAGENTA);

        panel.add(btnRegistrarCandidatura);
        panel.add(btnEmitirVoto);
        panel.add(btnRegistrarVotante);
        panel.add(btnInfoProcesoElectoral);
        panel.add(btnAvanzarHora);
        panel.add(btnResultadosEleccion);

        add(panel);

        btnRegistrarCandidatura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!procesoElectoral.isProcesoElectoralCerrado()) {
                    registrarCandidatura();
                } else {
                    JOptionPane.showMessageDialog(null, "El proceso electoral está cerrado. No se pueden registrar candidaturas.");
                }
            }
        });

        btnRegistrarVotante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!procesoElectoral.isProcesoElectoralCerrado()) {
                    registrarVotante();
                } else {
                    JOptionPane.showMessageDialog(null, "El proceso electoral está cerrado. No se pueden registrar votantes.");
                }
            }
        });

        btnInfoProcesoElectoral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInformacionProcesoElectoral();
            }
        });

        btnAvanzarHora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!procesoElectoral.isProcesoElectoralCerrado()) {
                    procesoElectoral.avanzarHora();
                    JOptionPane.showMessageDialog(null, "Hora avanzada a las " + procesoElectoral.getHoraActual() + ":00.");
                } else {
                    JOptionPane.showMessageDialog(null, "El proceso electoral está cerrado. No se puede avanzar la hora.");
                }
            }
        });

        btnEmitirVoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emitirVoto();
            }
        });

        btnResultadosEleccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (procesoElectoral.isProcesoElectoralCerrado()) {
                    procesoElectoral.mostrarResultadosEleccion();
                    JOptionPane.showMessageDialog(null, "Opción: Resultados Oficiales");
                } else {
                    JOptionPane.showMessageDialog(null, "El proceso electoral aún no ha concluido. No hay resultados disponibles.");
                }
            }
        });

        JOptionPane.showMessageDialog(this,
                "Bienvenido al Proceso Electoral\n\n" +
                        "Por favor, haga clic en 'OK' para continuar.",
                "Bienvenida", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        return boton;
    }

    private void mostrarInformacionProcesoElectoral() {
        StringBuilder info = new StringBuilder();
        info.append("=== Información del Proceso Electoral ===\n");

        info.append("Candidatos y Partidos Políticos:\n");
        for (Candidato candidato : procesoElectoral.getCandidatos().values()) {
            info.append(candidato.getNombre()).append(" ").append(candidato.getApellido())
                    .append(" - ").append(candidato.getPartidoPolitico()).append("\n");
        }

        info.append("Periodo Electoral:\n");
        info.append("Desde las 05:00 AM hasta las ").append(procesoElectoral.HORA_CIERRE).append(":00 PM\n");

        info.append("Votantes Registrados:\n");
        for (Votante votante : procesoElectoral.getVotantes().values()) {
            info.append(votante.getNombre()).append(" ").append(votante.getApellido()).append("\n");
        }

        JOptionPane.showMessageDialog(null, info.toString(), "Información del Proceso Electoral", JOptionPane.INFORMATION_MESSAGE);
    }

    private void registrarCandidatura() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del candidato:");
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del candidato:");
        String fechaNacimiento = JOptionPane.showInputDialog("Ingrese la fecha de nacimiento del candidato (DD/MM/AAAA):");
        String id = JOptionPane.showInputDialog("Ingrese el número de identificación (cédula o pasaporte) del candidato:");
        String genero = JOptionPane.showInputDialog("Ingrese el género del candidato (Masculino, Femenino):");
        String telefono = JOptionPane.showInputDialog("Ingrese el número de teléfono del candidato:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo electrónico del candidato:");
        String partidoPolitico = JOptionPane.showInputDialog("Ingrese el nombre del partido político del candidato:");

        Candidato candidato = new Candidato(nombre, apellido, fechaNacimiento, id, genero, telefono, correo, partidoPolitico);

        if (Candidato.validarCandidato(candidato, procesoElectoral.getCandidatos())) {
            procesoElectoral.getCandidatos().put(id, candidato);
            JOptionPane.showMessageDialog(null, "Candidatura registrada con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Error: No se pudo registrar la candidatura. Revise los datos proporcionados.");
        }
    }

    private void registrarVotante() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del votante:");
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido del votante:");
        String id = JOptionPane.showInputDialog("Ingrese el número de identificación (cédula o pasaporte) del votante:");

        if (!procesoElectoral.getVotantes().containsKey(id) && esCedulaValida(id)) {
            Votante votante = new Votante(nombre, apellido, id);
            procesoElectoral.getVotantes().put(id, votante);
            JOptionPane.showMessageDialog(null, "Votante registrado exitosamente.");
        }
    }

    private boolean esCedulaValida(String cedula) {
        return cedula.length() == 10;
    }

    private void emitirVoto() {
        if (procesoElectoral.isProcesoElectoralCerrado()) {
            JOptionPane.showMessageDialog(null, "El proceso electoral está cerrado. No se pueden emitir votos.");
            return;
        }

        String id = JOptionPane.showInputDialog("Número de identificación (cédula o pasaporte):");

        if (!procesoElectoral.getVotantes().containsKey(id)) {
            JOptionPane.showMessageDialog(null, "El votante no está registrado.");
            return;
        }

        Votante votante = procesoElectoral.getVotantes().get(id);

        if (votante.getVoto().isVotoEmitido()) {
            JOptionPane.showMessageDialog(null, "Este votante ya ha ejercido su voto.");
            return;
        }

        StringBuilder opcionesCandidatos = new StringBuilder("Candidatos disponibles:\n");
        int i = 1;
        for (Candidato candidato : procesoElectoral.getCandidatos().values()) {
            opcionesCandidatos.append(i).append(") ").append(candidato.getNombre()).append(" ").append(candidato.getApellido()).append(" - ").append(candidato.getPartidoPolitico()).append("\n");
            i++;
        }

        opcionesCandidatos.append(i).append(") Voto en blanco\n");
        i++;
        opcionesCandidatos.append(i).append(") Voto nulo\n");

        String opcionVoto = JOptionPane.showInputDialog(null, opcionesCandidatos.toString(), "Emitir Voto", JOptionPane.PLAIN_MESSAGE);

        try {
            int numeroOpcion = Integer.parseInt(opcionVoto);

            if (numeroOpcion >= 1 && numeroOpcion <= procesoElectoral.getCandidatos().size()) {
                Candidato candidatoElegido = procesoElectoral.getCandidatos().values().toArray(new Candidato[0])[numeroOpcion - 1];
                candidatoElegido.incrementarVotos();
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(numeroOpcion));
                JOptionPane.showMessageDialog(null, "Voto registrado exitosamente para " + candidatoElegido.getNombre() + " " + candidatoElegido.getApellido());
            } else if (numeroOpcion == i - 1) {
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(i - 1));
                JOptionPane.showMessageDialog(null, "Voto en blanco registrado exitosamente.");
            } else if (numeroOpcion == i) {
                votante.getVoto().setVotoEmitido(true);
                votante.getVoto().setOpcionVoto(String.valueOf(i));
                JOptionPane.showMessageDialog(null, "Voto nulo registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, ingrese una opción válida y vuelva a intentarlo.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, ingrese un número.");
        }
    }

    private void mostrarResultadosEleccion() {
        if (!procesoElectoral.isProcesoElectoralCerrado()) {
            JOptionPane.showMessageDialog(null, "El proceso electoral aún no ha concluido. No hay resultados disponibles.");
            return;
        }

        StringBuilder resultados = new StringBuilder();
        resultados.append("=== Resultados Oficiales de las Elecciones ===\n");

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
        for (Votante votante : procesoElectoral.getVotantes().values()) {
            if (votante.getVoto().isVotoEmitido()) {
                // Voto válido emitido
                if (votante.getVoto().getOpcionVoto().equals(String.valueOf(procesoElectoral.getCandidatos().size() + 1))) {
                    votosBlancos++;
                } else if (votante.getVoto().getOpcionVoto().equals(String.valueOf(procesoElectoral.getCandidatos().size() + 2))) {
                    votosNulos++;
                } else {
                    // Voto válido para un candidato
                    votosValidos++;
                    int numeroOpcion = Integer.parseInt(votante.getVoto().getOpcionVoto());
                    Candidato candidato = procesoElectoral.getCandidatos().values().toArray(new Candidato[0])[numeroOpcion - 1];

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

        // Agregar resultados al cuadro de diálogo
        resultados.append("a) Cantidad de votos en blanco: ").append(votosBlancos).append("\n");
        resultados.append("b) Cantidad de votos nulos: ").append(votosNulos).append("\n");
        resultados.append("c) Cantidad de votos válidos: ").append(votosValidos).append("\n");

        // Agregar votos por cada candidato al cuadro de diálogo
        resultados.append("d) Cantidad de votos obtenidos por cada candidato:\n");
        for (Candidato candidato : votosPorCandidato.keySet()) {
            resultados.append("   ").append(candidato.getNombre()).append(" ").append(candidato.getApellido())
                    .append(": ").append(votosPorCandidato.get(candidato)).append(" votos\n");
        }

        // Agregar candidato más votado al cuadro de diálogo
        resultados.append("e) Candidato más votado: ").append(candidatoMasVotado.getNombre()).append(" ")
                .append(candidatoMasVotado.getApellido()).append("\n");

        // Agregar candidato menos votado al cuadro de diálogo
        resultados.append("f) Candidato menos votado: ").append(candidatoMenosVotado.getNombre()).append(" ")
                .append(candidatoMenosVotado.getApellido()).append("\n");

        // Agregar porcentajes al cuadro de diálogo
        resultados.append("g) Porcentaje de todos los candidatos:\n");
        for (Candidato candidato : votosPorCandidato.keySet()) {
            double porcentajeCandidato = (votosPorCandidato.get(candidato) * 100.0) / votosValidos;
            resultados.append("   ").append(candidato.getNombre()).append(" ").append(candidato.getApellido())
                    .append(": ").append(porcentajeCandidato).append("%\n");
        }

        resultados.append("h) Porcentaje de votos en blanco: ").append(porcentajeVotosBlancos).append("%\n");
        resultados.append("i) Porcentaje de votos nulos: ").append(porcentajeVotosNulos).append("%\n");
        resultados.append("j) Porcentaje de votos válidos: ").append(porcentajeVotosValidos).append("%\n");

        // Mostrar el cuadro de diálogo con los resultados
        JOptionPane.showMessageDialog(null, resultados.toString(), "Resultados de las Elecciones", JOptionPane.INFORMATION_MESSAGE);
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazProcesoElectoral().setVisible(true);
            }
        });
    }
}
