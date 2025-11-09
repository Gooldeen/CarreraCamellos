package carreracamellos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CarreraCamellos extends JFrame {

    public String[] listaColores = {"Verde", "Rojo", "Amarillo", "Celeste", "Azul", "Magenta", "Naranja", "Rosa"};
    private boolean carreraTerminada =false;
    public ArrayList<Thread> hilosCamellos;
    private static int number=4; //número de camellos //maximo 8 para que no se vea mal

    private static final java.awt.Color COLORS[]= {Color.GREEN, Color.RED, Color.YELLOW,
            Color.CYAN, Color.BLUE, Color.MAGENTA,
            Color.ORANGE, Color.PINK};
    private static final int H_SIZE=800;
    private static final int LINE_V_GAP=20;
    private static final int LINE_H_SIZE=H_SIZE;
    private static final int LINE_V_SIZE=60;
    private static final int MESSAGE_V_SIZE=25;
    private static final int BUTTON_V_SIZE=25;
    private static final int BUTTON_H_SIZE=120;
    private static final int FINISH_LINE_X=600;
    private static final int V_SIZE=number*(LINE_V_GAP+LINE_V_SIZE)+LINE_V_SIZE+MESSAGE_V_SIZE+BUTTON_V_SIZE;
    private final static ImageIcon camelIcon = new ImageIcon("./src/camellos/camel.png");

    private JPanel bgPanel;
    private JButton buttonRun;
    private ArrayList<Calle> calles;
    private JPanel finishLine;
    JLabel labelWinner = new JLabel();

    public CarreraCamellos() {
        createUIComponents();
        buttonRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerHilos();  // Detén hilos anteriores
                for(Calle calle : calles){
                    calle.reset();
                }
                setCarreraTerminada(false);
                //ejemplo de como usar el movimiento...
                inicializarCarrera();
                for (Thread t : hilosCamellos) {
                    t.start();
                }

            }
        });
    }

    private void detenerHilos() {
        if (hilosCamellos != null) {
            for (Thread hilo : hilosCamellos) {
                if (hilo.isAlive()) {
                    hilo.interrupt();
                }
            }
        }
    }

    private void inicializarCarrera() {
        hilosCamellos = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Camello camello = new Camello(calles.get(i), this, i);
            Thread t = new Thread(camello);
            hilosCamellos.add(t);
        }
    }

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CarreraCamellos().setVisible(true);
            }
        });
    }

    public boolean isCarreraTerminada () {
        return carreraTerminada;
    }

    public void setCarreraTerminada(boolean carreraTerminada) {
        this.carreraTerminada=carreraTerminada;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        bgPanel= new JPanel(null);//null=absoluteLayout,usar coordenadas con pixeles
        setContentPane(bgPanel);
        setTitle("Carrera de Camellos");
        setSize(H_SIZE, V_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // lo necesitamos para establecer la distancia de las calles
        int CALLE_INIT_X=0;
        int CALLE_INIT_Y=0;
        int CALLE_END_X=0;
        int CALLE_END_Y=0;
        int WINNER_Y=0;

        calles= new ArrayList<Calle>();
        for (int i = 0; i < number; i++) {
            Calle calle = new Calle();
            calle.setBackground(COLORS[i]);
            CALLE_INIT_X=0;
            CALLE_INIT_Y=(i*LINE_V_SIZE)+(i+1)*LINE_V_GAP;
            CALLE_END_X=LINE_H_SIZE;
            CALLE_END_Y=LINE_V_SIZE;

            calle.setBounds(CALLE_INIT_X,CALLE_INIT_Y,CALLE_END_X,CALLE_END_Y);
            bgPanel.add(calle);
            calles.add(calle);
        }

        finishLine= new JPanel();
        finishLine.setLayout(null);
        finishLine.setBackground(Color.BLACK);
        finishLine.setBounds(FINISH_LINE_X,LINE_V_GAP,5,CALLE_INIT_Y+LINE_V_SIZE-LINE_V_GAP);
        bgPanel.add(finishLine);
        bgPanel.setComponentZOrder(finishLine, 0);
        for (Calle calle : calles) {
            bgPanel.setComponentZOrder(calle, 1);
        }

        buttonRun = new JButton("Run");

        labelWinner.setText("El Ganador es el Jugador: "); // nombre del ganador
        labelWinner.setFont(new Font("Arial", Font.BOLD, 20));
        labelWinner.setForeground(Color.BLACK);
        labelWinner.setHorizontalAlignment(JLabel.CENTER);
        WINNER_Y=(number*(LINE_V_SIZE+LINE_V_GAP));
        labelWinner.setBounds(0, WINNER_Y,H_SIZE,MESSAGE_V_SIZE);
        labelWinner.setVisible(true);
        bgPanel.add(labelWinner);
        int BUTTON_Y=WINNER_Y+LINE_V_GAP + MESSAGE_V_SIZE;
        buttonRun.setBounds((H_SIZE-BUTTON_H_SIZE)/2, BUTTON_Y,BUTTON_H_SIZE,BUTTON_V_SIZE);
        buttonRun.setText("Run");
        buttonRun.setVisible(true);
        bgPanel.add(buttonRun);

   }

   public void mostrarGanador(int idCamello) {
        labelWinner.setText("¡El ganador es el jugador: " + listaColores[idCamello] + "!");
   }

}
