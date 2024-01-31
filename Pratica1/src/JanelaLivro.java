import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JanelaLivro extends ModeloJanela {
    private Livro livro;
    public JanelaLivro(String [] lables, int num_botoes) {
        super("Livros", "Autor:", lables, num_botoes);
    }

    @Override
    public JPanel meio() {
        JPanel panelMeio = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelMeio.add(new JLabel("Ano:                                     "));
        panelMeio.add(Box.createRigidArea(new Dimension(-100, 0)));

        campo3 = new JTextField(4);
        panelMeio.add(campo3);

        panelMeio.add(Box.createRigidArea(new Dimension(170, 0)));

        return panelMeio;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Livro getLivro() {
        return this.livro;
    }

    public void inserir(List<Modelo> lista){
        super.inserir(lista);
        String Autor = campo2.getText();
        String Ano = campo3.getText();
        setLivro(new Livro(Titulo, Autor, Ano));
        lista.add(getLivro());
    }

}