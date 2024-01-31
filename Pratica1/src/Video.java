public class Video extends Modelo{
    private final String Autor;
    private final String Duracao;
    public Video(String Titulo, String Autor, String Duracao) {
        super(Titulo);
        this.Autor = Autor;
        this.Duracao = Duracao;
    }
    public String toString() {
        return "Video: " + Titulo + " - " + Autor + " - " + Duracao;
    }
}

