import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Listagem {
    private final JFrame tela;
    private final JPanel center;
    
    public Listagem() {
        tela = new JFrame();
        tela.setSize(340, 200);

        Container contentPane = tela.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JLabel titulo = new JLabel("Listagem");
        top.add(titulo);
        contentPane.add(top, BorderLayout.NORTH);

        center = new JPanel();
        contentPane.add(center, BorderLayout.CENTER);

    }

    public void mostrarLista(List<Modelo> lista){
        center.removeAll();
        StringBuilder l = new StringBuilder();
        for (Modelo o : lista) {
            l.append(o.toString()).append("\n");
        }
        JTextArea texto = new JTextArea(l.toString());
        center.add(texto);
    }

    public void abrir(){
        tela.setVisible(true);
    }

    public void fechar(){
        tela.setVisible(false);
    }

}


