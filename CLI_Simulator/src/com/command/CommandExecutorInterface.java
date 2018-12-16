package com.command;

import com.errors.CommandError;
import com.pojo.File;
/*
 * Base interface for commands // Add correspondings methods to add more commands
 */
public interface CommandExecutorInterface {
	String rmCmd(String path,File currentFile,File root) throws CommandError;
	String mkdirCmd(String path,File currentFile,File root) throws CommandError;
	String[] lsCmd(File currentFile);
	String pwdCmd(File currentFile);
	File cdCmd(String path,File currentFile,File root) throws CommandError;
}
