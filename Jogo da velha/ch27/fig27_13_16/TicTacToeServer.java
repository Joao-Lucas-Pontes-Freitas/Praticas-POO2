// Fig. 27.13: TicTacToeServer.java
// Server side of client/server Tic-Tac-Toe program.

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TicTacToeServer extends JFrame {
    private final String[] board = new String[9]; // tic-tac-toe board
    private final JTextArea outputArea; // for outputting moves
    private final Player[] players; // array of Players
    private ServerSocket server; // server socket to connect with clients
    private int currentPlayer; // keeps track of player with current move
    private int winnerPlayer = -2;
    private final static int PLAYER_X = 0; // constant for first player
    private final static int PLAYER_O = 1; // constant for second player
    private final static String[] MARKS = {"X", "O"}; // array of marks
    private final ExecutorService runGame; // will run players
    private final Lock gameLock; // to lock game for synchronization
    private boolean novoTabuleiro = false;
    private final Condition otherPlayerConnected; // to wait for other player
    private final Condition otherPlayerTurn; // to wait for other player's turn
    private String chaveJogo;
    private String novoBoard;

    // set up tic-tac-toe server and GUI that displays messages
    public TicTacToeServer() {
        super("Tic-Tac-Toe Server"); // set title of window
        runGame = Executors.newFixedThreadPool(2);
        gameLock = new ReentrantLock(); // create lock for game
        otherPlayerConnected = gameLock.newCondition();
        otherPlayerTurn = gameLock.newCondition();
        criarTabuleiro();
        players = new Player[2]; // create array of players
        currentPlayer = PLAYER_X; // set current player to first player
        try {
            server = new ServerSocket(12345, 2); // set up ServerSocket
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        } // end catch
        outputArea = new JTextArea(); // create JTextArea for output
        add(outputArea, BorderLayout.CENTER);
        outputArea.setText("Server awaiting connections\n");
        setSize(300, 300); // set size of window
        setVisible(true); // show window
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                salvarJogo();
            }

        });
    }
    public void salvarJogo() {
        Jogo jogo = new Jogo(currentPlayer, String.join("", board), chaveJogo);
        for (String c : board)
            System.out.println("String: " + c);
        System.out.println(String.join("", board));
        if (novoTabuleiro)
            new DBUpdating().launch(jogo);
        else
            new DBPopulation().launch(jogo);
    }

    // wait for two connections so game can be played
    public void execute() {
        // wait for each client to connect
        for (int i = 0; i < players.length; i++) {
            try // wait for connection, create Player, start runnable
            {
                players[i] = new Player(server.accept(), i);
                runGame.execute(players[i]); // execute player runnable
            } // end try
            catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            } // end catch
        } // end for

        gameLock.lock(); // lock game to signal player X's thread

        try {
            players[PLAYER_X].setSuspended(false); // resume player X
            otherPlayerConnected.signal(); // wake up player X's thread

        } // end try
        finally {
            gameLock.unlock(); // unlock game after signalling player X
        } // end finally
    } // end method execute

    public void criarTabuleiro() {
        for (int i = 0; i < 9; i++)
            board[i] = "";
    }

    public Jogo carregarJogo(String chave) {
        Jogo jogo = new DBListing().launch(chave);
        if (jogo != null) {
            String[] tabuleiro = jogo.tabuleiro.split("");
            System.arraycopy(tabuleiro, 0, board, 0, 9);
            currentPlayer = jogo.jogador;
            novoTabuleiro = true;
            return jogo;
        } else {
            criarTabuleiro();
            currentPlayer = PLAYER_X;
            return null;
        }
    }


    // display message in outputArea
    private void displayMessage(final String messageToDisplay) {
        // display message from event-dispatch thread of execution
        // updates outputArea
// end  method run
        SwingUtilities.invokeLater(
                () -> {
                    outputArea.append(messageToDisplay); // add message
                } // end inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

    // determine if move is valid
    public boolean validateAndMove(int location, int player) {
        // while not current player, must wait for turn
        while (player != currentPlayer) {
            gameLock.lock(); // lock game to wait for other player to go

            try {
                otherPlayerTurn.await(); // wait for player's turn
            } // end try
            catch (InterruptedException exception) {
                exception.printStackTrace();
            } // end catch
            finally {
                gameLock.unlock(); // unlock game after waiting
            } // end finally
        } // end while

        // if location not occupied, make move
        if (!isOccupied(location)) {
            board[location] = MARKS[currentPlayer]; // set move on board
            currentPlayer = (currentPlayer + 1) % 2; // change player

            isGameOver();

            // let new current player know that move occurred
            players[currentPlayer].otherPlayerMoved(location);

            gameLock.lock(); // lock game to signal other player to go

            try {
                otherPlayerTurn.signal(); // signal other player to continue
            } // end try
            finally {
                gameLock.unlock(); // unlock game after signaling
            } // end finally

            return true; // notify player that move was valid
        } // end if
        else // move was not valid
            return false; // notify player that move was invalid
    } // end method validateAndMove

    // determine whether location is occupied
    public boolean isOccupied(int location) {
        // location is not occupied
        return board[location].equals(MARKS[PLAYER_X]) ||
                board[location].equals(MARKS[PLAYER_O]); // location is occupied
    } // end method isOccupied

    // place code in this method to determine whether game over
    public boolean isGameOver() {
        // Verificar linhas
        for (int i = 0; i < 9; i += 3)
            if (board[i] != null && board[i].equals(board[i + 1]) && board[i].equals(board[i + 2]) && verificaBoard(i)) {
                if (board[i].equals("X"))
                    winnerPlayer = 0;
                else
                    winnerPlayer = 1;
                return true;
            }

        // Verificar colunas
        for (int i = 0; i < 3; i++)
            if (board[i] != null && board[i].equals(board[i + 3]) && board[i].equals(board[i + 6]) && verificaBoard(i)) {
                if (board[i].equals("X"))
                    winnerPlayer = 0;
                else
                    winnerPlayer = 1;
                return true;
            }

        // Verificar diagonais
        if (board[0] != null && board[0].equals(board[4]) && board[0].equals(board[8]) && verificaBoard(0)) {
            if (board[0].equals("X"))
                winnerPlayer = 0;
            else
                winnerPlayer = 1;
            return true;
        }
        if (board[2] != null && board[2].equals(board[4]) && board[2].equals(board[6]) && verificaBoard(2)) {
            if (board[2].equals("X"))
                winnerPlayer = 0;
            else
                winnerPlayer = 1;
            return true;
        }

        // Verificar se o tabuleiro está cheio (empate)
        int c = 0;
        for (int i = 0; i < 9; i++) {
            assert board[i] != null;
            if (verificaBoard(i))
                c++; // contador de espaços utilizados
        }

        if (c == 9) {
            winnerPlayer = -1; // Empate
            return true;
        } else
            return false;

    } // end method isGameOver

    private boolean verificaBoard(int i) {
        return board[i].equals("X") || board[i].equals("O");
    }

    // private inner class Player manages each Player as a runnable
    private class Player implements Runnable {
        private final Socket connection; // connection to client
        private Scanner input; // input from client
        private Formatter output; // output to client
        private final int playerNumber; // tracks which player this is
        private final String mark; // mark for this player
        private boolean suspended = true; // whether thread is suspended

        // set up Player thread
        public Player(Socket socket, int number) {
            System.out.println("Entrou no Player");
            playerNumber = number; // store this player's number
            mark = MARKS[playerNumber]; // specify player's mark
            connection = socket; // store socket for client

            try // obtain streams from Socket
            {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } // end try
            catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            } // end catch
        } // end Player constructor

        // send message that other player moved
        public void otherPlayerMoved(int location) {
            // notify player
            output.format("Opponent moved\n"); // notify player
            output.format(location + " " + winnerPlayer + '\n');
            output.flush(); // flush output

        }

        public void GameOver() {
            displayMessage("\n");
            if (winnerPlayer == 0) {
                output.format("Game Over! X Wins!\n"); // notify player
                displayMessage("Game Over! X Wins!\n");
            } else if (winnerPlayer == 1) {
                output.format("Game Over! O Wins!\n"); // notify player
                displayMessage("Game Over! O Wins!\n");
            } else if (winnerPlayer == -1) {
                output.format("Game Over! Draw!\n");
                displayMessage("Game Over! O Wins!\n");
            }
            output.flush();
        }


        // control thread's execution
        public void run() {
            Jogo jogo;
            // send client its mark (X or O), process messages from client
            try {
                displayMessage("Player " + mark + " connected\n");
                output.format("%s\n", mark); // send player's mark
                output.flush(); // flush output

                // if player X, wait for another player to arrive
                if (playerNumber == PLAYER_X) {
                    output.format("%s\n%s", "Player X connected",
                            "Waiting for another player\n");
                    output.flush(); // flush output

                    gameLock.lock(); // lock game to  wait for second player

                    String chave = input.nextLine();
                    chaveJogo = chave;
                    jogo = carregarJogo(chave);
                    if (jogo != null) {
                        novoBoard = jogo.tabuleiro;
                    }


                    if (novoTabuleiro) {
                        output.format("New Board\n");
                        assert jogo != null;
                        output.format(jogo.tabuleiro);
                        output.flush();
                    }

                    try {
                        while (suspended) {
                            otherPlayerConnected.await(); // wait for player O
                        } // end while
                    } // end try
                    catch (InterruptedException exception) {
                        exception.printStackTrace();
                    } // end catch
                    finally {
                        gameLock.unlock(); // unlock game after second player
                    } // end finally

                    // send message that other player connected
                    output.format("Other player connected. Your move.\n");
                    output.flush(); // flush output
                } // end if
                else {

                    output.format("Player O connected, please wait\n");
                    output.flush(); // flush output

                    if (novoTabuleiro) {
                        output.format("New Board\n");
                        output.format("%s\n", novoBoard);
                        output.flush();
                    }

                } // end else

                // while game not over
                while (!isGameOver()) {
                    int location = 0; // initialize move location
                    if (input.hasNext())
                        location = input.nextInt(); // get move location
                    // check for valid move
                    if (validateAndMove(location, playerNumber)) {
                        displayMessage("\nlocation: " + location);
                        output.format("Valid move.\n"); // notify client
                        output.flush(); // flush output
                    } // end if
                    else // move was invalid
                    {
                        output.format("Invalid move, try again\n");
                        output.flush(); // flush output
                    } // end else
                } // end while

                GameOver();
            } // end try
            finally {
                try {
                    connection.close(); // close connection to client
                } // end try
                catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(1);
                } // end catch

//                for (Player player : players) {
//                    try {
//                        player.connection.close(); // Fechar conexão
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

            } // end finally
        } // end method run

        // set whether or not thread is suspended
        public void setSuspended(boolean status) {
            suspended = status; // set value of suspended
        } // end method setSuspended

    } // end class Player
} // end class TicTacToeServer

