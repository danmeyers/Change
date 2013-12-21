package image;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Framework {

	private int PICTURE_SHOW_MILLISECOND;

	private int PICTURE_2_SHOW_MILLISECOND;

	private int BLANK_SHOW_MILLISECOND;
	
	private int PER_PIC_TIME_LIMIT;

	ArrayList<FilePair> imagePairs = new ArrayList<FilePair>();
	
	JFrame frame;

	private FilePair currentPair;
	
	public Framework()  {
		frame = new JFrame();
		
        frame.setLayout(new FlowLayout());
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Test");
        
		if (!getOptions()) return;

		
		 picSwitch = new Timer(PICTURE_SHOW_MILLISECOND, picSwitchListener);
		// picSwitch2 = new FlickerTimer(PICTURE_2_SHOW_MILLISECOND, a);
		
		 blank = new FlickerTimer(BLANK_SHOW_MILLISECOND, blankListener);

	}


	public void run() {
		for (FilePair fp : imagePairs){
			//frame.setSize(fp.first.getIconWidth(), fp.first.getIconHeight());
			testPair(fp);
		//	JLabel ll = new JLabel();
		//	ll.setIcon(fp.first);
		//	ll.setBackground(Color.red);
		//	ll.validate();
			//frame.repaint();
			//frame.add(ll);
			//System.out.println(ll.isVisible());
		}
	}
	
	volatile long go = 0;
	//Stage stage = Stage.STAGE_0;
	
	ActionListener picSwitchListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.remove(currentImages[0]);
			frame.remove(currentImages[1]);
			
			picSwitch.stop();
			
			//TODO get rid of some of these
			frame.repaint();
			frame.validate();
			frame.repaint();

			
			blank.start();
			
		}
		
	};
	
	ActionListener blankListener = new ActionListener() {

		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (blank.chooseFirst()) frame.add(currentImages[0]);
			else frame.add(currentImages[1]);
			
			
			blank.stop();
			blank.toggle();
			
			//TODO get rid of some of these
			frame.repaint();
			frame.validate();
			frame.repaint();

			
			picSwitch.start();
			
			//switchImage();
			//listResult(switchImage() - e.getWhen()); // TODO make this work
		}
		
	};
	
	//FlickerTimer currentTimer;
	
//	FlickerTimer picSwitch1;// = new SwitchTimer(PICTURE_SHOW_MILLISECOND, a);
//	FlickerTimer picSwitch2;// = new SwitchTimer(PICTURE_2_SHOW_MILLISECOND, a);
	
	FlickerTimer blank ;//= new SwitchTimer(BLANK_SHOW_MILLISECOND, a);
	Timer picSwitch;
	
	JLabel[] currentImages = new JLabel[2];
	
	public void testPair(FilePair fp){
		
		go = 0;
		
        JLabel image1 = new JLabel();
        image1.setIcon(fp.first);
//        
//        image1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
//        image1.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        JLabel image2 = new JLabel();
        image2.setIcon(fp.second);

//        image2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
//        image2.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        currentImages[0] = image1;
        currentImages[1] = image2;
		
       // stage = Stage.STAGE_0;
        
		SwitchTimer main = new SwitchTimer(PER_PIC_TIME_LIMIT, new ActionListener() {
						
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e);
				//System.out.println(new ActionEvent(this, 0, null, e.getWhen(), 0));
				moveToNext(e.getWhen());
				
				picSwitch.stop();				
				blank.reset();
				
				frame.remove(currentImages[0]);
				frame.remove(currentImages[1]);
			}
			
			
			
		});
		
		frame.addKeyListener(main);
		
	//	currentTimer = picSwitch1;
		
		//frame.add(image2);
		
		frame.add(currentImages[0], BorderLayout.CENTER);
		//TODO get rid of some
		frame.repaint();
		frame.validate();
		frame.repaint();
		
		picSwitch.start();
		blank.toggle();
		main.start();
		

		
//		switchImage();
		
		while (go == 0) {}

		
		fp.result = go;
		
		frame.removeKeyListener(main);
		main.switchStop();
	}


//	protected Long switchImage() {
//		//ll.validate();
//		//frame.repaint();
//		//frame.add(ll);
//
//		Long toReturn = null;
//		
//		if (stage == Stage.STAGE_0) {
//			System.out.println("stage 1");
//			stage = Stage.STAGE_1;
//			System.out.println(frame.getComponents());
//			//frame.remove(1);
//			frame.add(currentImages[0], BorderLayout.CENTER);
//			currentTimer = picSwitch1;
//		}
//		else if (stage == Stage.STAGE_1) {
//			System.out.println("stage 2");
//
//			stage = Stage.STAGE_2;
//			frame.remove(currentImages[0]);
//			currentTimer.stop();
//
//			currentTimer = blank;
//		}
//		else if (stage == Stage.STAGE_2) {
//			System.out.println("stage 3");
//
//			stage = Stage.STAGE_3;
//			frame.add(currentImages[1], BorderLayout.CENTER);
//			currentTimer.stop();
//
//			currentTimer = picSwitch2;
//
//		}
//		else if (stage == Stage.STAGE_3) {
//			
//			System.out.println("stage 4");
//			
//			stage = Stage.STAGE_4;
//			frame.remove(currentImages[1]);
//			currentTimer.stop();
//			
//			currentTimer = blank;
//		}
//		else if (stage == Stage.STAGE_4) {
//			System.out.println("stage 1");
//
//			stage = Stage.STAGE_1;
//			frame.add(currentImages[0], BorderLayout.CENTER);
//			
//			//currentTimer.restart();
//			currentTimer.stop();
//
//			currentTimer = picSwitch1;
//
//		}
//		frame.repaint();
//		frame.validate();
//		frame.repaint();
//		currentTimer.start();
//		return toReturn;
//	}


	protected void moveToNext(long l) {
		go = l;
	}

	public void shutdown() {
		for (FilePair fp : imagePairs){
			if (fp.result > PER_PIC_TIME_LIMIT) fp.result = Double.POSITIVE_INFINITY;
			System.out.println(fp.result / 1000);
			try {
				output.write(fp.name + ": " + fp.result + "ms");	
				output.newLine();
				System.out.println("AGEAGE");
			} catch (IOException e) {
				System.err.println("error writing to fle");
			}
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			e.printStackTrace();
		} catch (CancelException e){
			//cancelled
		}
		framework.run();
		framework.shutdown();
	}
	
	private boolean getOptions() {
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
		JOptionPane.showOptionDialog(null,message,"demo",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,null,null,null);

		try {
		PICTURE_SHOW_MILLISECOND = Integer.parseInt(jtf1.getText());

		PICTURE_2_SHOW_MILLISECOND = Integer.parseInt(jtf1.getText());

		BLANK_SHOW_MILLISECOND = Integer.parseInt(jtf2.getText());
		
		PER_PIC_TIME_LIMIT = Integer.parseInt(jtf3.getText());

		
		} catch (NumberFormatException e) {
			return false;
		}
		
		System.out.println(PICTURE_SHOW_MILLISECOND);
		return true;
	}

	BufferedWriter output;


	private void getFile() throws IOException, CancelException {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));

		BufferedReader input;
		String abspath;
		
		if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getAbsolutePath();
			abspath = fc.getSelectedFile().getParentFile().getAbsolutePath();
			//InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
			FileInputStream in = new FileInputStream(fc.getSelectedFile());
			System.out.println(path);
			input = new BufferedReader(new InputStreamReader(in));
		} else throw new CancelException();
		
		
		if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			String path = fc.getSelectedFile().getAbsolutePath();
			File out = new File(path);
			if (!out.exists()) out.createNewFile();
			FileWriter fw = new FileWriter(path);
			output = new BufferedWriter(fw);
			
		} else throw new CancelException();
		
		if (input != null) {
			String curLine = input.readLine();
			while (curLine != null) {
				ImageIcon n = new ImageIcon(ImageIO.read(new File(abspath + "/" + curLine)));
				curLine = input.readLine();
				if (curLine == null) throw new CancelException();
				ImageIcon m = new ImageIcon(ImageIO.read(new File(abspath + "/" + curLine)));
		        imagePairs.add(new FilePair(n,m, curLine));
				curLine = input.readLine();
		        System.out.println(curLine);
			}
			
			input.close();
		}
		
	}

}
