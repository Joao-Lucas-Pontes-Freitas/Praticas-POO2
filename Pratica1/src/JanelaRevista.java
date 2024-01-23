import javax.swing.*;
import java.awt.*;

public class JanelaRevista extends ModeloJanela {

    private Revista revista;

    protected JTextField campo4;
    protected JTextField campo5;
    public JanelaRevista() {
        super("Revistas", "Org.:  ", "Livros");
    }

    @Override
    public JPanel meio() {
        JPanel center = new JPanel(new FlowLayout());

        center.add(new JLabel("Vol.:"));
        center.add(Box.createRigidArea(new Dimension(4, 0)));

        campo3 = new JTextField(3);
        center.add(campo3);
        center.add(Box.createRigidArea(new Dimension(4, 0)));

        center.add(new JLabel("Nro.:"));
        center.add(Box.createRigidArea(new Dimension(4, 0)));

        campo4 = new JTextField(3);
        center.add(campo4);
        center.add(Box.createRigidArea(new Dimension(4, 0)));

        center.add(new JLabel("Ano:"));
        center.add(Box.createRigidArea(new Dimension(4, 0)));

        campo5 = new JTextField(3);
        center.add(campo5);

        return center;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    public Revista getRevista() {
        return this.revista;
    }

    @Override
    public void apagar(){
        super.apagar();
        campo4.setText("");
        campo5.setText("");
    }
}