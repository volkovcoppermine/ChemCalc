package com.example.chemistry;

import java.util.regex.Pattern;

public class Substance {
	private String subType;
	private Element elem;
	private String radical;
	private int elemQuantity;
	private int radQuantity;

	public static String react(Substance s){
		String type = s.subType;
		String res = "";
		switch (type){
		case "water":
			res = "H2 + O2";
			break;
		case "base":
			if ((Element.getSym(s.elem) == "Mg")||(Element.getSym(s.elem) == "Ca")||(Element.getSym(s.elem) == "Zn")||(Element.getSym(s.elem) == "Cu")||(Element.getSym(s.elem) == "Al")||(Element.getSym(s.elem) == "Fe")||(Element.getSym(s.elem) == "Be")){
				res = Element.getSym(s.elem) + "O" + " + " + "H2O";
			}
			else{
				res = s.toString();
			}
			break;
		default:
			res = s.toString();
			break;
		}
		return res;
	}

	public void setType(String s) {
		this.subType = s;
	}
	
	public String getType(){
		return this.subType;
	}

	/*
	 * private void setElemQ(int q) { this.elemQuantity = q; }
	 */

	private void setRadQ(int q) {
		this.radQuantity = q;
	}

	// ���������� �������� � �������
	public String extractRadical(String input) {
		int start = input.indexOf('(') + 1;
		int end = input.indexOf(')');
		return input.substring(start, end);
	}

	// ������� ������� � ������� � ��� ������
	public void SetRadWithQuantity(String input) {
		this.radical = extractRadical(input);
		if (Character.isDigit(input.charAt(input.length() - 1)) == true) {
			this.setRadQ(input.charAt(input.length() - 1) - '0');
			Pattern.compile(this.radical + "\\d").matcher(input).replaceAll("");
		} else {
			this.setRadQ(1);
			Pattern.compile(this.radical).matcher(input).replaceAll("");
		}
	}

	public Substance(String input, String subType) {
		this.subType = subType;
		switch (subType) {
		case "simple":
			this.subType = subType;
			if (Character.isDigit(input.charAt(input.length() - 1)) == true) {
				this.elemQuantity = input.charAt(input.length() - 1) - '0';
				Pattern.compile("\\d").matcher(input).replaceAll("");
			} else {
				this.elemQuantity = 1;
				this.radQuantity = 0;
			}
			this.elem = Element.getElemBySym(input);
			this.radical = null;
			break;
		case "water":
			this.elem = Element.getElemBySym("H");
			this.elemQuantity = 2;
			this.radical = Radical.O.toString();
			this.radQuantity = 1;
			break;
		case "NaS":
			break;
		default:
			this.subType = subType;
			SetRadWithQuantity(input);
			if (Character.isLetter(input.charAt(input.length() - 1)) == true) {
				this.elem = Element.getElemBySym(input);
			} else {
				this.elemQuantity = input.charAt(input.length() - 1) - '0';
				Pattern.compile("\\d").matcher(input).replaceAll("");
				this.elem = Element.getElemBySym(input);
				break;
			}
		}
	}
}
