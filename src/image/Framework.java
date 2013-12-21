package image;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Framework {
	
	JFrame frame;

	Options options;
	
	ArrayList<FilePair> imagePairs = new ArrayList<FilePair>();

	public Framework(Options options)  {
		frame = new JFrame();
		
        frame.setLayout(new FlowLayout());
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Test");
        
        this.options = options;
        
        
	//	if (!getOptions()) return;
		
		 picSwitch = new Timer(options.picMilli, picSwitchListener);
		 
		 blank = new FlickerTimer(options.blankMilli, blankListener);
		
		 imagePairs = options.imagePairs;
		 
		 main.setInitialDelay(options.perPicMilli);
	}

	
	SwitchTimer main = new SwitchTimer(0, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			go = e.getWhen();
			
			picSwitch.stop();				
			blank.reset();
			
			frame.remove(currentImages[0]);
			frame.remove(currentImages[1]);
			frame.validate();
			frame.repaint();
		}
		
	});

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
	
	
	ActionListener picSwitchListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.remove(currentImages[0]);
			frame.remove(currentImages[1]);
			frame.validate();
			frame.repaint();

			
			picSwitch.stop();
			blank.start();
		}
	};
	
	ActionListener blankListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (blank.chooseFirst()) frame.add(currentImages[0]);
			else frame.add(currentImages[1]);
			frame.validate();
			frame.repaint();

			blank.stop();
			blank.toggle();
						
			picSwitch.start();
		}
	};
		
	FlickerTimer blank;
	Timer picSwitch;
	
	JLabel[] currentImages = new JLabel[2];
	
	

	
	volatile long go;
	
	public void testPair(FilePair fp){
		
		go = 0;
		
        JLabel image1 = new JLabel();
        image1.setIcon(fp.first);

        JLabel image2 = new JLabel();
        image2.setIcon(fp.second);

        currentImages[0] = image1;
        currentImages[1] = image2;
		        
		frame.addKeyListener(main);
		
		frame.add(currentImages[0]);
		frame.validate();
		frame.repaint();
		
		picSwitch.start();
		blank.toggle();
		main.start();
		
		while (go == 0) {}
		frame.removeKeyListener(main);

		fp.result = go;
		
		main.stop();
	}

	
	public void shutdown() {
		for (FilePair fp : imagePairs){
			if (fp.result > options.perPicMilli) fp.result = options.perPicMilli;
			System.out.println(fp.result / 1000);
			try {
				options.output.write(fp.name + ": " + fp.result + "ms");	
				options.output.newLine();
				System.out.println("AGEAGE");
			} catch (IOException e) {
				System.err.println("error writing to fle");
			}
		}
		try {
			options.output.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error printing to file.");
		}
		frame.setVisible(false);
		frame.dispose();
	}



}
