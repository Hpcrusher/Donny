package covfefe.network;

import covfefe.types.MazeCom;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;

public class XmlInStream extends UTFInputStream {

    private Unmarshaller unmarshaller;

    public XmlInStream(InputStream in) {
        super(in);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MazeCom.class);
            unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            try {
                // muss getResourceAsStream() statt getResource() sein
                // damit es auch in jars funktioniert
                InputStream resourceAsStream = getClass().getResourceAsStream("/xsd/mazeCom.xsd"); //$NON-NLS-1$
                // convert inputstream to file, no better implementation available
                File tempFile = File.createTempFile("temp", ".xsd");  //$NON-NLS-1$//$NON-NLS-2$
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                int read = 0;
                byte[] b = new byte[1024];
                while ((read = resourceAsStream.read(b)) != -1) {
                    fileOutputStream.write(b, 0, read);
                }
                fileOutputStream.close();

                Schema schema = schemaFactory.newSchema(tempFile);
                unmarshaller.setSchema(schema);
                tempFile.deleteOnExit();
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Liest eine Nachricht und gibt die entsprechende Instanz zurueck
     *
     * @return
     * @throws IOException
     */
    public MazeCom readMazeCom() throws IOException, UnmarshalException {
        byte[] bytes = null;
        MazeCom result = null;
        try {
            String xml = this.readUTF8();
            bytes = xml.getBytes();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            result = (MazeCom) this.unmarshaller.unmarshal(bais);
        } catch (UnmarshalException e) {
            throw e;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }

}