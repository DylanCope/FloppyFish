package com.cope.flight.tween;

import com.cope.flight.data.Event;

public class TimedEvent {
	
	private long startTime;
	private Event event;
	private float delay, delta;
	
	public TimedEvent(float delay, Event event){
		this.startTime = System.currentTimeMillis();
		this.event = event;
		this.delay = delay;
	}
	
	public void update(){
		
		delta = System.currentTimeMillis() - startTime;
		
		if (delta >= delay){
			event.onEvent();
		}
		
	}
	
	public boolean isFinished(){
		if (delta < delay){
			return false;
		}else{
			return true;
		}
	}
	
}
