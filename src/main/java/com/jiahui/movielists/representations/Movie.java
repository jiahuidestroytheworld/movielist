package com.jiahui.movielists.representations;

import java.util.List;

public class Movie {

	private Long id;
	private String title;
	private List<Actor> casts;
	
	public Movie () {
		
	}
	public Movie (Long id, String title, List<Actor> casts) {
		this.id = id;
		this.title = title;
		this.casts = casts;
	}
	
	public Long getId () {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Actor> getCasts(){
		return casts;
	}
	
	public void setCasts(List<Actor> casts) {
		this.casts = casts;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null||o.getClass() != getClass()) return false;
		Movie movie = (Movie)o;
		if((id != null? !id.equals(movie.id):movie.id != null)
				||(title != null? !title.equals(movie.title):movie.title != null)
				||(casts != null? !casts.equals(movie.casts):movie.casts != null))			
				return false;
			return true;
	}
	
	@Override
	public int hashCode(){
		int result = 23;
		result += id != null? id.hashCode():0;
		result += title != null? title.hashCode():0;
		result += casts != null? casts.hashCode():0;
		return result;
	}
}

