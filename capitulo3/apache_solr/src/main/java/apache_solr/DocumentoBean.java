package apache_solr;

import org.apache.solr.client.solrj.beans.Field;

public class DocumentoBean {

    String id;
    String name;
    Integer value;

    public DocumentoBean(String id, String name, Integer value) {
        super();
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    @Field("id")
    protected void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Field("name")
    protected void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    @Field("value")
    protected void setValue(Integer value) {
        this.value = value;
    }
}
