package it.polito.tdp.borders.model;

public class Country {
	
	private int cCode;
	private String abbr;
	private String name;
	
	public Country(int cCode, String abbr, String name) {
		super();
		this.cCode = cCode;
		this.abbr = abbr;
		this.name = name;
	}

	public int getcCode() {
		return cCode;
	}

	public void setcCode(int cCode) {
		this.cCode = cCode;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name + " (" + this.abbr + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbr == null) ? 0 : abbr.hashCode());
		result = prime * result + cCode;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (abbr == null) {
			if (other.abbr != null)
				return false;
		} else if (!abbr.equals(other.abbr))
			return false;
		if (cCode != other.cCode)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
