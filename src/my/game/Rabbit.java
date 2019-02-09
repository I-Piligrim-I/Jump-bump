package my.game;
//Lundina Darya
//18.05.18 (сами кролики)
//Jump&Bump

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Rabbit extends Hitable implements Drawable {
    public static final int DeathTime = 3000;
    double vx;
    double Previous_Vetical_Speed;

    double vy;
    static final double VERTICAL_SEED_CONSTANT = 5;
    static final double JUMP_SPEED_CONSTANT = -7.69;
    double VERTICAL_SPEED_CHANGE = 0.14;
    int Left_Key;
    int Right_Key;
    int Up_Key;
    int score;
    String A_NAME;
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

    boolean direction = true;
    boolean disabledFlag = false;

//    int Blocked_Y;

    Rabbit(int _left, int _up, int _right, int X, String RunImage, String JumpImage, String A_Name) throws IOException {
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

        A_NAME = A_Name;

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
                direction = true;
            }
            if (key == Left_Key)
                direction = true;
            if ((key == Right_Key) && (isBlockedFromLeft == false)) {
                vx = VERTICAL_SEED_CONSTANT;
                Previous_Vetical_Speed = vx;
            }
            if (key == Right_Key)
                direction = false;
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

                            if (hitable.getClass() == Rabbit.class) {
                                final int deadRabbitIdx = i;
                                Timer deathDelayTimerSoSad = new Timer(DeathTime, e -> {
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
                if (vx > 0 || (vx == 0 && Previous_Vetical_Speed > 0 || direction == false)) {
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
                if (vx > 0 || (vx == 0 && Previous_Vetical_Speed > 0 || direction == false)) {
                    g2d.drawImage(jumping.getImage(), (int) x, (int) y, RABBIT_SIZE, RABBIT_SIZE, null);
                } else {
                    g2d.drawImage(jumping.getImage(), (int) x + RABBIT_SIZE, (int) y, -RABBIT_SIZE, RABBIT_SIZE, null);
                }
            }
        }

        Font font = new Font("PaladinPCRus Medium", Font.BOLD, 15);
        g2d.setFont(font);
        g2d.setColor(new Color(212, 25, 69));
        g2d.drawString(A_NAME, (int) x, (int) (y + RABBIT_SIZE));
    }
}