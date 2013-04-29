package de.dom.noter.mvc.controller.action;

import junit.framework.TestCase;

public class ActionControlTest extends TestCase {

	int redoCount;
	int undoCount;

	Action action;

	ActionControl actionControl;

	@Override
	protected void setUp() throws Exception {
		redoCount = 0;
		undoCount = 0;

		action = new Action() {

			@Override
			public void undo() {
				undoCount += 1;
			}

			@Override
			public void redo() {
				redoCount += 1;
			}
		};

		actionControl = new ActionControl();
	}

	public void testDoAction() throws Exception {
		actionControl.doAction( action );
		assertEquals( 1, redoCount );
	}

}
