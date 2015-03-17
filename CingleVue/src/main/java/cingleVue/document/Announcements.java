package cingleVue.document;

import java.util.Date;

import org.junit.Ignore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import cingleVue.framework.mongo.AbstractMongoDocument;


@Document
public class Announcements extends AbstractMongoDocument {

	@Id
	private String id;	
	private String title;
	private String body;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd MMM yyyy HH:mm:ss")
	private Date startDate;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd MMM yyyy HH:mm:ss")
	private Date expireyDate;

	
	public Announcements(String _id, String title, String body, Date startDate,
			Date expireyDate) {
		super();
		this.id = _id;
		this.title = title;
		this.body = body;
		this.startDate = startDate;
		this.expireyDate = expireyDate;
	}

	public Announcements(String title, String body, Date startDate,
			Date expireyDate) {
		super();		
		this.title = title;
		this.body = body;
		this.startDate = startDate;
		this.expireyDate = expireyDate;
	}

	public Announcements() {
		super();
	}
	
	public Announcements updateForSave(){
		this.setStartDate(new Date());
		this.setId(null);
		return this;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpireyDate() {
		return expireyDate;
	}

	public void setExpireyDate(Date expireyDate) {
		this.expireyDate = expireyDate;
	}
}
