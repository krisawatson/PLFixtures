package com.kricko.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.kricko.domain.Fixture;
import com.kricko.domain.TeamMatchDayCount;

public class FileUtils {
	
	private static final DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");
	
	public static void outputMatchesByMatchday(List<TeamMatchDayCount> mdcList) {
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
	
	public static void outputMatchesByTimestamp(List<Fixture> fixtures) {
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
