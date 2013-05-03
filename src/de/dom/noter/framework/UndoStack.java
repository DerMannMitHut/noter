package de.dom.noter.framework;

import java.util.NoSuchElementException;

public class UndoStack<E> {

	private CoWList<E> undoable;
	private CoWList<E> redoable;
	private int undoableSize;
	private int redoableSize;
	private final int capacity;
	private int undoableSizeInternal;

	public UndoStack() {
		this( Integer.MAX_VALUE );
	}

	public UndoStack(final int capacity) {
		this.capacity = capacity;
		undoable = CoWList.empty();
		redoable = CoWList.empty();
		undoableSize = 0;
		redoableSize = 0;
		undoableSizeInternal = 0;
	}

	public UndoStack<E> add( final E e ) {
		redoable = CoWList.empty();
		undoable = undoable.add( e );
		redoableSize = 0;
		undoableSize += 1;
		undoableSizeInternal += 1;
		if( undoableSize > capacity ) {
			undoableSize = capacity;
		}
		if( undoableSizeInternal > (undoableSize << 1) ) {
			undoable = undoable.headList( undoableSize );
			undoableSizeInternal = undoableSize;
		}
		return this;
	}

	public E getNextUndoElement() {
		if( !hasUndoElement() ) {
			throw new NoSuchElementException();
		}
		final E e = undoable.head();
		undoable = undoable.tail();
		redoable = redoable.add( e );
		undoableSize -= 1;
		redoableSize += 1;
		undoableSizeInternal -= 1;
		return e;
	}

	public Object getNextRedoElement() {
		if( !hasRedoElement() ) {
			throw new NoSuchElementException();
		}
		final E e = redoable.head();
		redoable = redoable.tail();
		undoable = undoable.add( e );
		undoableSize += 1;
		redoableSize -= 1;
		undoableSizeInternal += 1;
		return e;
	}

	public int size() {
		return undoableSize + redoableSize;
	}

	public boolean hasRedoElement() {
		return redoableSize > 0;
	}

	public boolean hasUndoElement() {
		return undoableSize > 0;
	}

}
