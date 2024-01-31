import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        ArrayList<Modelo> lista = new ArrayList<>();
        Listagem telaListagem = new Listagem();
        JanelaLivro livros = new JanelaLivro(new String[]{"Revistas", "Videos"}, 2);
        JanelaRevista revistas = new JanelaRevista(new String[]{"Livros", "Videos"}, 2);
        JanelaVideo videos = new JanelaVideo(new String[]{"Livros", "Revistas"}, 2);
        configurarJanela(livros, lista, telaListagem, revistas, videos);
        configurarJanela(revistas, lista, telaListagem, livros, videos);
        configurarJanela(videos, lista, telaListagem, livros, revistas);
        livros.abrir();
    }
    private static void configurarJanela(ModeloJanela janela, ArrayList<Modelo> lista, Listagem telaListagem, ModeloJanela... outrasJanelas) {
        janela.incluir.addActionListener(e -> {
            janela.inserir(lista);
            janela.apagar();
        });
        for (int i = 0; i < janela.getNum_botoes(); i++) {
            int finalI = i;
            janela.botoes[i].addActionListener(e -> {
                janela.apagar();
                janela.fechar();
                outrasJanelas[finalI].abrir();
            });
        }
        janela.listagem.addActionListener(e -> {
            telaListagem.mostrarLista(lista);
            telaListagem.abrir();
        });
    }
}