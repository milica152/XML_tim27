package ftn.project.xml.model;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	private TRole type;

	public TRole getType() {
		return type;
	}

	public void setType(TRole type) {
		this.type = type;
	}

	@Override
	public String getAuthority() {
		return type.toString();
	}

	@Override
	public boolean equals(Object o) {

		if (o == this) {
			return true;
		}

		if (!(o instanceof Authority)) {
			return false;
		}
		Authority authority = (Authority) o;
		return (this.getType()!=null ? this.getType().equals(authority.getType()): authority.getType()==null);

	}

}


