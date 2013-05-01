package de.dom.noter.mvc.controller.command;

import java.io.File;
import java.util.Collection;

import de.dom.noter.mvc.controller.NotesController;

public class ImportFromFileCommand implements UndoableCommand {

	private final NotesController notesController;
	private final File fileToRead;
	private Collection<Long> ids;

	public ImportFromFileCommand(final NotesController notesController, final File fileToRead) {
		this.notesController = notesController;
		this.fileToRead = fileToRead;
	}

	@Override
	public void redo() {
		ids = notesController.importAllNotes( fileToRead );
	}

	@Override
	public void undo() {
		for( final long id : ids ) {
			notesController.removeNote( id );
		}
	}
}
