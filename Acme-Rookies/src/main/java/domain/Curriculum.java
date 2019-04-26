
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "isOriginal, hacker")
})
public class Curriculum extends DomainEntity {

	// Constructor

	public Curriculum() {
		super();
	}


	// Attributes

	private boolean	isOriginal;
	private String	title;


	public boolean getIsOriginal() {
		return this.isOriginal;
	}

	public void setIsOriginal(final boolean isOriginal) {
		this.isOriginal = isOriginal;
	}

	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}


	// Relationships

	private Hacker							hacker;
	private Collection<MiscellaneousData>	miscellaneousDatas;
	private Collection<EducationData>		educationDatas;
	private Collection<PositionData>		positionDatas;
	private PersonalData					personalData;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousData> getMiscellaneousDatas() {
		return this.miscellaneousDatas;
	}

	public void setMiscellaneousDatas(final Collection<MiscellaneousData> miscellaneousDatas) {
		this.miscellaneousDatas = miscellaneousDatas;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationData> getEducationDatas() {
		return this.educationDatas;
	}

	public void setEducationDatas(final Collection<EducationData> educationDatas) {
		this.educationDatas = educationDatas;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PositionData> getPositionDatas() {
		return this.positionDatas;
	}

	public void setPositionDatas(final Collection<PositionData> positionDatas) {
		this.positionDatas = positionDatas;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

}
