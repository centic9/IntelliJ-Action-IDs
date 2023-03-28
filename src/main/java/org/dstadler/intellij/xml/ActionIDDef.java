package org.dstadler.intellij.xml;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about a single ActionID in IntelliJ
 */
public class ActionIDDef implements Comparable<ActionIDDef> {
    /**
     * The files are spread across multiple directories in the Github repo
     */
    private static final Map<String, String> XML_TO_GITHUB = new HashMap<>();
    static {
        XML_TO_GITHUB.put("JavaActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/resources/src/idea");
        XML_TO_GITHUB.put("RichPlatformActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/resources/src/idea");

        XML_TO_GITHUB.put("XmlActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/platform/platform-resources/src/META-INF");

        XML_TO_GITHUB.put("ExternalSystemActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/platform/platform-resources/src/idea");
        XML_TO_GITHUB.put("LangActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/platform/platform-resources/src/idea");
        XML_TO_GITHUB.put("PlatformActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/platform/platform-resources/src/idea");
        XML_TO_GITHUB.put("VcsActions.xml", "https://github.com/JetBrains/intellij-community/tree/master/platform/platform-resources/src/idea");
    }

    private String id;
    private String text;
    private String group;
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public static boolean isActionFile(File file) {
        return file.getName().endsWith("Actions.xml");
    }

    /**
     * Parse an XML-file and read all the ActionIDs
     * @param file The file to parse
     * @return A collection of found ActionIDs.
     */
    public static Collection<? extends ActionIDDef> parse(File file) throws IOException {
        try(InputStream stream = new FileInputStream(file)) {
            return parse(stream, file.getName());
        }
    }

    /**
     * Parse an XML-file and read all the ActionIDs
     * @param stream The XML content to parse
     * @return A collection of found ActionIDs.
     */
    public static Collection<? extends ActionIDDef> parse(InputStream stream, String fileName) throws IOException {
		try (stream) {
			ActionXmlHandler handler = new ActionXmlHandler();
			handler.parseContent(stream);
			final Collection<? extends ActionIDDef> actions = handler.getConfigs().values();

			// populate the links if we find the filename in our static list
			String link = XML_TO_GITHUB.get(fileName);
			if (link != null) {
				for (ActionIDDef action : actions) {
					action.setLink(link + "/" + fileName);
				}
			}

			return actions;
		} catch (SAXException e) {
			throw new IOException(e);
		}
    }

    @Override
    public String toString() {
        return "ActionIDDef{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", group='" + group + '\'' +
                '}';
    }

    @Override
    public int compareTo(ActionIDDef o) {
        return id.compareTo(o.id);
    }
}
