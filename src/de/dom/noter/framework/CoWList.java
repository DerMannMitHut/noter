package de.dom.noter.framework;

import java.util.NoSuchElementException;

public class CoWList<E> {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final CoWList EMPTY = new CoWList( null, null ) {
		@Override
		public Object head() {
			throw new NoSuchElementException();
		};

		@Override
		public CoWList tail() {
			throw new NoSuchElementException();
		};
	};

	@SuppressWarnings("unchecked")
	public static <E> CoWList<E> empty() {
		return EMPTY;
	}

	private final E head;
	private final CoWList<E> tail;

	private CoWList(final E head, final CoWList<E> tail) {
		this.head = head;
		this.tail = tail;
	}

	public CoWList<E> add( final E e ) {
		return new CoWList<E>( e, this );
	}

	public E head() {
		return head;
	}

	public CoWList<E> tail() {
		return tail;
	}

	public CoWList<E> headList( final int undoableSize ) {
		return headList( this, undoableSize );
	}

	private CoWList<E> headList( final CoWList<E> orig, final int size ) {
		if( size > 0 ) {
			return headList( orig.tail(), size - 1 ).add( orig.head() );
		}
		return empty();
	}

	@Override
	public String toString() {
		return "[" + head + ", " + tail + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((tail == null) ? 0 : tail.hashCode());
		return result;
	}

	@Override
	public boolean equals( final Object obj ) {
		if( this == obj ) {
			return true;
		}
		if( obj == null ) {
			return false;
		}
		if( getClass() != obj.getClass() ) {
			return false;
		}

		final CoWList<?> other = (CoWList<?>) obj;

		if( head == null ) {
			if( other.head != null ) {
				return false;
			}
		}
		else if( !head.equals( other.head ) ) {
			return false;
		}
		if( tail == null ) {
			if( other.tail != null ) {
				return false;
			}
		}
		else if( !tail.equals( other.tail ) ) {
			return false;
		}
		return true;
	}

}