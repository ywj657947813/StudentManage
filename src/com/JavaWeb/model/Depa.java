package com.JavaWeb.model;

public class Depa {
	 private String dId;
     private String deId;
     private String depaName;   
     
     public String getdId() {
    	 return dId;
     }
     
     public void setdId(String dId) {
    	 this.dId = dId;
     }
     public Depa(String depaName,String deId) {
		super();
		this.depaName = depaName;
		this.deId = deId;
	}

	public Depa() {
 		super();
 		// TODO Auto-generated constructor stub
 	}

	

	public String getDeId() {
		return deId;
	}

	public void setDeId(String deId) {
		this.deId = deId;
	}

	public String getDepaName() {
		return depaName;
	}

	public void setDepaName(String depaName) {
		this.depaName = depaName;
	}


	
    
 	
}
