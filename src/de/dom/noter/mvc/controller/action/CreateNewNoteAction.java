package de.dom.noter.mvc.controller.action;

import de.dom.noter.mvc.controller.NotesController;

public class CreateNewNoteAction implements Action {

	NotesController notesController;
	private long createdId;

	public CreateNewNoteAction(final NotesController notesController) {
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
