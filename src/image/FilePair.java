package image;

import javax.swing.ImageIcon;

public class FilePair {

	public ImageIcon first;
	public ImageIcon second;
	public double result = Double.POSITIVE_INFINITY;
	public String name;
	
	public FilePair(ImageIcon a, ImageIcon b, String curLine) {
		first = a;
		second = b;
		name = curLine;
	}
	
}
