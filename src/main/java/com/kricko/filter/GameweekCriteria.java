package com.kricko.filter;

import java.util.ArrayList;
import java.util.List;

import com.kricko.domain.Fixture;

public class GameweekCriteria implements Criteria {

	private int gameweek;
	
	public GameweekCriteria(int gameweek) {
		this.gameweek = gameweek;
	}
	
	@Override
	public List<Fixture> meetCriteria(List<Fixture> fixtures) {
		List<Fixture> gameweekFixtures = new ArrayList<Fixture>(); 
	      
	      for (Fixture fixture : fixtures) {
	         if(fixture.getGameWeek() == gameweek){
	        	 gameweekFixtures.add(fixture);
	         }
	      }
	      return gameweekFixtures;
	}

}
