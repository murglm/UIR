import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Scanner;

import javax.swing.JFrame;

public class Main {
	
	private static Scanner sc = new Scanner(System.in);
	private static int glyphSize = 0;

	public static void main(String[] args) {
		
	//	System.out.print("Napiste velikost: ");
	//	glyphSize = Integer.parseInt(sc.next());
		
		JFrame frame = new JFrame();
		WaterNetwork network = new WaterNetwork();
        frame.setTitle("UPG SP 1 - A16B0114P");
        //frame.setSize(640, 480);
        DrawingPanel panel = new DrawingPanel(glyphSize);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setMinimumSize(new Dimension(400, 400));
        frame.add(panel);        
        frame.pack();        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);    
        frame.setVisible(true);
        
        if (network.currentSimulationTime() % 100 == 0)
        	frame.repaint();
	}
	
	
		

}
