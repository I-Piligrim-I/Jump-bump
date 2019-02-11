package my.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Rabbit extends Hitable implements Drawable {
    public static final int DEATH_TIMEOUT = 3000;
    public static final double VERTICAL_SEED_CONSTANT = 5;
    public static final double JUMP_SPEED_CONSTANT = -7.69;


    private static final int MAX_DIED_PERIOD = 20;

    private final AffineTransformOp upFilter;

    protected double vx;
    protected double Previous_Vetical_Speed;

    protected double vy;

    double VERTICAL_SPEED_CHANGE = 0.14;
    protected int Left_Key;
    protected int Right_Key;
    protected int Up_Key;
    public int score;

    private List<Consumer<Integer>> scoreListeners = new ArrayList<>();
    static final int RABBIT_SIZE = 70;

    private BufferedImage imageDead;
    private BufferedImage jump;
    private BufferedImage jumpInv;

    public static ImageIcon jumping;
    public static ImageIcon running;

    boolean isBlockedFormAbove = false;
    boolean isBlockingDown = false;
    boolean isBlockedFromRight = false;
    boolean isBlockedFromLeft = false;

    Random spawnPointRandom = new Random();
    double spawnPoint;
    double startX;

    static double X;
    static double Y;

    boolean directionToTheRight = true;
    boolean disabledFlag = false;
    int dieCounter = MAX_DIED_PERIOD;

    private Map<Coords, BufferedImage> coordsBufferedImageMap = new HashMap<>();

    private final AffineTransformOp downFilter;

//    int Blocked_Y;

    public Rabbit(int _left, int _up, int _right, int X, String runImage, String jumpImage) throws IOException {
        Left_Key = _left;
        Up_Key = _up;
        Right_Key = _right;
        width = RABBIT_SIZE;
        height = RABBIT_SIZE;
        startX = X;
        x = X;


        //Trying to do gif.
        jumping = new ImageIcon(jumpImage, "");
        running = new ImageIcon(runImage, "");

        // Flip the image horizontally

        imageDead = ImageIO.read(new File("dead-easter-bunny.png"));


// Rotation information

        double rotationRequired = Math.toRadians(15);
        double locationX = running.getIconWidth() / 2;
        double locationY = running.getIconHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        downFilter = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        AffineTransform tx2 = AffineTransform.getRotateInstance(Math.toRadians(-15), locationX, locationY);
        upFilter = new AffineTransformOp(tx2, AffineTransformOp.TYPE_BILINEAR);
    }


    public void addScoreListener(Consumer<Integer> listener) {
        this.scoreListeners.add(listener);
    }

    void keyreleased(int key) {
        if (key == Left_Key) {
            vx = 0;
        }
        if (key == Right_Key) {
            vx = 0;
        }

    }

    void keypressed(int key) {
        if (!disabledFlag) {
            if ((key == Left_Key) && (isBlockedFromRight == false)) {
                vx = -VERTICAL_SEED_CONSTANT;
                Previous_Vetical_Speed = vx;
                directionToTheRight = true;
            }
            if (key == Left_Key)
                directionToTheRight = true;
            if ((key == Right_Key) && (isBlockedFromLeft == false)) {
                vx = VERTICAL_SEED_CONSTANT;
                Previous_Vetical_Speed = vx;
            }
            if (key == Right_Key)
                directionToTheRight = false;
            if ((key == Up_Key) && (isBlockedFormAbove == false) && (isBlockingDown == true)) {
                vy = JUMP_SPEED_CONSTANT;

            }
        }

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        for (Consumer<Integer> scoreListener : scoreListeners) {
            scoreListener.accept(this.score);
        }
    }

    @Override
    public void disable(boolean d) {
        disabledFlag = d;
        if (d) {
            BufferedImage image = convertToBufferedImage(jumping.getImage(), RABBIT_SIZE);
            coordsBufferedImageMap = divideIntoBufferedImages(image);
        }
    }

    public void update() {
        isBlockedFormAbove = false;
        isBlockingDown = false;
        isBlockedFromRight = false;
        isBlockedFromLeft = false;

        for (int i = 0; i < Hitable.hitables.size(); i++) {
            if (Hitable.hitables.get(i) != this) {
                if (((Hitable.hitables.get(i).hitTest(x, y, RABBIT_SIZE) == 1) && (vy >= 0))) {
                    isBlockingDown = true;

                    // rabbit dies
                    Hitable hitable = Hitable.hitables.get(i);
                    if (hitable instanceof Rabbit) {
                        Rabbit diedRabbit = (Rabbit) hitable;
                        if (!diedRabbit.isDisabled()) {
                            diedRabbit.disable(true);

                            this.setScore(score + 1);
                        } else {
                            if (diedRabbit.dieCounter > 0) {
                                diedRabbit.dieCounter--;
                            } else {
                                diedRabbit.dieCounter = MAX_DIED_PERIOD;
                                diedRabbit.respawn(true);
                            }
                        }
                    }
                    //TODO rework on Blocked_Y, wall detection.
//                    if (hitable.getClass() == Wall.class)
//                        Blocked_Y = (int) hitable.y;

                }
//                if (Hitable.hitables.size() > 0)
                {
                    if (((Hitable.hitables.get(i).hitTest(x, y, RABBIT_SIZE) == 2) && (vx <= 0))) {
                        isBlockedFromRight = true;
                    }
                    if (((Hitable.hitables.get(i).hitTest(x, y, RABBIT_SIZE) == 3) && (vy <= 0))) {
                        isBlockedFormAbove = true;
                    }
                    if (((Hitable.hitables.get(i).hitTest(x, y, RABBIT_SIZE) == 4) && (vx >= 0))) {
                        isBlockedFromLeft = true;
                    }
                }
            }
        }

        if ((isBlockingDown == true)) {
            vy = 0;
            // y = Blocked_Y - RABBIT_SIZE;
        } else {
            if (isBlockedFormAbove == true) {
                vy = -vy;
            }
            vy = vy + VERTICAL_SPEED_CHANGE;
            y = y + vy;
        }
        if ((isBlockedFromRight == true) && (vx <= 0)) {
            vx = 0;
        }
        if ((isBlockedFromLeft == true) && (vx >= 0)) {
            vx = 0;
        }

        x = x + vx;

    }

    public void respawn(boolean rand) {
        disable(false);
        if (!rand) {
            x = startX;
        } else {
            spawnPoint = spawnPointRandom.nextInt(JumpBump.WINDOW_WIDTH - RABBIT_SIZE);
            x = spawnPoint;
        }

        //Blocked_Y = (int) hitable.y - RABBIT_SIZE;
        y = 0;
    }

    protected boolean isDisabled() {
        return disabledFlag;
    }


    public static BufferedImage convertToBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    public static BufferedImage convertToBufferedImage(Image image, int rabbitSize) {
        BufferedImage newImage = new BufferedImage(
                rabbitSize, rabbitSize,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, rabbitSize, rabbitSize, null);
        g.dispose();
        return newImage;
    }

    static class Coords {
        public int x, y;
        public int directionAngle;

        public Coords(int x, int y, int directionAngle) {
            this.x = x;
            this.y = y;
            this.directionAngle = directionAngle;
        }
    }

    public static Map<Coords, BufferedImage> divideIntoBufferedImages(BufferedImage image) {
        Map<Coords, BufferedImage> chunks = new HashMap<>();

        int width = image.getWidth();
        int height = image.getHeight();
        int chunckCntX = 3;
        int chunckCntY = 3;
        for (int i = 0; i < chunckCntX; i++) {
            for (int j = 0; j < chunckCntY; j++) {
                Coords c1 = new Coords(0 + i * (width / chunckCntX), 0 + j * (height / chunckCntY), 10 + i * 170 / (chunckCntX - 1));
                BufferedImage subimage = image.getSubimage(c1.x, c1.y, width / chunckCntX, height / chunckCntY);
                chunks.put(c1, subimage);
            }
        }

        return chunks;
    }


    public void draw(Graphics2D g2d) {
        // Drawing the rotated image at the required drawing locations
        // g2d.drawImage(op.filter(image, null), drawLocationX, drawLocationY, null);

        if (isBlockingDown) {
            if (disabledFlag) {
                drawDeadRabbit(g2d);
            } else {
                Image image = running.getImage();
                if (!directionToTheRight) {
                    g2d.drawImage(image, (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
                } else {
                    g2d.drawImage(image, (int) x + RABBIT_SIZE, (int) y, -RABBIT_SIZE, RABBIT_SIZE, null);
                }
            }
        } else {
            if (disabledFlag) {
                drawDeadRabbit(g2d);
            } else {
                boolean revFlag = false;
                BufferedImage image = convertToBufferedImage(jumping.getImage());
//                System.out.println("h: " + image.getHeight() + "  w=" + image.getHeight());
                image = (vy > 0) ? downFilter.filter(image, null) : upFilter.filter(image, null);
                if (!directionToTheRight) {
                    g2d.drawImage(image, (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
                } else {
                    g2d.drawImage(image, (int) x + RABBIT_SIZE, (int) y, -RABBIT_SIZE, RABBIT_SIZE, null);
                }
            }
        }


    }

    public void drawDeadRabbit(Graphics2D g2d) {

        int delta = 10;
        for (Map.Entry<Coords, BufferedImage> imageEntry : coordsBufferedImageMap.entrySet()) {
            Coords coords = imageEntry.getKey();
            g2d.drawImage(imageEntry.getValue(), (int)x + coords.x, (int)y + coords.y, null);
            double dxx;
            double dyy;
            if (coords.directionAngle > 90) {
                int angl = 180 - coords.directionAngle;
                dxx = delta * Math.cos(Math.toRadians(angl));
                dyy = -delta * Math.sin(Math.toRadians(angl));
            } else {
                dxx = -delta * Math.cos(Math.toRadians(coords.directionAngle));
                dyy = -delta * Math.sin(Math.toRadians(coords.directionAngle));
            }
            coords.x += (int) dxx;
            coords.y += (int) dyy;
        }
    }
}