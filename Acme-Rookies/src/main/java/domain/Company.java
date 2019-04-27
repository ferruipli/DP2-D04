
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	// Constructor

	public Company() {
		super();
	}


	// Attributes

	private String	commercialName;
	private Double	auditScore;


	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	@Digits(integer = 4, fraction = 2)
	@Range(min = 0, max = 1)
	public Double getAuditScore() {
		return this.auditScore;
	}

	public void setAuditScore(final Double auditScore) {
		this.auditScore = auditScore;
	}

}
