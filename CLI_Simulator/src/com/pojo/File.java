package com.pojo;

import java.util.List;

public class File {
private String name; 			//name of File or directory
private List<File> files; 		//files in directory, java.util.Set is not used intentionally 
private String path;			//path to file or directory from root
//private boolean isFile;    	//for future use to determine this is File or Directory


public File(String name, List<File> files, String path) {
	super();
	this.name = name;
	this.files = files;
	this.path = path;
}


public File() {
	super();
}


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public List<File> getFiles() {
	return files;
}
public void setFiles(List<File> files) {
	this.files = files;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}

@Override
	public boolean equals(Object obj) {
		
		return this.name.equals(((File)obj).getName());
	}
}
