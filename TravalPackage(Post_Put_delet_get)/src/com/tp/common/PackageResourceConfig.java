package com.tp.common;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.tp.resources.Tervals;

@ApplicationPath("/api")
public class PackageResourceConfig extends Application{
	public Set<Object> singleTone;
	
	

	@Override
	public Set<Object> getSingletons() {
		 singleTone = new HashSet<>();
		Tervals tervals = new Tervals();
		singleTone.add(tervals);
		
		return singleTone;
	}

}
