package com.kricko.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.kricko.domain.Fixture;
import com.kricko.domain.Match;
import com.kricko.domain.TeamMatchDayCount;
import com.kricko.filter.GameweekCriteria;
import com.kricko.filter.MatchdayCriteria;

public class FixtureBuilder {
	
	public static List<Fixture> convertMatchesToMatchDay(List<Match> matches, 
			DateTime startDate, DateTime endDate){
		List<Fixture> fixtures = new ArrayList<Fixture>();
		
		for(Match match:matches){
			Fixture fixture = new Fixture();
			DateTime matchTime = new DateTime(match.getTimestamp());
			if(!matchTime.isBefore(startDate) && !matchTime.isAfter(endDate)){
				
				int gw = matchTime.getWeekOfWeekyear();
				int md = matchTime.getDayOfWeek();
				
				// If the matchday is before Saturday subtract 1 from gw
				// this will allow for midweek games
				if(md < 5){ 
					gw--;
					md = md + 7; // Add 7 so that the md is ordered correctly
				}
				
				md = md - 5;
				
				if(gw < 32) {
					gw = gw + 52;
				}
				
				gw = gw - 31;
				
//				System.out.println("GW: " + gw + " MD: " + md + " Fixture: " + match.getHomeTeamName() + " vs " + match.getAwayTeamName());
				fixtures.add(
					fixture.setGameWeek(gw)
						.setMatchDay(md)
						.setHomeTeam(match.getHomeTeamName())
						.setAwayTeam(match.getAwayTeamName()));
			}
		}
		
		return fixtures;
	}
	
	public static List<TeamMatchDayCount> buildGameWeekCounts(List<Fixture> fixtures) {
		
		List<TeamMatchDayCount> mdcList = new ArrayList<TeamMatchDayCount>();
		
		List<Integer> gameweeks = getUniqueGWs(fixtures);
		
		for(int gameweek:gameweeks){
			GameweekCriteria gw = new GameweekCriteria(gameweek);
			List<Fixture> gwFix = gw.meetCriteria(fixtures);
			List<Integer> matchdays = getUniqueMDs(gwFix);
			
			int offset = 1 - matchdays.get(0);
			for(int matchday:matchdays){
				MatchdayCriteria md = new MatchdayCriteria(matchday);
				List<Fixture> mdFix = md.meetCriteria(gwFix);
				
				matchday = matchday + offset;
				
				for(Fixture fixture:mdFix){
					TeamMatchDayCount mdcHome = getTeam(fixture.getHomeTeam(), mdcList);
					TeamMatchDayCount mdcAway = getTeam(fixture.getAwayTeam(), mdcList);
					
					if(mdcHome != null){
						mdcList.remove(mdcHome);
						mdcHome.incrementMatchDayCount(matchday);
						mdcList.add(mdcHome);
					} else {
						mdcHome = new TeamMatchDayCount();
						mdcHome.setTeam(fixture.getHomeTeam())
							.incrementMatchDayCount(matchday);
						mdcList.add(mdcHome);
					}
					
					if(mdcAway != null){
						mdcList.remove(mdcAway);
						mdcAway.incrementMatchDayCount(matchday);
						mdcList.add(mdcAway);
					} else {
						mdcAway = new TeamMatchDayCount();
						mdcAway.setTeam(fixture.getAwayTeam())
							.incrementMatchDayCount(matchday);
						mdcList.add(mdcAway);
					}
				}
			}
		}
		
		return mdcList;
	}
	
	private static List<Integer> getUniqueGWs(List<Fixture> fixtures) {
		List<Integer> uniqueGWs = new ArrayList<Integer>();
		
		for(Fixture fixture:fixtures){
			int gw = fixture.getGameWeek();
			if(!uniqueGWs.contains(gw)){
				uniqueGWs.add(gw);
			}
		}
		
		Collections.sort(uniqueGWs);
		
		return uniqueGWs;
	}
	
	private static List<Integer> getUniqueMDs(List<Fixture> fixtures) {
		List<Integer> uniqueMDs = new ArrayList<Integer>();
		
		for(Fixture fixture:fixtures){
			int md = fixture.getMatchDay();
			if(!uniqueMDs.contains(md)){
				uniqueMDs.add(md);
			}
		}
		
		Collections.sort(uniqueMDs);
		
		return uniqueMDs;
	}
	
	public static List<String> getUniqueTeamMDs(List<TeamMatchDayCount> mdcLists) {
		List<String> uniqueMDs = new ArrayList<String>();
		
		for(TeamMatchDayCount mdc:mdcLists){
			
			Map<String, Integer> md = mdc.getMatchDayCount();
			Set<String> mdSet = md.keySet();
			for(String mdName:mdSet){
				if(!uniqueMDs.contains(mdName)){
					uniqueMDs.add(mdName);
				}
			}
		}
		
		Collections.sort(uniqueMDs);
		
		return uniqueMDs;
	}
	
	private static TeamMatchDayCount getTeam(String teamName, List<TeamMatchDayCount> mdcList) {
		for(TeamMatchDayCount mdc:mdcList){
			if(teamName.equals(mdc.getTeam())){
				return mdc;
			}
		}
		
		return null;
	}
}
