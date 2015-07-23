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

import com.kricko.domain.Fixture;
import com.kricko.domain.Match;
import com.kricko.domain.PLSite;
import com.kricko.domain.TeamMatchDayCount;
import com.kricko.utils.FixtureBuilder;

public class PLFixtures {

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
			PLSite site = mapper.readValue(new URL("http://www.premierleague.com/ajax/site-header/ajax-all-fixtures.json"), PLSite.class);
			
			List<Match> matches = site.getSiteHeaderSection().getMatches();
			
			List<Fixture> fixtures = 
					FixtureBuilder.convertMatchesToMatchDay(matches, startDate, endDate);
			
			List<TeamMatchDayCount> mdcList = FixtureBuilder.buildGameWeekCounts(fixtures);
			
			Collections.sort(mdcList);
			
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
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
