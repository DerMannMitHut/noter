package de.dom.noter.mvc.controller.command;

import de.dom.noter.mvc.controller.command.MacroCommand;
import de.dom.noter.mvc.controller.command.UndoableCommand;
import junit.framework.TestCase;

public class MacroCommandTest extends TestCase {

	int redoCount;
	int undoCount;
	int stepSize;

	UndoableCommand command1;
	UndoableCommand command2;
	MacroCommand macroAction;

	@Override
	protected void setUp() throws Exception {
		redoCount = 0;
		undoCount = 0;
		stepSize = 1;

		command1 = new UndoableCommand() {
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

		command2 = new UndoableCommand() {
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

		macroAction = new MacroCommand();
	}

	public void testSize() throws Exception {
		assertEquals( 0, macroAction.size() );
		macroAction.add( command1 );
		assertEquals( 1, macroAction.size() );
	}

	public void testRedoA() throws Exception {
		macroAction.add( command1 );
		macroAction.add( command2 );
		macroAction.redo();
		assertEquals( -1, redoCount );
	}

	public void testRedoB() throws Exception {
		macroAction.add( command2 );
		macroAction.add( command1 );
		macroAction.redo();
		assertEquals( 1, redoCount );
	}

	public void testUndoA() throws Exception {
		macroAction.add( command1 );
		macroAction.add( command2 );
		macroAction.undo();
		assertEquals( 1, undoCount );
	}

	public void testUndoB() throws Exception {
		macroAction.add( command2 );
		macroAction.add( command1 );
		macroAction.undo();
		assertEquals( -1, undoCount );
	}

}
