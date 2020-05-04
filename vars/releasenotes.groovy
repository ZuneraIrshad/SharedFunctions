import java.io.*;
import groovy.io.*;
// place most groovy code in var dir 
// noncps is for testing - i need to rewrite this script without CPS and Closure
@NonCPS
def call(Map config=[:]){
    def dir = new File(pwd());
    
    new File(dir.path + '/releasenotes.txt').withWriter('utf-8')
    {    
        writer -> 
            dir.eachFileRecurse(FileType.ANY){ file ->
                if (file.isDirectory()) {
                    writer.writeLine(file.name);
                }
                else
                {
                    writer.writeLine('/t' + file.name + '/t' + file.length());
                }
        }
    }
}
 
// map is key/value pair 
