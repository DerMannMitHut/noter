package de.dom.noter.mvc.controller.command;

public interface UndoableCommand {

	void redo();

	void undo();

}
