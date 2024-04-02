package ui;

public class Student {

	private  String stdid;
	private  String Fname;
	private  String lname;
	private int age;
	private  String gender;

	public Student(String stdid,String fname,String lname,String gender,int age) {
		this.stdid = stdid;
		this.Fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.age = age;
	}





	public String getStdid() {
		return stdid;
	}
	public void setStdid(String stdid) {
		this.stdid = stdid;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		this.Fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	}

	
	

