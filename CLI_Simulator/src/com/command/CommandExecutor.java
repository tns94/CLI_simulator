package com.command;

import java.util.ArrayList;
import java.util.List;

import com.errors.CommandError;
import com.pojo.File;

public class CommandExecutor implements CommandExecutorInterface{

	/*
	 * check if path is valid path of only a directory name
	 */
	static boolean isPath(String path)
	{
		return path.indexOf("/")!=-1?true:false;
	}
	
	/*
	 * check for path is absolute or not
	 */
	static boolean isAbsolutePath(String path)
	{
		return path.charAt(0)=='/'?true:false;
	}
	
	/*
	 *give token directory name from path 
	 */
	static String[] getTokenNamesFromPath(String path)
	{
		return path.split("/");
	}
	
	/*
	 * returns File Object to which operation has to be performed
	 * like path is dir1/dir2/dir3 then it will return reference to dir2
	 */
	static File getReferenceToWorkingDirectory(String path,File currentFile,File root) throws CommandError
	{
		if(!isPath(path))
			return currentFile;
		
		File tempfile1=new File();
		int index;	
		boolean absolutePath=isAbsolutePath(path);
		String[] args;
		File tempfile;
		if (absolutePath)
		{
			tempfile=root;
			args=getTokenNamesFromPath(path.substring(1));
		}
		else
		{
			tempfile=currentFile;
			args=getTokenNamesFromPath(path);
		}
		
		for (int i = 0; i < args.length-1; i++) 
		{
			tempfile1.setName(args[i]);
			index=tempfile.getFiles().indexOf(tempfile1);
			if (index!=-1)
				tempfile=tempfile.getFiles().get(index);
			else
				{
				throw new CommandError("ERR: INVALID PATH");  // If File Name not exist in Tree
				}
			
		}
		return tempfile;
	}
	
	/*
	 * rmCmd() method take path to remove 
	 * throw error if any path is invalid
	 * 
	 * */
	public String rmCmd(String path,File currentFile,File root) throws CommandError
	{
				
		String directoryName=isPath(path)?path.substring(path.lastIndexOf("/")+1):path;
			
		if (directoryName.isEmpty())
			throw new CommandError("ERR: INVALID PATH "+path);  // File Name cannot empty
		
			File tempfile=new File();
			File workingDirectory=getReferenceToWorkingDirectory(path, currentFile, root);
			tempfile.setName(directoryName);
			
			if (workingDirectory.getFiles().contains(tempfile))
				workingDirectory.getFiles().remove(tempfile);
			else
				throw new CommandError("ERR: INVALID PATH "+path);  // If File to remove is not present
			
		
		return "SUCC: DELETED";
	}
	
	
	/*
	 * mkdirCmd method take path to make directories
	 * throw error if path is invalid
	 */
	public String mkdirCmd(String path,File currentFile,File root) throws CommandError
	{
		
		String directoryName=isPath(path)?path.substring(path.lastIndexOf("/")+1):path;
		
		if (directoryName.isEmpty())
			throw new CommandError("ERR: INVALID PATH "+path);  // File Name cannot empty
		

		File workingDirectory=getReferenceToWorkingDirectory(path, currentFile, root);
		File tempfile=new File();
		tempfile.setName(directoryName);
		tempfile.setFiles(new ArrayList<File>());
		if (workingDirectory.getPath().equals("/"))
			tempfile.setPath("/"+directoryName);
		else
			tempfile.setPath(workingDirectory.getPath()+"/"+directoryName);
		
		if (workingDirectory.getFiles().contains(tempfile))
			throw new CommandError("ERR: DIRECTORY ALREADY EXIST "+directoryName);  // If File Already Exist at Path
		else
			workingDirectory.getFiles().add(tempfile);
		
		return "SUCC: CREATED "+directoryName; 
	}
	
	/*
	 * lsCmd method returns directory Names which are in current directory
	 */
	public String[] lsCmd(File currentFile)
	{
		List<File> fileList=currentFile.getFiles();
		int size=fileList.size();
		String directoryNames[]=new String[size];
		
		for (int i = 0; i < size; i++) {
			directoryNames[i]=fileList.get(i).getName();
		}
		return directoryNames;
	}
	
	public String pwdCmd(File currentFile)
	{
		return currentFile.getPath();
	}
	
	/*
	 * cdCmd take path to change current working pointer to that directory
	 * 
	 */
	public File cdCmd(String path,File currentFile,File root) throws CommandError
	{
		
		if (path.equals("/"))
			return root;
		
		File workingDirectory=getReferenceToWorkingDirectory(path, currentFile, root);
		int index;
		String directoryName=isPath(path)?path.substring(path.lastIndexOf("/")+1):path;
		
		if (directoryName.isEmpty())
			throw new CommandError("ERR: INVALID PATH");  //  File Name cannot empty
		
		File tempfile=new File();
		tempfile.setName(directoryName);
		index=workingDirectory.getFiles().indexOf(tempfile);
		if (index!=-1)
			return workingDirectory.getFiles().get(index);
		else
			{
			throw new CommandError("ERR: INVALID PATH");  // If File Name not exist in Tree
			}
		
		
	}
	
	
}
