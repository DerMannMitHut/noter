package de.dom.noter.swing.action;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;

final class ActionData {

	final CommandControl commandControl;
	final NotesController notesController;

	public ActionData(final NotesController notesController, final CommandControl commandControl) {
		this.commandControl = commandControl;
		this.notesController = notesController;
	}

}
