# Virtual-file-system
This is a Java implementation of a Unix-based virtual file system, which allows the user to manage virtual files, directories and users.
The following commands are implemented:
* adduser <user_name>;
* chuser <user_name> (changes user);
* deluser <user_name> (deletes user);
* cd <path_to_directory> (changes directory);
* ls <path_to_directory_or_file> (lists directory or information about a file);
* mkdir <path_to_directory> (creates directory);
* chmod <permission> <path_to_directory_or_file> (permissions are specified for the owner and other users, but not for groups);
* touch <path_to_file> (creates file);
* rm <path_to_file> (deletes file);
* rm -r <path_to_directory_or_file> (deletes directory or file);
* rmdir <path_to_directory> (deletes empty directory);
* writetofile <path_to_file> <content>;
* cat <path_to_file> (displays the content of a file);
  
Usage example: 
* compile: javac Test.java
* run: java Test or java Test <input_file>
