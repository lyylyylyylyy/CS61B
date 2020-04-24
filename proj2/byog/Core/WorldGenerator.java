package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;


class WorldGenerator {
    private static final int MAXWIDTH = 6;
    private static final int MAXHEIGHT = 8;

    private int width;
    private int height;
    private int initialX;
    private int initialY;
    private Random random;
    private TETile[][] world;


    WorldGenerator(int w, int h, int X, int Y, long seed) {
        width = w;
        height = h;
        initialX = X;
        initialY = Y;
        random = new Random(seed);
    }

    WorldGenerator(int w, int h, int X, int Y) {
        width = w;
        height = h;
        initialX = X;
        initialY = Y;
        random = new Random();
    }
    private void initialize() {
        world = new TETile[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }

    }

    /* Checks availability for a given rectangular space to add another room */
    private boolean check(Integer[][] positions) {
        int leftBottomX = positions[0][0];
        int leftBottomY = positions[0][1];
        int rightUpperX = positions[1][0];
        int rightUpperY = positions[1][1];

        if (leftBottomX < 0 || width <= leftBottomX
                || leftBottomY < 0 || height <= leftBottomY
                || rightUpperX < 0 || width <= rightUpperX
                || rightUpperY < 0 || height <= rightUpperY) {
            return false;
        }

        for (int x = leftBottomX; x <= rightUpperX; x += 1) {
            for (int y = leftBottomY; y <= rightUpperY; y += 1) {
                TETile currentTile = world[x][y];
                if (currentTile == Tileset.WALL || currentTile == Tileset.FLOOR) {
                    return false;
                }
            }
        }

        return true;
    }

    // Returns random positions for a new room
    private Integer[][] randomPositionNorth(int x, int y, int w, int h) {
        Integer[][] positions = new Integer[2][2];
        int leftBottomX = x - random.nextInt(w) - 1;
        int leftBottomY = y;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;

        positions[0][0] = leftBottomX;
        positions[0][1] = leftBottomY;
        positions[1][0] = rightUpperX;
        positions[1][1] = rightUpperY;

        if (!check(positions)) {
            return null;
        } else {
            return positions;
        }
    }

        private Integer[][] randomPositionEast(int x, int y, int w, int h) {
        Integer[][] positions = new Integer[2][2];
        int leftBottomX = x ;
        int leftBottomY = y - random.nextInt(h) - 1;
        int rightUpperX = leftBottomX + w + 1;
        int rightUpperY = leftBottomY + h + 1;

        positions[0][0] = leftBottomX;
        positions[0][1] = leftBottomY;
        positions[1][0] = rightUpperX;
        positions[1][1] = rightUpperY;

        if (!check(positions)) {
            return null;
        } else {
            return positions;
        }
    }

    private Integer[][] randomPositionSouth(int x, int y, int w, int h) {
        Integer[][] positions = new Integer[2][2];

        int rightUpperX = x + random.nextInt(w) + 1;
        int rightUpperY = y;

        int leftBottomX = rightUpperX - w -1;
        int leftBottomY = rightUpperY - h - 1;

        positions[0][0] = leftBottomX;
        positions[0][1] = leftBottomY;
        positions[1][0] = rightUpperX;
        positions[1][1] = rightUpperY;

        if (!check(positions)) {
            return null;
        } else {
            return positions;
        }
    }

    private Integer[][] randomPositionWest(int x, int y, int w, int h) {
        Integer[][] positions = new Integer[2][2];

        int rightUpperX = x;
        int rightUpperY = y + random.nextInt(w) + 1;

        int leftBottomX = rightUpperX - w -1;
        int leftBottomY = rightUpperY - h - 1;

        positions[0][0] = leftBottomX;
        positions[0][1] = leftBottomY;
        positions[1][0] = rightUpperX;
        positions[1][1] = rightUpperY;

        if (!check(positions)) {
            return null;
        } else {
            return positions;
        }
    }


    private void makeRoom(Integer[][] positions) {
        int leftBottomX = positions[0][0];
        int leftBottomY = positions[0][1];
        int rightUpperX = positions[1][0];
        int rightUpperY = positions[1][1];

        for (int w = leftBottomX; w <= rightUpperX; w++) {
            for (int h = leftBottomY; h <= rightUpperY; h++) {
                if (w == leftBottomX || w == rightUpperX || h == leftBottomY || h == rightUpperY) {
                    world[w][h] = Tileset.WALL;
                } else {
                    world[w][h] = Tileset.FLOOR;
                }
            }
        }
    }

    // make initial entrance
    private void makeInitialEntrance(int initialX, int initialY) {
        world[initialX][initialY] = Tileset.LOCKED_DOOR;
    }

    // Makes exit by changing WALL to FLOOR at entryPoint
    private void makeExit(int exitX, int exitY) {
        world[exitX][exitY] = Tileset.FLOOR;
    }

    // Makes entrance by changing WALL to FLOOR at entryPoint
    private void makeEntrance(Integer[] entry) {
        world[entry[0]][entry[1]] = Tileset.FLOOR;
    }


    /* Returns the reverse direction of a given direction */
    private int getReverseDirection(int direction) {
        switch (direction) {
            case 3:
                return 2;
            case 4:
                return 1;
            case 2:
                return 3;
            default:
                return 4;
        }
    }

    // Returns random exit position and direction from a given rectangular room
    private Integer[] randomExit(Integer[][] positions, int flag) {
        int leftBottomX = positions[0][0];
        int leftBottomY = positions[0][1];
        int rightUpperX = positions[1][0];
        int rightUpperY = positions[1][1];

        int w = rightUpperX - leftBottomX -1;
        int h = rightUpperY - leftBottomY - 1;

        // 4 directions random exit
        int exitLeftXx = leftBottomX;
        int exitLeftXy = leftBottomY + random.nextInt(h) + 1;

        int exitLeftYx = leftBottomX + random.nextInt(w) + 1;
        int exitLeftYy = leftBottomY;

        int exitRightXx = rightUpperX;
        int exitRightXy = rightUpperY - random.nextInt(h) - 1;

        int exitRightYx = rightUpperX - random.nextInt(w) - 1;
        int exitRightYy = rightUpperY;

        List<Integer[]> exitPositions = new LinkedList<>();

        Integer[][] record = new Integer[4][3];
        record[0][0] = exitLeftXx;
        record[0][1] = exitLeftXy;
        record[0][2] = 1;
        record[1][0] = exitLeftYx;
        record[1][1] = exitLeftYy;
        record[1][2] = 2;
        record[2][0] = exitRightXx;
        record[2][1] = exitRightXy;
        record[2][2] = 4;
        record[3][0] = exitRightYx;
        record[3][1] = exitRightYy;
        record[3][2] = 3;

        for (int i = 0; i < 4; i++) {
            if (record[i][2] != getReverseDirection(flag)) {
                exitPositions.add(record[i]);
            }
        }

        // next exit entrance

        int index = random.nextInt(3);

        Integer[] tmp = exitPositions.get(index);



        Integer[] exit = new Integer[3];

        exit[0] = tmp[0];
        exit[1] = tmp[1];
        exit[2] = tmp[2];

        return exit;

    }

    private void exit2(Integer[][] positions, int current) {
        Integer[] exitPosition = randomExit(positions, current);
        int exitX1 = exitPosition[0];
        int exitY1 = exitPosition[1];
        int flag1 = exitPosition[2];


        Integer[] nextExitPosition;
        int exitX2 = exitX1;
        int exitY2 = exitY1;
        int flag2 = flag1;


        while (flag1 == flag2) {
            nextExitPosition = randomExit(positions, current);
            exitX2 = nextExitPosition[0];
            exitY2 = nextExitPosition[1];
            flag2 = nextExitPosition[2];
        }

        randomAddRooms(exitX1, exitY1, flag1);
        randomAddRooms(exitX2, exitY2, flag2);
    }

    private void randomAddRooms(int exitX, int exitY, int flag) {
        int x = exitX;
        int y = exitY;

        int w = random.nextInt(MAXWIDTH) + 1;
        int h = random.nextInt(MAXHEIGHT) + 1;

        Integer[] entryPosition;
        Integer[][] lrPositions;

        switch (flag) {
            case 3:
                entryPosition = new Integer[2];
                entryPosition[0] = x;
                entryPosition[1] = y + 1;
                lrPositions = randomPositionNorth(x, y + 1, w, h);
                break;
            case 4:
                entryPosition = new Integer[2];
                entryPosition[0] = x + 1;
                entryPosition[1] = y;
                lrPositions = randomPositionEast(x + 1, y, w, h);
                break;
            case 2:
                entryPosition = new Integer[2];
                entryPosition[0] = x;
                entryPosition[1] = y - 1;
                lrPositions = randomPositionSouth(x, y - 1, w, h);
                break;
            default:
                entryPosition = new Integer[2];
                entryPosition[0] = x - 1;
                entryPosition[1] = y;
                lrPositions = randomPositionWest(x - 1, y, w, h);
                break;
        }

        if (lrPositions != null) {
            makeExit(exitX, exitY);
            makeRoom(lrPositions);
            makeEntrance(entryPosition);
            exit2(lrPositions, flag);
        }


    }

    TETile[][] generate() {
        initialize();
        Integer[][] positions = randomPositionNorth(initialX, initialY, MAXWIDTH, MAXHEIGHT);
        makeRoom(positions);
        makeInitialEntrance(initialX, initialY);
        exit2(positions, 1);
        return world;
    }

    public static void main(String[] args) {
        int w = 60;
        int h = 50;

        TERenderer ter = new TERenderer();
        ter.initialize(w, h);
        WorldGenerator wg = new WorldGenerator(w, h, 40, 5, 42);
        wg.initialize();
        wg.generate();
        // System.out.println(TETile.toString(wg.world));
        ter.renderFrame(wg.world);
    }
}