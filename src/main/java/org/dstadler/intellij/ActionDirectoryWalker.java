package org.dstadler.intellij;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.io.DirectoryWalker;
import org.dstadler.commons.zip.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;


public class ActionDirectoryWalker extends DirectoryWalker<File> {
	private Multimap<String, ActionIDDef> idsPerFile = HashMultimap.create();

	public Multimap<String, ActionIDDef> walk(File startPath) throws IOException {
		// we can also start with a Zip-file...
		if(startPath.isDirectory()) {
			walk(startPath, Collections.emptyList());
		} else {
			handleFile(startPath, 0, Collections.emptyList());
		}

		return idsPerFile;
	}

	@Override
	protected boolean handleDirectory(File directory, int depth, Collection<File> results) throws IOException {
		return true;
	}

	@Override
	protected void handleFile(File file, int depth, Collection<File> results) throws IOException {
		if(ZipUtils.isZip(file.getName())) {
			new ZipFileWalker(file).walk(idsPerFile);
		}

		if(ActionIDDef.isActionFile(file)) {
			idsPerFile.putAll(file.getPath(), ActionIDDef.parse(file));
		}
	}
}
