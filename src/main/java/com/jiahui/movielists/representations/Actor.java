package com.jiahui.movielists.representations;

public class Actor {
	
	private Long id;
	private String name;
	private String dob;
	
	public Actor() {
		
	}
	public Actor(Long id, String name, String dob) {
		this.id = id;
		this.name = name;
		this.dob = dob;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDob () {
		return dob;
	}
	
	public void SetDob (String dob) {
		this.dob = dob;
	}

	@Override
	public int hashCode(){
		int result = 23;
		result += id != null? id.hashCode() : 0;
		result += name != null? name.hashCode() : 0;
		result += dob != null? dob.hashCode() : 0;		
		return result;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null||o.getClass() != getClass()) return false;
		Actor actor = (Actor)o;
		if((id != null? !id.equals(actor.id):actor.id != null)
			||(name != null? !name.equals(actor.name):actor.name != null)
			||(dob != null? !dob.equals(actor.dob):actor.dob != null))			
			return false;
		return true;
	}
}
