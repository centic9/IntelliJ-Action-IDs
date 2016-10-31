package org.dstadler.intellij.xml;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Information about a single ActionID in IntelliJ
 */
public class ActionIDDef {
    private String id;
    private String text;
    private String group;

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
            return parse(stream);
        }
    }

    /**
     * Parse an XML-file and read all the ActionIDs
     * @param stream The XML content to parse
     * @return A collection of found ActionIDs.
     */
    public static Collection<? extends ActionIDDef> parse(InputStream stream) throws IOException {
        try {
            ActionXmlHandler handler = new ActionXmlHandler();
            handler.parseContent(stream);
            return handler.getConfigs().values();
        } catch (SAXException e) {
            throw new IOException(e);
        } finally {
            stream.close();
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
}
