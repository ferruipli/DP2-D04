
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	// Constructor

	public PersonalData() {
		super();
	}


	// Attributes

	private String	fullname;
	private String	statement;
	private String	phoneNumber;
	private String	githubProfile;
	private String	linkedInProfile;


	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getGithubProfile() {
		return this.githubProfile;
	}

	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}

	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
	}

}
