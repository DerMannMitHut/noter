package de.dom.noter.mvc.controller.command;

import java.util.Collection;
import java.util.Collections;

import de.dom.noter.mvc.model.Note;

public abstract class UndoableCommand extends Command {

	private Collection<Note> redoNotes;
	private Collection<Note> undoNotes;

	public UndoableCommand() {
		redoNotes = Collections.emptyList();
		undoNotes = Collections.emptyList();
	}

	final public Collection<Note> getRedoNotes() {
		checkSealed();
		return redoNotes;
	}

	final public Collection<Note> getUndoNotes() {
		checkSealed();
		return undoNotes;
	}

	final protected void setRedoNotes( final Collection<Note> redoNotes ) {
		checkNotSealed();
		this.redoNotes = redoNotes;
	}

	final protected void setUndoNotes( final Collection<Note> undoNotes ) {
		checkNotSealed();
		this.undoNotes = undoNotes;
	}

}
