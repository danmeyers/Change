package image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

import javax.swing.Timer;

public class SwitchTimer extends Timer implements KeyListener {

	long startTime;
		
	public SwitchTimer(int delay, ActionListener listener) {
		super(delay, listener);
		
	}
	
	@Override
	public void start() {
		startTime = new Date().getTime();
		super.start();
	}
	
	public long switchStop() {
		super.stop();
		return startTime;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) fireActionPerformed(new ActionEvent(this, 0, null, e.getWhen() - startTime, 0));
	}

	@Override
	public void keyReleased(KeyEvent e) {

		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

		//if (e.getKeyCode() == KeyEvent.VK_SPACE) switchStop();		
	}

}
