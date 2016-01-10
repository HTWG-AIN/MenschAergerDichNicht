package de.htwg.menschaergerdichnicht.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import de.htwg.menschaergerdichnicht.controller.Controller;
import de.htwg.menschaergerdichnicht.model.Player;
import de.htwg.util.observer.IObserver;

@SuppressWarnings("serial")
public class GUI implements IObserver {

	private JFrame frame;
	private JPanel wurfelPanel;
	private JLabel Myturn;
	private Controller c;
	private JPanel container;

	public GUI(Controller c) {
		this.c = c;
		c.registerObserver(this);

		frame = new JFrame("Mensch �rgere dich nicht");
		this.container = new SpielFeldGUI();
		this.wurfelPanel = new JPanel();
		this.Myturn = new JLabel(c.wuerfeln());

		this.wurfelPanel.setBackground(Color.MAGENTA);
		this.wurfelPanel.add(Myturn);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(wurfelPanel, BorderLayout.NORTH);
		frame.getContentPane().add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 700);
		frame.setVisible(true);
		frame.setResizable(false);

	}

	private class SpielFeldGUI extends JPanel implements MouseListener {
		private byte[][] feld = null;
		private MyPoint[] figurenPositionen = null;
		private MyPoint[] stoneBlock0 = null;
		private MyPoint[] stoneBlock1 = null;
		private MyPoint[] stoneBlock2 = null;
		private MyPoint[] stoneBlock3 = null;
		MyPoint clickedPoint = null;

		public SpielFeldGUI() {
			this.setBackground(Color.WHITE);

			this.feld = new byte[][] { 
					{ 'R', 'R', '0', '0', 'x', 'x', 'x', '0', '0', 'B', 'B' },
					{ 'R', 'R', '0', '0', 'x', ' ', 'x', '0', '0', 'B', 'B' },
					{ '0', '0', '0', '0', 'x', ' ', 'x', '0', '0', '0', '0' },
					{ '0', '0', '0', '0', 'x', ' ', 'x', '0', '0', '0', '0' },
					{ 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x' },
					{ 'x', ' ', ' ', ' ', ' ', '0', ' ', ' ', ' ', ' ', 'x' },
					{ 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x' },
					{ '0', '0', '0', '0', 'x', ' ', 'x', '0', '0', '0', '0' },
					{ '0', '0', '0', '0', 'x', ' ', 'x', '0', '0', '0', '0' },
					{ 'P', 'P', '0', '0', 'x', ' ', 'x', '0', '0', 'G', 'G' },
					{ 'P', 'P', '0', '0', 'x', 'x', 'x', '0', '0', 'G', 'G' } };

			this.stoneBlock0 = new MyPoint[] { new MyPoint(0, 0, 100), new MyPoint(1, 0, 100), new MyPoint(0, 1, 100),
					new MyPoint(1, 1, 100) };
			this.stoneBlock1 = new MyPoint[] {  new MyPoint(9, 0, 200), new MyPoint(10, 0, 200), new MyPoint(9, 1, 200),
					new MyPoint(10, 1, 200) };
			this.stoneBlock2 = new MyPoint[] {  new MyPoint(9, 9, 400), new MyPoint(10, 9, 400), new MyPoint(9, 10, 400),
					new MyPoint(10, 10, 400) };
			this.stoneBlock3 = new MyPoint[] {  new MyPoint(0, 9, 300), new MyPoint(1, 9, 300), new MyPoint(0, 10, 300),
					new MyPoint(1, 10, 300) };
			

			this.figurenPositionen = new MyPoint[] { new MyPoint(4, 0, 30), new MyPoint(4, 1, 31),
					new MyPoint(4, 2, 32), new MyPoint(4, 3, 33), new MyPoint(4, 4, 34), new MyPoint(3, 4, 35),
					new MyPoint(2, 4, 36), new MyPoint(1, 4, 37), new MyPoint(0, 4, 38), new MyPoint(0, 5, 39),
					new MyPoint(0, 6, 0), new MyPoint(1, 6, 1), new MyPoint(2, 6, 2), new MyPoint(3, 6, 3),
					new MyPoint(4, 6, 4), new MyPoint(4, 7, 5), new MyPoint(4, 8, 6), new MyPoint(4, 9, 7),
					new MyPoint(4, 10, 8), new MyPoint(5, 10, 9), new MyPoint(6, 10, 10), new MyPoint(6, 9, 11),
					new MyPoint(6, 8, 12), new MyPoint(6, 7, 13), new MyPoint(6, 6, 14), new MyPoint(7, 6, 15),
					new MyPoint(8, 6, 16), new MyPoint(9, 6, 17), new MyPoint(10, 6, 18), new MyPoint(10, 5, 19),
					new MyPoint(10, 4, 20), new MyPoint(9, 4, 21), new MyPoint(8, 4, 22), new MyPoint(7, 4, 23),
					new MyPoint(6, 4, 24), new MyPoint(6, 3, 25), new MyPoint(6, 2, 26), new MyPoint(6, 1, 27),
					new MyPoint(6, 0, 28), new MyPoint(5, 0, 29), new MyPoint(5, 1, 0), new MyPoint(5, 2, 0),
					new MyPoint(5, 3, 0), new MyPoint(5, 4, 0), new MyPoint(1, 5, 0), new MyPoint(2, 5, 0),
					new MyPoint(3, 5, 0), new MyPoint(4, 5, 0), new MyPoint(5, 6, 0), new MyPoint(5, 7, 0),
					new MyPoint(5, 8, 0), new MyPoint(5, 9, 0), new MyPoint(6, 5, 0), new MyPoint(7, 5, 0),
					new MyPoint(8, 5, 0), new MyPoint(9, 5, 0) };

			addMouseListener(this);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int laenge = this.getWidth() / 11;
			Graphics2D g2 = (Graphics2D) g;
			Color tmpColor = g2.getColor();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			for (int i = 0; i < this.feld.length; i++) {
				for (int j = 0; j < feld[i].length; j++) {
					Color currentColor = myFeldColor(feld[i][j]);
					if (currentColor == null)
						continue;
					g2.setColor(currentColor);
					g2.fillOval(j * laenge + laenge / 10, i * laenge + laenge / 10, laenge - laenge / 5,
							laenge - laenge / 5);
					g2.setColor(Color.BLACK);
					g2.drawOval(j * laenge + laenge / 10, i * laenge + laenge / 10, laenge - laenge / 5,
							laenge - laenge / 5);
				}
			}

			MyPoint position = new MyPoint();

			if (clickedPoint != null) {

				for (int i = 0; i < this.figurenPositionen.length; i++) {
					if (clickedPoint.x == this.figurenPositionen[i].x
							&& clickedPoint.y == this.figurenPositionen[i].y) {
						clickedPoint.setIdx(this.figurenPositionen[i].getIdx());

						position.x = figurenPositionen[i + c.dice()].y;
						position.y = figurenPositionen[i + c.dice()].x;
						break;
					}
				}

				position.x = position.x * laenge;
				position.y = position.y * laenge;

				if (c.meinSpieler() == 0) {
					g2.setColor(Color.RED.darker());
					g2.fillOval(position.x + laenge / 4, position.y + laenge / 4, laenge / 2, laenge / 2);
					g2.setColor(Color.RED.darker().darker());
					g2.fillOval(position.x + laenge / 3, position.y + laenge / 3, laenge / 3, laenge / 3);
				}

				if (c.meinSpieler() == 1) {
					g2.setColor(Color.BLUE.darker());
					g2.fillOval(position.x + laenge / 4, position.y + laenge / 4, laenge / 2, laenge / 2);
					g2.setColor(Color.BLUE.darker().darker());
					g2.fillOval(position.x + laenge / 3, position.y + laenge / 3, laenge / 3, laenge / 3);
				}
				if (c.meinSpieler() == 2) {
					g2.setColor(Color.YELLOW.darker());
					g2.fillOval(position.x + laenge / 4, position.y + laenge / 4, laenge / 2, laenge / 2);
					g2.setColor(Color.YELLOW.darker().darker());
					g2.fillOval(position.x + laenge / 3, position.y + laenge / 3, laenge / 3, laenge / 3);
				}
				if (c.meinSpieler() == 3) {
					g2.setColor(Color.PINK.darker());
					g2.fillOval(position.x + laenge / 4, position.y + laenge / 4, laenge / 2, laenge / 2);
					g2.setColor(Color.PINK.darker().darker());
					g2.fillOval(position.x + laenge / 3, position.y + laenge / 3, laenge / 3, laenge / 3);
				}
			}
			int red = -1;
			int blue = -1;
			int yellow = -1;
			int pink =-1;
			for (int player = 0; player < 4; player++) {
				for (int block = 0; block < 4; block++) {
					Color stoneColor = Color.WHITE;
					char s = c.getTokenColorBlock(player, block);
					char h = c.getTokenColorHouse(player, block);
					
					
					if (s != ' ') {
							switch (s) {
							case 'R' :	
								red++;
							stoneColor = Color.RED;
							position.x = stoneBlock0[red].x;
							position.y = stoneBlock0[red].y;
								break;
							case 'B' :	
								blue++;
							stoneColor = Color.BLUE;
							position.x = stoneBlock1[blue].x;
							position.y = stoneBlock1[blue].y;
								break;
							case 'G' :	
								yellow++;
							stoneColor = Color.YELLOW;
							position.x = stoneBlock2[yellow].x;
							position.y = stoneBlock2[yellow].y;
								break;
							case 'P' :	
								pink++;
							stoneColor = Color.PINK;
							position.x = stoneBlock3[pink].x;
							position.y = stoneBlock3[pink].y;
								break;
							}
						position.x = position.x * laenge;
						position.y = position.y * laenge;
						g2.setColor(stoneColor.darker());
						g2.fillOval(position.x + laenge / 4, position.y + laenge / 4, laenge / 2, laenge / 2);
						g2.setColor(stoneColor.darker().darker());
						g2.fillOval(position.x + laenge / 3, position.y + laenge / 3, laenge / 3, laenge / 3);
					} else {
						for (int j = 0; j < 4; j++) {

							for (int i = 0; i < figurenPositionen.length; i++) {
								if (c.isStoneHere(figurenPositionen[i].getIdx()) != -1 ) {
									if (c.isStoneHere(figurenPositionen[i].getIdx())==0) {
										stoneColor = Color.RED;
									}
									if (c.isStoneHere(figurenPositionen[i].getIdx())==1) {
										stoneColor = Color.BLUE;
									}
									if (c.isStoneHere(figurenPositionen[i].getIdx())==2)  {
										stoneColor = Color.YELLOW;
									}
									if (c.isStoneHere(figurenPositionen[i].getIdx())==3)  {
										stoneColor = Color.PINK;
									}
									position.x = figurenPositionen[i].y;
									position.y = figurenPositionen[i].x;
									position.x = position.x * laenge;
									position.y = position.y * laenge;
									g2.setColor(stoneColor.darker());
									g2.fillOval(position.x + laenge / 4, position.y + laenge / 4, laenge / 2,
											laenge / 2);
									g2.setColor(stoneColor.darker().darker());
									g2.fillOval(position.x + laenge / 3, position.y + laenge / 3, laenge / 3,
											laenge / 3);
									if(h != ' '){
										
									}
								}

							}
						}

					}
				
					
					
					
					
					
					
					
				}

			}

			g2.setColor(tmpColor);

		}

		// private int myStoneColor(int i) {
		// MyPoint position = new MyPoint();
		// switch (i) {
		// case 0:
		// position.x = 0;
		// position.y = 0;
		// case 1:
		// case 2:
		// case 3:
		// break;
		// case 4:
		// case 5:
		// case 6:
		// case 7:
		// break;
		// case 8:
		// case 9:
		// case 10:
		// case 11:
		// break;
		// case 12:
		// case 13:
		// case 14:
		// case 15:
		// break;
		//
		// }
		// return i;
		// }

		private Color myFeldColor(int i) {
			Color currentColor = null;
			switch (i) {
			case '0':
				break;
			case 'R':
				currentColor = Color.RED;
				break;
			case 'B':
				currentColor = Color.BLUE;
				break;
			case 'G':
				currentColor = Color.YELLOW;
				break;
			case 'P':
				currentColor = Color.PINK;
				break;
			case 'x':
				currentColor = Color.WHITE;
				break;
			case ' ':
				currentColor = Color.LIGHT_GRAY;
			default:
				break;
			}

			return currentColor;

		}

		@Override
		public void mouseClicked(MouseEvent e) {

			for (int i = 0; i < this.figurenPositionen.length; i++) {

				int size = this.getWidth();
				clickedPoint = new MyPoint(e.getY() * 11 / size, e.getX() * 11 / size);
				int l = e.getY() * 11 / size;
				int m = e.getX() * 11 / size;
				boolean figurExistiert = false;

				if (l == this.figurenPositionen[i].x && m == this.figurenPositionen[i].y) {
					clickedPoint.setIdx(this.figurenPositionen[i].getIdx());
			//	if(c.isStoneHere(figurenPositionen[i].getIdx()) == 0)
					if (c.moveStart()) {
						if (c.move(this.figurenPositionen[clickedPoint.getIdx()].idx + 10))
							repaint();
					}
					break;
					
					
					
				}
			}

		}

		@Override
		public void mouseEntered(MouseEvent e) {
 
		}

		@Override
		public void mouseExited(MouseEvent e) {
 
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

	}
	 
	@Override
	public void update(Player currentPlayer, boolean gameEnded) {

	}

	@Override
	public void showDice(Player currentplayer, int dice) {
 
	}

}