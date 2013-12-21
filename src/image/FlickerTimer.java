package image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class FlickerTimer extends Timer {

	private boolean first;
		
	public FlickerTimer(int delay, ActionListener a) {
		super(delay, a);
		first = true;
	}
	
	public void toggle() {
		first = !first;
	}
	
	public void reset() {
		first = true;
		this.stop();
	}
	
	public boolean chooseFirst() {
		return first;
	}

}
