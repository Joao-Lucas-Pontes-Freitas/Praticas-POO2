import javax.swing.*;
import java.awt.*;
import java.util.List;


public abstract class ModeloJanela {
    protected String Titulo;
    protected JFrame tela;
    protected JTextField titulo;
    protected JTextField campo2;
    protected JTextField campo3;
    protected JButton incluir;
    protected JButton [] botoes;
    protected int num_botoes;
    protected JButton listagem;
    public ModeloJanela(String tipo, String campo, String[] lables, int num_botoes) {
        this.num_botoes = num_botoes;
        botoes = new JButton[num_botoes];
        tela = new JFrame();
        tela.setSize(410, 200);
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
        center.add(Box.createRigidArea(new Dimension(100, 0)));
        center.add(new JLabel(campo));
        center.add(Box.createRigidArea(new Dimension(50, 0)));
        campo2 = new JTextField(15);
        center.add(campo2);
        center.add(Box.createRigidArea(new Dimension(100, 0)));
        contentPane.add(center, BorderLayout.CENTER);
        center.add(meio());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        incluir = new JButton("Incluir");
        bottom.add(incluir);
        for (int i = 0; i < num_botoes; i++) {
            botoes[i] = new JButton(lables[i]);
            bottom.add(botoes[i]);
        }
        listagem = new JButton("Listagem");
        bottom.add(listagem);
        contentPane.add(bottom, BorderLayout.SOUTH);
    }

    public abstract JPanel meio();

    public void inserir(List<Modelo> lista){
        Titulo = titulo.getText();
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

    public int getNum_botoes() {
        return num_botoes;
    }
}