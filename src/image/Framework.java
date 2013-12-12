package image;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Framework {

	private static final int PICTURE_SHOW_MILLISECOND = 0;

	ArrayList<FilePair> imagePairs = new ArrayList<FilePair>();
	
	JFrame frame;
	
	public Framework()  {
		frame = new JFrame();
		
        frame.setLayout(new FlowLayout());
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Test");
	}


	public void run() {
		for (FilePair fp : imagePairs){
			testPair(fp);
		}
		
		
	}
	
	boolean go = true;
	Stage stage = Stage.STAGE_1;
	
	public void testPair(FilePair fp){
		
        JLabel image1 = new JLabel();
        image1.setIcon(fp.first);
        
        JLabel image2 = new JLabel();
        image1.setIcon(fp.second);

		
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveToNext(e.getWhen());
				
			}
			
		});
		
		Timer picSwitch = new Timer(PICTURE_SHOW_MILLISECOND, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switchImage();
			}
			
		});
		
        frame.add(image1);
		timer.start();
		
		while (go) {
			if (stage == Stage.STAGE_1) {
				frame.remove(image2);
				if (frame.frame.add(image1);
			}
			else if (stage == Stage.STAGE_2) {
				frame.remove(image1);
			}
			else if (stage == Stage.STAGE_3) {
				frame.add(image2);
			}
		}
		
		timer.stop();
	}
	
	protected void switchImage() {
		if (stage == Stage.STAGE_1) stage = Stage.STAGE_2;
		else if (stage == Stage.STAGE_2) stage = Stage.STAGE_3;
		else if (stage == Stage.STAGE_3) stage = Stage.STAGE_1;
	}


	protected void moveToNext(long l) {
		go = false;
	}

	public void shutdown() {
		frame.setVisible(false);
		frame.dispose();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Framework framework = new Framework();
		try {
			framework.getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		framework.run();
		framework.shutdown();
	}


	private void getFile()throws IOException {
        ImageIcon n = new ImageIcon(ImageIO.read(new File("src/images/f.png")));
        imagePairs.add(new FilePair(n,n));
	}

}
