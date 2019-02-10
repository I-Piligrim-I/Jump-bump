package my.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Rabbit extends Hitable implements Drawable {
    public static final int DEATH_TIMEOUT = 3000;
    public static final double VERTICAL_SEED_CONSTANT = 5;
    public static final double JUMP_SPEED_CONSTANT = -7.69;



    double vx;
    double Previous_Vetical_Speed;

    double vy;

    double VERTICAL_SPEED_CHANGE = 0.14;
    int Left_Key;
    int Right_Key;
    int Up_Key;
    public int score;
    static final int RABBIT_SIZE = 70;

    BufferedImage imageDead;
    BufferedImage jump;
    BufferedImage jumpInv;

    public static ImageIcon jumping;
    public static ImageIcon running;

    boolean isBlockedFormAbove = false;
    boolean isBlockingDown = false;
    boolean isBlockedFromRight = false;
    boolean isBlockedFromLeft = false;

    Random spawnPointRandom = new Random();
    double spawnPoint;

    static double X;
    static double Y;

    boolean directionToTheRight = true;
    boolean disabledFlag = false;

//    int Blocked_Y;

    public Rabbit(int _left, int _up, int _right, int X, String RunImage, String JumpImage) throws IOException {
        Left_Key = _left;
        Up_Key = _up;
        Right_Key = _right;
        width = RABBIT_SIZE;
        height = RABBIT_SIZE;
        x = X;
        Drawable.drawable.add(this);
        Hitable.hitableObjects.add(this);

        //Trying to do gif.
        jumping = new ImageIcon(JumpImage, "");
        running = new ImageIcon(RunImage, "");

        // Flip the image horizontally

        imageDead = ImageIO.read(new File("dead-easter-bunny.png"));

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

    @Override
    public void disable(boolean d) {
        disabledFlag = d;
    }

    public void update() {
        isBlockedFormAbove = false;
        isBlockingDown = false;
        isBlockedFromRight = false;
        isBlockedFromLeft = false;
        for (int i = 0; i < Hitable.hitableObjects.size(); i++) {
            if (Hitable.hitableObjects.get(i) != this) {
                if (((Hitable.hitableObjects.get(i).hitTest(x, y, RABBIT_SIZE) == 1) && (vy >= 0))) {
                    isBlockingDown = true;
                    // rabbit dies
                    Hitable hitable = Hitable.hitableObjects.get(i);
                    if (!hitable.isDisabled()) {
                        {
                            hitable.disable(true);

                            if (hitable instanceof Rabbit) {
                                final int deadRabbitIdx = i;
                                Timer deathDelayTimerSoSad = new Timer(DEATH_TIMEOUT, e -> {
                                    vx = 0;
                                    hitable.disable(false);
                                    spawnPoint = spawnPointRandom.nextInt(JumpBump.WINDOW_WIDTH - RABBIT_SIZE);
                                    Hitable.hitableObjects.get(deadRabbitIdx).x = spawnPoint;
                                    //Blocked_Y = (int) hitable.y - RABBIT_SIZE;
                                    Hitable.hitableObjects.get(deadRabbitIdx).y = 0;

                                });
                                deathDelayTimerSoSad.setRepeats(false);
                                deathDelayTimerSoSad.start();
                                score = score + 1;
                            }
                        }
                    }
                    //TODO rework on Blocked_Y, wall detection.
//                    if (hitable.getClass() == Wall.class)
//                        Blocked_Y = (int) hitable.y;

                }
                if (((Hitable.hitableObjects.get(i).hitTest(x, y, RABBIT_SIZE) == 2) && (vx <= 0))) {
                    isBlockedFromRight = true;
                }
                if (((Hitable.hitableObjects.get(i).hitTest(x, y, RABBIT_SIZE) == 3) && (vy <= 0))) {
                    isBlockedFormAbove = true;
                }
                if (((Hitable.hitableObjects.get(i).hitTest(x, y, RABBIT_SIZE) == 4) && (vx >= 0))) {
                    isBlockedFromLeft = true;
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

    protected boolean isDisabled() {
        return disabledFlag;
    }

    public void draw(Graphics2D g2d) {
        if (isBlockingDown) {
            if (disabledFlag) {
                g2d.drawImage(imageDead, (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
            } else {
                if (!directionToTheRight) { //(vx > 0 || (vx == 0 && Previous_Vetical_Speed > 0 || directionToTheRight == false)) {
                    g2d.drawImage(running.getImage(), (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
                } else {
                    g2d.drawImage(running.getImage(), (int) x + RABBIT_SIZE, (int) y, -RABBIT_SIZE, RABBIT_SIZE, null);
                }
            }
        } else {
            if (disabledFlag) {
                g2d.drawImage(imageDead, (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
            } else {
                boolean revFlag = false;
                if (!directionToTheRight) { //(vx > 0 || (vx == 0 && Previous_Vetical_Speed > 0 || directionToTheRight == false)) {
                    g2d.drawImage(jumping.getImage(), (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
                } else {
                    g2d.drawImage(jumping.getImage(), (int) x + RABBIT_SIZE, (int) y, -RABBIT_SIZE, RABBIT_SIZE, null);
                }
            }
        }


    }
}