package be.vdab.entities;

import java.math.BigDecimal;

public class Films implements Comparable<Films> {
	private long id;
	private long genreId;
	private String titel;
	private long voorraad;
	private long gereserveerd;
	private BigDecimal prijs;
	
	public Films(long id, long genreId, String titel, long voorraad, long gereserveerd, BigDecimal prijs) {
		this.id = id;
		this.genreId = genreId;
		this.titel = titel;
		this.voorraad = voorraad;
		this.gereserveerd = gereserveerd;
		this.prijs = prijs;
	}
	
	public Films(long id, String titel, long voorraad, long gereserveerd, BigDecimal prijs) {
		this.id = id;
		this.titel = titel;
		this.voorraad = voorraad;
		this.gereserveerd = gereserveerd;
		this.prijs = prijs;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGenreId() {
		return genreId;
	}

	public void setGenreId(long genreId) {
		this.genreId = genreId;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public long getVoorraad() {
		return voorraad;
	}

	public void setVoorraad(long voorraad) {
		this.voorraad = voorraad;
	}

	public long getGereserveerd() {
		return gereserveerd;
	}

	public void setGereserveerd(long gereserveerd) {
		this.gereserveerd = gereserveerd;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {	
		if (!(obj instanceof Films))
			return false;
		Films film = (Films) obj;
		
		return id == film.getId();
	}

	@Override
	public int compareTo(Films o) {
		return (int) (this.id - o.id);
	}
	
	
}
