package GUIProject.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
//import javax.swing.JLabel;
//import javax.swing.JTextField;

//import javafx.scene.web.WebView;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ScrollPaneConstants;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class TestConponent {

	private JFrame frame;
//	private JTextField textField;
//	private JTextField textField_1;
//	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestConponent window = new TestConponent();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestConponent() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new MigLayout("", "[]", "[][]"));
		int num = 10;
		for(int i = 0;i<num;i++){
			JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
			panel.add(chckbxNewCheckBox, "cell 0 "+i);
		}
		
		ArrayList<Integer> al = new ArrayList<>();
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component[] compnent = panel.getComponents();
				for(int i = 0;i<num;i++){
					JCheckBox tmp = (JCheckBox) compnent[i];
					if(tmp.isSelected()){
						al.add(i);
						System.out.println(i);
					}
				}
			}
		});
		panel.add(btnNewButton, "cell 0 "+num);
		
		
		
		
		
		
/*
		for (int i = 0; i < 50; i++) {
			JLabel l = new JLabel("aaaa");
			frame.add(l);
		}*/
		
		/*
		 * 
		 * frame.getContentPane().setLayout(new MigLayout("", "[][grow]",
		 * "[][]"));
		 * 
		 * JLabel lblNewLabel = new JLabel("New label");
		 * frame.getContentPane().add(lblNewLabel, "cell 0 0,alignx trailing");
		 * 
		 * textField = new JTextField(); frame.getContentPane().add(textField,
		 * "cell 1 0,growx"); textField.setColumns(10);
		 * 
		 * JLabel lblNewLabel_1 = new JLabel("New label");
		 * frame.getContentPane().add(lblNewLabel_1,
		 * "cell 0 1,alignx trailing");
		 * 
		 * textField_1 = new JTextField();
		 * frame.getContentPane().add(textField_1, "cell 1 1,growx");
		 * textField_1.setColumns(10);
		 */
	}
}
