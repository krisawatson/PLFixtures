package com.kricko.domain;

public class Club {

	private int clubId;
	
	private String clubName;
	
    private String clubOverriddenName;
    
    private String clubSortingName;

	public int getClubId() {
		return clubId;
	}

	public void setClubId(int clubId) {
		this.clubId = clubId;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getClubOverriddenName() {
		return clubOverriddenName;
	}

	public void setClubOverriddenName(String clubOverriddenName) {
		this.clubOverriddenName = clubOverriddenName;
	}

	public String getClubSortingName() {
		return clubSortingName;
	}

	public void setClubSortingName(String clubSortingName) {
		this.clubSortingName = clubSortingName;
	}
}
