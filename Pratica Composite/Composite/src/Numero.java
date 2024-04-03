public class Numero extends Expressao {
    private final double valor;

    public Numero(double valor) {
        this.valor = valor;
    }

    public double getResultado() {
        return valor;
    }
}

