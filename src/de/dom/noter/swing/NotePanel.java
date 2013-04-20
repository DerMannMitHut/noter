package de.dom.noter.swing;

import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.dom.noter.mvc.controller.NoteController;
import de.dom.noter.mvc.model.Note;
import de.dom.noter.mvc.view.NoteView;
import de.dom.noter.swing.Timer.TimerListener;

public class NotePanel extends JPanel implements NoteView {

	private static final int TYPING_PAUSE = 1000;

	private static final long serialVersionUID = -28761231700267765L;

	private NoteController noteController;

	private final JTextField labelTitle;
	private final JTextArea areaContent;

	private final Timer timer;

	public NotePanel(final Timer timer) {
		super();
		this.timer = timer;

		final LayoutManager layout = new BoxLayout( this, BoxLayout.PAGE_AXIS );
		setLayout( layout );

		final JPanel header = new JPanel();

		labelTitle = new JTextField();
		labelTitle.setAlignmentX( LEFT_ALIGNMENT );
		labelTitle.setFont( new Font( "SansSerif", Font.BOLD, 16 ) );
		labelTitle.addKeyListener( new KeyListener() {

			@Override
			public void keyTyped( final KeyEvent arg0 ) {
				timer.fireTimer( TYPING_PAUSE, new TimerListener() {
					@Override
					public void onTimerFired() {
						noteController.setTitle( labelTitle.getText() );
					}
				} );
			}

			@Override
			public void keyReleased( final KeyEvent arg0 ) {
			}

			@Override
			public void keyPressed( final KeyEvent arg0 ) {
			}
		} );
		add( labelTitle );

		areaContent = new JTextArea();
		areaContent.setAlignmentX( LEFT_ALIGNMENT );
		areaContent.setFont( new Font( "Monospaced", Font.PLAIN, 12 ) );
		areaContent.addKeyListener( new KeyListener() {

			@Override
			public void keyTyped( final KeyEvent arg0 ) {
				timer.fireTimer( TYPING_PAUSE, new TimerListener() {
					@Override
					public void onTimerFired() {
						noteController.setContent( areaContent.getText() );
					}
				} );
			}

			@Override
			public void keyReleased( final KeyEvent arg0 ) {
			}

			@Override
			public void keyPressed( final KeyEvent arg0 ) {
			}
		} );

		add( areaContent );
	}

	@Override
	public void onNoteChanged( final Note newNote ) {
		final String title = newNote.getTitle();
		setTitle( title == null ? "<Titel>" : title );
		setContent( newNote.getContent() );
	}

	private void setContent( final String content ) {
		if( !areaContent.getText().equals( content ) ) {
			areaContent.setText( content );
		}
	}

	private void setTitle( final String title ) {
		if( !labelTitle.getText().equals( title ) ) {
			labelTitle.setText( title );
		}
	}

	@Override
	public void setController( final NoteController newNoteController ) {
		noteController = newNoteController;
	}

	Timer getTimer() {
		return timer;
	}

}
