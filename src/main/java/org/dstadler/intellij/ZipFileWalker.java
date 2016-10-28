package org.dstadler.intellij;

import com.google.common.collect.Multimap;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.dstadler.commons.zip.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * File Walker for Zip-Files which can step down into nested zip-files while
 * looking for matches.
 *
 * @author dominik.stadler
 */
public class ZipFileWalker {
	private final File zip;

	public ZipFileWalker(File file) {
		this.zip = file;
	}

	public void walk(Multimap<String, ActionIDDef> idsPerFile) throws IOException {
		try (final ZipFile zipFile = new ZipFile(zip)) {
			// walk all entries and look for matches
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();

				File file = new File(zip, entry.getName());
				if(ActionIDDef.isActionFile(file)) {
					idsPerFile.putAll(file.getPath(), ActionIDDef.parse(zipFile.getInputStream(entry)));
				}

				// look for name or content inside zip-files as well, do it recursively to also
				// look at content of nested zip-files
				if(ZipUtils.isZip(entry.getName())) {
					walkRecursive(file, zipFile.getInputStream(entry), idsPerFile);
				}
			}
		}
	}

	@SuppressWarnings("resource")
	private void walkRecursive(File base, InputStream stream, Multimap<String, ActionIDDef> idsPerFile) throws IOException {
		ZipInputStream zipStream = new ZipInputStream(stream);
		while(true) {
			final ZipEntry entry;
			try {
				entry = zipStream.getNextEntry();
			} catch (IOException e) {
				throw new IOException("While handling file: " + base, e);
			}

			if(entry == null) {
				break;
			}

			File file = new File(base, entry.getName());
			if(ActionIDDef.isActionFile(file)) {
				idsPerFile.putAll(file.getPath(), ActionIDDef.parse(new CloseShieldInputStream(zipStream)));
			}

			if(ZipUtils.isZip(entry.getName())) {
				walkRecursive(file, zipStream, idsPerFile);
			}
			zipStream.closeEntry();
		}
	}
}
