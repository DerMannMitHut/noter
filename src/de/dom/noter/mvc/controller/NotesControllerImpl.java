package de.dom.noter.mvc.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.dom.noter.mvc.model.Model;
import de.dom.noter.mvc.view.NoteView;
import de.dom.noter.mvc.view.NotesView;

public class NotesControllerImpl implements NotesController {

	private final Model model;
	private final NotesView view;
	private final Map<Long, NoteController> noteControllers;

	public NotesControllerImpl(final Model model, final NotesView view) {
		this.model = model;
		this.view = view;
		noteControllers = new HashMap<Long, NoteController>();

		view.setController( this );
		model.addNotesChangedListener( view );

		for( final long id : model.getNoteIds() ) {
			createAndAddNoteView( id );
		}
	}

	@Override
	public long createNewNote() {
		final long id = model.createNote().getId();
		createAndAddNoteView( id );
		return id;
	}

	private void createAndAddNoteView( final long id ) {
		final Map<Long, ? extends NoteView> noteViews = view.getNoteViews();
		final NoteView noteView = noteViews.get( id );
		final NoteController nc = new NoteControllerImpl( model, id, noteView );
		noteControllers.put( id, nc );
	}

	public Collection<Long> getNoteIds() {
		return model.getNoteIds();
	}

	public Object getView() {
		return view;
	}

	@Override
	public void removeNote( final long noteId ) {
		model.removeNote( noteId );
	}

}
