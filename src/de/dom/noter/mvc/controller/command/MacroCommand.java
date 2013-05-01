package de.dom.noter.mvc.controller.command;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MacroCommand implements UndoableCommand {

	private final List<UndoableCommand> actions;

	public MacroCommand() {
		actions = new ArrayList<UndoableCommand>();
	}

	@Override
	public void redo() {
		final ListIterator<UndoableCommand> listIterator = actions.listIterator();
		while( listIterator.hasNext() ) {
			listIterator.next().redo();
		}
	}

	@Override
	public void undo() {
		final ListIterator<UndoableCommand> listIterator = actions.listIterator( actions.size() );
		while( listIterator.hasPrevious() ) {
			listIterator.previous().undo();
		}
	}

	public Object size() {
		return actions.size();
	}

	public void add( final UndoableCommand action ) {
		actions.add( action );
	}

}
