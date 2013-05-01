package de.dom.noter.mvc.controller.command;

import de.dom.noter.mvc.controller.NoteController;

public class SetTitleCommand implements UndoableCommand {

	NoteController noteController;
	private final String newTitle;
	private String oldTitle;

	public SetTitleCommand(final NoteController noteController, final String newTitle) {
		this.noteController = noteController;
		this.newTitle = newTitle;
	}

	@Override
	public void redo() {
		oldTitle = noteController.setTitle( newTitle );
	}

	@Override
	public void undo() {
		noteController.setTitle( oldTitle );
	}

}
