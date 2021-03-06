package coolkey.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import coolkey.CoolKey;
import coolkey.CourseFactory;
import coolkey.Lesson;
import coolkey.Markov;
import coolkey.TypingTest;
import coolkey.Utils;
import coolkey.defender.Defender;
import coolkey.defender.Engine;

/**
 * Pasek menu.
 */
public class MenuBar {

	/**
	 * Przypisanie tytułów do nazw plików tekstowych.
	 */
	private Properties textTitles;
	private final MenuItem continueItem;

	public MenuBar() {
		textTitles = new Properties();
		try {
			FileInputStream fis =
				new FileInputStream(CoolKey.TEXT_DIRECTORY + "titles.xml");
			textTitles.loadFromXML(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Menu menu = new Menu(GUI.shell, SWT.BAR);

		/* Menu Użytkownik */
		final MenuItem user = new MenuItem(menu, SWT.CASCADE);
		user.setText("&Użytkownik");

		final Menu userMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		user.setMenu(userMenu);
		final MenuItem addUserItem = new MenuItem(userMenu, SWT.PUSH);
		addUserItem.setText("&Nowy użytkownik");
		final MenuItem changeUserItem = new MenuItem(userMenu, SWT.PUSH);
		changeUserItem.setText("&Zmień użytkownika");
		final MenuItem settingsItem = new MenuItem(userMenu, SWT.PUSH);
		settingsItem.setText("&Ustawienia");
		final MenuItem statsItem = new MenuItem(userMenu, SWT.PUSH);
		statsItem.setText("&Statystyka");
		new MenuItem(userMenu, SWT.SEPARATOR);
		final MenuItem exitMenuItem = new MenuItem(userMenu, SWT.PUSH);
		exitMenuItem.setText("&Zakończ");

		/* Kurs */
		final MenuItem course = new MenuItem(menu, SWT.CASCADE);
		course.setText("&Kurs");

		final Menu courseMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		course.setMenu(courseMenu);
		continueItem = new MenuItem(courseMenu, SWT.PUSH);
		continueItem.setText("Kontynuuj aktualny kurs");
		if (CoolKey.getUser().getCourses().size() == 0) {
			continueItem.setEnabled(false);
		}
		final MenuItem newCourseItem = new MenuItem(courseMenu, SWT.PUSH);
		newCourseItem.setText("Rozpocznij nowy kurs");
		final MenuItem courseManagerItem = new MenuItem(courseMenu, SWT.PUSH);
		courseManagerItem.setText("Zarządzanie kursami");

		/* Pojedynczy test */
		final MenuItem singleTest = new MenuItem(menu, SWT.CASCADE);
		singleTest.setText("&Pojedynczy test");
		// teksty literackie
		final Menu singleTestMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		singleTest.setMenu(singleTestMenu);
		MenuItem preparedTextItem = new MenuItem(singleTestMenu, SWT.CASCADE);
		preparedTextItem.setText("Teksty literackie");
		Menu preparedTextMenu = new Menu(singleTestMenu);
		preparedTextItem.setMenu(preparedTextMenu);
		final MenuItem normalItem = new MenuItem(preparedTextMenu, SWT.CASCADE);
		normalItem.setText("Standardowe");
		Menu normalMenu = new Menu(singleTestMenu);
		normalItem.setMenu(normalMenu);
		textsSubmenu(normalMenu, CoolKey.TEXT_DIRECTORY + "normal");
		final MenuItem hardItem = new MenuItem(preparedTextMenu, SWT.CASCADE);
		hardItem.setText("Specjalistyczne");
		Menu hardMenu = new Menu(singleTestMenu);
		hardItem.setMenu(hardMenu);
		textsSubmenu(hardMenu, CoolKey.TEXT_DIRECTORY + "hard");
		final MenuItem verseItem = new MenuItem(preparedTextMenu, SWT.CASCADE);
		verseItem.setText("Wiersze");
		Menu verseMenu = new Menu(singleTestMenu);
		verseItem.setMenu(verseMenu);
		textsSubmenu(verseMenu, CoolKey.TEXT_DIRECTORY + "verse");

		MenuItem fileTextItem = new MenuItem(singleTestMenu, SWT.PUSH);
		fileTextItem.setText("Tekst z pliku");
		MenuItem autoTextItem = new MenuItem(singleTestMenu, SWT.PUSH);
		autoTextItem.setText("Automatycznie wygenerowany tekst");
		// lekcje QWERTY
		MenuItem qwertyLessonsItem = new MenuItem(singleTestMenu, SWT.CASCADE);
		qwertyLessonsItem.setText("Lekcje QWERTY");
		Menu qwertyLessonsMenu = new Menu(singleTestMenu);
		for (final Lesson lesson : CourseFactory.qwertyLessons()) {
			final MenuItem lessonItem = new MenuItem(qwertyLessonsMenu, SWT.PUSH);
			lessonItem.setText(lesson.getName());
			qwertyLessonsItem.setMenu(qwertyLessonsMenu);
			lessonItem.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					String text = lesson.getText();
					CoolKey.setCurrentTest(new TypingTest(text, false));
					GUI.refresh();
			    	int style = SWT.ICON_INFORMATION;
			    	MessageBox messageBox = new MessageBox(GUI.shell, style);
			    	messageBox.setText("Instrukcja");
			    	messageBox.setMessage(lesson.getInstructions());
			    	messageBox.open();
				}
			});
		}
		// lekcje Dvorak
		MenuItem dvorakLessonsItem = new MenuItem(singleTestMenu, SWT.CASCADE);
		dvorakLessonsItem.setText("Lekcje Dvorak");
		Menu dvorakLessonsMenu = new Menu(singleTestMenu);
		for (final Lesson lesson : CourseFactory.dvorakLessons()) {
			final MenuItem lessonItem = new MenuItem(dvorakLessonsMenu, SWT.PUSH);
			lessonItem.setText(lesson.getName());
			dvorakLessonsItem.setMenu(dvorakLessonsMenu);
			lessonItem.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					String text = lesson.getText();
					CoolKey.setCurrentTest(new TypingTest(text, false));
					GUI.refresh();
			    	int style = SWT.ICON_INFORMATION;
			    	MessageBox messageBox = new MessageBox(GUI.shell, style);
			    	messageBox.setText("Instrukcja");
			    	messageBox.setMessage(lesson.getInstructions());
			    	messageBox.open();
				}
			});
		}
		// wprawki
		MenuItem practiceItem = new MenuItem(singleTestMenu, SWT.CASCADE);
		practiceItem.setText("Wprawki");
		Menu practiceMenu = new Menu(singleTestMenu);
		practiceItem.setMenu(practiceMenu);
		String[] QWERTY = { "qwertyuiop", "asdfghjkl;", "zxcvbnm,./" };
        String[] Dvorak = { "',.pyfgcrl", "aoeuidhtns", ";qjkxbmwvz" };
		final String LETTERS = QWERTY[0] + QWERTY[1] + QWERTY[2];
		// wprawki - QWERTY
		practiceMenuItem(practiceMenu, "QWERTY, palce małe",
				LETTERS, "qazp;/");
		practiceMenuItem(practiceMenu, "QWERTY, palce serdeczne",
				LETTERS, "wsxol.");
		practiceMenuItem(practiceMenu, "QWERTY, palce środkowe",
				LETTERS, "edcik,");
		practiceMenuItem(practiceMenu, "QWERTY, palce wskazujące",
				LETTERS, "rfvtgbyhnujm");
		practiceMenuItem(practiceMenu, "QWERTY, górny rząd",
				QWERTY[0] + QWERTY[1], QWERTY[0]);
		practiceMenuItem(practiceMenu, "QWERTY, środkowy rząd",
				QWERTY[1], QWERTY[1]);
		practiceMenuItem(practiceMenu, "QWERTY, dolny rząd",
				QWERTY[1] + QWERTY[2], QWERTY[2]);
		// wprawki - Dvorak
		practiceMenuItem(practiceMenu, "Dvorak, palce małe",
				LETTERS, "'a;lsz");
		practiceMenuItem(practiceMenu, "Dvorak, palce serdeczne",
				LETTERS, ",oqrnv");
		practiceMenuItem(practiceMenu, "Dvorak, palce środkowe",
				LETTERS, ".ejctw");
		practiceMenuItem(practiceMenu, "Dvorak, palce wskazujące",
				LETTERS, "pukyixfdbghm");
		practiceMenuItem(practiceMenu, "Dvorak, górny rząd",
				Dvorak[0] + Dvorak[1], Dvorak[0]);
		practiceMenuItem(practiceMenu, "Dvorak, środkowy rząd",
				Dvorak[1], Dvorak[1]);
		practiceMenuItem(practiceMenu, "Dvorak, dolny rząd",
				Dvorak[1] + Dvorak[2], Dvorak[2]);
		// test spersonalizowany
		MenuItem customTestItem = new MenuItem(singleTestMenu, SWT.PUSH);
		customTestItem.setText("Test spersonalizowany");

		/* Gra */
		final MenuItem game = new MenuItem(menu, SWT.CASCADE);
		game.setText("&Gra");

		final Menu gameMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		game.setMenu(gameMenu);
		final MenuItem startItem = new MenuItem(gameMenu, SWT.PUSH);
		startItem.setText("Rozpocznij grę");

		/* Pomoc */
		final MenuItem help = new MenuItem(menu, SWT.CASCADE);
		help.setText("P&omoc");

		final Menu helpMenu = new Menu(GUI.shell, SWT.DROP_DOWN);
		help.setMenu(helpMenu);
		final MenuItem helpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpItem.setText("Tematy pomocy");
		final MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
		aboutItem.setText("O programie");

		GUI.shell.setMenuBar(menu);

		addUserItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				AddUser addShell = new AddUser();
				addShell.open();
			}
		});
		changeUserItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ChangeUser changeShell = new ChangeUser(false);
				changeShell.open();
			}
		});
		newCourseItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				NewCourse newCourseShell = new NewCourse();
				newCourseShell.open();
			}
		});
		statsItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Stats statsShell = new Stats();
				statsShell.open();
			}
		});
		exitMenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				CoolKey.persistState();
				System.exit(0);
			}
		});
		courseManagerItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				CourseManager cmShell = new CourseManager();
				cmShell.open();
			}
		});
		settingsItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				UserSettings usShell = new UserSettings();
				usShell.open();
			}
		});
		continueItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				CoolKey.setCurrentTest(new TypingTest(CoolKey.getUser()
						.getCurrentCourse().getCurrentLesson().getText(),
						true));
				GUI.refresh();
				GUI.buttonBar.showLessonInstructions();
			}
		});
		// załaduj plik tekstowy
		fileTextItem.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog (GUI.shell, SWT.OPEN);
				dialog.setFileName("*.txt");
				dialog.setFilterNames (new String[] {"*.txt", "Wszystkie pliki"});
				dialog.setFilterExtensions (new String[] {"*.txt", "*" });
				dialog.setText("Otwórz plik");
				String result = dialog.open();

				if (result != null) {
					String text;
					try {
						text = Utils.readFileAsString(new File(result));
						CoolKey.setCurrentTest(new TypingTest(text, false));
						GUI.refresh();
					} catch (IOException e) {
						MessageBox messageBox = new MessageBox(GUI.shell, SWT.ICON_ERROR);
						messageBox.setMessage("Nie można otworzyć pliku.");
						messageBox.open();
					}
				}
			}
		});
		// automatycznie wygenerowany tekst
		autoTextItem.addListener(SWT.Selection,  new Listener() {
			public void handleEvent(Event event) {
				final Shell shell = new Shell(GUI.shell, SWT.DIALOG_TRIM
						| SWT.APPLICATION_MODAL);
				shell.setText("Autotekst");
				shell.setLayout(new GridLayout(2, false));

				Composite settings = new Composite(shell, SWT.NONE);
				settings.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
				settings.setLayout(new GridLayout(2, false));

				Label minLines = new Label(settings, SWT.NONE);
				minLines.setText("Ilość linii (minimum):");
				final Spinner spinner = new Spinner(settings, SWT.BORDER);
				spinner.setMinimum(1);
				spinner.setMaximum(100);
				spinner.setSelection(10);
				spinner.setIncrement(1);
				spinner.setPageIncrement(5);

				Composite buttons = new Composite(shell, SWT.NONE);
				buttons.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 2, 1));
				buttons.setLayout(new GridLayout(2, false));

				Composite left = new Composite(buttons, SWT.NONE);
				left.setLayout(new GridLayout());
				Composite right = new Composite(buttons, SWT.NONE);
				right.setLayout(new GridLayout());

				Button confirm = new Button(left, SWT.PUSH);
				confirm.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
				confirm.setText(" Wygeneruj ");
				Button cancel = new Button(right, SWT.PUSH);
				cancel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
				cancel.setText(" Anuluj ");

				Listener generate = new Listener() {
					@Override
					public void handleEvent(Event event) {
						int minGenTextLines = spinner.getSelection();
						int minGenTextLength = (minGenTextLines - 1) * (
								CoolKey.MAX_CHARS_IN_LINE - 1);
						CoolKey.setCurrentTest(new TypingTest(
								Markov.generateMarkovChain(
										Utils.words(
												CoolKey.TEXT_NORM_DIRECTORY),
												minGenTextLength), false));
						GUI.refresh();
						shell.dispose();
					}
				};
				confirm.addListener(SWT.Selection, generate);
				spinner.addListener(SWT.DefaultSelection, generate);

				cancel.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event event) {
						shell.close();
					}
				});

				shell.pack();
				shell.open();
			}
		});
		// test spersonalizowany
		customTestItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				new CustomTest();
			}
		});
		// rozpocznij grę
		startItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				final Shell shell = new Shell(GUI.shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				shell.setText("Defender");
				Point p = shell.computeSize(Engine.WIDTH, Engine.HEIGHT);
				shell.setSize(p);
				shell.setMinimumSize(p);
				Defender defender = new Defender(shell);
				// defender.showFps(true);
				defender.start();
				shell.open();
				while (!shell.isDisposed()){
					if (!GUI.display.readAndDispatch()) GUI.display.sleep();
				}
				defender.dispose();
			}
		});
		// pomoc programu
		helpItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				new Help().open();
			}
		});
		// informacje o programie
		aboutItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				new About();
			}
		});
	}

	private void textsSubmenu(Menu menu, String txtDirectory) {
		File[] files = new File(txtDirectory).listFiles(
				Utils.TEXTFILE_FILTER);
		Arrays.sort(files);
		for (File file : files) {
			final String text;
			try {
				text = Utils.readFileAsString(file, "UTF-8");
				MenuItem textItem = new MenuItem(menu, SWT.PUSH);
				String title = textTitles.getProperty(file.getName());
				title = title == null ? "Nieznany plik tekstowy" : title;
				textItem.setText(title);
				textItem.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event event) {
						CoolKey.setCurrentTest(new TypingTest(text, false));
						GUI.refresh();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void practiceMenuItem(Menu menu, String title,
			String allowedChars, String practicedChars) {
		final Lesson lesson =
			new Lesson(allowedChars, practicedChars, title, "");
		MenuItem textItem = new MenuItem(menu, SWT.PUSH);
		textItem.setText(lesson.getName());
		textItem.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String text = lesson.getText();
				CoolKey.setCurrentTest(new TypingTest(text, false));
				GUI.refresh();
			}
		});
	}

	public void refresh() {
		continueItem.setEnabled(CoolKey.getUser().getCourses().size() > 0);
	}
}
