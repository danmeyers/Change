package image;

import javax.swing.ImageIcon;

public class FilePair {

	public ImageIcon first;
	public ImageIcon second;
	public double result = Double.POSITIVE_INFINITY;
	
	FilePair(ImageIcon a, ImageIcon b) {
		first = a;
		second = b;
	}
	
}
