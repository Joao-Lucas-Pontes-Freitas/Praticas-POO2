import javax.swing.*;
import java.awt.*;


public abstract class ModeloJanela {

    protected JFrame tela;
    protected JTextField titulo;
    protected JTextField campo2;
    protected JTextField campo3;
    protected JButton incluir;
    protected JButton botao;
    protected JButton listagem;

    public ModeloJanela(String tipo, String campo, String labelBotao) {
        tela = new JFrame();
        tela.setSize(310, 200);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = tela.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.add(new JLabel(tipo));
        contentPane.add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();

        center.add(new JLabel("Título:"));
        center.add(Box.createRigidArea(new Dimension(50, 0)));
        titulo = new JTextField(15);
        center.add(titulo);

        center.add(new JLabel(campo));
        center.add(Box.createRigidArea(new Dimension(50, 0)));

        campo2 = new JTextField(15);
        center.add(campo2);

        contentPane.add(center, BorderLayout.CENTER);

        center.add(meio());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        incluir = new JButton("Incluir");
        bottom.add(incluir);

        botao = new JButton(labelBotao);
        bottom.add(botao);

        listagem = new JButton("Listagem");
        bottom.add(listagem);

        contentPane.add(bottom, BorderLayout.SOUTH);
    }

    public JPanel meio() {
        return null;
    }

    public void abrir(){
        tela.setVisible(true);
    }

    public void fechar(){
        tela.setVisible(false);
    }

    public void apagar(){
        titulo.setText("");
        campo2.setText("");
        campo3.setText("");
    }

}