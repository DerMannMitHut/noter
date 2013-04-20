package de.dom.noter.mvc.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {

	public void testUid() throws Exception {
		final User u1 = new User();
		final User u2 = new User();

		assertFalse( u1.equals( u2 ) );
		assertFalse( u1.getId() == u2.getId() );
	}

	public void testName() throws Exception {
		final User u = new User();
		assertTrue( u.setName( "bla" ).equals( u.setName( "bla" ) ) );
		assertFalse( u.setName( "bla" ).equals( u.setName( "blubb" ) ) );
		assertFalse( u.equals( u.setName( "blubb" ) ) );
		assertFalse( u.setName( "bla" ).equals( u ) );
	}

	public void testGetSetName() throws Exception {
		final User u = new User();
		assertEquals( "bla", u.setName( "bla" ).getName() );
	}

	public void testEquals() throws Exception {
		final User u = new User();
		assertFalse( u.equals( null ) );
		assertFalse( u.equals( new Object() ) );
		assertFalse( u.equals( new User() ) );
		assertTrue( u.equals( u.setName( null ) ) );
	}

}
