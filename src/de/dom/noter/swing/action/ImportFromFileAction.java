package de.dom.noter.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.Action;

import de.dom.noter.mvc.controller.NotesController;
import de.dom.noter.mvc.controller.command.CommandControl;
import de.dom.noter.mvc.controller.command.ImportFromFileCommand;
import de.dom.noter.swing.FileChooser;
import de.dom.noter.swing.FileChooser.FileSelectionHandler;
import de.dom.noter.swing.FileChooser.Mode;
import de.dom.noter.swing.MainWindow;

public class ImportFromFileAction extends AbstractNoterAction {
	private static final String ACTION_NAME = "Import notes…";
	private static final String ACTION_DESCRIPTION = "Reads notes from a text file.";

	private final CommandControl commandControl;
	private final NotesController notesController;
	private final MainWindow mainWindow;

	private ImportFromFileAction(final CommandControl commandControl, final NotesController notesController, final MainWindow mainWindow) {
		this.commandControl = commandControl;
		this.notesController = notesController;
		this.mainWindow = mainWindow;
		putValue( Action.NAME, ACTION_NAME );
		putValue( Action.SHORT_DESCRIPTION, ACTION_DESCRIPTION );
		putValue( Action.LONG_DESCRIPTION, ACTION_DESCRIPTION );
	}

	@Override
	public void actionPerformed( final ActionEvent e ) {
		final FileChooser chooser = new FileChooser( mainWindow, ACTION_NAME, Mode.LOAD_FILE );
		chooser.setFilenameFilter( new FilenameFilter() {
			@Override
			public boolean accept( final File dir, final String name ) {
				return name.endsWith( ".txt" );
			}
		} );
		chooser.openDialog( new FileSelectionHandler() {
			@Override
			public void onFileSelected( final File file ) {
				commandControl.doCommand( new ImportFromFileCommand( notesController, file ) );
			}
		} );
	}

	public static Action create( final ActionData data ) {
		return new ImportFromFileAction( data.commandControl, data.notesController, data.mainWindow );
	}

}
