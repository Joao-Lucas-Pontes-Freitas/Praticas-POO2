public class Livro extends Modelo{
    private final String Autor;
    public Livro(String Titulo, String Autor, String Ano) {
        super(Titulo, Ano);
        this.Autor = Autor;
    }

    @Override
    public String toString() {
        return "Livro: " + Titulo + " - " + Autor + " - " + Ano;
    }
}
