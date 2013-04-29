package de.dom.noter.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.action.Action;
import de.dom.noter.mvc.controller.action.ActionControl;
import de.dom.noter.mvc.controller.action.CreateNewNoteAction;
import de.dom.noter.mvc.view.NoteView;
import de.dom.noter.mvc.view.NotesView;

public class MainWindow extends JFrame implements NotesView {

	private static final String NORTH = SpringLayout.NORTH;
	private static final String SOUTH = SpringLayout.SOUTH;
	private static final String WEST = SpringLayout.WEST;
	private static final String EAST = SpringLayout.EAST;

	private static final long serialVersionUID = -218773183357310209L;

	private NotesController notesController;
	private NotesPanel notesPanel;
	private final ActionControl actionControl;

	public MainWindow(final ActionControl actionControl) {
		super( "Noter" );
		this.actionControl = actionControl;

		create();
	}

	private void create() {
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		final Container contentPane = getContentPane();

		final SpringLayout layout = new SpringLayout();
		setLayout( layout );

		final JButton buttonPlus = new JButton( "+" );
		layout.putConstraint( EAST, buttonPlus, -10, EAST, contentPane );
		layout.putConstraint( NORTH, buttonPlus, 10, NORTH, contentPane );
		contentPane.add( buttonPlus );
		buttonPlus.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( final ActionEvent arg0 ) {
				final Action action = new CreateNewNoteAction( notesController );
				doAction( action );
			}
		} );

		notesPanel = new NotesPanel( this );
		final JScrollPane scrollPane = new JScrollPane( notesPanel );

		layout.putConstraint( WEST, scrollPane, 10, WEST, contentPane );
		layout.putConstraint( NORTH, scrollPane, 5, SOUTH, buttonPlus );
		layout.putConstraint( EAST, scrollPane, -10, EAST, contentPane );
		layout.putConstraint( SOUTH, scrollPane, -10, SOUTH, contentPane );
		contentPane.add( scrollPane );

		setMinimumSize( new Dimension( 400, 200 ) );
		setPreferredSize( new Dimension( 1024, 768 ) );

		pack();
	}

	public void open() {
		javax.swing.SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				setVisible( true );
			}
		} );
	}

	@Override
	public void onNotesChanged( final Collection<Long> newNoteIds ) {
		notesPanel.onNotesChanged( newNoteIds );
	}

	@Override
	public void setController( final NotesController newNotesController ) {
		notesController = newNotesController;
	}

	@Override
	public NotesController getController() {
		return notesController;
	}

	@Override
	public Map<Long, ? extends NoteView> getNoteViews() {
		return notesPanel.getNotePanels();
	}

	@Override
	public void onNoteChanged( final Long id ) {
		// ignore
	}

	protected void doAction( final Action action ) {
		actionControl.doAction( action );
	}

}
