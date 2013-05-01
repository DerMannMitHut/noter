package de.dom.noter.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.controller.command.CreateNewNoteCommand;
import de.dom.noter.swing.MainWindow;

public class CreateNewNoteAction extends AbstractNoterAction {
	private final CommandControl commandControl;
	private final NotesController notesController;
	private final MainWindow mainWindow;

	private CreateNewNoteAction(final CommandControl commandControl, final NotesController notesController, final MainWindow mainWindow) {
		this.commandControl = commandControl;
		this.notesController = notesController;
		this.mainWindow = mainWindow;
		putValue( Action.NAME, "New Note" );
		putValue( Action.SHORT_DESCRIPTION, "Creates a new note." );
		putValue( Action.LONG_DESCRIPTION, getValue( Action.SHORT_DESCRIPTION ) );
		putValue( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke( "meta N" ) );
	}

	@Override
	public void actionPerformed( final ActionEvent e ) {
		commandControl.doCommand( new CreateNewNoteCommand( notesController ) );
		mainWindow.selectFirstTitle();
	}

	public static Action create( final ActionData data ) {
		return new CreateNewNoteAction( data.commandControl, data.notesController, data.mainWindow );
	}

}
