package de.dom.noter.main;

import java.io.File;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.NotesControllerImpl;
import de.dom.noter.mvc.controller.action.ActionControl;
import de.dom.noter.mvc.model.Model;
import de.dom.noter.mvc.model.NotesIO;
import de.dom.noter.swing.MainWindow;

public class Noter {

	private static final File PATH = new File( "/Users/dom/tmp/noter" );

	public static void main( final String[] args ) {
		final ActionControl ac = new ActionControl();
		final MainWindow mw = new MainWindow( ac );

		final Model m = new Model();
		final NotesIO io = new NotesIO( m, PATH );
		io.readModelFromFiles();

		m.addNotesChangedListener( io );

		final NotesController nc = new NotesControllerImpl( m, mw );

		mw.open();
	}
}