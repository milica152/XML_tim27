package ftn.project.xml.templates;

import org.exist.xupdate.XUpdateProcessor;

    public class XUpdateTemplate {

        public static final String TARGET_NAMESPACE = "https://github.com/milica152/XML_tim27";


        public static final String INSERT_AFTER = "<xu:modifications version=\"1.0\" xmlns:xu=\""
                + XUpdateProcessor.XUPDATE_NS + "\" xmlns=\"" + TARGET_NAMESPACE + "\">"
                + "<xu:insert-after select=\"%1$s\">%2$s</xu:insert-after>" + "</xu:modifications>";

        public static final String INSERT_BEFORE = "<xu:modifications version=\"1.0\" xmlns:xu=\""
                + XUpdateProcessor.XUPDATE_NS + "\" xmlns=\"" + TARGET_NAMESPACE + "\">"
                + "<xu:insert-before select=\"%1$s\">%2$s</xu:insert-before>" + "</xu:modifications>";


        public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
                + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
                + "</xu:modifications>";

        public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
                + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
                + "</xu:modifications>";


        public static final String REMOVE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
                + "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:remove select=\"%1$s\"/>" + "</xu:modifications>";
    }
