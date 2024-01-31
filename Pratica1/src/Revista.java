public class Revista extends Modelo{
    private final String Org;
    private final String Vol;
    private final String Num;
    private final String Ano;

    public Revista(String Titulo, String Ano, String Org, String Vol, String Num){
        super(Titulo);
        this.Org = Org;
        this.Vol = Vol;
        this.Num = Num;
        this.Ano = Ano;
    }

    @Override
    public String toString() {
        return "Revista: " + Titulo + " - " + Org + " - " + Vol + " - " + Num + " - " + Ano;
    }
}
