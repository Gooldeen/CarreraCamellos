package carreracamellos;

import javax.swing.*;

public class Calle extends JPanel  {


    private final static ImageIcon camelIcon = new ImageIcon("./src/resources/camel.png");

    private JLabel camelLabel;

    public Calle() {
        super(null);
        camelLabel= new JLabel();
        camelLabel.setIcon(camelIcon);
        camelLabel.setBounds(0,0,100,100);
        this.add(camelLabel);
    }


    public void avanzaUnPaso(int pasos) {
        System.out.println("avanzaUnPaso");
        int new_x_position = camelLabel.getBounds().x+(20*pasos);
        System.out.println("avanzaUnPaso:"+new_x_position);
        camelLabel.setBounds(new_x_position,0,100,100);
    }

    public void reset() {
        camelLabel.setBounds(0,0,100,100);
    }

}
