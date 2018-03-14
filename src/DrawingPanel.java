import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.Timer;

import javax.sound.sampled.Line;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel {
	
    private static final long serialVersionUID = 1L;

    private double offsetX;
    private double offsetY;
    private double scale;
    private final double X_POSUN = 20;
    private final double Y_POSUN = 20;
    private int glyphSize = 100 ;

    public DrawingPanel(int glyphSize) {
    	//this.glyphSize = glyphSize;
	}
    @Override
	protected void paintComponent(Graphics g) {
		this.setBackground(new Color(255, 255, 255));
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform at = g2.getTransform();

        WaterNetwork network = new WaterNetwork();

        initScale(g2);
        
        drawNetwork(network, g2);

        
        

		
		/*
		// prvni rezervoár
		int sizeOfReservoir = 0;
		if (this.getWidth() < this.getHeight())
			sizeOfReservoir = this.getWidth()/4;
		else sizeOfReservoir = this.getHeight()/4;
		
		Point2D positionOfReservoir1 = new Point2D.Double(sizeOfReservoir/2 + 10, sizeOfReservoir/2 + 10);
		float fill1 = (float) 0.33;
		drawReservoir(positionOfReservoir1, sizeOfReservoir, fill1, g2); 
		
		// druhý rezervoár
		Point2D positionOfReservoir2 = new Point2D.Double(this.getWidth() - sizeOfReservoir/2 - 10, this.getHeight() - sizeOfReservoir/2 - 10);
		float fill2 = (float) 0.66;
		drawReservoir(positionOfReservoir2, sizeOfReservoir, fill2, g2);	
			
		// potrubí
		int sizeOfPipe = sizeOfReservoir/4;
		double xStart = positionOfReservoir1.getX() + sizeOfReservoir/2;
		double yStart = positionOfReservoir1.getY() + sizeOfReservoir/6;
		Point2D positionOfStartPipe = new Point2D.Double(xStart, yStart);
		
		double xEnd = positionOfReservoir2.getX() - sizeOfReservoir/2;
		double yEnd = positionOfReservoir2.getY() - sizeOfReservoir/5;
		Point2D positionOfEndPipe = new Point2D.Double(xEnd, yEnd);
		float flow = (float) -0.8;
		float open = (float) 0.78;
		drawPipe(positionOfStartPipe, positionOfEndPipe, sizeOfPipe, flow, open, g2);
		*/
		
    }
    
    public void drawReservoir(Point2D position, double size, float fill, Graphics2D g) {
    	double x = (position.getX());
    	double y = (position.getY());
    	g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(x, y, size, size));
		
		double yFill = y + size*(1-fill);
		double heightFill = (size * fill);	
		g.setColor(new Color(78, 78, 255));
		g.fill(new Rectangle2D.Double(x, yFill, size, heightFill));
		g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(x, yFill, size, heightFill));
    	/*double x = (position.getX()-size/2);
    	double y = (position.getY()-size/2);
    	g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(x, y, size, size));
		
		double yFill = Math.ceil((y + size*(1-fill)));
		double heightFill = (size * fill);	
		g.setColor(new Color(78, 78, 255));
		g.fill(new Rectangle2D.Double(x, yFill, size, heightFill));
		g.setColor(Color.BLACK);
		g.draw(new Rectangle2D.Double(x, yFill, size, heightFill));*/
	}
    
    public void drawValve(Point2D position, double sizeOfValve, float open, Graphics2D g) {

    	double x = (position.getX()-sizeOfValve*5/8);
    	double y = (position.getY()-sizeOfValve*5/8);
    	g.setColor(Color.BLACK);
    	
    	//g.fillOval(x, y, sizeOfValve, sizeOfValve);
    	//g.drawOval(x, y, sizeOfValve, sizeOfValve);
    	
    	double yFill = Math.ceil((y + sizeOfValve*(1-open)));
		double heightFill = (sizeOfValve * (1-open));
		double heightFill2 = (sizeOfValve * open);

    	Rectangle clipBounds = g.getClipBounds();

        try
        {
          g.clip(new Rectangle.Double(x, y, sizeOfValve, heightFill));
          g.setColor(new Color(255, 255, 255));
          g.fill(new Ellipse2D.Double(x, y, sizeOfValve, sizeOfValve));
          
        }
        finally
        {
          g.setClip(clipBounds);
        }

        try
        {
          g.clip(new Rectangle.Double(x, yFill, sizeOfValve, heightFill2));
          g.setColor(new Color(100, 100, 100));
          g.fill(new Ellipse2D.Double(x, y, sizeOfValve, sizeOfValve));
        }
        finally
        {
          g.setClip(clipBounds);
        }	
    }
    
	public void drawPipe(Point2D start, Point2D end, double size, float flow, float open, Graphics2D g) {

		int[] xPoints = {(int)start.getX() + glyphSize/2, (int)end.getX() + glyphSize/2, (int)end.getX() + glyphSize/2, (int)start.getX() + glyphSize/2};
		int[] yPoints = {(int)(start.getY() + glyphSize/2 - size/2), (int)(end.getY() + glyphSize/2 - size/2), (int)(end.getY() + glyphSize/2 + size/2), (int)(start.getY() + glyphSize/2 + size/2) };
		int nPoints = 4;
		//g.setColor(new Color(78, 78, 255));
		g.setColor(Color.GREEN);
		g.fillPolygon(xPoints, yPoints, nPoints);

		// ventil
		double xValve = Math.min(start.getX(), end.getX()) + Math.abs(start.getX() - end.getX())/4;
		double posunY = Math.abs(start.getY() - end.getY())/4;
		if (Math.min(start.getX(), end.getX()) == start.getX() && Math.min(start.getY(), end.getY()) != start.getY()) posunY *= 3;
		else if (Math.min(start.getX(), end.getX()) == end.getX() && Math.min(start.getY(), end.getY()) != end.getY()) posunY *= 3;
		double yValve = Math.min(start.getY(), end.getY()) + size/2 + posunY;
		
		Point2D positionOfValve = new Point2D.Double(xValve, yValve);
		drawValve(positionOfValve, size, open, g);
		
		// sipka
		g.setColor(Color.BLACK);
		int x1 = (int)(Math.min(start.getX(), end.getX()) + Math.abs(start.getX() - end.getX())/2);
		int y1 = (int)(Math.min(start.getY(), end.getY()) + Math.abs(start.getY() - end.getY())/2);
		g.drawLine(x1 , y1, x1 + (int)size, y1 + (int)size);
		
		
		
	/*	int x1 = (int) (start.getX() + xPipe/5*2);
		int y1 = (int) (start.getY() + size/2 + yPipe/5*2);
		int x2 = (int) (start.getX() + xPipe/5*3);
		int y2 = (int) (start.getY() + size/2 + yPipe/5*3);
		g.setStroke(new BasicStroke(3));
		g.drawLine(x1, y1, x2, y2);
		
		if (flow < 0) {
			int xEndOfArrow = (int) (start.getX() + xPipe/2);
			int yEndOfArrow = (int) (start.getY() + yPipe/2);
			g.drawLine(x1, y1, xEndOfArrow, yEndOfArrow);
			g.drawLine(x1, y1, xEndOfArrow, (int) (yEndOfArrow + size));
		}
		else {
			int xEndOfArrow = (int) (start.getX() + xPipe/2);
			int yEndOfArrow = (int) (start.getY() + yPipe/2);
			g.drawLine(x2, y2, xEndOfArrow, yEndOfArrow);
			g.drawLine(x2, y2, xEndOfArrow, (int) (yEndOfArrow + size));
		}
		g.setStroke(new BasicStroke(1));
	
		
		
		// rychlost proudìní
        String text = flow + " m3/s";
		FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textHeight = metrics.getHeight();
        int textWidth = metrics.stringWidth(text);
		int xText = (int) (start.getX() + xPipe/2 - textWidth/2);
		int yText = (int) (start.getY() + yPipe/2 + textHeight);
		AttributedString as = new AttributedString(text);
        as.addAttribute(TextAttribute.FAMILY, "Times New Roman");
        as.addAttribute(TextAttribute.SIZE, this.getWidth()/20);
        as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 5, 6);
        g.drawString(as.getIterator(), xText, yText);*/
	}
	
//	int[] xPoints = {(int)start.getX(), (int)end.getX(), (int)end.getX(), (int)start.getX() };
//	int[] yPoints = {(int)start.getY(), (int)(end.getY() - sizeEnd), (int)end.getY(), (int)(start.getY() + size) };
//	int nPoints = 4;
//	//g.setColor(new Color(78, 78, 255));
//	g.setColor(Color.GREEN);
//	g.fillPolygon(xPoints, yPoints, nPoints);
//	
//	double xPipe = this.getWidth() - start.getX()*2;
//	double yPipe = this.getHeight() - start.getY() - (this.getHeight()-end.getY()) - size;
//
//	// ventil
//	int sizeOfValve = Math.min(this.getWidth(), this.getHeight())/8;
//	/*if (this.getWidth() < this.getHeight())
//		sizeOfValve = this.getWidth() / 8;
//	else
//		sizeOfValve = this.getHeight() / 8;*/
//
//	double xValve = start.getX() + xPipe/4;
//	double yValve = start.getY() + size/2 + yPipe/4;
//	Point2D positionOfValve = new Point2D.Double(xValve, yValve);
//	drawValve(positionOfValve, sizeOfValve, open, g);
//	
//	// sipka
//	g.setColor(Color.BLACK);
//	int x1 = (int) (start.getX() + xPipe/5*2);
//	int y1 = (int) (start.getY() + size/2 + yPipe/5*2);
//	int x2 = (int) (start.getX() + xPipe/5*3);
//	int y2 = (int) (start.getY() + size/2 + yPipe/5*3);
//	g.setStroke(new BasicStroke(3));
//	g.drawLine(x1, y1, x2, y2);
//	
//	if (flow < 0) {
//		int xEndOfArrow = (int) (start.getX() + xPipe/2);
//		int yEndOfArrow = (int) (start.getY() + yPipe/2);
//		g.drawLine(x1, y1, xEndOfArrow, yEndOfArrow);
//		g.drawLine(x1, y1, xEndOfArrow, (int) (yEndOfArrow + size));
//	}
//	else {
//		int xEndOfArrow = (int) (start.getX() + xPipe/2);
//		int yEndOfArrow = (int) (start.getY() + yPipe/2);
//		g.drawLine(x2, y2, xEndOfArrow, yEndOfArrow);
//		g.drawLine(x2, y2, xEndOfArrow, (int) (yEndOfArrow + size));
//	}
//	g.setStroke(new BasicStroke(1));
//	
//	
//	
//	// rychlost proudìní
//    String text = flow + " m3/s";
//	FontMetrics metrics = g.getFontMetrics(g.getFont());
//    int textHeight = metrics.getHeight();
//    int textWidth = metrics.stringWidth(text);
//	int xText = (int) (start.getX() + xPipe/2 - textWidth/2);
//	int yText = (int) (start.getY() + yPipe/2 + textHeight);
//	AttributedString as = new AttributedString(text);
//    as.addAttribute(TextAttribute.FAMILY, "Times New Roman");
//    as.addAttribute(TextAttribute.SIZE, this.getWidth()/20);
//    as.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 5, 6);
//    g.drawString(as.getIterator(), xText, yText);
//}

	public void initScale(Graphics2D g) {
		/*double designWidth = panelSizeX;
        double designHeight = panelSizeY;
        double scaleX = this.getWidth() / designWidth;
        double scaleY = this.getHeight() / designHeight;
        scale = Math.min(scaleX, scaleY);

        if (scaleX < scaleY) {
            scale = scaleX;
            offsetX = 0;
            offsetY = (this.getHeight() - designHeight*scale) / 2;            
        } else {
            scale = scaleY;
            offsetX = (this.getWidth() - designWidth*scale) / 2;
            offsetY = 0;
        }*/
		
		g.setColor(Color.MAGENTA);
		g.draw(new Rectangle2D.Double(X_POSUN, Y_POSUN, this.getWidth()-X_POSUN*2, this.getHeight()-Y_POSUN*2));
		g.translate(20, 20);
		g.setColor(Color.BLACK);
		System.out.println(this.getWidth());
		System.out.println(this.getHeight());
		g.scale(this.getWidth()/624.0, this.getHeight()/442.0);
		

        
	}
	
	public Point2D model2window(Point2D m) {
		
		return null;
	}
	
	public void drawNetwork(WaterNetwork network, Graphics2D g) {
		
		
		
		NetworkNode[] nodes = network.getAllNetworkNodes();
		double x = 0;
		double y = 0;
		for (int i = 0; i < nodes.length; i ++) {
			 if (nodes[i] instanceof Reservoir) {
				    Reservoir r = (Reservoir)nodes[i];
				    if (r.position.getX() > x) x = r.position.getX();
				    if (r.position.getY() > y) y = r.position.getY();
			 }
		}
	
		for (int i = 0; i < nodes.length; i ++) {
			 if (nodes[i] instanceof Reservoir) {
				    Reservoir r = (Reservoir)nodes[i];
 
				    r.position = new Point2D.Double((this.getWidth()-glyphSize- X_POSUN*2)*r.position.getX()/x, (this.getHeight()-glyphSize - Y_POSUN*2)/y*r.position.getY());
				    float fill;
				    if (r.content == 0) fill = 0;
				    else fill = (float) (r.content / glyphSize);
				  		drawReservoir(r.position, glyphSize, fill, g);
				  }
			
		}
		
		Pipe[] pipes = network.getAllPipes();
		for (int i = 0; i < pipes.length; i ++) {
			Pipe pipe = pipes[i];
			/*double sizeStart = 0;
			
			if (pipe.start instanceof Reservoir) {
				    Reservoir r = (Reservoir)pipe.start;
				  		sizeStart = r.level();
				  	
				  }
			double sizeEnd = 0;
			if (pipe.end instanceof Reservoir) {
			    Reservoir r = (Reservoir)pipe.end;
			  		sizeEnd = r.level();			  	
			  }*/
			double size = glyphSize/5;
			drawPipe(pipe.start.position, pipe.end.position, size, (float)pipe.flow, (float)pipe.open, g);
		}
		}
}

