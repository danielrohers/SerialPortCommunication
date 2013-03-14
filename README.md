# SerialPortCommunication

This project was created for example communication simple serial port.

## Technologies

* [RXTX](http://rxtx.qbang.org/wiki/index.php/Main_Page/)
* [JSSC] (https://code.google.com/p/java-simple-serial-connector/)
* [Netbeans](http://netbeans.org/)

## Configurations

### RXTX

#### Windows

	RXTXcomm.jar goes in \jre\lib\ext (under java)
	rxtxSerial.dll goes in \jre\bin

#### Mac OS X (x86 and ppc)
	
	RXTXcomm.jar goes in  /Library/Java/Extensions
	librxtxSerial.jnilib goes in /Library/Java/Extensions
	Run fixperm.sh thats in the directory.  Fix perms is in the Mac_OS_X
	subdirectory.
	
#### Linux (only x86, x86_64, ia64 here but more in the ToyBox)

	RXTXcomm.jar goes in /jre/lib/ext (under java)
	librxtxSerial.so goes in /jre/lib/[machine type] (i386 for instance)
	Make sure the user is in group lock or uucp so lockfiles work.

#### Solaris (sparc only so far)

	RXTXcomm.jar goes in /jre/lib/ext (under java)
	librxtxSerial.so goes in /jre/lib/[machine type]
	Make sure the user is in group uucp so lockfiles work.

## Author

Daniel Röhers Moura