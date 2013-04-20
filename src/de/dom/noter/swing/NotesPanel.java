package de.dom.noter.swing;

import java.awt.LayoutManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class NotesPanel extends JPanel {

	private static final long serialVersionUID = -5580939459033087664L;

	private Map<Long, NotePanel> notePanels;

	private final MainWindow mainWindow;

	private final Timer timer;

	public NotesPanel(final MainWindow mainWindow) {
		super();
		final LayoutManager layout = new BoxLayout( this, BoxLayout.PAGE_AXIS );
		setLayout( layout );
		this.mainWindow = mainWindow;
		timer = new Timer();
		timer.start();
		notePanels = new LinkedHashMap<Long, NotePanel>();
		setPanels( notePanels );
	}

	private void setPanels( final Map<Long, NotePanel> newPanels ) {
		final Iterator<Entry<Long, NotePanel>> iterator = newPanels.entrySet().iterator();
		int index = getComponentCount() - 1;

		while( iterator.hasNext() && index >= 0 ) {
			final Entry<Long, NotePanel> entry = iterator.next();
			final NotePanel newPanel = entry.getValue();
			final NotePanel oldPanel = (NotePanel) getComponent( index );

			if( newPanel == oldPanel ) {
				index -= 1;
				continue;
			}

			final long id = entry.getKey();
			if( notePanels.containsKey( id ) ) {
				notePanels.remove( id );
				remove( index );
				continue;
			}

			add( newPanel, index );
			index -= 1;
		}

		while( iterator.hasNext() ) {
			add( iterator.next().getValue(), 0 );
		}

		notePanels = newPanels;

		// mainWindow.repaint();
		mainWindow.pack();
	}

	public void onNotesChanged( final Collection<Long> newNoteIds ) {
		setPanels( copyAndCreatePanels( newNoteIds ) );
	}

	private Map<Long, NotePanel> copyAndCreatePanels( final Collection<Long> newNoteIds ) {
		final Map<Long, NotePanel> newPanels = new LinkedHashMap<Long, NotePanel>();
		final Iterator<Long> iterNew = newNoteIds.iterator();
		while( iterNew.hasNext() ) {
			final long id = iterNew.next();
			NotePanel panel = notePanels.get( id );
			if( null == panel ) {
				panel = new NotePanel( timer );
			}
			newPanels.put( id, panel );
		}
		return newPanels;
	}

	public Map<Long, NotePanel> getNotePanels() {
		return Collections.unmodifiableMap( notePanels );
	}

}
