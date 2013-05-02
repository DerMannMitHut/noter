package de.dom.noter.main;

import javax.swing.UIManager;

import de.dom.noter.framework.Persistence;
import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.NotesControllerImpl;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.model.Model;
import de.dom.noter.mvc.model.NotesIO;
import de.dom.noter.swing.MainWindow;

public class Noter {

	private static final Persistence PERSISTENCE = new Persistence();

	public static String getApplicationQualifiedName() {
		return Noter.class.getName();
	}

	public static String getApplicationName() {
		return Noter.class.getSimpleName();
	}

	public static void main( final String[] args ) throws Exception {
		initGuiLookAndFeel();

		final CommandControl cc = new CommandControl();
		final MainWindow mw = new MainWindow( cc );

		final Model m = new Model();
		final NotesIO io = new NotesIO( m, getPersistence() );
		io.readModelFromFiles();

		m.addNotesChangedListener( io );

		final NotesController nc = new NotesControllerImpl( m, mw );
		mw.setController( nc );

		mw.open();
	}

	static void initGuiLookAndFeel() throws Exception {
		System.setProperty( "apple.laf.useScreenMenuBar", "true" );
		System.setProperty( "com.apple.mrj.application.apple.menu.about.name", "Noter" );
		System.setProperty( "apple.awt.brushMetalLook", "true" );
		UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
	}

	public static Persistence getPersistence() {
		return PERSISTENCE;
	}
}