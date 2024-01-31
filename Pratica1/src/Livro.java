public class Livro extends Modelo{
    private final String Autor;
    private final String Ano;
    public Livro(String Titulo, String Autor, String Ano) {
        super(Titulo);
        this.Autor = Autor;
        this.Ano = Ano;
    }
    public String toString() {
        return "Livro: " + Titulo + " - " + Autor + " - " + Ano;
    }
}
