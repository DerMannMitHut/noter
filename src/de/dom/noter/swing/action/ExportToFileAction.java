package de.dom.noter.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JFileChooser;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.controller.command.ExportToFileCommand;
import de.dom.noter.swing.MainWindow;

public class ExportToFileAction extends AbstractNoterAction {
	private final CommandControl commandControl;
	private final NotesController notesController;
	private final MainWindow mainWindow;

	private ExportToFileAction(final CommandControl commandControl, final NotesController notesController, final MainWindow mainWindow) {
		this.commandControl = commandControl;
		this.notesController = notesController;
		this.mainWindow = mainWindow;
		putValue( Action.NAME, "Export notes" );
		putValue( Action.SHORT_DESCRIPTION, "Create a human readable file, wich contains all notes." );
		putValue( Action.LONG_DESCRIPTION, getValue( Action.SHORT_DESCRIPTION ) );
	}

	@Override
	public void actionPerformed( final ActionEvent e ) {
		final JFileChooser chooser = new JFileChooser();
		final int result = chooser.showSaveDialog( mainWindow );
		if( result == JFileChooser.APPROVE_OPTION ) {
			commandControl.doCommand( new ExportToFileCommand( notesController, chooser.getSelectedFile() ) );
		}
	}

	public static Action create( final ActionData data ) {
		return new ExportToFileAction( data.commandControl, data.notesController, data.mainWindow );
	}

}
