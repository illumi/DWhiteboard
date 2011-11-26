public class User {
	Integer id;
	String name;
	
	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public String getName(){
		return name;
	}
}