package de.dom.noter.mvc.controller.command;

public class CommandControl {

	public void doCommand( final Command command ) {
		command.redo();
	}

}
