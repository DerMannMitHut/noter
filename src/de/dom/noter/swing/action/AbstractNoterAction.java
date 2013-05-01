package de.dom.noter.swing.action;

import java.awt.Toolkit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

public abstract class AbstractNoterAction extends AbstractAction {

	int shortcutMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	protected KeyStroke getShortcutStroke( final char keyCode ) {
		return KeyStroke.getKeyStroke( keyCode, shortcutMask );
	}

	public static Action create( final ActionData data ) {
		throw new UnsupportedOperationException( "Override this method!" );
	}

}
