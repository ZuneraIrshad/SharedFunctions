import java.io.*;
import groovy.io.*;
import java.util.Calendar.*;
import java.text.SimpleDateFormat
import hudson.model.* 
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
    def date = new Date()
    def sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
    //echo "Date and Time IS: " + sdf.format(date)
    writer.writeLine("Date and Time IS: " + sdf.format(date));
    // currentBuild.getNumber - this gives me last build number
    writer.writeLine("Build Number IS: ${BUILD_NUMBER}");
    
    def changeLogSets = currentBuild.changeSets;
    
    if (config.changes != "false"){
        for (change in changeLogSets) {
            def entries = change.items;
            for (entry in entries) {
                writer.writeLine("${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg}")
                for (file in entry.affectedFiles) {
                    writer.writeLine("${file.editType.name} ${file.path}");
                }
            }
        }   
    }
  }
}
// added changes logic in config changes block
// map is key/value pair 
// moved echo statments to writer.writeline - so only release notes have that data
