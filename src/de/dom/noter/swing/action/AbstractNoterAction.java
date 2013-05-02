package de.dom.noter.swing.action;

import java.awt.Toolkit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

public abstract class AbstractNoterAction extends AbstractAction {

	private static final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	public static Action create( final ActionData data ) {
		throw new UnsupportedOperationException( "Override this method!" );
	}

	protected void setName( final String name ) {
		putValue( Action.NAME, name );
	}

	protected String getName() {
		return (String) getValue( Action.NAME );
	}

	protected void setDescription( final String description ) {
		putValue( Action.SHORT_DESCRIPTION, description );
		putValue( Action.LONG_DESCRIPTION, description );
	}

	protected void setShortcut( final char keyCode ) {
		setShortcut( keyCode, 0 );
	}

	protected void setShortcut( final char keyCode, final int additionalModifiers ) {
		final KeyStroke shortcutStroke = KeyStroke.getKeyStroke( keyCode, SHORTCUT_MASK | additionalModifiers );
		putValue( Action.ACCELERATOR_KEY, shortcutStroke );
	}
}
