package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.io.Serializable;

import java.awt.Font;

import edu.princeton.cs.introcs.StdDraw;

public class Game implements Serializable{

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private static final int EntryX = 40;
    private static final int EntryY = 5;

    private static final String North = "w";
    private static final String East = "d";
    private static final String South = "s";
    private static final String West = "a";

    private static final String Path = "./saved.txt";
    private static final int WelcomeWidth = 600;
    private static final int WelcomeHeight = 800;

    private boolean setup = true;
    private boolean newGame = false;
    private boolean quit = false;

    private String seedString = "";
    private Long seed;
    private Random random;

    TERenderer ter = new TERenderer();
    private TETile[][] world;
    private int playerX;
    private int playerY;

    private void switchSetupMode() {
        setup = !setup;
    }

    private void switchNewGameMode() {
        newGame = !newGame;
    }

    private void switchQuitMode() {
        quit = !quit;
    }

    /* Processes game according to a given single input String */
    private void processInputString(String first) {
        if (setup) {
            switch (first) {
                case "n":
                    switchNewGameMode();
                    break;
                case "s":
                    setupNewGame();
                    break;
                case "l":
                    load();
                    break;
                case "q":
                    System.exit(0);
                    break;
                default:
                    try {
                        Long.parseLong(first);
                        seedString += first;
                    } catch (NumberFormatException e) { // exit program if input is invalid
                        System.out.println("Invalid input given" + first);
                        System.exit(0);
                    }
                    break;
            }
        } else { // when the setup has been done
            switch (first) {
                // add my keyboard preference
                case North:
                case "o":
                case East:
                case "l":
                case South:
                case "n":
                case West:
                case "k":
                    move(first);
                    break;
                case ":":
                    switchQuitMode();
                    break;
                case "q":
                    saveAndQuit();
                    System.exit(0);
                    break;
                default:
            }
        }
    }

    // generate a random world and put a player in it
    private void setupNewGame() {
        // check if the input is valid
        if (!newGame) {
            String error = "Input string " + "\"S\" given, but no game has been initialized.\n" + "Please initialize game first by input string \"N\" and following random seed"
                    + "numbers";
            System.out.println(error);
            System.exit(0);
        }

        switchNewGameMode();

        // set up a random seed and generate a world according to it
        WorldGenerator wg;
        if (seedString.equals("")) {
            wg = new WorldGenerator(WIDTH, HEIGHT, EntryX, EntryY);
        } else {
            seed = Long.parseLong(seedString);
            wg = new WorldGenerator(WIDTH, HEIGHT, EntryX, EntryY, seed);
        }


        world = wg.generate();

        // setup a player
        int i = WIDTH - 1;
        int j = HEIGHT - 1;

        while (i > 0 && j > 0) {
            if (world[i][j] == Tileset.FLOOR) {
                world[i][j] = Tileset.PLAYER;
                playerX = i;
                playerY = j;
                break;
            }
            i--;
            j--;
        }


        switchSetupMode();
    }

    // load a previous game
    private TETile[][] load() {
        File f = new File(Path);
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            world = (TETile[][]) ois.readObject();
            ois.close();
            return world;
        } catch (FileNotFoundException e) {
            System.out.println("No previously saved world found.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("Class TETile[][] not found.");
            System.exit(0);
        }

        // switch off setupMode
        switchSetupMode();

        // rewrite playerX, playerY
        rewritePlayerLocation();
        return null;
    }

    // rewrite playerX,playerY from saved game
    private void rewritePlayerLocation() {
        for (int w = 0; w < WIDTH; w++) {
            for (int h = 0; h < HEIGHT; h++) {
                if (world[w][h].equals(Tileset.PLAYER)) {
                    playerX = w;
                    playerY = h;
                }
            }
        }
    }

    // move a player according to input string
    private void move(String input) {
        switch (input) {
            case North:
            case "o":
                if (world[playerX][playerY + 1].equals(Tileset.FLOOR)) {
                    world[playerX][playerY + 1] = Tileset.PLAYER;
                    world[playerX][playerY] = Tileset.FLOOR;
                    playerY = playerY + 1;
                }
                return;
            case East:
            case "l":
                if (world[playerX + 1][playerY].equals(Tileset.FLOOR)) {
                    world[playerX + 1][playerY] = Tileset.PLAYER;
                    world[playerX][playerY] = Tileset.FLOOR;
                    playerX = playerX + 1;
                }
                return;
            case South:
            case "n":
                if (world[playerX][playerY - 1].equals(Tileset.FLOOR)) {
                    world[playerX][playerY - 1] = Tileset.PLAYER;
                    world[playerX][playerY] = Tileset.FLOOR;
                    playerY -= 1;
                }
                return;
            case West:
            case "k":
                if (world[playerX - 1][playerY].equals(Tileset.FLOOR)) {
                    world[playerX - 1][playerY] = Tileset.PLAYER;
                    world[playerX][playerY] = Tileset.FLOOR;
                    playerX -= 1;
                }
                return;
            default:
        }
    }

    // quit and save a current game
    private void saveAndQuit() {
        // ignore if quit flag : hasn't been inputted in advance
        switchQuitMode();

        File f = new File(Path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(world);
            oos.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }



    /* Processes game recursively according to a given input Strings */
    private void processInput(String input) {
        if (input == null) {
            System.out.println("No input given.");
            System.exit(0);
        }

        String first = Character.toString(input.charAt(0));
        first = first.toLowerCase();
        processInputString(first);

        if (input.length() > 1) {
            String rest = input.substring(1);
            processInput(rest);
        }
    }

    // process keyboard inputs in setup mode
    private void processWelcome() {
        // prepare welcome board window
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(WelcomeWidth, WelcomeHeight);
        StdDraw.clear(StdDraw.BLACK);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String typed = Character.toString(StdDraw.nextKeyTyped());
                processInput(typed);
            }

            renderWelcomeBoard();

            if (!setup) { // break after setup has been done and enter game mode
                break;
            }
        }
        processGame();
    }
    // render welcome board in setup mode
    private void renderWelcomeBoard() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);

        // title
        StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
        StdDraw.text(0.5, 0.8, "CS61B: BYoG");

        // menu
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.text(0.5, 0.5, "New Game: N");
        StdDraw.text(0.5, 0.475, "Load Game: L");
        StdDraw.text(0.5, 0.45, "Quit: Q");

        // seed
        if (newGame) {
            StdDraw.text(0.5, 0.25, "Seed: " + seedString);
            StdDraw.text(0.5, 0.225, "(Press S to start the game)");
        }

        StdDraw.show();
        StdDraw.pause(100);
    }

    // process keyboard inputs in game mode
    private void processGame() {
        ter.initialize(WIDTH, HEIGHT);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String typed = Character.toString(StdDraw.nextKeyTyped());
                processInput(typed);
            }

            renderGame();
        }
    }

    // render the current state of the game
    private void renderGame() {
        renderWorld();
        showTileOnHover();
        StdDraw.pause(10);
    }

    private void renderWorld() {
        StdDraw.setFont();
        StdDraw.setPenColor();
        ter.renderFrame(world);
    }

    // Draws text describing the Tile currently under the mouse pointer
    private void showTileOnHover() {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();
        TETile mouseTile = world[mouseX][mouseY];

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(1, HEIGHT - 1, mouseTile.description());
        StdDraw.show();
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        processWelcome();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        processInput(input);
        return world;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playWithKeyboard();
    }
}
