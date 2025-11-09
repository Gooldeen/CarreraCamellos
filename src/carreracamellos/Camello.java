package carreracamellos;

import javax.swing.*;
import java.util.ArrayList;

public class Camello implements Runnable {

    // Atributos
    public int idCaballo;
    private Calle calle;
    private CarreraCamellos carreraCamellos;
    private int posicionX  = 0;

    // Constructores
    public Camello(Calle calle, CarreraCamellos carreraCamellos, int idCaballo) {
        this.calle = calle;
        this.carreraCamellos = carreraCamellos;
        this.idCaballo = idCaballo;
    }

    // Run
    @Override
    public void run() {
        try {
            while (!carreraCamellos.isCarreraTerminada()) { // Se ejecuta el run hasta que termine la carrera
                Thread.sleep(1000); // 1 Segundo de espera para generar número aleatorio
                int pasos = (int)(Math.random() * 4); // Número 0-3
                posicionX += 20 * pasos;
                SwingUtilities.invokeLater(() -> calle.avanzaUnPaso(pasos));
                if (pasos != 0) {
                    Thread.sleep((pasos * 10) - 10);
                }
                if (posicionX >= 600) { // 600 Es la línea de meta
                    synchronized (carreraCamellos) {
                        if (!carreraCamellos.isCarreraTerminada()) {
                            carreraCamellos.setCarreraTerminada(true);
                            carreraCamellos.mostrarGanador(idCaballo);
                        }
                    }
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
