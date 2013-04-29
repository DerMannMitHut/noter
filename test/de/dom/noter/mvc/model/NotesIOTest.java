package de.dom.noter.mvc.model;

import java.io.BufferedReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class NotesIOTest extends TestCase {

	BufferedReader r;
	Writer w;

	@Override
	protected void setUp() throws Exception {
		w = new PipedWriter();
		r = new BufferedReader( new PipedReader( (PipedWriter) w ) );
	}

	@Override
	protected void tearDown() throws Exception {
		r.close();
		w.close();
	}

	public void testListOfNoteIds() throws Exception {
		final List<Long> ids = Arrays.asList( 1L, 2L, 3L );
		NotesIO.writeListOfNoteIdsToWriter( ids, w );
		assertEquals( ids, NotesIO.readListOfNoteIdsFromReader( r ) );
	}

	public void testEmptyListOfNoteIds() throws Exception {
		final List<Long> ids = Collections.emptyList();
		NotesIO.writeListOfNoteIdsToWriter( ids, w );
		assertEquals( ids, NotesIO.readListOfNoteIdsFromReader( r ) );
	}

	public void testNoteEmpty() throws Exception {
		final Note note = new Note();
		NotesIO.writeNoteToWriter( note, w );
		final Note noteFromReader = NotesIO.readNoteFromReader( r );
		assertEquals( note, noteFromReader );
	}

	public void testNote() throws Exception {
		final Note note = new Note().setTitle( "t" ).setContent( "c\n" );
		NotesIO.writeNoteToWriter( note, w );
		final Note noteFromReader = NotesIO.readNoteFromReader( r );

		assertEquals( note, noteFromReader );
	}

	public void testNoteEol() throws Exception {
		final Note note = new Note().setTitle( "t" ).setContent( "c\nd\n\re\r\nf" );
		NotesIO.writeNoteToWriter( note, w );
		final Note noteFromReader = NotesIO.readNoteFromReader( r );

		assertEquals( note, noteFromReader );
	}

	public void testModelEmpty() throws Exception {
		final Model model = new Model();
		NotesIO.exportModelToWriter( w, model );
		final Model modelFromReader = new Model();
		NotesIO.importNotesFromReader( modelFromReader, r );

		assertEquals( model, modelFromReader );
	}

	public void testModel() throws Exception {
		final Model model = new Model();
		model.createNote();
		NotesIO.exportModelToWriter( w, model );
		final Model modelFromReader = new Model();
		NotesIO.importNotesFromReader( modelFromReader, r );

		assertEquals( model, modelFromReader );
	}

}
