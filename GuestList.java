
import java.io.Serializable;
import java.util.ArrayList;

public class GuestList implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int numberOfPlaces;
	private ArrayList<Guest> participantList;

	public GuestList(int numberOfPlaces) {
		this.participantList = new ArrayList<Guest>(numberOfPlaces);
		this.numberOfPlaces = numberOfPlaces;
	}

	//	Check input numberOfPlaces
	public static boolean checkNumber(String noPlaces) {
		if (noPlaces.length() == 0) {
			return false;
		}

		for (int i = 0; i < noPlaces.length(); i++) {
			if (!Character.isDigit(noPlaces.charAt(i)) ||
					Character.getNumericValue(noPlaces.charAt(0)) < 1) {
				return false;
			}
		}
		return true;
	}

	public static int returnNoPlacesDigit(String noPlaces) {
		int n = 0;

		for (int i = 0; i < noPlaces.length(); i++) {
			int digit = Character.getNumericValue(noPlaces.charAt(i));
			n = n*10 + digit;
		}

		if (n > 25) {
			n = 25;
			System.out.println("Lista creata cu maximum 25 locuri.\n");
		} 

		return n;
	}

	// 1.
	public String addParticipant(String firstName, String lastName, String email, String phoneNumber) {
		Guest participant = new Guest(firstName, lastName, email, phoneNumber);
		firstName = Guest.returnCapitalizeFirstLetter(firstName);
		int x;

		for (int i = 0; i < this.participantList.size(); i++) {
			if ((this.participantList.get(i).checkFieldsParticipant(participant))) {
				return firstName + " esti deja inscris.";
			}
		}

		this.participantList.add(participant);

		if (this.participantList.size() > this.numberOfPlaces) {
			x = this.participantList.size() - this.numberOfPlaces;
			return firstName + " te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine " + x + ".\n" +
			"Te vom notifica daca un loc devine disponibil.";
		}

		x = participantList.indexOf(participant) + 1;
		return "Felicitari " + firstName + "! Locul tau la eveniment este confirmat. Ai locul " + x + ".\nTe asteptam!";
	}

	public ArrayList<Guest> getParticipantList() {
		return this.participantList;
	}

	public void printAll(String listType) {
		int n = this.participantList.size();
		if (n == 0) {
			System.out.println("Nicio persoana inscrisa pe liste");
		}
		int i;

		if (listType.equals("guests")) {
			n = participantList.size() < this.numberOfPlaces ? this.participantList.size() : this.participantList.size() - this.numberOfPlaces;
			i = 0;
		} else {
			n = participantList.size() < this.numberOfPlaces ? 0 : this.participantList.size();
			i = this.numberOfPlaces-1;
		}

		while (i < n) {
			this.participantList.get(i).printGuest();
			i++;
		}
	}

	public int checkListByName(String firstName, String lastName) {
		for (int i = 0; i < this.participantList.size(); i++) {
			if (this.participantList.get(i).checkName(firstName, lastName)) {
				return i;
			}
		}
		return -1;
	}

	public int checkListByEmail(String email) {
		for (int i = 0; i < this.participantList.size(); i++) {
			if (this.participantList.get(i).checkEmail(email)) {
				return i;
			}
		}
		return -1;
	}

	public int checkListByPhoneNumber(String phoneNumber) {
		for (int i = 0; i < this.participantList.size(); i++) {
			if (this.participantList.get(i).checkPhoneNumber(phoneNumber)) {
				return i;
			}
		}

		return -1;
	}

	public String removeParticipantByName(String firstName, String lastName) {
		int i = checkListByName(firstName, lastName);

		if (i < 0) {
			return "Actiunea de eliminare a esuat. Persoana nu a fost gasita.";
		}
		this.participantList.remove(i);
		return "Participant eliminat cu succes.";
	}

	public String removeParticipantByEmail(String email) {
		int i = checkListByEmail(email);

		if (i < 0) {
			return "Actiunea de eliminare a esuat. Persoana nu a fost gasita";
		}
		this.participantList.remove(i);
		return "Participant eliminat cu succes.";
	}

	public String removeParticipantByPhoneNumber(String phoneNumber) {
		int i = this.checkListByPhoneNumber(phoneNumber);

		if (i < 0) {
			return "Actiunea de eliminare a esuat. Persoana nu a fost gasita.\n";
		}
		this.participantList.remove(i);
		return"Participant eliminat cu succes.";
	}

	public String updateParticipantByName(int i, String newFirstName, String newLastName) {	

		this.participantList.get(i).setFirstName(newFirstName);
		this.participantList.get(i).setLastName(newLastName);
		return "Nume actualizat cu succes.\n";
	}

	public String updateParticipantByEmail(int i, String newEmail) {
		this.participantList.get(i).setEmail(newEmail);
		return "Email actualizat cu succes.\n";
	}

	public String updateParticipantByPhoneNumber(int i, String newPhoneNumber) {
		this.participantList.get(i).setPhoneNumber(newPhoneNumber);
		return"Numar de telefon actualizat cu succes.\n";
	}

	public int guestsNo() {
		return Math.min(this.numberOfPlaces, this.participantList.size());
	}

	public int availableSeats() {
		int available = this.numberOfPlaces - this.participantList.size();
		return (available > 0) ? available : 0;
	}

	public int waitlistNo() {
		int waitlistNo = this.participantList.size() - this.numberOfPlaces;
		return (waitlistNo > 0) ? waitlistNo : 0;
	}

	public int totalPersons() {
		return this.participantList.size();
	}

	public ArrayList<Guest> searchParticipant(String criteria) {
		ArrayList<Guest> newList = new ArrayList<>();

		for (int i = 0; i < this.participantList.size(); i++) {
			if (this.participantList.get(i).smartSearch(criteria)) {
				newList.add(this.participantList.get(i));
			}
		}

		return newList;
	}
}



