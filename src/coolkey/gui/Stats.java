package coolkey.gui;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import coolkey.CoolKey;

/**
 * Wyświetla statystyki użytkownika.
 */
public class Stats {
	private static Shell statsShell;
	private final int LEFT_MARGIN = 5;
	private final int BOTTOM_MARGIN = 10;
	private final int BOTTOM_MARGIN2 = 20;
	private final int LEGEND_WIDTH = 150;
	

	public Stats() {
		statsShell = new Shell(GUI.shell, SWT.DIALOG_TRIM
				| SWT.APPLICATION_MODAL | SWT.RESIZE);
		statsShell.setText("Statystyka");
		statsShell.setLayout(new FillLayout());
		statsShell.setMinimumSize(760, 600);

		CTabFolder stats = new CTabFolder(statsShell, SWT.BORDER | SWT.MULTI);

		CTabItem speed = new CTabItem(stats, SWT.NONE);
		speed.setText("Prędkość  ");
		CTabItem accuracy = new CTabItem(stats, SWT.NONE);
		accuracy.setText("Poprawność  ");

		Composite speedComp = new Composite(stats, SWT.NONE);
		speedComp.setLayout(new GridLayout(1, false));
		Composite accuracyComp = new Composite(stats, SWT.NONE);
		accuracyComp.setLayout(new FillLayout());
		speed.setControl(speedComp);
		accuracy.setControl(accuracyComp);
		
		Group speedType = new Group(speedComp, SWT.SHADOW_IN);
		speedType.setLayout(new GridLayout());
		speedType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		final Button speedTest = new Button(speedType, SWT.RADIO);
		speedTest.setText("Szybkość dla poszczególnych testów");
		speedTest.setSelection(true);
		Button speedKeys = new Button(speedType, SWT.RADIO);
		speedKeys.setText("Szybkość dla poszczególnych liter");
		
		final Canvas speedCanv = new Canvas(speedComp, SWT.BORDER);
		speedCanv.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		/*Canvas accCanv = */new Canvas(accuracyComp, SWT.BORDER);
		//TODO canvas statystyk poprawności
		
		speedCanv.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				GC gc = pe.gc;
				Point canvasSize = speedCanv.getSize();
				gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
				gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLACK));
				gc.fillRectangle(0, 0, canvasSize.x, canvasSize.y); // tło

				int x=20;	//początkowy odstęp od lewej
				int inc = canvasSize.x / CoolKey.getUser().getStatistics().getSpeeds().size()-2; //odstęp między słupkami
				
				if (speedTest.getSelection()) {
					/*
					 * Osie i etykiety
					 */
					gc.drawLine(5, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN);		//oś x 
					gc.drawString("znak/min", 7, 5);
					gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN, canvasSize.x-10, canvasSize.y-BOTTOM_MARGIN);	//oś y 
					gc.drawString("Test", canvasSize.x-30, canvasSize.y-30);	
					
					/*
					 * Legenda
					 */
					gc.drawRectangle(canvasSize.x-(LEGEND_WIDTH +10), 5, LEGEND_WIDTH, 40);
					gc.drawString("Prędkość", canvasSize.x-(LEGEND_WIDTH-20), 8);
					gc.drawString("Prędkość realna", canvasSize.x-(LEGEND_WIDTH-20), 24);
					gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
					gc.fillRectangle(canvasSize.x-(LEGEND_WIDTH + 5), 12, 10, 10);
					gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
					gc.fillRectangle(canvasSize.x-(LEGEND_WIDTH + 5), 27, 10, 10);
					
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_RED));
					/*
					 * Prędkość.
					 */
					List<Double> speeds = CoolKey.getUser().getStatistics().getSpeeds();
					for (int i=0; i<speeds.size(); i++) {
						double speed = speeds.get(i);
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
						gc.fillOval(x-3, canvasSize.y-(int)speed-BOTTOM_MARGIN-3, 6, 6); 
						/*
						 * prędkość nad słupkiem
						 */
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						gc.drawString(String.format("%.2f", speed), x-10, canvasSize.y-(int)speed-BOTTOM_MARGIN-30);
						if (i>0) {
							gc.drawLine(x, canvasSize.y-(int)speed-BOTTOM_MARGIN, x-inc, canvasSize.y-speeds.get(i-1).intValue()-BOTTOM_MARGIN);
						}
						
						x+=inc; //zwiększenie odstępu do następnego słupka
					}
					/*
					 * Prędkość realna. (analogicznie do wyżej)
					 */
					x = 20;
					gc.setForeground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
					speeds = CoolKey.getUser().getStatistics().getRealSpeeds();
					for (int i=0; i<speeds.size(); i++) {
						double speed2 = speeds.get(i);
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_BLUE));
						gc.fillOval(x-3, canvasSize.y-(int)speed2-BOTTOM_MARGIN-3, 6, 6);
						if (i>0) {
							gc.drawLine(x, canvasSize.y-(int)speed2-BOTTOM_MARGIN+1, x-inc, canvasSize.y-speeds.get(i-1).intValue()-BOTTOM_MARGIN+1);
						}
						x+=inc;
					}
				} else {
					
					Font font = new Font(GUI.display,"Arial",8, SWT.None);
					gc.setFont(font);
					gc.drawLine(5, 5, LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN2);		//oś x 
					gc.drawString("Prędkość", 7, 5);
					gc.drawLine(LEFT_MARGIN, canvasSize.y-BOTTOM_MARGIN2, canvasSize.x-10, canvasSize.y-BOTTOM_MARGIN2);	//oś y 
					Map<Character, Double> litery = CoolKey.getUser().getStatistics().getCharSpeeds();
					inc = canvasSize.x / CoolKey.getUser().getStatistics().getCharSpeeds().size();
					
					Iterator it = litery.entrySet().iterator();
				    while (it.hasNext()) {
				        Map.Entry<Character, Double> pair = (Map.Entry<Character, Double>)it.next();
				        double speed = (Double)pair.getValue();
				        gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_RED));
						Rectangle rec = new Rectangle(x, canvasSize.y-(int)speed-BOTTOM_MARGIN2, 10, (int)speed);
						gc.fillRectangle(rec);
						
						gc.setBackground(GUI.display.getSystemColor(SWT.COLOR_WHITE));
						gc.drawString(String.format("%.2f", speed), x-10, canvasSize.y-(int)speed-BOTTOM_MARGIN2-30);
						if (pair.getKey() == ' ')
							gc.drawString("spac.", x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						else if (pair.getKey() == '\r')
							gc.drawString("ent.", x, canvasSize.y-BOTTOM_MARGIN2+3, true);
						else
							gc.drawString(pair.getKey().toString(), x, canvasSize.y-BOTTOM_MARGIN2+3);
						x+=inc;
				    }
				    gc.dispose();
				}
			}
		});
		
		System.out.println("Statystyki dla kolejnych ukończonych testów:");
		System.out.println("Prędkość: "
				+ CoolKey.getUser().getStatistics().getSpeeds());
		System.out.println("Realna prędkość: "
				+ CoolKey.getUser().getStatistics().getRealSpeeds());
		System.out.println("Poprawność: "
				+ CoolKey.getUser().getStatistics().getAccuracies());
		System.out.println("Statystyki dla poszczególnych znaków:");
		// Enter to '\r' (powrót karetki)
		System.out.println("Prędkość: "
				+ CoolKey.getUser().getStatistics().getCharSpeeds());
		System.out.println("Poprawność: "
				+ CoolKey.getUser().getStatistics().getCharAccuracies());

		statsShell.setSize(400, 300);
		
		speedTest.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				speedCanv.redraw();
				statsShell.setSize(760, 600);
			}
		});
		speedKeys.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				speedCanv.redraw();
				statsShell.setSize(GUI.display.getBounds().width, 600);
			}
		});
	}

	public void open() {
		statsShell.open();
	}
}
