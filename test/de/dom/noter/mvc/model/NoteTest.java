package de.dom.noter.mvc.model;

import junit.framework.TestCase;

public class NoteTest extends TestCase {

	public void testUid() throws Exception {
		final Note n1 = new Note( null );
		final Note n2 = new Note( null );

		assertFalse( n1.equals( n2 ) );
		assertFalse( n1.getId() == n2.getId() );
	}

	public void testTitle() throws Exception {
		final Note n = new Note( null );

		assertTrue( n.setTitle( "bla" ).equals( n.setTitle( "bla" ) ) );
		assertFalse( n.setTitle( "bla" ).equals( n.setTitle( "blubb" ) ) );
		assertFalse( n.equals( n.setTitle( "blubb" ) ) );
		assertFalse( n.setTitle( "bla" ).equals( n ) );

		assertEquals( "bla", n.setTitle( "bla" ).getTitle() );
	}

	public void testContent() throws Exception {
		final Note n = new Note( null );

		assertTrue( n.setContent( "bla" ).equals( n.setContent( "bla" ) ) );
		assertFalse( n.setContent( "bla" ).equals( n.setContent( "blubb" ) ) );
		assertFalse( n.equals( n.setContent( "blubb" ) ) );
		assertFalse( n.setContent( "bla" ).equals( n ) );

		assertEquals( "bla", n.setContent( "bla" ).getContent() );
	}

	public void testCreator() throws Exception {
		final User creator = new User();
		final Note n = new Note( creator );

		assertEquals( creator, n.getCreator() );
	}

	public void testEquals() throws Exception {
		final User creator = new User();
		final Note n = new Note( creator );
		assertFalse( n.equals( null ) );
		assertFalse( n.equals( new Object() ) );
		assertFalse( n.equals( new Note( creator ) ) );
		assertTrue( n.equals( n ) );
	}

}
