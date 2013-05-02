package de.dom.noter.mvc.controller.command;

import java.io.File;
import java.util.Collection;

import de.dom.noter.mvc.controller.NotesController;

public class ImportFromFileCommand implements UndoableCommand {

	private final NotesController notesController;
	private final File fileToRead;
	private Collection<Long> ids;
	private final String separator;

	public ImportFromFileCommand(final NotesController notesController, final File fileToRead, final String separator) {
		this.notesController = notesController;
		this.fileToRead = fileToRead;
		this.separator = separator;
	}

	@Override
	public void redo() {
		ids = notesController.importAllNotes( fileToRead, separator );
	}

	@Override
	public void undo() {
		for( final long id : ids ) {
			notesController.removeNote( id );
		}
	}
}
