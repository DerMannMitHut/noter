package de.dom.noter.mvc.controller;

import java.io.File;

public interface NotesController {

	long createNewNote();

	void removeNote( long noteId );

	void exportAllNotes( File fileToSave );

}