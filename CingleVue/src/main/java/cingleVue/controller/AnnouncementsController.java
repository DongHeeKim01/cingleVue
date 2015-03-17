package cingleVue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cingleVue.dao.MongoDocumentsDao;
import cingleVue.document.Announcements;
import cingleVue.framework.mongo.myQueryBuilder;
import cingleVue.framework.mvc.CommResponse;

@Controller
public class AnnouncementsController {

	@Autowired
	MongoDocumentsDao<Announcements> mongoDao;
	private static String COLNAME="announcements";
	
	@RequestMapping(value="/index",method={RequestMethod.GET})
	public String getIndex(){		
		return "index";
	}
	
	@RequestMapping(value="/announcements",method={RequestMethod.GET},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getAnnouncements(){
		CommResponse response = new CommResponse(true);
		List<Announcements> listAnnouncements =  this.mongoDao.findAll(Announcements.class, COLNAME);
		response.setBody(listAnnouncements);
		return new ResponseEntity<CommResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/announcement/{annonunceId}",method={RequestMethod.GET},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getAnnouncement(@PathVariable("annonunceId") String announceId){
		CommResponse response = new CommResponse(true);
		Announcements announcement =  this.mongoDao.findOne(new myQueryBuilder().queryBuilder(null, "_id", announceId),Announcements.class);
		response.setBody(announcement);
		return new ResponseEntity<CommResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/announcement/{annonunceId}/{mode}",method={RequestMethod.POST},produces={MediaType.APPLICATION_JSON_VALUE},consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateAnnonucement(@PathVariable("annonunceId") String announceId,@PathVariable("mode") String mode, @RequestBody Announcements announcements ) throws Exception{
		CommResponse response = new CommResponse(true);
		Announcements announcement =  this.mongoDao.findOne(new myQueryBuilder().queryBuilder(null, "_id", announceId),Announcements.class);
		if(announcement!=null) {
			if(mode.equals("delete")) {
				this.mongoDao.remove(announcements,COLNAME);
			} else if(mode.equals("update")) {
				this.mongoDao.save(announcements);
				response.setBody(announcement);
			}
		} else {
			response.setTransactionStatus(false);
		}
		return new ResponseEntity<CommResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value="/announcements",method={RequestMethod.POST},produces={MediaType.APPLICATION_JSON_VALUE},consumes={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getAnnouncements( @RequestBody Announcements announcements ) throws Exception{
		CommResponse response = new CommResponse(true);
		Announcements retrivedAnnouncement =  this.mongoDao.findOne(new myQueryBuilder().queryBuilder(null, "_id", announcements.getId()),Announcements.class);
		if(retrivedAnnouncement==null) {
			this.mongoDao.save(announcements.updateForSave());
			response.setBody(announcements);
		} else {
			response.setTransactionStatus(false);
			response.setErrorMsgSingle("Id(Key value) cannnot be duplicated");
		}
		return new ResponseEntity<CommResponse>(response, HttpStatus.OK);
	}
	
	
}
