package de.dom.noter.mvc.model;

public class User extends Data {

	private String name;

	public User() {
		super();
		name = "";
	}

	private User(final User orig) {
		super( orig );
		name = orig.name;
	}

	public User(final String string) {
		super( Long.parseLong( string.substring( string.lastIndexOf( "(" ) + 1, string.lastIndexOf( ")" ) ) ) );
		name = string.substring( 0, string.lastIndexOf( "(" ) ).trim();
	}

	public User setName( final String newName ) {
		final User u = new User( this );
		u.name = newName;
		return u;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals( final Object obj ) {
		if( this == obj ) {
			return true;
		}
		if( !super.equals( obj ) ) {
			return false;
		}
		final User other = (User) obj;
		if( name == null ) {
			if( other.name != null ) {
				return false;
			}
		}
		else if( !name.equals( other.name ) ) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return name + " (" + getId() + ")";
	}

}
