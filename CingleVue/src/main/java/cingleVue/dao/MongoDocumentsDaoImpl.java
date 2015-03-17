package cingleVue.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import cingleVue.framework.mongo.AbstractMongoDocument;

import com.mongodb.WriteResult;

@Component
public class MongoDocumentsDaoImpl<T extends AbstractMongoDocument> implements MongoDocumentsDao<T>  {

	@Autowired
	MongoTemplate mongoTemplate;
	

	@Override
	public void save(T obj) throws Exception {
		mongoTemplate.save(obj);
	}
	@Override
	public void save(T obj, String collectionName) throws Exception {
		mongoTemplate.save(obj,collectionName);
	}	
	
	@Override
	public T findOne(Query query,Class<T> entityClass) {		
		return  mongoTemplate.findOne(query,entityClass);
	}
	@Override
	public T findOne(Query query, String collectionName,Class<T> classEntity) {
		return mongoTemplate.findOne(query,classEntity,collectionName);
	}
	@Override
	public List<T> findAll(Class<T> entityClass, String collectionName) {
		return mongoTemplate.findAll(entityClass, collectionName);
	}
	@Override
	public List<T> findAll(Class<T> entityClass) {
		return mongoTemplate.findAll(entityClass);
	}
	@Override
	public void remove(T obj) {
		mongoTemplate.remove(obj);
	}

	public void remove(T obj, String collectionName) {
		mongoTemplate.remove(obj,collectionName);
	}
	
	@Override
	public WriteResult  update(Query query, Update update, Class<T> entityClass,
			boolean multiUpdate) {
		if(multiUpdate==true) {
			return mongoTemplate.updateMulti(query, update, entityClass);
		} else {
			return mongoTemplate.updateFirst(query, update, entityClass);			
		}
	}
}
