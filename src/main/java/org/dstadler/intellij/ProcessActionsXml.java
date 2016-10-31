package org.dstadler.intellij;

import com.google.common.collect.Multimap;
import org.dstadler.intellij.file.ActionDirectoryWalker;
import org.dstadler.intellij.xml.ActionIDDef;

import java.io.File;
import java.io.IOException;

/**
 * Small helper app to extract all the available ActionIDs of an IntelliJ installation
 * to provide a nicely readable list
 */
public class ProcessActionsXml {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.err.println("Usage: ProcessActionsXml <IntelliJDir1> [<IntelliJDir2] ...");
            return;
        }

        for(String dir : args) {
            ActionDirectoryWalker walker = new ActionDirectoryWalker();
            final Multimap<String, ActionIDDef> ids = walker.walk(new File(dir));
            for(String file : ids.keySet()) {
                for (ActionIDDef def : ids.get(file)) {
                    System.out.println("File: " + file + ": had " + def.getId() + "/" + def.getText() + "/" + def.getGroup());
                }
            }

            System.out.println("Found " + ids.size() + " actions in " + ids.keySet().size() + " files");
        }
    }
}
