
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationData extends DomainEntity {

	// Constructor

	public EducationData() {
		super();
	}


	// Attributes

	private String	degree;
	private String	institution;
	private double	mark;
	private Date	startDate;
	private Date	endDate;


	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(final String degree) {
		this.degree = degree;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@Digits(integer = 5, fraction = 2)
	@Range(min = 0, max = 100)
	public double getMark() {
		return this.mark;
	}

	public void setMark(final double mark) {
		this.mark = mark;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
