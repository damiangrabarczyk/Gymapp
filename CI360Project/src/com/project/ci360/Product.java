package com.project.ci360;

public class Product {

	private String name;
	private String description;
	private int iconID;
	
	
	
	public Product(String name, String description, int iconID) {
		super();
		this.name = name;
		this.description = description;
		this.iconID = iconID;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getIconID() {
		return iconID;
	}
	
	
}
