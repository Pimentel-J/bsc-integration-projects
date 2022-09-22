package lapr2.project.agpsd.model;

import java.util.Objects;

/**
 * Represents a supporting document of service provider application.
 *
 *
 */
public class Document {

    /**
     * Document
     */
    private String doc;

    /**
     * Default document
     */
    private static final String DEFAULT_DOCUMENT = "no document";

    /**
     * Initializes the default document's constructor
     */
    public Document() {
        this.doc = DEFAULT_DOCUMENT;
    }

    /**
     * Initializes the document's constructor
     *
     * @param doc
     */
    public Document(String doc) {
        this.doc = doc;
    }
    
    /**
     * Returns the document
     *
     * @return doc
     */
    public String getDoc() {
        return doc;
    }

    @Override
    public String toString() {
        return String.format("%s%n", this.doc);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hash(this.doc);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/
        
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        // field comparison
        Document obj = (Document) o;
        return (Objects.equals(doc, obj.doc));
    }

}
