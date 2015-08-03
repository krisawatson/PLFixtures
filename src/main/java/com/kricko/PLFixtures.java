package com.kricko;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

import com.kricko.domain.Fixture;
import com.kricko.domain.Match;
import com.kricko.domain.PLSite;
import com.kricko.domain.TeamMatchDayCount;
import com.kricko.utils.FileUtils;
import com.kricko.utils.FixtureBuilder;

public class PLFixtures {
	
	private static final String PL_FIXTURE_JSON_URL = "http://www.premierleague.com/ajax/site-header/ajax-all-fixtures.json";
	
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("Requires start and end dates as arguments");
			System.out.println("i.e. java -jar PLFixtures.jar 2015-08-01 2015-10-31");
		} else {
			DateTime startDate = new DateTime(args[0]).withTimeAtStartOfDay();
			DateTime endDate = new DateTime(args[1]).plusDays(1).withTimeAtStartOfDay();
			
			buildTeamMatchdays(startDate, endDate);
		}
	}
	
	private static void buildTeamMatchdays(DateTime startDate, DateTime endDate) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			PLSite site = mapper.readValue(new URL(PL_FIXTURE_JSON_URL), PLSite.class);
			
			List<Match> matches = site.getSiteHeaderSection().getMatches();
			
			List<Fixture> fixtures = 
					FixtureBuilder.convertMatchesToMatchDay(matches, startDate, endDate);
			

			FileUtils.outputMatchesByTimestamp(fixtures);
			
			List<TeamMatchDayCount> mdcList = FixtureBuilder.buildGameWeekCounts(fixtures);
			
			Collections.sort(mdcList);
			
			FileUtils.outputMatchesByMatchday(mdcList);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
