package de.dom.noter.mvc.controller.command;

import java.util.Collections;

import de.dom.noter.mvc.controller.NotesController;

public class UCCreateNewNote extends UndoableCommand {

	private final NotesController notesController;

	public UCCreateNewNote(final NotesController notesController) {
		this.notesController = notesController;
	}

	@Override
	public void performInternal() {
		setRedoNotes( Collections.singleton( notesController.createNewNote() ) );
	}
}
