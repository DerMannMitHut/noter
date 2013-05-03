package de.dom.noter.framework;

import java.util.NoSuchElementException;

import junit.framework.TestCase;

public class UndoStackCapacityTest extends TestCase {

	private UndoStack<Object> us;
	private Object e1;
	private Object e2;
	private Object e3;

	@Override
	public void setUp() {
		us = new UndoStack<Object>( 2 );
		e1 = new Object();
		e2 = new Object();
		e3 = new Object();
	}

	public void testAdd() throws Exception {
		us.add( e3 ).add( e1 ).add( e2 );
		assertEquals( 2, us.size() );
	}

	public void testUndo() throws Exception {
		us.add( e3 ).add( e1 ).add( e2 );

		assertFalse( us.hasRedoElement() );
		assertTrue( us.hasUndoElement() );

		try {
			us.getNextRedoElement();
			fail();
		}
		catch( final NoSuchElementException expected ) {
		}

		assertEquals( e2, us.getNextUndoElement() );

		assertTrue( us.hasRedoElement() );
		assertTrue( us.hasUndoElement() );

		assertEquals( e2, us.getNextRedoElement() );

		assertEquals( e2, us.getNextUndoElement() );
		assertEquals( e1, us.getNextUndoElement() );

		assertTrue( us.hasRedoElement() );
		assertFalse( us.hasUndoElement() );

		assertEquals( e1, us.getNextRedoElement() );
		assertEquals( e2, us.getNextRedoElement() );
		try {
			us.getNextRedoElement();
			fail();
		}
		catch( final NoSuchElementException expected ) {
		}

		assertEquals( e2, us.getNextUndoElement() );
		assertEquals( e1, us.getNextUndoElement() );
		try {
			us.getNextUndoElement();
			fail();
		}
		catch( final NoSuchElementException expected ) {
		}
	}

}
