package coolkey.defender;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * Klasa obsługująca zdarzenia na klawiaturze.
 */
public class Key implements KeyListener {
	/**
	 * Stała zdefiniowana jako klawisz Enter.
	 */
	private final int KEY_ENTER = 13;
	/**
	 * Uchwyt do mechanizmu gry.
	 */
	private	 Engine engine;
	
	/**
	 * Tworzy nowy obiekt obsługujący zdarzenia na klawiaturze.
	 * @param engine uchwyt do mechanizmu gry
	 */
	public Key(Engine engine) {
		this.engine = engine;
	}

	public void keyPressed(KeyEvent ke) {
		switch(this.engine.getState()) {
			case Engine.STATE_MENU:
				switch(ke.keyCode) {
					case SWT.ARROW_UP:
						this.engine.keyPrev();
						break;
					case SWT.ARROW_DOWN:
						this.engine.keyNext();
						break;
					case KEY_ENTER:
						this.engine.keyEnter();
						break;
				}
				break;
			case Engine.STATE_GAME:
				switch(ke.keyCode) {
					case SWT.BS:
						this.engine.gameWordDel();
						break;
					case SWT.ESC:
						this.engine.keyEsc();
						break;
					default:
						this.engine.gameWordAdd(ke.character);
				}
				break;
			case Engine.STATE_RESULT:
			case Engine.STATE_TOP10:
			case Engine.STATE_HELP:
				if(ke.keyCode == SWT.ESC)
						this.engine.keyEsc();
				break;
		}
	}

	public void keyReleased(KeyEvent ke) {

	}
}
