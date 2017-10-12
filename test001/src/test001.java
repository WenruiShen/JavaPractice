import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



public class test001 {
	
	public static void main(String args[]){
		//System.out.println("HelloWorld!");
		
		APPGraphInput a = new APPGraphInput();
		a.start();
	}
}


class APPGraphInput{
	public void start(){
		new AppFrame();
	}
}

class AppFrame extends JFrame{
	JTextField in = new JTextField(10);
	JButton btn = new JButton("Get Square");
	JLabel out = new JLabel("Label used to display result");
	
	public AppFrame(){
		setLayout( new FlowLayout() );
		getContentPane().add( in );
		getContentPane().add( btn );
		getContentPane().add( out );
		btn.addActionListener( new BtnActionAdapter() );
		setSize( 400, 100 );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	class BtnActionAdapter implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String s = in.getText();
			double d = Double.parseDouble(s);
			double sq = d * d; //Math.sqrt(d);
			out.setText(d + " squared equals " + sq);
		}
	}
	
}



