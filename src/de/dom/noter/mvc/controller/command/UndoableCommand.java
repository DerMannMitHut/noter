package de.dom.noter.mvc.controller.command;

public interface UndoableCommand extends Command {

	void undo();

}
