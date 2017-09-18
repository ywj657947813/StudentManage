package com.JavaWeb.model;

public class Grade {
     private String gradeName;
     private String specName;
     private String specId;
     private String depaId;
     private String depaName;
     private String grId;
     private String gId;
          
     public Grade(String grId,String specId,String gradeName,String  depaId) {
    	 super();
    	 this.grId = grId;
    	 this.specId = specId;
    	 this.gradeName = gradeName;
    	 this.depaId = depaId;
    	 
     }
     
     public String getgId() {
		return gId;
	}

	public void setgId(String gId) {
		this.gId = gId;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public Grade() {
 		super();
 		// TODO Auto-generated constructor stub
 	}
    

	public String getDepaId() {
		return depaId;
	}
	public void setDepaId(String depaId) {
		this.depaId = depaId;
	}
	public String getDepaName() {
		return depaName;
	}
	public void setDepaName(String depaName) {
		this.depaName = depaName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	
	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getGrId() {
		return grId;
	}

	public void setGrId(String grId) {
		this.grId = grId;
	}

	
}
