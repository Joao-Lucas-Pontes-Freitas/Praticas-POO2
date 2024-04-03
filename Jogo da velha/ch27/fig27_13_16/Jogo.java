public class Jogo {
    public int jogador;
    public String tabuleiro;
    public String chave;

    Jogo (int jogador, String tabuleiro, String chave){
        this.jogador = jogador;
        this.tabuleiro = tabuleiro;
        this.chave = chave;
    }

    Jogo (String chave){
        this.chave = chave;
    }
}
