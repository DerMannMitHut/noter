package de.dom.noter.mvc.controller.command;

import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.controller.command.UndoableCommand;
import junit.framework.TestCase;

public class CommandControlTest extends TestCase {

	int redoCount;
	int undoCount;

	UndoableCommand command;

	CommandControl commandControl;

	@Override
	protected void setUp() throws Exception {
		redoCount = 0;
		undoCount = 0;

		command = new UndoableCommand() {

			@Override
			public void undo() {
				undoCount += 1;
			}

			@Override
			public void redo() {
				redoCount += 1;
			}
		};

		commandControl = new CommandControl();
	}

	public void testDoAction() throws Exception {
		commandControl.doCommand( command );
		assertEquals( 1, redoCount );
	}

}
