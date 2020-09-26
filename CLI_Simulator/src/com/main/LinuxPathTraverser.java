package com.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.command.CommandExecutor;
import com.command.CommandExecutorInterface;
import com.errors.CommandError;
import com.pojo.File;



/*
 * Class with main method to start application
 */
public class LinuxPathTraverser {
	
	/*
	 * static field root points to root of directory tree
	 */
	static File root;
	
	
	/*
	 * static field currentFile points to current working directory
	 */
	static File currentFile=root;
	
	
	/*
	 * static block to initialize root and currentFile
	 * root initialized with '/' 
	 * currentPath is poiniting to root initially
	 */
	static {
		root=new File("/",new ArrayList<File>(),"/");
		currentFile=root;
	}
	
	
	
	/*
	 * main method to start execution
	 * take input and pass to executeCommands to execute commands
	 */
	public static void main(String[] args) {
		
		System.out.println("Application Started");
		
		Scanner sc=new Scanner(System.in);
		CommandExecutorInterface cmdexec=new CommandExecutor();
		String command;

		while(!(command=sc.nextLine()).equalsIgnoreCase("exit"))
			{
				executeCommands(command, cmdexec);
			}	
		
	}
	
	
	/*
	 * method execute commands by calling CommandExecutorInterface
	 * method responsible for printing output message and error messages
	 * 
	 * for commands mkdir and rm with multiple args, method will produce messages for each argument seperately
	 */
	static void executeCommands(String cmd,CommandExecutorInterface cmdexec)
	{
		String[] cmdarr=cmd.split(" ");
		String msg;
		String lsmsg[];
		switch (cmdarr[0]) {
		case "rm":  														//case for rm command
				if (cmdarr.length>1)
				{		
					for (int i = 1; i < cmdarr.length; i++) 
					{
						try {
						msg=cmdexec.rmCmd(cmdarr[i], currentFile, root);
						System.out.println(msg);
						}
						catch(CommandError exc)
						{
						System.out.println(exc.getMessage());	
						}
					} 
				}else
					unrecognizedInputError();
				
			break;
			
		case "ls":																	//case for ls command
			if (cmdarr.length==1)
			{
				lsmsg=cmdexec.lsCmd(currentFile);
				System.out.print("DIRS:");
				for (String s:lsmsg)
					System.out.print(" "+s);
				System.out.println();
			}
				else
					unrecognizedInputError();
			break;
			
		case "mkdir":																//case for mkdir command
			if (cmdarr.length>1)
			{
			for (int i = 1; i < cmdarr.length; i++) 
				{
					try {
						msg=cmdexec.mkdirCmd(cmdarr[i], currentFile, root);
						System.out.println(msg);
					}
					catch(CommandError exc)
					{
						System.out.println(exc.getMessage());	
					}
				}
			}
			else
				unrecognizedInputError();
			break;
			
		case "pwd":																	//case for pwd command
			if (cmdarr.length==1)
			{
				msg=cmdexec.pwdCmd(currentFile);
				System.out.println("PATH: "+msg);
			}
			else
				unrecognizedInputError();
			break;
			
		case "cd":																	//case for cd command
			if (cmdarr.length==2)
			{
				try {
				currentFile=cmdexec.cdCmd(cmdarr[1], currentFile, root);
				System.out.println("SUCC: REACHED");
				}catch (CommandError e) {
					System.out.println(e.getMessage());
				}
			}	
			else
				unrecognizedInputError();
			break;
			
		case "session":															//case for session clear command
			if (cmdarr[1].equals("clear") && cmdarr.length==2)
			{
			root=new File("/",new ArrayList<File>(),"/");
			currentFile=root;
			System.out.println("SUCC: CLEARED: RESET TO ROOT");
			}
			else 
				unrecognizedInputError(); 
			break;
			

		default:
			unrecognizedInputError(); 
		}  
				
	}
	
	/*
	 * method will be called when command is unrecognized
	 */
	static void unrecognizedInputError()
	{
		System.out.println("ERR: CANNOT RECOGNIZE INPUT");   
	}
	
}
