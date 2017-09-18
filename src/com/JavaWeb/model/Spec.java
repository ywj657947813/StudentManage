package com.JavaWeb.model;

public class Spec {
	 private String sId;
  	 private String spId;
     private String specName;
     private String depaId;
     private String depaName;
     
    

     public String getsId() {
    	 return sId;
     }
     
     public void setsId(String sId) {
    	 this.sId = sId;
     }
	public String getDepaName() {
		return depaName;
	}

	public void setDepaName(String depaName) {
		this.depaName = depaName;
	}

	public Spec(String spId, String specName, String depaId) {
		super();
		this.spId = spId;
		this.specName = specName;
		this.depaId = depaId;
	}

	public String getSpecName() {
 		return specName;
 	}

 	public void setSpecName(String specName) {
 		this.specName = specName;
 	}
     public Spec() {
 		super();
 		// TODO Auto-generated constructor stub
 	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getDepaId() {
		return depaId;
	}

	public void setDepaId(String depaId) {
		this.depaId = depaId;
	}
    
 	
}
