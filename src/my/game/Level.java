package my.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Level {

    static List<Wall> walls = new ArrayList<>();
    static List<NamedRabbit> rabbits = new ArrayList<>();

    public Level(String filename) throws IOException {

        try (Scanner sc = new Scanner(new File("levelText.txt"))) {
            BackGround background = new BackGround(sc.next());
            int WallAmmount = sc.nextInt();

            for (int i = 0; i < WallAmmount; i++) {
                walls.add(new Wall(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.next()));
            }
        }
        Score score = new Score();
        try (Scanner scan = new Scanner(new File("rabbitTest.txt"))) {
            int NumberOfRabbits = scan.nextInt();
            for (int i = 0; i < NumberOfRabbits; i++) {
                rabbits.add(new NamedRabbit(
                        scan.nextInt(),
                        scan.nextInt(),
                        scan.nextInt(),
                        scan.nextInt(),
                        scan.next(),
                        scan.next(),
                        scan.next())
                );

            }
        }
    }

}