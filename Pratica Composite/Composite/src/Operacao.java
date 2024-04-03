public class Operacao extends Expressao{
    private final Expressao num1;
    private final Expressao num2;
    private final char operador;

    public Operacao(Expressao num1, Expressao num2, char operador){
        this.num1 = num1;
        this.num2 = num2;
        this.operador = operador;
    }
    public double getResultado(){
        return switch (operador) {
            case '+' -> num1.getResultado() + num2.getResultado();
            case '-' -> num1.getResultado() - num2.getResultado();
            case '*' -> num1.getResultado() * num2.getResultado();
            case '/' -> num1.getResultado() / num2.getResultado();
            default -> 0;
        };
    }
}
