package de.dom.noter.mvc.controller.command;

import java.io.File;

import de.dom.noter.mvc.controller.NotesController;

public class UCImportFromFile extends UndoableCommand {

	private final NotesController notesController;
	private final File fileToRead;
	private final String separator;

	public UCImportFromFile(final NotesController notesController, final File fileToRead, final String separator) {
		this.notesController = notesController;
		this.fileToRead = fileToRead;
		this.separator = separator;
	}

	@Override
	public void performInternal() {
		setRedoNotes( notesController.importAllNotes( fileToRead, separator ) );
	}

}
