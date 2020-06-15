package ftn.project.xml.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class RDFAuthenticationUtilities {
    private static String connectionUri = "xmldb:exist://%1$s:%2$s/exist/xmlrpc";


    static public class RDFConnectionProperties {

        public String endpoint;
        public String dataset;
        public String queryEndpoint;
        public String updateEndpoint;
        public String dataEndpoint;

        public RDFConnectionProperties(Properties props) {
            super();

            dataset = props.getProperty("conn.dataset").trim();
            endpoint = props.getProperty("conn.endpoint").trim();

            queryEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.query").trim());
            updateEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.update").trim());

            dataEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.data").trim());
        }
    }


    public static RDFConnectionProperties loadProperties() throws IOException {
        String propsName = "connection.properties";
        InputStream propsStream = openStream(propsName);

        if (propsStream == null)
            throw new IOException("Could not read properties " + propsName);

        Properties props = new Properties();
        props.load(propsStream);

        return new RDFConnectionProperties(props);
    }


    public static InputStream openStream(String fileName) {
        return AuthenticationUtilities.class.getClassLoader().getResourceAsStream(fileName);
    }
}






/*
public class RDFConnectionProperties {
    private String endpoint;
    private String dataset;
    private String queryEndpoint;
    private String updateEndpoint;
    private String dataEndpoint;


    public RDFConnectionProperties(Properties props) {
        dataset = props.getProperty("conn.dataset").trim();
        endpoint = props.getProperty("conn.endpoint").trim();

        queryEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.query").trim());
        updateEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.update").trim());
        dataEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.data").trim());
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getQueryEndpoint() {
        return queryEndpoint;
    }

    public void setQueryEndpoint(String queryEndpoint) {
        this.queryEndpoint = queryEndpoint;
    }

    public String getUpdateEndpoint() {
        return updateEndpoint;
    }

    public void setUpdateEndpoint(String updateEndpoint) {
        this.updateEndpoint = updateEndpoint;
    }

    public String getDataEndpoint() {
        return dataEndpoint;
    }

    public void setDataEndpoint(String dataEndpoint) {
        this.dataEndpoint = dataEndpoint;
    }

}
*/
