package net.bobmandude9889.lock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

public class Display extends JPanel{
	private static final long serialVersionUID = 1L;

	int width;
	int height;
	
	public String code = "";
	
	public String secret = "1234";
	
	Color background = Color.RED;
	
	public Display(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.maxTime *= (60*1000);
		this.maxTime -= 1;
		
		System.out.println("display init");
	}
	
	int boxWidth = 250;
	int boxHeight = 100;
	
	long maxTime = 30;
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(background);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.PLAIN,200));
		long timeLeft = maxTime - (Lock.endTime - Lock.startTime);
		SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss.SSS");
		String timeText = timeFormat.format(new Date(timeLeft));
		g.drawString(timeText, width / 2 - (g.getFontMetrics().stringWidth(timeText) / 2), height / 2 - 100);

		g.setFont(new Font("Arial",Font.PLAIN,100));
		String text = "Please enter the code below.";
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, width / 2 - textWidth / 2, height / 2 + 100);
		g.setColor(Color.WHITE);
		int boxX = width / 2 - boxWidth / 2;
		int boxY = height / 2 - boxHeight / 2 + 200;
		g.fillRect(boxX, boxY, boxWidth, boxHeight);
		g.setColor(Color.BLACK);
		g.drawString(code, width / 2 - g.getFontMetrics().stringWidth(code) / 2, boxY + boxHeight - 15);
	}
	
}
