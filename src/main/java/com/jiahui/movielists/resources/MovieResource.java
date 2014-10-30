package com.jiahui.movielists.resources;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
import org.json.*;
import com.freebase.api.Freebase;
import com.freebase.json.*;

@Path("/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MovieResource {
	private String baseUrl = "http://api.rottentomatoes.com/api/public/v1.0";
	private String apiKey = "s6tkcqgsr36hk4vzx7ryw9sk";
	
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public HashMap<String, Integer> getInTheaterMovies() throws Exception {
		getActorInfo("Channing Tatum");
		String query = "/lists/movies/in_theaters.json?page_limit=16&page=1&country=us&apikey=";
		HashMap<String, Integer> movieList = new HashMap<String, Integer>();
		HashMap<String, Integer> actors = new HashMap<String, Integer>();
		URL url = new URL(baseUrl + query + apiKey);
		HttpURLConnection con = (HttpURLConnection)url.openConnection(); 
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Originating-Ip", "72.74.60.188");
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer(); 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject obj = new JSONObject(response.toString());
		String total =(String)obj.get("total");
		JSONArray arr = obj.getJSONArray("movies");
		for(int i=0;i<arr.length();i++) {
			String title = arr.getJSONObject(i).getString("title");
			int totalage = 0;
			JSONArray casts = arr.getJSONObject(i).getJSONArray("abridged_cast");
			for(int j=0;j<casts.length();j++) {
				String name = casts.getJSONObject(i).getString("name");
				if(actors.containsKey(name)) {
					totalage += actors.get(name);
				}
				else {
					String age = getActorInfo(name);
					int iage = Integer.parseInt(age);
					totalage += iage;
					actors.put(name, iage);
				}
			}
			movieList.put(title, totalage/casts.length());
		}
		return movieList;
	}
	
	public String getActorInfo(String name) throws Exception {
		Freebase freebase = Freebase.getFreebase();
		String query_str = "{" +
				  "\"type\":\"/people/person\"," +
				  "\"name\":\"" + name + "\"," +
				  "\"age\":null" +
				"}​";
				JSON query = JSON.parse(query_str);
		JSON result = freebase.mqlread(query);
		return result.get("result").get("age").toString();
	}

}
