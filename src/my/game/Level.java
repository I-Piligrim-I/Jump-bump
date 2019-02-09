package my.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;



public class Level {
	/**
	 * create ArrayLists
	 */
	
	static ArrayList<Wall> Object_Array_Wall = new ArrayList<Wall>();
	static ArrayList<Rabbit> Object_Array_Rabbit = new ArrayList<Rabbit>();

	public Level(String filename) throws IOException {
		
		
		Scanner sc = new Scanner(new File("levelText.txt"));
		BackGround background = new BackGround(sc.next());
		int WallAmmount = sc.nextInt();

		for (int i = 0; i < WallAmmount; i++) {
			Object_Array_Wall.add(new Wall(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.next()));
		}
		sc.close();
		Score score = new Score();
		Scanner scan = new Scanner(new File("rabbitTest.txt"));
		int NumberOfRabbits = scan.nextInt();
		for (int i = 0; i < NumberOfRabbits; i++) {
			Object_Array_Rabbit.add(new Rabbit(
					scan.nextInt(),
					scan.nextInt(),  
					scan.nextInt(),
					scan.nextInt(),
					scan.next(),
					scan.next(),
					scan.next())
			);

		}
		scan.close();
	}

}