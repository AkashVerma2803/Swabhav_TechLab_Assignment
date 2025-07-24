package com.swabhav.Final_Guitar_Call.Model;

import java.util.ArrayList;
import java.util.List;

public class GuitarInventory {

	public List<Final_Guitar> guitars = new ArrayList<>();

	public void addGuitar(String serialNumber, double price, GuitarSpec spec) {
		Final_Guitar guitar = new Final_Guitar(serialNumber, price, spec);
		guitars.add(guitar);
	}

	public List<Final_Guitar> search(GuitarSpec searchSpec) {
		List<Final_Guitar> matchingGuitar = new ArrayList<>();
		for (Final_Guitar guitar : guitars) {
			if (guitar.getSpec().matches(searchSpec)) {
				matchingGuitar.add(guitar);
			}
		}
		return matchingGuitar;
	}

	public Final_Guitar getGuitar(String serialNumber) {
		for (Final_Guitar guitar : guitars) {
			if (guitar.getSerialNumber().equals(serialNumber)) {
				return guitar;
			}
		}
		return null;
	}
}
