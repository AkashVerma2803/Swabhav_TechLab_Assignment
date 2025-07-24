package com.swabhav.Final_Guitar_Call.Model;

public class Final_Guitar {
	private String serialNumber;
	private double price;
	private GuitarSpec spec;

	public Final_Guitar(String serialNumber, double price, GuitarSpec spec) {
	    this.serialNumber = serialNumber;
	    this.price = price;
	    this.spec = spec;
	}


	public String getSerialNumber() {
		return serialNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public GuitarSpec getSpec() {
		return spec;
	}
	
	 public double calculateModifiedPrice() {
	        double finalPrice = this.price;

	        if (this.spec.getBuilder() == Builder.FENDER) {
	            finalPrice += 200; 
	        } else if (this.spec.getBuilder() == Builder.GIBSON) {
	            finalPrice += 300; 
	        } else if(this.spec.getBuilder() == Builder.COLLINGS) {
	        	finalPrice += 400;
	        }else if(this.spec.getBuilder() == Builder.MARTIN) {
	        	finalPrice += 500;
	        }else if(this.spec.getBuilder() == Builder.OLSON) {
	        	finalPrice += 600;
	        }else if(this.spec.getBuilder() == Builder.PRS) {
	        	finalPrice += 700;
	        }else if(this.spec.getBuilder() == Builder.ANY) {
	        	finalPrice += 800;
	        }else if(this.spec.getBuilder() == Builder.RYAN) {
	        	finalPrice += 900;
	        }

	        
	        if (this.spec.getFrontWood() == Wood.BRAZILIAN_ROSEWOOD) {
	            finalPrice += 200;  
	        }else if (this.spec.getFrontWood() == Wood.CEDAR) {
	            finalPrice += 300;  
	        }else if (this.spec.getFrontWood() == Wood.COCOBOLO) {
	            finalPrice += 400;  
	        }else if (this.spec.getFrontWood() == Wood.INDIAN_ROSEWOOD) {
	            finalPrice += 500;  
	        }else if (this.spec.getFrontWood() == Wood.MAHOGANY) {
	            finalPrice += 600;  
	        }else if (this.spec.getFrontWood() == Wood.MAHOGANY) {
	            finalPrice += 700;  
	        }

	        return finalPrice;
	    }
}
