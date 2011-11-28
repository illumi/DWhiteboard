import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

	class DrawingCanvas extends JPanel {
		private int x, y;
		private BasicStroke wideStroke = new BasicStroke(8.0f);
		boolean drawingEnabled = false;
		private Graphics2D g;
		private BufferedImage buff;
		WWindow w;

		public DrawingCanvas(WWindow w) {
			this.w = w;
			
			buff = new BufferedImage(500, 400, BufferedImage.TYPE_INT_ARGB);
			g = buff.createGraphics();
			g.setStroke(wideStroke);
			clear();

			addMouseListener(new MyMouseListener());
			addMouseMotionListener(new MyMouseMotionListener());
		}
		public void paintComponent(Graphics g) {
			super.paintComponents(g);
			g.drawImage(buff, 0, 0, this);
		}

		public void clear() {
			g.setPaintMode();
			g.setColor(Color.white);
			g.fillRect(0, 0, buff.getWidth(), buff.getHeight());
			g.setColor(Color.black); //set pen back to black
			repaint();
		}

		class MyMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if (drawingEnabled) {
					x = e.getX();
					y = e.getY();
					g.draw(new Line2D.Double(x, y, x, y));
					w.sendMessage("draw "+x+" " +y);
					repaint();
				}
			}
		}
		class MyMouseMotionListener extends MouseMotionAdapter {
			public void mouseDragged(MouseEvent e) {
				if (drawingEnabled) {
					x = e.getX();
					y = e.getY();
					g.draw(new Line2D.Double(x, y, x, y));
					w.sendMessage("draw "+x+" " +y);
					repaint();
				}
			}
		}
	}