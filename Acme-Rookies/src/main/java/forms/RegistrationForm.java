
package forms;

public class RegistrationForm {

	private int				id;
	private String			name;
	private String			surname;
	private String			fullname;
	private int				VATnumber;
	private CreditCardForm	creditCard;
	private String			photo;
	private String			email;
	private String			phoneNumber;
	private String			address;
	private Boolean			isSpammer;
	private String			commercialName;
	private UserAccountForm	userAccount;
	private boolean			checkBoxAccepted;
	private boolean			checkBoxDataProcessesAccepted;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Boolean getIsSpammer() {
		return this.isSpammer;
	}

	public void setIsSpammer(final Boolean isSpammer) {
		this.isSpammer = isSpammer;
	}

	public boolean getCheckBoxAccepted() {
		return this.checkBoxAccepted;
	}

	public void setCheckBoxAccepted(final boolean checkBoxAccepted) {
		this.checkBoxAccepted = checkBoxAccepted;
	}

	public int getVATnumber() {
		return this.VATnumber;
	}

	public void setVATnumber(final int vATnumber) {
		this.VATnumber = vATnumber;
	}

	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	public CreditCardForm getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCardForm creditCard) {
		this.creditCard = creditCard;
	}

	public UserAccountForm getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccountForm userAccount) {
		this.userAccount = userAccount;
	}

	public boolean getCheckBoxDataProcessesAccepted() {
		return this.checkBoxDataProcessesAccepted;
	}

	public void setCheckBoxDataProcessesAccepted(final boolean checkBoxDataProcessesAccepted) {
		this.checkBoxDataProcessesAccepted = checkBoxDataProcessesAccepted;
	}

}
