
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	// Constructor

	public Provider() {
		super();
	}


	// Attributes

	private String	makee;


	@NotBlank
	public String getMakee() {
		return this.makee;
	}

	public void setMakee(final String makee) {
		this.makee = makee;
	}

}
