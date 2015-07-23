package com.kricko.filter;

import java.util.ArrayList;
import java.util.List;

import com.kricko.domain.Fixture;

public class MatchdayCriteria implements Criteria {

	private int matchday;
	
	public MatchdayCriteria(int matchday) {
		this.matchday = matchday;
	}
	
	@Override
	public List<Fixture> meetCriteria(List<Fixture> fixtures) {
		List<Fixture> matchdayFixtures = new ArrayList<Fixture>(); 
	      
	      for (Fixture fixture : fixtures) {
	         if(fixture.getMatchDay() == matchday){
	        	 matchdayFixtures.add(fixture);
	         }
	      }
	      return matchdayFixtures;
	}

}
