package com.jiahui.movielists.resources;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;
//import org.json.*;


import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.jayway.jsonpath.JsonPath;
import com.jiahui.movielists.representations.*;

import java.io.FileInputStream;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MovieResource {
	private String baseUrl = "http://api.rottentomatoes.com/api/public/v1.0";
	private String apiKey = "s6tkcqgsr36hk4vzx7ryw9sk";
	private String GoogleApiKey = "AIzaSyDloyilmEQmQqDj7QC3H31BkNVsdv9Uk9A";
	
	@GET
	@Path("inTheater/{pageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public IntheaterMovieList getInTheaterMovies(@PathParam("pageId") long pageId) throws Exception {
		String query = "/lists/movies/in_theaters.json?page_limit=16&page=" + pageId + "&country=us&apikey=";
		IntheaterMovieList inTheater = new IntheaterMovieList();
		List<Movie> movielist = new ArrayList<Movie>();
		//HashMap<String, Integer> movieList = new HashMap<String, Integer>();
		HashMap<Long, String> actordobs = new HashMap<Long, String>();
	    JSONParser parser = new JSONParser();
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
		JSONObject obj = (JSONObject)parser.parse(response.toString());
		String total =JsonPath.read(obj,"$.total").toString();
		inTheater.setTotal(Integer.parseInt(total));
		JSONArray arr = JsonPath.read(obj,"$.movies");
		for(int i=0;i<arr.size();i++) {
			Movie movie = new Movie();
			movie.setTitle(JsonPath.read(arr.get(i),"$.title").toString());
			movie.setId(Long.parseLong(JsonPath.read(arr.get(i),"$.id").toString()));
			List<Actor> castlist = new ArrayList<Actor>();
			JSONArray casts = JsonPath.read(arr.get(i),"$.abridged_cast");
			for(int j=0;j<casts.size();j++) {
				Actor actor = new Actor();
				Long id = Long.parseLong(JsonPath.read(casts.get(j),"$.id").toString());
				actor.setId(id);
				String name = JsonPath.read(casts.get(j),"$.name").toString();
				actor.setName(name);
				if(actordobs.containsKey(id)) {
					actor.SetDob(actordobs.get(id));
				}
				else {
					String actorDob = getActorInfo(name);
					actor.SetDob(actorDob);
					actordobs.put(id, actorDob);
				}
				castlist.add(actor);
			}
			movie.setCasts(castlist);
			if(casts.size()>0)
				movielist.add(movie);
		}
		inTheater.setMovies(movielist);
		return inTheater;
	}
	/*
	public String getActorInfo(String name) throws Exception {
		Freebase freebase = Freebase.getFreebase();
		String query_str = "{" +
				"\"/people/person/date_of_birth\": \"null\"," +
				  "\"name\":\"" + name + "\"" +
				"}​";
				JSON query = JSON.parse(query_str);
		JSON result = freebase.mqlread(query);
		return result.get("result").get("age").toString();
	}
*/
	public String getActorInfo(String name) throws Exception {
		 HttpTransport httpTransport = new NetHttpTransport();
	      HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
	      JSONParser parser = new JSONParser();
	      String query = "[{\"/people/person/date_of_birth\": null," +
				  "\"name\":\"" + name + "\"}]";
	      GenericUrl url = new GenericUrl("https://www.googleapis.com/freebase/v1/mqlread");
	      url.put("query", query);
	      url.put("key", GoogleApiKey);
	      HttpRequest request = requestFactory.buildGetRequest(url);
	      //HttpResponse httpResponse = request.execute();
	      String responseS = request.execute().parseAsString();
	      JSONObject response = (JSONObject)parser.parse(responseS);
	      JSONArray results = (JSONArray)response.get("result");
	      for(Object result : results){
	    	  if(JsonPath.read(result,"$./people/person/date_of_birth")!=null)
	    		  return JsonPath.read(result,"$./people/person/date_of_birth").toString();
	      }
	      return "";
	}
	
	public int getActorAge(String actorDob) throws Exception{
		if(actorDob == "") return 30;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = sdf.parse(actorDob);
		Date now = new Date();
		return now.getYear() - dob.getYear();
	}
}
