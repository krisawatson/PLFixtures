package com.kricko.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

	private String matchState;
	
    private String matchStateKey;
    
    private String detailedState;
    
    private String detailedStateKey;
    
    private int matchId;
    
    private long timestamp;
    
//    private Score score;
    
//    private Venue venue;
    
    private String homeTeamId;
    
    private String awayTeamId;
    
    private String homeTeamName;
    
    private String awayTeamName;
    
//    private MatchAliasData matchCmsAliasData;
    
    private String homeTeamCode;

	private String awayTeamCode;
    
    private Integer minutesIntoMatch;
    
    public String getMatchState() {
		return matchState;
	}

	public void setMatchState(String matchState) {
		this.matchState = matchState;
	}

	public String getMatchStateKey() {
		return matchStateKey;
	}

	public void setMatchStateKey(String matchStateKey) {
		this.matchStateKey = matchStateKey;
	}

	public String getDetailedState() {
		return detailedState;
	}

	public void setDetailedState(String detailedState) {
		this.detailedState = detailedState;
	}

	public String getDetailedStateKey() {
		return detailedStateKey;
	}

	public void setDetailedStateKey(String detailedStateKey) {
		this.detailedStateKey = detailedStateKey;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(String awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public String getHomeTeamCode() {
		return homeTeamCode;
	}

	public void setHomeTeamCode(String homeTeamCode) {
		this.homeTeamCode = homeTeamCode;
	}

	public String getAwayTeamCode() {
		return awayTeamCode;
	}

	public void setAwayTeamCode(String awayTeamCode) {
		this.awayTeamCode = awayTeamCode;
	}

	public Integer getMinutesIntoMatch() {
		return minutesIntoMatch;
	}

	public void setMinutesIntoMatch(Integer minutesIntoMatch) {
		this.minutesIntoMatch = minutesIntoMatch;
	}
}
