package de.dom.noter.mvc.model;

import java.util.NoSuchElementException;

import junit.framework.TestCase;
import de.dom.noter.mvc.view.SimpleMock;

public class ModelTest extends TestCase {

	public void testCreateNote() throws Exception {
		final Model m = new Model();

		final Note n = m.createNote();
		assertTrue( m.hasNote( n.getId() ) );
	}

	public void testChangeGetNote() throws Exception {
		final Model m = new Model();
		final Note n = m.createNote();

		final Note n1 = n.setTitle( "bla1" );
		m.changeNote( n1 );
		assertEquals( n1, m.getNote( n.getId() ) );

		try {
			m.changeNote( new Note() );
			fail();
		}
		catch( final NoSuchElementException expected ) {
		}

		try {
			m.getNote( 42 );
			fail();
		}
		catch( final NoSuchElementException expected ) {
		}
	}

	public void testNoteListener() throws Exception {
		final Model m = new Model();
		final Note n = m.createNote();

		final NoteChangedListener l0 = (NoteChangedListener) SimpleMock.create( NoteChangedListener.class );
		final NoteChangedListener l1 = (NoteChangedListener) SimpleMock.create( NoteChangedListener.class );

		m.addNoteChangedListener( l0, n.getId() );
		m.addNoteChangedListener( l0, n.getId() );
		m.addNoteChangedListener( l1, n.getId() );

		assertEquals( 2, SimpleMock.getCalls( l0, "onNoteChanged" ) );
		assertEquals( 1, SimpleMock.getCalls( l1, "onNoteChanged" ) );

		m.changeNote( n.setTitle( "bla" ) );

		assertEquals( 3, SimpleMock.getCalls( l0, "onNoteChanged" ) );
		assertEquals( 2, SimpleMock.getCalls( l1, "onNoteChanged" ) );
	}

	public void testNotesListener() throws Exception {
		final Model m = new Model();

		final NotesChangedListener l0 = (NotesChangedListener) SimpleMock.create( NotesChangedListener.class );

		m.addNotesChangedListener( l0 );
		assertEquals( 1, SimpleMock.getCalls( l0, "onNotesChanged" ) );

		m.createNote();
		assertEquals( 2, SimpleMock.getCalls( l0, "onNotesChanged" ) );

		final NotesChangedListener l1 = (NotesChangedListener) SimpleMock.create( NotesChangedListener.class );

		m.addNotesChangedListener( l1 );
		assertEquals( 1, SimpleMock.getCalls( l1, "onNotesChanged" ) );

		m.createNote();
		assertEquals( 3, SimpleMock.getCalls( l0, "onNotesChanged" ) );
		assertEquals( 2, SimpleMock.getCalls( l1, "onNotesChanged" ) );
	}

}
