import javax.swing.*;
import java.awt.*;
import java.util.List;
public class JanelaVideo extends ModeloJanela {
    private Video Video;
    public JanelaVideo(String [] lables, int num_botoes) {
        super("Videos", "Autor:", lables, num_botoes);
    }
    @Override
    public JPanel meio() {
        JPanel panelMeio = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelMeio.add(new JLabel("Duração:                                                    "));
        panelMeio.add(Box.createRigidArea(new Dimension(-140, 0)));

        campo3 = new JTextField(4);
        panelMeio.add(campo3);

        panelMeio.add(Box.createRigidArea(new Dimension(170, 0)));

        return panelMeio;
    }
    public void setVideo(Video Video) {
        this.Video = Video;
    }
    public Video getVideo() {
        return this.Video;
    }
    public void inserir(List<Modelo> lista){
        super.inserir(lista);
        String Autor = campo2.getText();
        String Duracao = campo3.getText();
        setVideo(new Video(Titulo, Autor, Duracao));
        lista.add(getVideo());
    }

}