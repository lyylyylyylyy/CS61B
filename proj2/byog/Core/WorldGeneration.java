package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;

class WorldGeneration {
    private static final int MAXweight = 6;
    private static final int MAXheight = 6;
    private static final String East = "E";
    private static final String West = "W";
    private static final String South = "S";
    private static final String North = "N";

    private int width;
    private int height;
    private Position initialP;
    private Random random;
    private TETile[][] world;


    // play with string
    WorldGeneration(int w, int h, int xX, int yY) {
        width = w;
        height = h;
        initialP = new Position(xX, yY);
        random = new Random();
    }

    // play with keyboard
    WorldGeneration(int w, int h, int xX, int yY, long seed) {
        width = w;
        height = h;
        initialP = new Position(xX, yY);
        random = new Random(seed);
    }

    private class Position {
        int x;
        int y;

        Position(int X, int Y) {
            x = X;
            y = Y;
        }
    }

    private void Initialize() {

        world = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    // generate room with room and wall
    private void gRoom(Position leftBottom, Position rightUpper) {
        int leftBottomX = leftBottom.x;
        int leftBottomY = leftBottom.y;
        int rightUpperX = rightUpper.x;
        int rightUpperY = rightUpper.y;

        for (int a = leftBottomX; a <= rightUpperX; a++) {
            for (int b = leftBottomY; b <= rightUpperY; b++) {
                if (a == leftBottomX || a == rightUpperX || b == leftBottomY || b == rightUpperY) {
                    world[a][b] = Tileset.WALL;
                } else {
                    world[a][b] = Tileset.FLOOR;
                }
            }
        }
    }

    // locked door and entrance
    private void initialEntrance(Position initialEntrance) {
        world[initialEntrance.x][initialEntrance.y] = Tileset.LOCKED_DOOR;
    }

    // set entrance
    private void setEntrance(Position entrance) {
        world[entrance.x][entrance.y] = Tileset.FLOOR;
    }

    // exit
    private void setExit(Position exit) {
        world[exit.x][exit.y] = Tileset.FLOOR;
    }

    // check if there is space to add a room
    private boolean check(Position leftBottom, Position rightUpper) {
        int leftBottomX = leftBottom.x;
        int leftBottomY = leftBottom.y;
        int rightUpperX = rightUpper.x;
        int rightUpperY = rightUpper.y;

        if (leftBottomX < 0 || width <= leftBottomX || leftBottomY < 0 || height < leftBottomY || rightUpperX < 0 || width <= rightUpperX || rightUpperY < 0 || height <= rightUpperY) {
            return false;
        }



        for (int x = leftBottomX; x <= rightUpperX; x++) {
            for (int y = leftBottomY; y <= rightUpperY; y++) {
                TETile currentTile = world[x][y];
                if (currentTile == Tileset.WALL || currentTile == Tileset.FLOOR) {
                    return false;
                }
            }
        }
        return true;
    }

    // construct a new rom and return its positions
    private Position[] randomPositionNorth(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int leftBottomX = entryPositionX - random.nextInt(w) - 1;
        int leftBottomY = entryPositionY;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!check(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    /* Returns random positions for a new room on the east of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionEast(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int leftBottomX = entryPositionX;
        int leftBottomY = entryPositionY - random.nextInt(h) - 1;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!check(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    /* Returns random positions for a new room on the south of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionSouth(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int rightUpperX = entryPositionX + random.nextInt(w) + 1;
        int rightUpperY = entryPositionY;
        int leftBottomX = rightUpperX - w - 1;
        int leftBottomY = rightUpperY - h - 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!check(leftBottom, rightUpper)) {
            return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    /* Returns random positions for a new room on the west of entryPosition if available,
    otherwise returns null */
    private Position[] randomPositionWest(int w, int h, Position entryPosition) {
        int entryPositionX = entryPosition.x;
        int entryPositionY = entryPosition.y;
        int rightUpperX = entryPositionX;
        int rightUpperY = entryPositionY + random.nextInt(h) + 1;
        int leftBottomX = rightUpperX - w - 1;
        int leftBottomY = rightUpperY - h - 1;
        Position leftBottom = new Position(leftBottomX, leftBottomY);
        Position rightUpper = new Position(rightUpperX, rightUpperY);
        if (!check(leftBottom, rightUpper)) {
                return null;
        } else {
            return new Position[]{leftBottom, rightUpper};
        }
    }

    // reverse direction
    private String reverseDirection(String direction) {
        switch (direction) {
            case North:
                return South;
            case East:
                return West;
            case South:
                return North;
            default:
                return East;
        }
    }

    // random exit
    private Object[] randomExit(Position leftB, Position rightU, String CurrentDirection) {
        int leftBottomX = leftB.x;
        int leftBottomY = leftB.y;

        int rightUpperX = rightU.x;
        int rightUpperY = rightU.y;

        int w = rightUpperX - leftBottomX - 1;
        int h = rightUpperY- leftBottomY - 1;
        Object[] exitDirection = new Object[2];

        // next direction
        List<String> directions = new LinkedList<>();
        directions.add(North);
        directions.add(East);
        directions.add(South);
        directions.add(West);

        directions.remove(reverseDirection(CurrentDirection));
        String nextDirection = directions.get(random.nextInt(directions.size()));

        // next exit
        Position nextExitPosition;
        switch (nextDirection) {
            case North:
                nextExitPosition = new Position(rightUpperX - random.nextInt(w) - 1, rightUpperY);
                break;
            case East:
                nextExitPosition = new Position(rightUpperX, rightUpperY - random.nextInt(h) - 1);
                break;
            case South:
                nextExitPosition = new Position(leftBottomX + random.nextInt(w) + 1, leftBottomY);
                break;
            default:
                nextExitPosition = new Position(leftBottomX, leftBottomY + random.nextInt(h) + 1);
                break;
        }

        exitDirection[0] = nextExitPosition;
        exitDirection[1] = nextDirection;
        return exitDirection;
    }

    // 1-3 exits and adds another  room
    private void Exit1(Position leftBottom, Position rightUpper, String currentDirection) {
        Object[] exitAndDirection = randomExit(leftBottom, rightUpper, currentDirection);
        Position nextExitPosition = (Position) exitAndDirection[0];
        String nextDirection = (String) exitAndDirection[1];
        AddRandomRooms(nextExitPosition, nextDirection);
    }

    private void Exit2(Position leftBottom, Position rightUpper, String currentDirection) {
        Object[] exitAndDirection1 = randomExit(leftBottom, rightUpper, currentDirection);
        Position nextExitPosition1 = (Position) exitAndDirection1[0];
        String nextDirection1 = (String) exitAndDirection1[1];

        Object[] exitAndDirection2;
        Position nextExitPosition2 = nextExitPosition1;
        String nextDirection2 = nextDirection1;
        while (nextDirection2.equals(nextDirection1)) {
            exitAndDirection2 = randomExit(leftBottom, rightUpper, currentDirection);
            nextExitPosition2 = (Position) exitAndDirection2[0];
            nextDirection2 = (String) exitAndDirection2[1];
        }
        AddRandomRooms(nextExitPosition1, nextDirection1);
        AddRandomRooms(nextExitPosition2, nextDirection2);
    }

    private void Exit3(Position leftBottom, Position rightUpper, String CurrentDirection) {
        Object[] exitDirection1 = randomExit(leftBottom, rightUpper, CurrentDirection);
        Position nextExitPosition1 = (Position) exitDirection1[0];
        String nextDirection1 = (String) exitDirection1[1];

        Object[] exitAndDirection2;
        Position nextExitPosition2 = nextExitPosition1;
        String nextDirection2 = nextDirection1;
        while (nextDirection2.equals(nextDirection1)) {
            exitAndDirection2 = randomExit(leftBottom, rightUpper, CurrentDirection);
            nextExitPosition2 = (Position) exitAndDirection2[0];
            nextDirection2 = (String) exitAndDirection2[1];
        }

        Object[] exitAndDirection3;
        Position nextExitPosition3 = nextExitPosition1;
        String nextDirection3 = nextDirection1;
        while (nextDirection3.equals(nextDirection1) || nextDirection3.equals(nextDirection2)) {
            exitAndDirection3 = randomExit(leftBottom, rightUpper, CurrentDirection);
            nextExitPosition3 = (Position) exitAndDirection3[0];
            nextDirection3 = (String) exitAndDirection3[1];
        }

        AddRandomRooms(nextExitPosition1, nextDirection1);
        AddRandomRooms(nextExitPosition2, nextDirection2);
        AddRandomRooms(nextExitPosition3, nextDirection3);
    }

    // add random rooms
    private void AddRandomRooms(Position exitPosition, String currentDirection) {
        int exitX = exitPosition.x;
        int exitY = exitPosition.y;

        int w = random.nextInt(MAXweight) + 1;
        int h = random.nextInt(MAXheight) + 1;

        Position entry;
        Position[] lrPositions;

        switch (currentDirection) {
            case North:
                entry = new Position(exitX, exitY + 1);
                lrPositions = randomPositionNorth(w, h, entry);
                break;
            case East:
                entry = new Position(exitX + 1, exitY);
                lrPositions = randomPositionEast(w, h, entry);
                break;
            case South:
                entry = new Position(exitX, exitY - 1);
                lrPositions = randomPositionSouth(w, h, entry);
                break;
            default:
                entry = new Position(exitX - 1, exitY);
                lrPositions = randomPositionWest(w, h, entry);
                break;
        }

        if (lrPositions != null) {
            setExit(exitPosition);
            Position leftBottom = lrPositions[0];
            Position rightUpper = lrPositions[1];
            gRoom(leftBottom, rightUpper);
            setEntrance(entry);

            switch (random.nextInt(3) + 1) {
                case 1:
                    Exit1(leftBottom, rightUpper, currentDirection);
                    break;
                case 2:
                    Exit2(leftBottom, rightUpper, currentDirection);
                    break;
                default:
                    Exit3(leftBottom, rightUpper, currentDirection);
                    break;
            }
        }
    }



    TETile[][] generate() {
        Initialize();

        Position[] lrPositions = randomPositionNorth(MAXweight, MAXheight, initialP);
        Position leftBottom = lrPositions[0];
        Position rightUpper = lrPositions[1];

        gRoom(leftBottom, rightUpper);
        initialEntrance(initialP);

        Exit3(leftBottom, rightUpper, North);

        return world;
    }


    public static void main(String[] args) {
        int width = 80;
        int height = 50;

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        WorldGeneration wg = new WorldGeneration(width, height, 40, 5, 42);
        wg.Initialize();
        wg.generate();
        ter.renderFrame(wg.world);
    }
}