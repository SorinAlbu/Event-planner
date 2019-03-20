
import java.io.Serializable;
import java.util.Scanner;

public class Guest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String lastName;
	private String firstName;
	private String email;
	private String phoneNumber;
	private static final Scanner input = new Scanner(System.in);

	public Guest(String firstName, String lastName, String email, String phoneNumber) {
		this.lastName = lastName.toLowerCase();
		this.firstName = firstName.toLowerCase();
		this.email = email.toLowerCase();
		this.phoneNumber = phoneNumber;
	}
	
	public boolean smartSearch(String criteria) {
		return this.lastName.contains(criteria) ||
				this.firstName.contains(criteria) ||
				this.email.contains(criteria) ||
				this.phoneNumber.contains(criteria);
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// Validate Fields && Criteria To Search
	public static boolean validateFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			return false;
		}
		firstName = firstName.toLowerCase();

		for (int i = 0; i < firstName.length(); i++) {
			if (!Character.isLetter(firstName.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			return false;
		}
		lastName = lastName.toLowerCase();

		for (int i = 0; i < lastName.length(); i++) {
			if (!Character.isLetter(lastName.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateEmail(String email) {
		if (email == null || email.length() == 0) {
			return false;
		}
		email = email.toLowerCase();
		return (conditionEmail1(email) && conditionEmail2(email) && conditionEmail3(email)) ? true : false;
	}

	private static boolean conditionEmail1(String email) {
		return (email.contains("@") && email.contains(".")) ? true : false;
	}

	private static boolean conditionEmail2(String email) {
		String subStr = email.substring(email.indexOf('@') + 1);

		if (subStr.contains("@") || !subStr.contains(".") || (subStr.indexOf('.') < 1)) {
			return false;
		}
		return true;
	}

	private static boolean conditionEmail3(String email) {
		email = email.replace("@", "").replace(".", "");

		for (int i = 0; i < email.length(); i++) {
			if (!Character.isLetterOrDigit(email.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	public static boolean validatePhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.length() == 0) {
			return false;
		}
		if (phoneNumber.length() != 10) {
			return false;
		}

		for (int i = 0; i < phoneNumber.length(); i++) {
			if (!Character.isDigit(phoneNumber.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean checkFieldsParticipant(Guest participant) {
		if (this.firstName.equals(participant.firstName) && 
				this.lastName.equals(participant.lastName) ||
				this.email.equals(participant.email) ||
				this.phoneNumber.equals(participant.phoneNumber)) {
			
			return true;
		}
		
		return false;
	}
	
	public boolean checkName(String firstName, String lastName) {
		return this.firstName.equals(firstName) && this.lastName.equals(lastName) ? true : false;
	}
	
	public boolean checkEmail (String email) {
		return this.email.equals(email) ? true : false;
	}
	
	public boolean checkPhoneNumber(String phoneNumber) {
		return this.phoneNumber.equals(phoneNumber) ? true: false;
	}
	
//	Validate input as Guest Fields
	public static String inputAndValidateFirstName() {
		System.out.print("prenume: ");
		String firstName = Guest.input.nextLine().toLowerCase();

		while (!Guest.validateFirstName(firstName)) {
			System.out.print("Prenume invalid. Mai introdu o data: ");
			firstName = Guest.input.nextLine();
		}

		return firstName;
	}

	public static String inputAndValidateLastName() {
		System.out.print("nume familie: ");
		String lastName = Guest.input.nextLine().toLowerCase();

		while (!Guest.validateLastName(lastName)) {
			System.out.print("Nume familie invalid. Mai introdu o data: ");
			lastName = Guest.input.nextLine();
		}

		return lastName;
	}

	public static String inputAndValidateEmail() {
		System.out.print("email: ");
		String email = Guest.input.nextLine().toLowerCase();

		while (!Guest.validateEmail(email)) {
			System.out.print("email invalid. Mai introdu o data: ");
			email = Guest.input.nextLine();
		}

		return email;
	}

	public static String inputAndValidatePhoneNumber() {
		System.out.print("numar telefon: ");
		String email = Guest.input.nextLine();

		while (!Guest.validatePhoneNumber(email)) {
			System.out.print("Numar telefon invalid. Mai introdu o data: ");
			email = Guest.input.nextLine();
		}

		return email;
	}
	
	public void printGuest() {
		String firstName = returnCapitalizeFirstLetter(this.firstName);
		String lastName = returnCapitalizeFirstLetter(this.lastName);
		
		System.out.println(firstName + ", " + lastName + ", " + this.email + ", " + this.phoneNumber);
	}
	
	public static String returnCapitalizeFirstLetter(String word) {
		word = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
		return word;
	}
}

