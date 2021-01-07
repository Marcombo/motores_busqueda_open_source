package com.movies.spring.data.es.model;

import static org.springframework.data.elasticsearch.annotations.FieldType.Keyword;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.annotation.Id;

@Document(indexName = "movies",indexStoreType = "_doc")
public class Movie {

	@Id
	private String id;
		
	@Field(type = Keyword)
	private String title;
    
	@Field(type = Text)
	private String year;
    
	@Field(type = Keyword)
    private List<String> topics=new ArrayList<String>();

    public Movie(){}

    public Movie(String id, String title , String year, List<String> topics){
        this.id=id;
        this.title=title;
        this.year=year;
        this.topics=topics;
    }
    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", year=" + year + ", topics=" + topics + "]";
	}

	
}
