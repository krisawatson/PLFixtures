package com.kricko;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.kricko.domain.Fixture;
import com.kricko.domain.Match;
import com.kricko.domain.PLSite;
import com.kricko.domain.TeamMatchDayCount;
import com.kricko.utils.FixtureBuilder;

public class PLFixtures {
	
	private static final String PL_FIXTURE_JSON_URL = "http://www.premierleague.com/ajax/site-header/ajax-all-fixtures.json";
	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");

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
			

			outputMatchesByTimestamp(fixtures);
			
			List<TeamMatchDayCount> mdcList = FixtureBuilder.buildGameWeekCounts(fixtures);
			
			Collections.sort(mdcList);
			
			outputMatchesByMatchday(mdcList);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void outputMatchesByMatchday(List<TeamMatchDayCount> mdcList) {
		List<String> mds = FixtureBuilder.getUniqueTeamMDs(mdcList);
		
		List<String> lines = new ArrayList<>();
		String header = "Team";
		for(String md:mds){
			header += "," + md;
		}
		lines.add(header);
		for(TeamMatchDayCount mdc:mdcList) {
			StringBuilder sb = new StringBuilder();
			sb.append(mdc.getTeam());
			for(String md:mds){
				int count = 0;
				try { 
					count = mdc.getMatchDayCount().get(md);
				} catch (NullPointerException e) {
					// Do nothing count will be 0
				}
				sb.append("," + count);
			}
			lines.add(sb.toString());
		}
		
		try {
			Charset charset = Charset.forName("UTF-8");
            Files.write(Paths.get("./matchday.csv"), lines, charset, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e);
        }
	}
	
	private static void outputMatchesByTimestamp(List<Fixture> fixtures) {
		List<String> lines = new ArrayList<>();
		String header = "Time, Home, Away, Match Day, Game Week";
		lines.add(header);
		for(Fixture fixture:fixtures) {
			StringBuilder sb = new StringBuilder();
			sb.append(dtf.print(new DateTime(fixture.getTimestamp())));
			sb.append(", "+fixture.getHomeTeam());
			sb.append(", "+fixture.getAwayTeam());
			sb.append(", "+fixture.getMatchDay());
			sb.append(", "+fixture.getGameWeek());
			lines.add(sb.toString());
		}
		
		try {
			Charset charset = Charset.forName("UTF-8");
            Files.write(Paths.get("./fixtures.csv"), lines, charset, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e);
        }
	}
}
