package com.swabhav.Final_Guitar_Call.Model;

public enum Wood {
INDIAN_ROSEWOOD , BRAZILIAN_ROSEWOOD , MAHOGANY , MAPL , COCOBOLO , CEDAR;
	
	public String toString() {
		switch(this) {
		case INDIAN_ROSEWOOD : return "INDIAN_ROSEWOOD";
		case BRAZILIAN_ROSEWOOD :  return "BRAZILIAN_ROSEWOOD";
		case MAHOGANY : return "MAHOGANY";
		case MAPL : return "MAPL";
		case COCOBOLO : return "COCOBOLO";
		case CEDAR : return "CEDAR";
		}
		return "";
	}
}
