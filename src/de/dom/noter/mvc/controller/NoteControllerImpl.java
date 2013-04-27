package de.dom.noter.mvc.controller;

import de.dom.noter.mvc.model.Model;
import de.dom.noter.mvc.model.Note;
import de.dom.noter.mvc.view.NoteView;

public class NoteControllerImpl implements NoteController {

	private final Model model;
	private final NoteView view;
	private final long noteId;

	public NoteControllerImpl(final Model model, final long noteId, final NoteView view) {
		this.model = model;
		this.noteId = noteId;
		this.view = view;

		view.setController( this );
		model.addNoteChangedListener( view, noteId );
	}

	public Note getNote() {
		return model.getNote( noteId );
	}

	public NoteView getView() {
		return view;
	}

	@Override
	public void setTitle( final String newTitle ) {
		model.changeNote( getNote().setTitle( newTitle ) );
	}

	@Override
	public void setContent( final String newContent ) {
		model.changeNote( getNote().setContent( newContent ) );
	}

	@Override
	public void removeNote() {
		model.removeNote( getId() );
	}

	private long getId() {
		return getNote().getId();
	}

}
