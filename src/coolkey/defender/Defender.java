/* Copyright 2009 Michał Dettlaff, Karol Domagała, Łukasz Draba
 *
 * This file is part of CoolKey.
 *
 * CoolKey is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package coolkey.defender;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Shell;

/**
 * Klasa główna gry Defender.
 */
public class Defender {
	/**
	 * Uchwyt do shella.
	 */
	private final Shell shell;
	/**
	 * Obszar na którym będzie wyświetlana gra.
	 */
	private final Canvas container;
	/**
	 * Obiekt mechanizmu gry.
	 */
	private final Engine engine;
	/**
	 * Obiekt rysujący grę na ekranie.
	 */
	private final Paint paint;
	/**
	 * Konstruktor klasy
	 * @param shell - uchwyt do shella.
	 */
	public Defender(Shell shell) {
		this.shell = shell;
		this.container = new Canvas(this.shell, SWT.NO_SCROLL | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		this.container.setSize(Engine.WIDTH, Engine.HEIGHT);
		this.engine = new Engine(this.container);
		this.paint = new Paint(this.container.getDisplay(), this.engine);
		this.container.addPaintListener(this.paint);
		this.container.addKeyListener(new Key(this.engine));
		this.container.addMouseListener(new Mouse(this.engine));
		this.container.addMouseMoveListener(new MouseMove(this.engine));
	}
	/**
	 * Zwraca wymiary obszaru na której będzie wyświetlana gra.
	 * @return wymiary obszaru
	 */
	public Point getSize() {
		return this.container.getSize();
	}
	/**
	 * Zwraca przesunięcie obszaru od górnej lewej krawędzi okna.
	 * @return wymiary przesunięcia
	 */
	public Point getLocation() {
		return this.container.getLocation();
	}
	/**
	 * Ustawia przesunięcie obszaru od górnej lewej krawędzi okna.
	 * @param point wymiary przesunięcia
	 */
	public void setLocation(Point point) {
		this.container.setLocation(point);
	}
	/**
	 * Ustawia czy ilość klatek na sekundę ma być widoczna na ekranie.
	 * @param showFps - true - wyświetl, false - nie wyświetlaj
	 */
	public void showFps(boolean showFps) {
		this.engine.showFps(showFps);
	}
	/**
	 * Uruchamia mechanizm animacji.
	 */
	public void start() {
		this.engine.start();
	}
	/**
	 * Zatrzymuje mechanizm gry.
	 */
	public void stop() {
		this.engine.stop();
	}
	/**
	 * Zatrzymuje mechanizm gry oraz zwalnia wszystkie zajęte zasoby.
	 */
	public void dispose() {
		this.stop();
		if(!this.container.isDisposed())
			this.container.dispose();
		this.paint.dispose();
		if(!this.shell.isDisposed())
			this.dispose();
	}
}
