package org.dstadler.intellij.file;

import com.google.common.collect.*;
import org.apache.commons.io.DirectoryWalker;
import org.dstadler.commons.zip.ZipUtils;
import org.dstadler.intellij.xml.ActionIDDef;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;


public class ActionDirectoryWalker extends DirectoryWalker<File> {
	private final SortedSetMultimap<String, ActionIDDef> idsPerFile = TreeMultimap.create(Ordering.natural(), Ordering.natural());

	public SortedSetMultimap<String, ActionIDDef> walk(File startPath) throws IOException {
		// we can also start with a Zip-file...
		if(startPath.isDirectory()) {
			walk(startPath, Collections.emptyList());
		} else {
			handleFile(startPath, 0, Collections.emptyList());
		}

		return idsPerFile;
	}

	@Override
	protected boolean handleDirectory(File directory, int depth, Collection<File> results) {
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
