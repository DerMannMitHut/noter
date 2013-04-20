package de.dom.noter.mvc.model;

public class Note extends Data {

	private String title;
	private String content;

	public Note() {
		super();
		init( "", "" );
	}

	private void init( final String titleString, final String contentString ) {
		title = titleString;
		content = contentString;
	}

	private Note(final Note note) {
		super( note );
		init( note.title, note.content );
	}

	public Note(final long id) {
		super( id );
		init( "", "" );
	}

	public Note setTitle( final String newTitle ) {
		final Note n = new Note( this );
		n.title = newTitle;
		return n;
	}

	public String getTitle() {
		return title;
	}

	public Note setContent( final String newContent ) {
		final Note n = new Note( this );
		n.content = newContent;
		return n;
	}

	public String getContent() {
		return content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals( final Object obj ) {
		if( this == obj ) {
			return true;
		}
		if( !super.equals( obj ) ) {
			return false;
		}
		final Note other = (Note) obj;
		if( content == null ) {
			if( other.content != null ) {
				return false;
			}
		}
		else if( !content.equals( other.content ) ) {
			return false;
		}
		if( title == null ) {
			if( other.title != null ) {
				return false;
			}
		}
		else if( !title.equals( other.title ) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return title + " (" + getId() + ")";
	}

}
