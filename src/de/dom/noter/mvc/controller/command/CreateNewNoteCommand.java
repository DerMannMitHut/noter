package de.dom.noter.mvc.controller.command;

import de.dom.noter.mvc.controller.NotesController;

public class CreateNewNoteCommand implements UndoableCommand {

	NotesController notesController;
	private long createdId;

	public CreateNewNoteCommand(final NotesController notesController) {
		this.notesController = notesController;
	}

	@Override
	public void redo() {
		createdId = notesController.createNewNote();
	}

	@Override
	public void undo() {
		notesController.removeNote( createdId );
	}

}
