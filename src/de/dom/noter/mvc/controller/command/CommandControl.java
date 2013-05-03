package de.dom.noter.mvc.controller.command;

import de.dom.noter.framework.UndoStack;

public class CommandControl {

	private static final int MAX_UNDO_STACK_SIZE = 100;

	private final UndoStack<UndoableCommand> undoStack;

	public CommandControl() {
		undoStack = new UndoStack<UndoableCommand>( MAX_UNDO_STACK_SIZE );
	}

	public void doCommand( final Command command ) {
		command.perform();
		if( command instanceof UndoableCommand ) {
			undoStack.add( (UndoableCommand) command );
		}
	}

}
