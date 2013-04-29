package de.dom.noter.mvc.controller.action;

import junit.framework.TestCase;

public class MacroActionTest extends TestCase {

	int redoCount;
	int undoCount;
	int stepSize;

	Action action1;
	Action action2;
	MacroAction macroAction;

	@Override
	protected void setUp() throws Exception {
		redoCount = 0;
		undoCount = 0;
		stepSize = 1;

		action1 = new Action() {
			@Override
			public void undo() {
				undoCount += stepSize;
				stepSize += 1;
			}

			@Override
			public void redo() {
				redoCount += stepSize;
				stepSize += 1;
			}
		};

		action2 = new Action() {
			@Override
			public void undo() {
				undoCount -= stepSize;
				stepSize += 1;
			}

			@Override
			public void redo() {
				redoCount -= stepSize;
				stepSize += 1;
			}
		};

		macroAction = new MacroAction();
	}

	public void testSize() throws Exception {
		assertEquals( 0, macroAction.size() );
		macroAction.add( action1 );
		assertEquals( 1, macroAction.size() );
	}

	public void testRedoA() throws Exception {
		macroAction.add( action1 );
		macroAction.add( action2 );
		macroAction.redo();
		assertEquals( -1, redoCount );
	}

	public void testRedoB() throws Exception {
		macroAction.add( action2 );
		macroAction.add( action1 );
		macroAction.redo();
		assertEquals( 1, redoCount );
	}

	public void testUndoA() throws Exception {
		macroAction.add( action1 );
		macroAction.add( action2 );
		macroAction.undo();
		assertEquals( 1, undoCount );
	}

	public void testUndoB() throws Exception {
		macroAction.add( action2 );
		macroAction.add( action1 );
		macroAction.undo();
		assertEquals( -1, undoCount );
	}

}
