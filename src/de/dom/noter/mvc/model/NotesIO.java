package de.dom.noter.mvc.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class NotesIO implements NotesChangedListener {

	private static final String NL = "\n";
	private static final String PRE_TITLE = ">>> ";
	private static final String POST_TITLE = " <<<";
	private static final String PRE_ID = "###";
	private static final String POST_ID = "###";

	private static final String IDS_FILENAME = "ids.list";
	private static final String NOTE_EXTENSION = ".note";
	private static final String NOTES_SEPARATOR = "---";

	private final Model model;
	private final File path;

	public NotesIO(final Model model, final File path) {
		this.model = model;
		this.path = path;
		ensureFolder( path );
	}

	private void ensureFolder( final File folder ) {
		if( folder.exists() ) {
			if( !folder.isDirectory() ) {
				throw new IllegalArgumentException( "Given path " + folder.getAbsolutePath() + " is not a directory." );
			}
			if( !folder.canWrite() ) {
				throw new IllegalArgumentException( "Can't write into directory " + folder.getAbsolutePath() );
			}
		}
		else {
			if( folder.mkdirs() == false ) {
				throw new IllegalArgumentException( "Can't create directory " + folder.getAbsolutePath() );
			}
		}
	}

	// ---------------------------------------------------------------------------- top level read methods

	public void readModelFromFiles() {
		final Collection<Long> noteIds = readListOfNoteIds();
		createModelFromNotes( noteIds );
	}

	public static Collection<Long> importNotesFromFile( final File notesFile, final Model model ) {
		BufferedReader r = null;
		try {
			r = new BufferedReader( new FileReader( notesFile ) );
			return importNotesFromReader( model, r );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
		finally {
			closeIgnoreException( r );
		}
	}

	// ---------------------------------------------------------------------------- intermediate read methods

	private Collection<Long> readListOfNoteIds() {
		final File idsFile = getIdsFile();
		if( !idsFile.exists() || !idsFile.canRead() ) {
			return Collections.emptyList();
		}

		BufferedReader r = null;
		try {
			r = new BufferedReader( new FileReader( idsFile ) );
			return readListOfNoteIdsFromReader( r );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
		finally {
			closeIgnoreException( r );
		}
	}

	private void createModelFromNotes( final Collection<Long> noteIds ) {
		for( final long id : noteIds ) {
			model.setNote( readNoteFromFile( id, getNoteFile( id ) ) );
		}
	}

	private Note readNoteFromFile( final long id, final File noteFile ) {
		if( !noteFile.exists() || !noteFile.canRead() ) {
			return null;
		}

		BufferedReader r = null;

		try {
			r = new BufferedReader( new FileReader( noteFile ) );
			return readNoteFromReader( r );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
		finally {
			closeIgnoreException( r );
		}
	}

	// ---------------------------------------------------------------------------- Reader methods

	static Collection<Long> readListOfNoteIdsFromReader( final BufferedReader r ) throws IOException {
		final ArrayList<Long> ids = new ArrayList<Long>();
		while( r.ready() ) {
			ids.add( Long.parseLong( r.readLine() ) );
		}
		return ids;
	}

	static Note readNoteFromReader( final BufferedReader r ) throws IOException {
		return readNoteFromReader( null, r );
	}

	static Collection<Long> importNotesFromReader( final Model m, final BufferedReader r ) throws IOException {
		final ArrayList<Long> ids = new ArrayList<Long>();
		while( r.ready() ) {
			final Note note = readNoteFromReader( NOTES_SEPARATOR, r );
			m.setNote( note );
			ids.add( note.getId() );
		}

		return Collections.unmodifiableList( ids );
	}

	static Note readNoteFromReader( final String notesSeparator, final BufferedReader r ) throws IOException {
		Note result = new Note();

		final StringBuilder content = new StringBuilder();
		int nullcount = 0;

		String nlSep = "";
		while( r.ready() ) {
			final String line = r.readLine();
			if( null == line ) {
				nullcount += 1;
				if( nullcount > 10 ) {
					break;
				}
				else {
					sleepAWhile( nullcount * 3 );
					continue;
				}
			}
			else if( line.equalsIgnoreCase( notesSeparator ) ) {
				break;
			}
			else if( !result.hasTitle() && line.startsWith( PRE_ID ) && line.endsWith( POST_ID ) ) {
				try {
					final long id = Long.parseLong( line.substring( PRE_ID.length(), line.length() - POST_ID.length() ) );
					result = result.setId( id );
					continue;
				}
				catch( final NumberFormatException ignore ) {
				}
			}
			else if( !result.hasTitle() && line.startsWith( PRE_TITLE ) && line.endsWith( POST_TITLE ) ) {
				result = result.setTitle( line.substring( PRE_TITLE.length(), line.length() - POST_TITLE.length() ) );
				continue;
			}

			content.append( nlSep ).append( line );

			nlSep = NL;
			nullcount = 0;
		}

		final String contentString = content.toString();

		if( !result.hasTitle() ) {
			result = setTitleFromContent( result, contentString );
		}

		return result.setContent( contentString );
	}

	private static Note setTitleFromContent( Note result, final String content ) {
		final String trimmed = content.trim();
		final int firstNlInContent = trimmed.indexOf( NL );
		if( firstNlInContent >= 0 ) {
			result = result.setTitle( trimmed.substring( 0, firstNlInContent ) );
		}
		else {
			result = result.setTitle( trimmed.toString() );
		}
		return result;
	}

	private static void closeIgnoreException( final Reader r ) {
		try {
			r.close();
		}
		catch( final Exception igonre ) {
		}
	}

	// ---------------------------------------------------------------------------- top level write methods

	@Override
	public void onNotesChanged( final Collection<Long> noteIds ) {
		Writer w = null;
		try {
			w = new BufferedWriter( new FileWriter( getIdsFile() ) );
			writeListOfNoteIdsToWriter( noteIds, w );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
		finally {
			closeIgnoreException( w );
		}
	}

	@Override
	public void onNoteChanged( final Long id ) {
		BufferedWriter w = null;
		try {
			w = new BufferedWriter( new FileWriter( getNoteFile( id ) ) );
			writeNoteToWriter( model.getNote( id ), w );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
		finally {
			closeIgnoreException( w );
		}
	}

	public static void exportModel( final File notesFile, final Model model ) {
		BufferedWriter w = null;
		try {
			w = new BufferedWriter( new FileWriter( notesFile ) );

			exportModelToWriter( w, model );
		}
		catch( final IOException e ) {
			throw new RuntimeException( e );
		}
		finally {
			closeIgnoreException( w );
		}
	}

	// ---------------------------------------------------------------------------- Writer methods
	static void writeListOfNoteIdsToWriter( final Collection<Long> ids, final Writer w ) throws IOException {
		for( final long id : ids ) {
			w.write( id + NL );
		}
	}

	static void writeNoteToWriter( final Note note, final Writer w ) throws IOException {
		w.write( PRE_ID + note.getId() + POST_ID + NL );

		if( note.hasTitle() ) {
			w.write( PRE_TITLE + note.getTitle() + POST_TITLE + NL );
		}

		if( note.hasContent() ) {
			w.write( note.getContent() + NL );
		}

	}

	static void exportModelToWriter( final Writer w, final Model m ) throws IOException {
		String sep = "";
		for( final long id : m.getNoteIds() ) {
			w.append( sep );
			sep = NOTES_SEPARATOR + NL;

			final Note note = m.getNote( id );

			writeNoteToWriter( note, w );
		}
	}

	private static void closeIgnoreException( final Writer w ) {
		try {
			w.close();
		}
		catch( final Exception igonre ) {
		}
	}

	// ---------------------------------------------------------------------------- Helper methods

	private File getIdsFile() {
		return new File( path, IDS_FILENAME );
	}

	private File getNoteFile( final long id ) {
		return new File( path, Long.toHexString( id ) + NOTE_EXTENSION );
	}

	private static void sleepAWhile( final int timeToSleep ) {
		try {
			Thread.sleep( timeToSleep );
		}
		catch( final InterruptedException ignore ) {
		}
	}

}
