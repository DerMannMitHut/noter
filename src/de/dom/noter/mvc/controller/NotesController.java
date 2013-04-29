package de.dom.noter.mvc.controller;

public interface NotesController {

	long createNewNote();

	void removeNote( long noteId );

}