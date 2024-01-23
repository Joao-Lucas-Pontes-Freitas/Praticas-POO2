public class Revista extends Modelo{
    private final String Org;
    private final String Vol;
    private final String Num;

    public Revista(String Titulo, String Ano, String Org, String Vol, String Num){
        super(Titulo, Ano);
        this.Org = Org;
        this.Vol = Vol;
        this.Num = Num;
    }

    @Override
    public String toString() {
        return "Revista: " + Titulo + " - " + Org + " - " + Vol + " - " + Num + " - " + Ano;
    }
}
