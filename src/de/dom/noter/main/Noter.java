package de.dom.noter.main;

import java.io.File;

import javax.swing.UIManager;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.NotesControllerImpl;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.model.Model;
import de.dom.noter.mvc.model.NotesIO;
import de.dom.noter.swing.MainWindow;

public class Noter {

	private static final File PATH = new File( "/Users/dom/tmp/noter" );

	public static void main( final String[] args ) throws Exception {
		initGuiLookAndFeel();

		final CommandControl cc = new CommandControl();
		final MainWindow mw = new MainWindow( cc );

		final Model m = new Model();
		final NotesIO io = new NotesIO( m, PATH );
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
}