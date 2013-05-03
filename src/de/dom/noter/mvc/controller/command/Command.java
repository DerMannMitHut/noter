package de.dom.noter.mvc.controller.command;

public abstract class Command {

	boolean isSealed;

	public Command() {
		isSealed = false;
	}

	final public void perform() {
		checkNotSealed();
		performInternal();
		seal();
	}

	final protected void seal() {
		isSealed = true;
	}

	final protected void checkSealed() {
		if( !isSealed ) {
			throw new IllegalArgumentException( "Command is not sealed." );
		}
	}

	final protected void checkNotSealed() {
		if( isSealed ) {
			throw new IllegalArgumentException( "Command is sealed." );
		}
	}

	abstract protected void performInternal();

}
