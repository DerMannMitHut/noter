package de.dom.noter.swing.action;

import java.util.Map;

import javax.swing.Action;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;

public class ActionRegistry {

	Map<ActionType, Action> actions;

	public ActionRegistry(final NotesController notesController, final CommandControl commandControl) {
		final ActionData data = new ActionData( notesController, commandControl );
		actions = ActionType.createActions( data );
	}

	public Action get( final ActionType actionType ) {
		return actions.get( actionType );
	}

}
