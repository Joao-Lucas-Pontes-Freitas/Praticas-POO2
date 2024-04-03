public class Main {
    public static void main(String[] args) {

        // Letra a)
        Expressao expressao0 = new Numero(0);
        System.out.println("a) Resultado da expressão: 0 = " + expressao0.getResultado());

        // Letra b)
        Expressao expressao1 = new Numero(1);
        Expressao expressao2 = new Numero(2);
        Expressao expressaoB = new Operacao(expressao1, expressao2, '+');
        System.out.println("b) Resultado da expressão: 1 + 2 = " + expressaoB.getResultado());

        // Letra c)
        Expressao expressao3 = new Numero(3);
        Expressao expressaoC1 = new Operacao(expressao2, expressao3, '+');
        Expressao expressaoC = new Operacao(expressao1, expressaoC1, '*');
        System.out.println("c) Resultado da expressão: 1 * (2 + 3) = " + expressaoC.getResultado());

        // Letra d)
        Expressao expressao4 = new Numero(4);
        Expressao expressao5 = new Numero(5);
        Expressao expressaoD1 = new Operacao(expressao5, expressao3, '-');
        Expressao expressaoD2 = new Operacao(expressao4, expressaoD1, '/');
        Expressao expressaoD3 = new Operacao(expressao2, expressao3, '*');
        Expressao expressaoD = new Operacao(expressaoD2, expressaoD3, '+');
        System.out.println("d) Resultado da expressão: (4 - 5) / 2 + 3 * 2 = " + expressaoD.getResultado());
    }
}