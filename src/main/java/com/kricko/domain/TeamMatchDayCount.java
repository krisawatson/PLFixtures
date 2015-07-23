package com.kricko.domain;

import java.util.HashMap;
import java.util.Map;

public class TeamMatchDayCount implements Comparable<TeamMatchDayCount>{

	private String team;
	
	private Map<String, Integer> matchDayCount;

	public String getTeam() {
		return team;
	}

	public TeamMatchDayCount setTeam(String team) {
		this.team = team;
		return this;
	}

	public Map<String, Integer> getMatchDayCount() {
		return matchDayCount;
	}

	public TeamMatchDayCount incrementMatchDayCount(int md) {
		int count = 1;
		if(matchDayCount == null){
			matchDayCount = new HashMap<String, Integer>();
		} else if(matchDayCount.containsKey("MD" + md)){
			count = matchDayCount.get("MD" + md) + 1;
		}
		matchDayCount.put("MD" + md, count);
		return this;
	}

	@Override
	public int compareTo(TeamMatchDayCount o) {
		if (this == o) return 0;
	    
	    return team.compareTo(o.getTeam());
	}
}
