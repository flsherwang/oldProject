package com.example.constant;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusProvider{

	public static Bus bus;

	public static Bus getInstance() {
		if (bus == null) {
			bus = new Bus(ThreadEnforcer.ANY);
		}
		return bus;
	}

	private BusProvider() {
	}
}
