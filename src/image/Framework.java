package image;

import java.awt.Color;
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

	private static final int PICTURE_SHOW_MILLISECOND = 2000;

	private static final int PICTURE_2_SHOW_MILLISECOND = 2000;

	private static final int BLANK_SHOW_MILLISECOND = 100;

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
		//	JLabel ll = new JLabel();
		//	ll.setIcon(fp.first);
		//	ll.setBackground(Color.red);
			//frame.setSize(fp.first.getIconWidth(), fp.first.getIconHeight());
		//	ll.validate();
			//frame.repaint();
			//frame.add(ll);
			//System.out.println(ll.isVisible());
		}
	}
	
	boolean go = true;
	Stage stage = Stage.STAGE_0;

	ActionListener a = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			switchImage();
			e.getWhen(); // TODO make this work
		}
		
	};
	
	Timer currentTimer;
	
	Timer picSwitch1 = new Timer(PICTURE_SHOW_MILLISECOND, a);
	Timer picSwitch2 = new Timer(PICTURE_2_SHOW_MILLISECOND, a);
	
	Timer blank = new Timer(BLANK_SHOW_MILLISECOND, a);

	JLabel[] currentImages = new JLabel[2];
	
	public void testPair(FilePair fp){
		
        JLabel image1 = new JLabel();
        image1.setIcon(fp.first);
        
        JLabel image2 = new JLabel();
        image2.setIcon(fp.second);

        currentImages[0] = image1;
        currentImages[1] = image2;
		
		Timer timer = new Timer(30000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveToNext(e.getWhen());
			}
			
		});
		
		currentTimer = picSwitch1;
		
		//frame.add(image2);
		frame.repaint();
		frame.validate();
		frame.repaint();
		timer.start();
		switchImage();
		
		while (go) {
//			if (stage == Stage.STAGE_1) {
//				currentTimer = picSwitch1;
//			}
//			else if (stage == Stage.STAGE_2) {
//				currentTimer = blank;
//			}
//			else if (stage == Stage.STAGE_3) {
//				currentTimer = picSwitch2;
//			}
		}
		
		timer.stop();
	}
	
	protected void switchImage() {
		//ll.validate();
		//frame.repaint();
		//frame.add(ll);

		if (stage == Stage.STAGE_0) {
			System.out.println("stage 0");
			stage = Stage.STAGE_1;
			System.out.println(frame.getComponents());
			//frame.remove(1);
			frame.add(currentImages[0]);
			currentTimer = picSwitch1;
		}
		else if (stage == Stage.STAGE_1) {
			System.out.println("stage 1");

			stage = Stage.STAGE_2;
			frame.removeAll();
			currentTimer = blank;
		}
		else if (stage == Stage.STAGE_2) {
			System.out.println("stage 2");

			stage = Stage.STAGE_3;
			frame.add(currentImages[1]);
			currentTimer = picSwitch2;

		}
		else if (stage == Stage.STAGE_3) {
			System.out.println("stage 3");

			stage = Stage.STAGE_1;
			frame.remove(1);
			frame.add(currentImages[0]);
			currentTimer = picSwitch1;

		}
		frame.repaint();
		frame.validate();
		frame.repaint();
		currentTimer.restart();
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
		//framework.shutdown();
	}


	private void getFile()throws IOException {
        ImageIcon n = new ImageIcon(ImageIO.read(new File("src/images/f.png")));
        imagePairs.add(new FilePair(n,n));
	}

}
