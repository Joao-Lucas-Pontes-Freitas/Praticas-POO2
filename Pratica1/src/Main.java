import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Modelo> lista = new ArrayList<>();
        Listagem telaListagem = new Listagem();
        JanelaLivro livros = new JanelaLivro();
        JanelaRevista revistas = new JanelaRevista();

        configurarJanela(livros, lista, telaListagem, revistas);
        configurarJanela(revistas, lista, telaListagem, livros);

        livros.abrir();
    }

    private static void configurarJanela(ModeloJanela janela, ArrayList<Modelo> lista, Listagem telaListagem, ModeloJanela outraJanela) {

        janela.incluir.addActionListener(e -> {
            janela.inserir(lista);
            janela.apagar();
        });

        janela.botao.addActionListener(e -> {
            janela.apagar();
            janela.fechar();
            outraJanela.abrir();
        });

        janela.listagem.addActionListener(e -> {
            telaListagem.mostrarLista(lista);
            telaListagem.abrir();
        });
    }
}
