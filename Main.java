
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static final Scanner input = new Scanner(System.in);

	public static String  nextCommand() {
		System.out.print("Introdu o comanda: ");
		String command = Main.input.nextLine().toLowerCase();
		return command;
	}

	public static void showMenu() {
		System.out.println("add \t\t- Adauga o noua persoana \n" +
				"check \t\t- Verifica daca o persoana este inscriasa la eveniment \n" +
				"remove \t\t- Sterge o persoana existenta din lista \n" +
				"remove all \t- Sterge toate persoanele din ambele liste \n" +
				"update \t\t- Actualizeaza detaliile unei persoane \n" +
				"guests \t\t- lista de persoane care participa la eveniment \n" +
				"waitlist \t- Persoanele din lista de asteptare \n" +
				"available \t- Numarul de locuri libere \n" +
				"guests_no \t- Numarul de persoane care participa la eveniment \n" +
				"waitlist_no \t- Numarul de persoane din lista de asteptare \n" +
				"subscribe_no \t- Numarul total de persone inscrise \n" +
				"search \t\t- Cauta toti invitatii conform sirului de caractere introdus \n" +
				"quit \t\t- Inchide aplicatia");
	}

	public static String searchType() {
		System.out.println("Cautare dupa: \n" + "\tNume: n \n" + "\tEmail: e \n" + "\tNumar telefon: t \n");
		String criteria = Main.input.nextLine().toLowerCase();

		return criteria;
	}

	public static String updateType() {
		System.out.println("Actualizare camp: \n" + "\tNume: n \n" + "\tEmail: e \n" + "\tNumar telefon: t \n");
		String criteria = Main.input.nextLine().toLowerCase();

		return criteria;
	}

	public static String updateChoice(GuestList list, String updateField, int i) {

		while (true) {

			switch (updateField) {

			case "n":
				String newFirstName = Guest.inputAndValidateFirstName();
				String newLastName = Guest.inputAndValidateLastName();
				
				if (list.checkListByName(newFirstName, newLastName) >= 0) {
					return "Nu se poate efectua actualizarea cu un nume deja existent pe liste.";
				}
				
				return list.updateParticipantByName(i, newFirstName, newLastName);

			case "e":
				String newEmail = Guest.inputAndValidateEmail();
				
				if (list.checkListByEmail(newEmail) >= 0) {
					return "Nu se poate efectua actualizarea cu un nume deja existent pe liste.";
				}

				return list.updateParticipantByEmail(i, newEmail);

			case "t":
				String newPhoneNumber = Guest.inputAndValidatePhoneNumber();

				if (list.checkListByPhoneNumber(newPhoneNumber) >= 0) {
					return "Nu se poate efectua actualizarea cu un numar de telefon deja existent pe liste.";
				}

				return list.updateParticipantByPhoneNumber(i, newPhoneNumber);

			default:
				System.out.println("Mai alege inca o data:");
				updateField = Main.input.nextLine().toLowerCase();
				break;
			}
		}
	}

	public static String inputCriteriaToSearch() {
		System.out.print("Cautare dupa: ");
		String criteria = Main.input.nextLine().toLowerCase();

		return criteria;
	}

	public static GuestList createList(String noPlaces) {
		while (!GuestList.checkNumber(noPlaces)) {
			System.out.print("Introdu numai numere pozitive diferite de 0: ");
			noPlaces = Main.input.nextLine();
		}
		int n = GuestList.returnNoPlacesDigit(noPlaces);

		return new GuestList(n);
	}
	
	public static GuestList readFromBinaryFIle() throws IOException {
		GuestList guestList = null;
		
		try(ObjectInputStream binaryFileGuestList = new ObjectInputStream(new FileInputStream("C:/data_local/1Sorin/P1/guestList.dat"))) {
			while(true) {
				try {
					guestList = (GuestList) binaryFileGuestList.readObject();
					return guestList;
				} catch (EOFException e) {
					break;
				}
			}
		} catch (EOFException e) {
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		} catch (FileNotFoundException e) {
		}
		
		return guestList;
	}

	public static void writeInBinaryFile(GuestList guestList) throws IOException {
		try(ObjectOutputStream binaryFileOutGuestList = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("C:/data_local/1Sorin/P1/guestList.dat")))) {
			binaryFileOutGuestList.writeObject(guestList);
		}
	}

	public static void main(String[] args) throws IOException {
//		System.out.print("Creaza lista.\nNumar locuri (maxim 25 locuri): ");
//		String noPlaces = Main.input.nextLine();
		GuestList guestList = readFromBinaryFIle();
		

//		Guest person1 = new Guest("sorin", "albu", "albusorin28@gmail.com", "0732300956");
//		Guest person2 = new Guest("andreea", "sendea", "sendeaandreea@gmail.com", "0720000176");
//		Guest person3 = new Guest("ion", "creanga", "ioncreanga@gmail.com", "0700123456");
//		Guest person4 = new Guest("ioan", "slavici", "ioanslavici@gmail.com", "0700987654");
//		Guest person5 = new Guest("mihai", "eminescu", "mihaieminescu@gmail.com", "0700000000");
//
//		guestList.addParticipant(person1);
//		guestList.addParticipant(person2);
//		guestList.addParticipant(person3);
//		guestList.addParticipant(person4);
//		guestList.addParticipant(person5);

		System.out.print("Introdu o comanda: \n" + "help - Afiseaza lista de comenzi \n");
		String command = Main.input.nextLine().toLowerCase();

		while(!command.equals("quit")) {
			switch (command) {

			case "help":
				showMenu();
				break;

			case "add":
				String firstName = Guest.inputAndValidateFirstName();
				String lastName = Guest.inputAndValidateLastName();
				String email = Guest.inputAndValidateEmail();
				String phoneNumber = Guest.inputAndValidatePhoneNumber();
				System.out.println(guestList.addParticipant(firstName, lastName, email, phoneNumber));
				break;

			case "check":
				if (guestList.totalPersons() < 1) {
					System.out.println("Lista vida. Comanda nu poate fi aplicata.");
					break;
				}
				String criteria = searchType();
				boolean isSearchDone = false;

				while (!isSearchDone) {
					switch(criteria) {

					case "n":
						firstName = Guest.inputAndValidateFirstName();
						lastName = Guest.inputAndValidateLastName();
						int a = guestList.checkListByName(firstName, lastName);
						System.out.println(a < 0 ? "Persoana nu a fost gasita" : "Persoana " + Guest.returnCapitalizeFirstLetter(firstName) +
								" " + Guest.returnCapitalizeFirstLetter(lastName) + " este inscrisa " + " pe pozitia " + (a+1));
						isSearchDone = true;
						break;

					case "e" :
						email = Guest.inputAndValidateEmail();
						a = guestList.checkListByEmail(email);
						System.out.println(a < 0 ? "Persoana nu a fost gasita" : "Persoana cu adresa de  email " +
						email + " este inscrisa " + " pe pozitia " + (a+1));
						isSearchDone = true;
						break;

					case "t":
						phoneNumber = Guest.inputAndValidatePhoneNumber();
						a = guestList.checkListByPhoneNumber(phoneNumber);
						System.out.println(a < 0 ? "Persoana nu a fost gasita" : "Persoana cu numarul de telefon " +
						phoneNumber + " este inscrisa " + " pe pozitia " + (a+1));
						isSearchDone = true;
						break;

					default:
						System.out.println("Criteriu de cautare invalid. Mai alege inca o data:");
						criteria = Main.input.nextLine().toLowerCase();
						break;
					}
				}
				break;

			case "remove":
				if (guestList.totalPersons() < 1) {
					System.out.println("Lista vida. Comanda nu poate fi aplicata.");
					break;
				}

				criteria = searchType();
				isSearchDone = false;

				while (!isSearchDone) {
					switch(criteria) {

					case "n":
						firstName = Guest.inputAndValidateFirstName();
						lastName = Guest.inputAndValidateLastName();
						System.out.println(guestList.removeParticipantByName(firstName, lastName));
						isSearchDone = true;
						break;

					case "e" :
						email = Guest.inputAndValidateEmail();
						System.out.println(guestList.removeParticipantByEmail(email));
						isSearchDone = true;
						break;

					case "t":
						phoneNumber = Guest.inputAndValidatePhoneNumber();
						System.out.println(guestList.removeParticipantByPhoneNumber(phoneNumber));
						isSearchDone = true;
						break;

					default:
						System.out.println("Mai alege inca o data:");
						criteria = Main.input.nextLine().toLowerCase();
						break;
					}
				}
				break;

			case "remove all":
				guestList.getParticipantList().clear();
				System.out.println("Ambele liste sunt goale.");
				break;

			case "update":
				if (guestList.totalPersons() < 1) {
					System.out.println("Lista vida. Comanda nu poate fi aplicata.");
					break;
				}

				criteria = searchType();
				isSearchDone = false;

				while (!isSearchDone) {
					
					switch(criteria) {
					
					case "n":
						firstName = Guest.inputAndValidateFirstName();
						lastName = Guest.inputAndValidateLastName();
						int a = guestList.checkListByName(firstName, lastName);
						
						if (a < 0) {
							System.out.println("Persoana nu a fost gasita");
							isSearchDone = true;
							break;
						}

						String fieldUpdate = updateType();
						System.out.println(updateChoice(guestList, fieldUpdate, a));
						isSearchDone = true;
						break;

					case "e" :
						email = Guest.inputAndValidateEmail();
						a = guestList.checkListByEmail(email);

						if (a < 0) {
							System.out.println("Persoana nu a fost gasita");
							isSearchDone = true;
							break;
						}

						fieldUpdate = updateType();
						System.out.println(updateChoice(guestList, fieldUpdate, a));
						isSearchDone = true;
						break;

					case "t":
						phoneNumber = Guest.inputAndValidatePhoneNumber();
						a = guestList.checkListByPhoneNumber(phoneNumber);
						
						if (a < 0) {
							System.out.println("Persoana nu a fost gasita");
							isSearchDone = true;
							break;
						}

						fieldUpdate = updateType();
						System.out.println(updateChoice(guestList, fieldUpdate, a));
						isSearchDone = true;
						break;

					default:
						System.out.println("Criteriu de cautare invalid. Mai alege inca o data:");
						criteria = Main.input.nextLine().toLowerCase();
						break;
					}
				}
				break;

			case "guests":
				guestList.printAll("guests");
				break;

			case "waitlist":
				guestList.printAll("waitlist");
				break;

			case "available":
				System.out.println(guestList.availableSeats());
				break;

			case "guests_no":
				System.out.println(guestList.guestsNo());
				break;

			case "waitlist_no":
				System.out.println(guestList.waitlistNo());
				break;

			case "subscribe_no":
				 System.out.println(guestList.totalPersons());
				break;

			case "search":
				if (guestList.totalPersons() < 1) {
					System.out.println("Lista vida. Comanda nu poate fi aplicata.");
					break;
				}

				String sequence = inputCriteriaToSearch();
				ArrayList<Guest> list = guestList.searchParticipant(sequence);
				
				for (int i = 0; i < list.size(); i++) {
					list.get(i).printGuest();
					System.out.println();
				}
				break;

			default:
				System.out.println("Comanda gresita. Alege o comanda din meniu:");
				System.out.println();
				showMenu();
				break;
			}

			System.out.println();
			command = nextCommand();
			System.out.println();
		}
		
		writeInBinaryFile(guestList);
		System.out.println("La revedere!");
	}
}
