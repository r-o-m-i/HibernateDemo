package dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Applicant
{
	@Id
	private long uid;

	private String fname;
	private String lname;
	public Applicant(String fname, String lname, long uid) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.uid = uid;
	}
	
	public Applicant() {

	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Applicant [uid=" + uid + ", fname=" + fname + ", lname=" + lname + "]";
	}
}
