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
            adicionarItem(janela, lista);
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

    private static void adicionarItem(ModeloJanela janela, ArrayList<Modelo> lista) {

        String Titulo = janela.titulo.getText();
        String Ano;

        if (janela instanceof JanelaLivro livro) {
            String Autor = livro.campo2.getText();
            Ano = livro.campo3.getText();

            livro.setLivro(new Livro(Titulo, Autor, Ano));
            lista.add(((JanelaLivro) janela).getLivro());
        }
        else if (janela instanceof JanelaRevista revista) {
            String Org = revista.campo2.getText();
            String Vol = revista.campo3.getText();
            String Nro = revista.campo4.getText();
            Ano = revista.campo5.getText();

            revista.setRevista(new Revista(Titulo, Ano, Org, Vol, Nro));
            lista.add(revista.getRevista());
        }
    }
}
