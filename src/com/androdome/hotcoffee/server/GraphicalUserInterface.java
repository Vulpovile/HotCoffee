package com.androdome.hotcoffee.server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JTextArea;

import java.awt.Font;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class GraphicalUserInterface extends JFrame implements ListSelectionListener {

	private static final long serialVersionUID = 0;
	private JPanel contentPane;
	public JTextField commandBar;
	public JTextArea ramUsage = new JTextArea();
	public JTextArea serverField = new JTextArea();
	boolean urgent = false;
	JList list = new JList();
	
	public GraphicalUserInterface() {	
		setResizable(false);
	    try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} 
		catch (Exception e) {
			e.printStackTrace();
		}
	     
		setTitle("HotCoffee Version PRE 0.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(133, 11, 541, 369);
		scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(scrollPane);
		serverField.setForeground(Color.BLACK);
		serverField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		serverField.setEditable(false);
		serverField.setLineWrap(true);
		scrollPane.setViewportView(serverField);
		
		commandBar = new JTextField();
		commandBar.setBounds(133, 391, 541, 20);
		contentPane.add(commandBar);
		commandBar.setColumns(10);
		commandBar.setBorder(BorderFactory.createLoweredBevelBorder());
		
		list.setFont(new Font("Tahoma", Font.PLAIN, 11));
		list.addListSelectionListener(this);
		String[] items = new String[]{"Commands:", ""};
		list.setListData(items);
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		list.setBounds(10, 144, 113, 236);
		list.setForeground(Color.BLACK);
		contentPane.add(list);
		
		JLabel lblCommand = new JLabel("Command:");
		lblCommand.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCommand.setBounds(10, 394, 113, 14);
		contentPane.add(lblCommand);
		ramUsage.setForeground(Color.BLACK);
		ramUsage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ramUsage.setText("Memory Availible:\r\n\r\n\r\nMemory Allocated:\r\n\r\n\r\nMemory Free:");
		ramUsage.setEditable(false);
		

		ramUsage.setBackground(Color.WHITE);
		ramUsage.setBounds(10, 11, 113, 122);
		ramUsage.setBorder(BorderFactory.createLoweredBevelBorder());
		contentPane.add(ramUsage);
	}
		public void write(String info)
		{
			this.serverField.append(info + "\r\n");
		}
		public void tickMemory(String m, String u, String f, boolean urgency)
		{
			if(urgency && !urgent)
			{
				this.write("###SEVERE! You are running out of memory!###");
				ramUsage.setForeground(Color.RED);
				urgent = true;
			}
			else if(!urgency && urgent)
			{
				ramUsage.setForeground(Color.RED);
				urgent = false;
			}
			ramUsage.setText("Memory Availible:\r\n" + m + "\r\n\r\nMemory Used:\r\n" + u + "\r\n\r\nMemory Free:\r\n" + f);
		}
		@Override
		public void valueChanged(ListSelectionEvent e) 
		{
			if(list.getSelectedIndex() > -1)
			{
				commandBar.setText(String.valueOf(list.getSelectedValue()));
			}
		}
}
