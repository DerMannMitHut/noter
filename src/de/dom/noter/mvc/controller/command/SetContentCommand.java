package de.dom.noter.mvc.controller.command;

import de.dom.noter.mvc.controller.NoteController;

public class SetContentCommand implements UndoableCommand {

	NoteController noteController;
	private final String newContent;
	private String oldContent;

	public SetContentCommand(final NoteController noteController, final String newContent) {
		this.noteController = noteController;
		this.newContent = newContent;
	}

	@Override
	public void redo() {
		oldContent = noteController.setContent( newContent );
	}

	@Override
	public void undo() {
		noteController.setContent( oldContent );
	}

}
