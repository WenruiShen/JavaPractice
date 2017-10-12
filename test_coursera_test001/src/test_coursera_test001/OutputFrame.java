package test_coursera_test001;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Coursera Java(PekU) week-2.3
class OutputFrame extends JFrame{
	JTextField in = new JTextField(10);
	JButton btn = new JButton("Make Square");
	JLabel out = new JLabel("Label used to display results");
	
	OutputFrame(){
		setLayout(new FlowLayout());
		getContentPane().add(in);
		getContentPane().add(btn);
		getContentPane().add(out);
		//btn.addActionListener(new BtnActionAdapter());
		btn.addActionListener( e->{
			String s = in.getText();
			double d = Double.parseDouble(s);
			double sq = d * d;
			out.setText(d + "'s square is: " + sq);
		});
		setSize(400, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	class BtnActionAdapter implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s = in.getText();
			double d = Double.parseDouble(s);
			double sq = d * d;
			out.setText(d + "'s square is: " + sq);
		}
	}
}

//Coursera Java(PekU) week-3.3.3


