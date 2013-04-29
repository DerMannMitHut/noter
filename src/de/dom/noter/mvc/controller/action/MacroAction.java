package de.dom.noter.mvc.controller.action;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MacroAction implements Action {

	private final List<Action> actions;

	public MacroAction() {
		actions = new ArrayList<Action>();
	}

	@Override
	public void redo() {
		final ListIterator<Action> listIterator = actions.listIterator();
		while( listIterator.hasNext() ) {
			listIterator.next().redo();
		}
	}

	@Override
	public void undo() {
		final ListIterator<Action> listIterator = actions.listIterator( actions.size() );
		while( listIterator.hasPrevious() ) {
			listIterator.previous().undo();
		}
	}

	public Object size() {
		return actions.size();
	}

	public void add( final Action action ) {
		actions.add( action );
	}

}
