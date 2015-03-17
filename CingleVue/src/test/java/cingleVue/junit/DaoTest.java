package cingleVue.junit;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cingleVue.dao.MongoDocumentsDao;
import cingleVue.document.Announcements;


@ContextConfiguration(locations={"file:WebContent/WEB-INF/spring/spring-config.xml","file:WebContent/WEB-INF/spring/spring-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoDocumentsDao<Announcements> aDao;
	
	@Test
	public void announcementTest() throws Exception{
		
		/*//Save 
		Announcements announcements = new Announcements("New new new ", "body body boyd", new Date(), new Date());
		mongoTemplate.save(announcements,"announcements");
		Assert.assertNotNull(announcements.getId());
		
		// findOne
		String _id=announcements.getId();
		Query query = new Query().addCriteria(new Criteria("_id").is(announcements.getId()));
		Announcements insertChecked = mongoTemplate.findOne(query,Announcements.class);
		Assert.assertEquals(announcements.getId(), insertChecked.getId());

		//findList 
		List<Announcements> listAnnounce =  mongoTemplate.findAll(Announcements.class, "announcements");
		Assert.assertTrue(listAnnounce.size()>0);
		
		//remove Test 
		mongoTemplate.remove(announcements,"announcements");
		Announcements nullAssumedAn = mongoTemplate.findOne(query,Announcements.class);
		Assert.assertNull(nullAssumedAn);
		*/
		
		Announcements announcements = new Announcements("New new new ", "body body boyd", new Date(), new Date());
		aDao.save(announcements,"announcements");
		Assert.assertNotNull(announcements.getId());
		
		String _id=announcements.getId();
		Query query = new Query().addCriteria(new Criteria("_id").is(announcements.getId()));
		
		Announcements t = aDao.findOne(query,Announcements.class);
		Assert.assertEquals(_id,t.getId());
		
		List<Announcements> listAnnounce =  aDao.findAll(Announcements.class, "announcements");
		Assert.assertTrue(listAnnounce.size()>0);
		
		aDao.remove(listAnnounce.get(0));
		
	}
	
	
	
}
