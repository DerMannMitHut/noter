package de.dom.noter.mvc.controller.command;

public class CommandControl {

	public void doCommand( final UndoableCommand command ) {
		command.redo();
	}

}
