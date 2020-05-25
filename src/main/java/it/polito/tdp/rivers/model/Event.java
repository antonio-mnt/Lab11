package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		F_IN,
		F_OUT,
		TRACIMAZIONE
	}
	
	private LocalDate time;
	private EventType type;
	private double flusso;
	private double p;
	
	public Event(LocalDate time, EventType type, double flusso) {
		super();
		this.time = time;
		this.type = type;
		this.flusso = flusso;
		this.p = Math.random();
	}

	public LocalDate getTime() {
		return time;
	}

	public EventType getType() {
		return type;
	}

	public double getFlusso() {
		return flusso;
	}
	
	public double getP() {
		return p;
	}

	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + ", flusso=" + flusso + ", p=" + p + "]";
	}
	

	

	
	
	
	
	

}
