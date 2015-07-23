package com.kricko.domain;

public class Fixture {
	
	private int gameWeek;
	
	private int matchDay;

	private String homeTeam;
	
	private String awayTeam;

	public int getGameWeek() {
		return gameWeek;
	}

	public Fixture setGameWeek(int gameWeek) {
		this.gameWeek = gameWeek;
		return this;
	}

	public int getMatchDay() {
		return matchDay;
	}

	public Fixture setMatchDay(int matchDay) {
		this.matchDay = matchDay;
		return this;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public Fixture setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
		return this;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public Fixture setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
		return this;
	}
}
