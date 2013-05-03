package de.dom.noter.mvc.controller.command;

import java.util.Collections;

import de.dom.noter.mvc.controller.NoteController;

public class UCSetTitle extends UndoableCommand {

	NoteController noteController;
	private final String newTitle;

	public UCSetTitle(final NoteController noteController, final String newTitle) {
		this.noteController = noteController;
		this.newTitle = newTitle;
	}

	@Override
	public void performInternal() {
		setUndoNotes( Collections.singleton( noteController.getNote() ) );
		setRedoNotes( Collections.singleton( noteController.setTitle( newTitle ) ) );
	}

}
