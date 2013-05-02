package de.dom.noter.mvc.controller;

import java.io.File;
import java.util.Collection;

public interface NotesController {

	long createNewNote();

	void removeNote( long noteId );

	void exportAllNotes( File fileToSave );

	Collection<Long> importAllNotes( File fileToRead, String separator );

}