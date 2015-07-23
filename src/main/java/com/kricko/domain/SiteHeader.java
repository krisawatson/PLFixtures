package com.kricko.domain;

import java.util.List;

public class SiteHeader {

	private CurrentSeason currentSeason;
	
	private List<Match> matches;
	
	private List<Club> clubList;

	public CurrentSeason getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(CurrentSeason currentSeason) {
		this.currentSeason = currentSeason;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	
	public List<Club> getClubList() {
		return clubList;
	}

	public void setClubList(List<Club> clubList) {
		this.clubList = clubList;
	}
}
