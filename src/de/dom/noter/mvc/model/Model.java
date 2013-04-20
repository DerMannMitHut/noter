package de.dom.noter.mvc.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import de.dom.noter.collections.IdentityHashSet;
import de.dom.noter.mvc.view.NoteView;
import de.dom.noter.mvc.view.NotesView;

public class Model {

	Map<Long, Note> notes;
	Map<Long, Collection<NoteChangedListener>> noteChangedListeners;
	Collection<NotesChangedListener> notesChangedListeners;

	public Model() {
		notes = new LinkedHashMap<Long, Note>();
		noteChangedListeners = new HashMap<Long, Collection<NoteChangedListener>>();
		notesChangedListeners = new IdentityHashSet<NotesChangedListener>();
	}

	public Note getNote( final long noteId ) {
		if( hasNote( noteId ) ) {
			return notes.get( noteId );
		}
		else {
			throw new NoSuchElementException( "noteId=" + noteId );
		}
	}

	public boolean hasNote( final long noteId ) {
		return notes.containsKey( noteId );
	}

	public void changeNote( final Note changedNote ) {
		if( hasNote( changedNote.getId() ) ) {
			setNote( changedNote );
		}
		else {
			throw new NoSuchElementException( "note=" + changedNote );
		}
	}

	Note setNote( final Note newNote ) {
		final long noteId = newNote.getId();
		final Note oldNote = notes.put( noteId, newNote );

		if( null == oldNote ) {
			notifyNotesChangedListeners();
		}

		if( !newNote.equals( oldNote ) ) {
			notifyNoteChangedListeners( newNote );
		}

		return oldNote;
	}

	private void notifyNotesChangedListeners() {
		for( final NotesChangedListener l : notesChangedListeners ) {
			notifyListener( l );
		}
	}

	private void notifyNoteChangedListeners( final Note note ) {
		for( final NoteChangedListener l : getNoteChangedListeners( note.getId() ) ) {
			notifyListener( l, note );
		}
		for( final NotesChangedListener l : notesChangedListeners ) {
			notifyListener( l, note );
		}
	}

	public void addNoteChangedListener( final NoteChangedListener listener, final long noteId ) {
		getNoteChangedListeners( noteId ).add( listener );
		notifyListener( listener, getNote( noteId ) );
	}

	private void notifyListener( final NoteChangedListener listener, final Note note ) {
		listener.onNoteChanged( note );
	}

	private Collection<NoteChangedListener> getNoteChangedListeners( final long noteId ) {
		if( !noteChangedListeners.containsKey( noteId ) ) {
			noteChangedListeners.put( noteId, new HashSet<NoteChangedListener>() );
		}
		return noteChangedListeners.get( noteId );
	}

	public void addNotesChangedListener( final NotesChangedListener listener ) {
		notesChangedListeners.add( listener );
		notifyListener( listener );
	}

	private void notifyListener( final NotesChangedListener listener ) {
		listener.onNotesChanged( getNoteIds() );
	}

	private void notifyListener( final NotesChangedListener listener, final Note note ) {
		listener.onNoteChanged( note.getId() );
	}

	public Collection<Long> getNoteIds() {
		return Collections.unmodifiableCollection( notes.keySet() );
	}

	public boolean isNoteChangeListener( final long noteId, final NoteView v ) {
		return getNoteChangedListeners( noteId ).contains( v );
	}

	public Note createNote( final User creator ) {
		final Note newNote = new Note( creator );
		setNote( newNote );
		return newNote;
	}

	public boolean isNotesChangeListener( final NotesView v ) {
		return notesChangedListeners.contains( v );
	}

}
