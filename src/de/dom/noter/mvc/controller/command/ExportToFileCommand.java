package de.dom.noter.mvc.controller.command;

import java.io.File;

import de.dom.noter.mvc.controller.NotesController;

public class ExportToFileCommand implements Command {

	private final NotesController notesController;
	private final File fileToSave;

	public ExportToFileCommand(final NotesController notesController, final File fileToSave) {
		this.notesController = notesController;
		this.fileToSave = fileToSave;
	}

	@Override
	public void redo() {
		notesController.exportAllNotes( fileToSave );
	}
}
