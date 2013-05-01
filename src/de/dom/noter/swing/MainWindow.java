package de.dom.noter.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.controller.command.UndoableCommand;
import de.dom.noter.mvc.view.NoteView;
import de.dom.noter.mvc.view.NotesView;
import de.dom.noter.swing.action.ActionRegistry;
import de.dom.noter.swing.action.ActionType;

public class MainWindow extends JFrame implements NotesView {

	private static final String NORTH = SpringLayout.NORTH;
	private static final String SOUTH = SpringLayout.SOUTH;
	private static final String WEST = SpringLayout.WEST;
	private static final String EAST = SpringLayout.EAST;

	private static final long serialVersionUID = -218773183357310209L;

	private final CommandControl commandControl;
	private final NotesPanel notesPanel;

	private NotesController notesController;
	ActionRegistry actions;

	public MainWindow(final CommandControl commandControl) {
		super( "Noter" );
		this.commandControl = commandControl;
		notesPanel = new NotesPanel( this );
	}

	private void create() {
		final MainMenu mainMenu = new MainMenu( this );
		setJMenuBar( mainMenu );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		final Container contentPane = getContentPane();

		final SpringLayout layout = new SpringLayout();
		setLayout( layout );

		final JButton buttonCreateNote = new JButton();
		buttonCreateNote.setAction( getAction( ActionType.CREATE_NEW_NOTE ) );
		layout.putConstraint( EAST, buttonCreateNote, -10, EAST, contentPane );
		layout.putConstraint( NORTH, buttonCreateNote, 10, NORTH, contentPane );
		contentPane.add( buttonCreateNote );

		final JScrollPane scrollPane = new JScrollPane( notesPanel );

		layout.putConstraint( WEST, scrollPane, 10, WEST, contentPane );
		layout.putConstraint( NORTH, scrollPane, 5, SOUTH, buttonCreateNote );
		layout.putConstraint( EAST, scrollPane, -10, EAST, contentPane );
		layout.putConstraint( SOUTH, scrollPane, -10, SOUTH, contentPane );
		contentPane.add( scrollPane );

		setMinimumSize( new Dimension( 400, 200 ) );
		setPreferredSize( new Dimension( 1024, 768 ) );

		pack();
	}

	public Action getAction( final ActionType actionType ) {
		return actions.get( actionType );
	}

	public void doCommand( final UndoableCommand command ) {
		commandControl.doCommand( command );
	}

	public void open() {
		create();

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
		actions = new ActionRegistry( notesController, commandControl );
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

}
