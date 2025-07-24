package com.swabhav.Final_Guitar_Call.Test;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.swabhav.Final_Guitar_Call.Model.Builder;
import com.swabhav.Final_Guitar_Call.Model.Final_Guitar;
import com.swabhav.Final_Guitar_Call.Model.GuitarSpec;
import com.swabhav.Final_Guitar_Call.Model.Type;
import com.swabhav.Final_Guitar_Call.Model.Wood;

public class Guitar2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		boolean validChoice = false;

		while (!validChoice) {
			try {
				System.out.println("Choose type of guitar you want:");
				System.out.println("1. ELECTRIC");
				System.out.println("2. ACOUSTIC");
				System.out.println("3. Your Own Fully Customized Guitar");
				System.out.print("Enter your choice (1-3): ");
				choice = scanner.nextInt();
				scanner.nextLine(); 

				if (choice >= 1 && choice <= 3) {
					validChoice = true;
				} else {
					System.out.println("Invalid choice! Please enter a number between 1 and 3.\n");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please enter a valid number between 1 and 3.\n");
				scanner.nextLine();
			}
		}

		switch (choice) {
		case 1:

			System.out.println("\nThese are all the 'ELECTRIC' guitars in the shop");
			System.out.println("--------------------------------------------------");
			System.out.println("SerialNo || Price || Builder || Name || String || Type || Front-Wood || Back-Wood");
			System.out.println("--------------------------------------------------");
			System.out.println(
					"1.123 || 1500.0 || FENDER || Stratocastor || String-6 || ELECTRIC || COCOBOLO || BRAZILIAN_ROSEWOOD");
			System.out.println(
					"2.124 || 1600.0 || FENDER || Stratocastor || String-4 || ELECTRIC || INDIAN_ROSEWOOD || CEDAR");
			System.out.println("--------------------------------------------------");

			String electricChoice = "";
			boolean validElectricSerial = false;

			while (!validElectricSerial) {
				System.out.print("Select the Serial Number of the electric guitar you want to buy (1.123 or 2.124): ");
				electricChoice = scanner.nextLine().trim();

				if (electricChoice.equals("1.123") || electricChoice.equals("2.124")) {
					validElectricSerial = true;
				} else {
					System.out.println("Invalid serial number! Please enter either 1.123 or 2.124.\n");
				}
			}

			Final_Guitar electricGuitar = null;
			if (electricChoice.equals("1.123")) {
				electricGuitar = new Final_Guitar("1.123", 1500.0, new GuitarSpec(Builder.FENDER, "Stratocastor",
						Type.ELECTRIC, Wood.COCOBOLO, Wood.BRAZILIAN_ROSEWOOD, "6"));
			} else if (electricChoice.equals("2.124")) {
				electricGuitar = new Final_Guitar("2.124", 1600.0, new GuitarSpec(Builder.FENDER, "Stratocastor",
						Type.ELECTRIC, Wood.INDIAN_ROSEWOOD, Wood.CEDAR, "4"));
			}

			if (electricGuitar != null) {
				checkout(electricGuitar);
			}
			break;

		case 2:

			System.out.println("\nThese are all the 'ACOUSTIC' guitars in the shop");
			System.out.println("--------------------------------------------------");
			System.out.println("SerialNo || Price || Builder || Name || String || Type || Front-Wood || Back-Wood");
			System.out.println("--------------------------------------------------");
			System.out.println(
					"1.456 || 1000.0 || COLLINGS || SoundMaster || String-6 || ACOUSTIC || MAHOGANY || MAHOGANY");
			System.out.println(
					"2.457 || 1200.0 || MARTIN || Harmony || String-12 || ACOUSTIC || BRAZILIAN_ROSEWOOD || CEDAR");
			System.out.println("--------------------------------------------------");

			String acousticChoice = "";
			boolean validAcousticSerial = false;

			while (!validAcousticSerial) {
				System.out.print("Select the Serial Number of the acoustic guitar you want to buy (1.456 or 2.457): ");
				acousticChoice = scanner.nextLine().trim();

				if (acousticChoice.equals("1.456") || acousticChoice.equals("2.457")) {
					validAcousticSerial = true;
				} else {
					System.out.println("Invalid serial number! Please enter either 1.456 or 2.457.\n");
				}
			}

			Final_Guitar acousticGuitar = null;
			if (acousticChoice.equals("1.456")) {
				acousticGuitar = new Final_Guitar("1.456", 1000.0, new GuitarSpec(Builder.COLLINGS, "SoundMaster",
						Type.ACOUSTIC, Wood.MAHOGANY, Wood.MAHOGANY, "6"));
			} else if (acousticChoice.equals("2.457")) {
				acousticGuitar = new Final_Guitar("2.457", 1200.0, new GuitarSpec(Builder.MARTIN, "Harmony",
						Type.ACOUSTIC, Wood.BRAZILIAN_ROSEWOOD, Wood.CEDAR, "12"));
			}

			if (acousticGuitar != null) {
				checkout(acousticGuitar);
			}
			break;

		case 3:

			System.out.println("\nBuilding a customized guitar...");

			Builder builder = null;
			while (builder == null) {
				try {
					System.out.println("Available builders: FENDER, GIBSON, COLLINGS, MARTIN, OLSON, PRS, ANY, RYAN");
					System.out.print("Enter the builder: ");
					String builderInput = scanner.nextLine().trim();
					builder = Builder.valueOf(builderInput.toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid builder! Please enter a valid builder from the list above.\n");
				}
			}

			String model = "";
			while (model.trim().isEmpty()) {
				System.out.print("Enter the model of the guitar: ");
				model = scanner.nextLine().trim();
				if (model.isEmpty()) {
					System.out.println("Model cannot be empty! Please enter a valid model name.\n");
				}
			}

			String stringCount = "";
			boolean validStringCount = false;
			while (!validStringCount) {
				System.out.print("Enter the string count (e.g., 6, 12): ");
				stringCount = scanner.nextLine().trim();

				try {
					int count = Integer.parseInt(stringCount);
					if (count > 0 && count <= 20) {
						validStringCount = true;
					} else {
						System.out.println("Invalid string count! Please enter a number between 1 and 20.\n");
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid input! Please enter a valid number for string count.\n");
				}
			}

			Wood frontWood = null;
			while (frontWood == null) {
				try {
					System.out.println(
							"Available wood types: BRAZILIAN_ROSEWOOD, CEDAR, COCOBOLO, INDIAN_ROSEWOOD, MAHOGANY");
					System.out.print("Enter the front wood type: ");
					String frontWoodInput = scanner.nextLine().trim();
					frontWood = Wood.valueOf(frontWoodInput.toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid wood type! Please enter a valid wood type from the list above.\n");
				}
			}

			Wood backWood = null;
			while (backWood == null) {
				try {
					System.out.println(
							"Available wood types: BRAZILIAN_ROSEWOOD, CEDAR, COCOBOLO, INDIAN_ROSEWOOD, MAHOGANY");
					System.out.print("Enter the back wood type: ");
					String backWoodInput = scanner.nextLine().trim();
					backWood = Wood.valueOf(backWoodInput.toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid wood type! Please enter a valid wood type from the list above.\n");
				}
			}

			Final_Guitar customGuitar = new Final_Guitar("Custom", 1500.0,
					new GuitarSpec(builder, model, Type.ELECTRIC, frontWood, backWood, stringCount));

			checkout(customGuitar);
			break;

		default:
			System.out.println("Invalid choice! Please choose between 1 and 3.");
			break;
		}

		scanner.close();
	}

	public static void checkout(Final_Guitar selectedGuitar) {
		System.out.println("\n------------ Checkout Details ------------");
		System.out.println("Serial Number: " + selectedGuitar.getSerialNumber());
		System.out.println("Builder: " + selectedGuitar.getSpec().getBuilder());
		System.out.println("Model: " + selectedGuitar.getSpec().getModel());
		System.out.println("Wood Type (Front): " + selectedGuitar.getSpec().getFrontWood());
		System.out.println("Wood Type (Back): " + selectedGuitar.getSpec().getBackWood());
		System.out.println("String Count: " + selectedGuitar.getSpec().getStringCount());
		System.out.println("Original Price: $" + selectedGuitar.getPrice());
		System.out.println("Final Price: $" + selectedGuitar.calculateModifiedPrice());
		System.out.println("----------------------------------------");
		System.out.println("Thank you for purchasing the guitar!");
	}
}