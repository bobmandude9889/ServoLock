package net.bobmandude9889.lock;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.fazecast.jSerialComm.SerialPort;

public class Lock {

	public static PrintStream ardOut;
	
	static boolean locked = true;
	
	public static void main(String[] args) {
		SerialPort[] ports = SerialPort.getCommPorts();
		String[] names = new String[ports.length];
		
		for(int i = 0; i < ports.length; i++) {
			names[i] = ports[i].getDescriptivePortName();
		}
		
		int index = JOptionPane.showOptionDialog(null, "Choose the COM port that the arduino is on.", "Port", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, names, 0);
		
		String code = JOptionPane.showInputDialog("Enter the code.");
		
		if(index == -1)
			System.exit(0);
		
		SerialPort port = ports[index];
		
		port.openPort();
		port.setBaudRate(9600);
		ardOut = new PrintStream(port.getOutputStream());
		try {
			Thread.sleep(1000l);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		ardOut.print(0);
		JFrame frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		Display display = new Display(frame.getWidth(), frame.getHeight());
		display.secret = code;
		frame.add(display);
		
		frame.setVisible(true);
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(c >= 48 && c <= 57) {
					if(display.code.length() < 4)
						display.code += c;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && display.code.length() > 0) {
					display.code = display.code.substring(0,display.code.length() - 1);
				} else if(e.getKeyCode() == KeyEvent.VK_ENTER && display.code.length() == 4) {
					if(display.code.equals(display.secret)) {
						locked = false;
						ardOut.print(180);
						display.background = Color.GREEN;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		while(true) {
			display.width = frame.getWidth();
			display.height = frame.getHeight();
			//ardOut.print(locked ? 0 : 180123);
			display.paintImmediately(0, 0, display.getWidth(), display.getHeight());
			try {
				Thread.sleep(10l);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
