package de.dom.noter.swing.action;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class AbstractNoterAction extends AbstractAction {

	public static Action create( final ActionData data ) {
		throw new UnsupportedOperationException( "Override this method!" );
	}

}
