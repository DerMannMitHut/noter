package de.dom.noter.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.controller.command.CreateNewNoteCommand;

public class CreateNewNoteAction extends AbstractNoterAction {
	private final CommandControl commandControl;
	private final NotesController notesController;

	private CreateNewNoteAction(final CommandControl commandControl, final NotesController notesController) {
		this.commandControl = commandControl;
		this.notesController = notesController;
		putValue( Action.NAME, "New Note" );
	}

	@Override
	public void actionPerformed( final ActionEvent e ) {
		commandControl.doCommand( new CreateNewNoteCommand( notesController ) );
	}

	public static Action create( final ActionData data ) {
		return new CreateNewNoteAction( data.commandControl, data.notesController );
	}

}
