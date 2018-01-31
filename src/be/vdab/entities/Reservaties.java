package be.vdab.entities;

import java.time.LocalDateTime;

public class Reservaties {
	private long klantId;
	private long filmId;
	private LocalDateTime reservatieDatum;
	public Reservaties(long klantId, long filmId, LocalDateTime reservatieDatum) {
		this.klantId = klantId;
		this.filmId = filmId;
		this.reservatieDatum = reservatieDatum;
	}
	public long getKlantId() {
		return klantId;
	}
	public void setKlantId(long klantId) {
		this.klantId = klantId;
	}
	public long getFilmId() {
		return filmId;
	}
	public void setFilmId(long filmId) {
		this.filmId = filmId;
	}
	public LocalDateTime getReservatieDatum() {
		return reservatieDatum;
	}
	public void setReservatieDatum(LocalDateTime reservatieDatum) {
		this.reservatieDatum = reservatieDatum;
	}
}
