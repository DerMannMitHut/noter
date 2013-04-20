package de.dom.noter.main;

import java.io.File;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.NotesControllerImpl;
import de.dom.noter.mvc.model.Model;
import de.dom.noter.mvc.model.NotesIO;
import de.dom.noter.mvc.model.User;
import de.dom.noter.swing.MainWindow;

public class Noter {

	private static final File PATH = new File( "/Users/dom/tmp" );

	public static void main( final String[] args ) {
		final MainWindow mw = new MainWindow();
		final Model m = NotesIO.createModelFromFiles( PATH );

		final User dom = new User().setName( "Dom" );

		// for( int i = 0; i < 20; ++i ) {
		// m.changeNote( m.createNote( dom ).setTitle( "bla" + i ).setContent(
		// "b\nlu\n\nbb" + i ) );
		// }

		final NotesController nc = new NotesControllerImpl( m, mw );

		final NotesIO io = new NotesIO( m, PATH );

		mw.open();
	}
}