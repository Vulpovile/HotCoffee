package com.androdome.hotcoffee.applettest;

import javax.swing.JApplet;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.androdome.hotcoffee.server.GraphicalUserInterface;
import com.androdome.hotcoffee.server.HotCoffeeServer;

public class AppletTest extends JApplet{

	private static final long serialVersionUID = 0;
	public JPanel contentPane = new JPanel();
	public JTextField commandBar;
	public JTextArea ramUsage = new JTextArea();
	public JTextArea serverField = new JTextArea();
	boolean urgent = false;
	JList list = new JList();
	HotCoffeeServer main = new HotCoffeeServer();
	
	public void init()
	{
		this.setVisible(true);
		main.gui = new GraphicalUserInterface(main);
		HotCoffeeServer.applet = true;
		this.setContentPane(main.gui.contentPane);
		this.setSize(main.gui.getSize());
		main.startup();
	}
}
