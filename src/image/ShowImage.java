package image;

/* ShowImage.java

This program loads and displays an image from a file.

mag-13May2008
updated 20Feb2009 by mag to incorporate suggestions
by mazing and iofthestorm on digg.
 */

//Import the basic graphics classes.
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ShowImage extends JPanel{
    public static void main(String avg[]) throws IOException
    {
    	ShowImage abc=new ShowImage();
    }

    public ShowImage() throws IOException
    {
        BufferedImage img=ImageIO.read(new File("src/images/f.png"));
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}