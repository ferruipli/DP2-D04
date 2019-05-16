
package forms;

public class CreditCardForm {

	private int		id;
	private String	holder;
	private String	make;
	private String	number;
	private String	expirationMonth;
	private String	expirationYear;
	private int		cvvCode;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public int getCvvCode() {
		return this.cvvCode;
	}

	public void setCvvCode(final int cvvCode) {
		this.cvvCode = cvvCode;
	}

}
