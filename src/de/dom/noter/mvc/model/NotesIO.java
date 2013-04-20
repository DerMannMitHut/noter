package de.dom.noter.mvc.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class NotesIO implements NotesChangedListener {

	private static final String NL = "\n";
	private static final String PRE_HEAD = ">>> ";
	private static final String POST_HEAD = " <<<";
	private static final String PRE_CREATOR = "Creator: ";

	private static final String IDS_FILENAME = "ids.list";
	private static final String NOTE_EXTENSION = ".note";

	private final Model model;
	private final File path;

	public NotesIO(final Model model, final File path) {
		this.model = model;
		this.path = path;
		model.addNotesChangedListener( this );
		ensureFolder();
	}

	private void ensureFolder() {
		if( path.exists() ) {
			if( !path.isDirectory() ) {
				throw new IllegalArgumentException( "Given path " + path.getAbsolutePath() + " is not a directory." );
			}
			if( !path.canWrite() ) {
				throw new IllegalArgumentException( "Can't write into directory " + path.getAbsolutePath() );
			}
		}
		else {
			if( path.mkdirs() == false ) {
				throw new IllegalArgumentException( "Can't create directory " + path.getAbsolutePath() );
			}
		}
	}

	@Override
	public void onNotesChanged( final Collection<Long> noteIds ) {
		try {
			writeListOfNoteIds( noteIds );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
	}

	private void writeListOfNoteIds( final Collection<Long> ids ) throws IOException {
		final File idsFile = getIdsFile( path );
		final Writer w = new BufferedWriter( new FileWriter( idsFile ) );
		try {
			for( final long id : ids ) {
				w.write( id + NL );
			}
		}
		finally {
			w.close();
		}
	}

	private static File getIdsFile( final File path ) {
		return new File( path, IDS_FILENAME );
	}

	@Override
	public void onNoteChanged( final Long id ) {
		final Note note = model.getNote( id );
		try {
			writeNote( note );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
	}

	private void writeNote( final Note note ) throws IOException {
		final File idsFile = getNoteFile( note.getId(), path );
		final BufferedWriter w = new BufferedWriter( new FileWriter( idsFile ) );
		try {
			w.write( PRE_HEAD + note.getTitle() + POST_HEAD + NL );
			w.write( note.getContent() + NL );
		}
		finally {
			w.close();
		}
	}

	private static File getNoteFile( final long id, final File path ) {
		return new File( path, Long.toHexString( id ) + NOTE_EXTENSION );
	}

	public static Model createModelFromFiles( final File path ) {
		final Model m = new Model();

		try {
			final Collection<Long> noteIds = readListOfNoteIds( path );
			readNotes( noteIds, m, path );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}

		return m;
	}

	private static Collection<Long> readListOfNoteIds( final File path ) throws IOException {
		final ArrayList<Long> ids = new ArrayList<Long>();

		final File idsFile = getIdsFile( path );
		if( !idsFile.exists() || !idsFile.canRead() ) {
			return Collections.emptyList();
		}

		final BufferedReader r = new BufferedReader( new FileReader( idsFile ) );

		try {
			while( r.ready() ) {
				ids.add( Long.parseLong( r.readLine() ) );
			}
		}
		finally {
			r.close();
		}

		return ids;
	}

	private static void readNotes( final Collection<Long> noteIds, final Model m, final File path ) throws IOException {
		for( final long id : noteIds ) {
			readNote( id, m, path );
		}
	}

	private static void readNote( final long id, final Model m, final File path ) throws IOException {
		final File noteFile = getNoteFile( id, path );

		if( !noteFile.exists() || !noteFile.canRead() ) {
			return;
		}

		final BufferedReader r = new BufferedReader( new FileReader( noteFile ) );

		String title = null;

		final StringBuilder content = new StringBuilder();

		while( r.ready() ) {
			final String line = r.readLine();
			if( title == null && line.startsWith( PRE_HEAD ) && line.endsWith( POST_HEAD ) ) {
				title = line.substring( PRE_HEAD.length(), line.length() - POST_HEAD.length() );
			}
			else {
				content.append( line ).append( NL );
			}
		}

		r.close();

		final Note n = new Note( id ).setTitle( title ).setContent( content.toString() );

		m.setNote( n );
	}
}
