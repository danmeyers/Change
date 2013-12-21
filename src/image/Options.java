package image;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Options {

	
	public BufferedWriter output;
	public int picMilli;
	public int blankMilli;
	public int perPicMilli;
	public ArrayList<FilePair> imagePairs = new ArrayList<FilePair>();
	
	public void chooseFiles() throws IOException, CancelException {
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));

		BufferedReader input;
		String abspath;
		
		int optionChosen;
		
		//OPEN FILE
		
		optionChosen = fc.showOpenDialog(null);

		if (optionChosen != JFileChooser.APPROVE_OPTION) throw new CancelException();

		
		String path = fc.getSelectedFile().getAbsolutePath();
		abspath = fc.getSelectedFile().getParentFile().getAbsolutePath();
		FileInputStream in = new FileInputStream(fc.getSelectedFile());

		input = new BufferedReader(new InputStreamReader(in));
		
		
		// PROCESSS OPENED FILE
		
		if (input != null) {
			String curLine = input.readLine();
			while (curLine != null) {
				ImageIcon n;
				try {
					n = new ImageIcon(ImageIO.read(new File(abspath + "/" + curLine)));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "File " + curLine + " not found.");
					in.close();
					input.close();
					throw new IOException();
				}
				curLine = input.readLine();
				if (curLine == null) {
					in.close();
					input.close();
					JOptionPane.showMessageDialog(null, "Input file error. Odd number of files.");
					throw new CancelException();
				}
				ImageIcon m;
				
				try {
					m = new ImageIcon(ImageIO.read(new File(abspath + "/" + curLine)));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "File " + curLine + " not found.");
					in.close();
					input.close();
					throw new IOException();
				}
		        imagePairs.add(new FilePair(n,m, curLine));
				curLine = input.readLine();
		        System.out.println(curLine);
			}
			
		}
		
		in.close();
		input.close();
		
		// save file
		
		optionChosen = fc.showSaveDialog(null);
		
		if (optionChosen != JFileChooser.APPROVE_OPTION) throw new CancelException();

		path = fc.getSelectedFile().getAbsolutePath();
		File out = new File(path);
		if (!out.exists()) out.createNewFile();
		FileWriter fw = new FileWriter(path);
		output = new BufferedWriter(fw);
			
	}

	public boolean setTimes() {
		
		while (true) {
			JPanel p1 = new JPanel();
			p1.add(new JLabel("Picture time (ms):"));
			JTextField jtf1 = new JTextField(9);
			jtf1.setText("240");
			p1.add(jtf1);
			
			JPanel p2 = new JPanel();
			p2.add(new JLabel("Blank time (ms):"));
			JTextField jtf2 = new JTextField(9);
			jtf2.setText("30");
			p2.add(jtf2);
			
			JPanel p3 = new JPanel();
			p2.add(new JLabel("Time limit per picture (ms):"));
			JTextField jtf3 = new JTextField(9);
			jtf3.setText("30000");
			p2.add(jtf3);
	
			
			Object[] message = {p1, p2, p3};
			
			int getBack = JOptionPane.showOptionDialog(null,message,"demo",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,null,null);
			
			if (getBack == JOptionPane.CANCEL_OPTION || getBack == JOptionPane.CLOSED_OPTION) return false;
			
			try {
				picMilli = Integer.parseInt(jtf1.getText());
				blankMilli = Integer.parseInt(jtf2.getText());
				perPicMilli = Integer.parseInt(jtf3.getText());
				break;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter numbers for these options.");
			}
		}
		
		return true;
	}

	
}
