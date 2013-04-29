package de.dom.noter.mvc.controller;

import de.dom.noter.mvc.model.Note;

public interface NoteController {

	String setTitle( String title );

	String setContent( String content );

	Note removeNote();

}