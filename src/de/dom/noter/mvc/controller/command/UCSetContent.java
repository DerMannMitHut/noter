package de.dom.noter.mvc.controller.command;

import java.util.Collections;

import de.dom.noter.mvc.controller.NoteController;

public class UCSetContent extends UndoableCommand {

	NoteController noteController;
	private final String newContent;

	public UCSetContent(final NoteController noteController, final String newContent) {
		this.noteController = noteController;
		this.newContent = newContent;
	}

	@Override
	public void performInternal() {
		setUndoNotes( Collections.singleton( noteController.getNote() ) );
		setRedoNotes( Collections.singleton( noteController.setContent( newContent ) ) );
	}
}
