package my.game;//Lundina Darya
//18.05.18 (Hittest)
//Jump&Bump

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Hitable {
	//переданные данные
	double x;
	double y;
	int height;
	int width;
	BufferedImage image;

	static ArrayList<Hitable> hitableObjects = new ArrayList<Hitable>();
	
	
	int hitTest(double RabX, double RabY, double RabR) {
		int side = 0;

		if ((RabX + RabR > x) && (RabX < x + width)) {
			if ((RabY <= y) && (RabY + RabR >= y)) {
				side = 1;
			}
			if ((RabY <= y + height) && (RabY + RabR >= y + height)) {
				side = 3;
			}
		} else {
			if ((RabY + RabR > y) && (RabY < y + height)) {
				if ((RabX <= x) && (RabX + RabR >= x - 4)) {
					side = 4;
				}
				if ((RabX <= x + width + 4) && (RabX + RabR >= x + width)) {
					side = 2;
				}
			}
			if (((RabY + RabR) > JumpBump.Height)) {
				side = 1;
			}
			if ((RabY < 0)) {
				side = 3;
			}
			if (((RabX + RabR) > JumpBump.Width)) {
				side = 4;
			}
			if (((RabX) < 0)) {
				side = 2;
			}


		}		
		
		return side;
		

	}

	public void disable(boolean d) {

	}

	protected boolean isDisabled() {
		return false;
	}
}
