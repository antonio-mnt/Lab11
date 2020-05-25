package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {
	
	
	// coda degli eventi
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//parametri di simulazione
	private double K = 1;
	private double fmedia;
	private double foutmin = 0;
	private double Q = 0;
	private double Ciniziale = 0;
	private List<Flow> flows;
	
	//modello del mondo
	private double C = 0;
	
	//valori da calcolare
	private int nienteErogazione = 0;
	private double Cmed = 0;
	private int numGiorni = 0;
	
	
	// metodi per restituire i risultati
	public int getNienteErogazione() {
		return nienteErogazione;
	}

	public double getCmed() {
		return Cmed;
	}

	public int getNumGiorni() {
		return numGiorni;
	}
	
	//simulazione
	public void run(double k, double fm, List<Flow> flows) {
		//prparazione iniziale mondo più coda eventi
		this.K = k;
		this.fmedia = fm*60*60*24;
		this.foutmin = this.fmedia*0.8;
		this.flows = flows;
		
		this.Q = this.K*this.fmedia*30;
		this.Ciniziale = this.Q/2;
		
		this.nienteErogazione = 0;
		this.Cmed = 0;
		this.numGiorni = 0;
		
		this.C = this.Ciniziale;
		
		this.queue.clear();
		for(Flow f: this.flows) {
			double fin = f.getFlow()*60*60*24;
			Event e = new Event(f.getDay(),EventType.F_IN,fin);
			queue.add(e);
		}
		
		// esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()) {
		Event e = this.queue.poll();
		System.out.println(e+" "+this.C+" "+this.Ciniziale+" "+this.Q);
		processEvent(e);
		}
		
		
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		
		case F_IN:
			
			if(this.C + e.getFlusso() > Q) {
				Event eve  = new Event(e.getTime(),EventType.TRACIMAZIONE,0);
				queue.add(eve);
				
			}else {
				this.C += e.getFlusso();
			}
			
			double fout = foutmin;
			
			if(e.getP()<=0.05) {
				fout = 10*foutmin;
			}
			
			Event ev = new Event (e.getTime(),EventType.F_OUT,fout);
			queue.add(ev);
			
			break;
			
			
			
		case F_OUT:
			
			
			if(this.C < this.foutmin) {
				this.nienteErogazione++;
			}else {
				if((this.C - e.getFlusso()) < 0) {
					this.C = 0;
				}else {
					this.C -= e.getFlusso();
				}

			}
			
			this.numGiorni++;
			this.Cmed+=C;
			
			
			
			break;
			
			
		case TRACIMAZIONE:
			
			this.C = Q;
			
			break;
			
			
		
		
		
		}
		
		
		
	}

	
	
	

}
