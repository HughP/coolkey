package coolkey.defender;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;

public class MouseMove implements MouseMoveListener {
	private Engine engine;
	
	public MouseMove(Engine engine) {
		this.engine = engine;
	}

	public void mouseMove(MouseEvent me) {
		switch(this.engine.getState()) {
		case Engine.STATE_MENU:
			this.engine.mouseMove(me.x, me.y);
			break;
		}
	}
}
