package cingleVue.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import cingleVue.framework.mongo.AbstractMongoDocument;

import com.mongodb.WriteResult;

public interface MongoDocumentsDao<T extends AbstractMongoDocument> {

	/**Save and update**/
	public void save(T obj) throws Exception ;
	public void save(T obj, String collectionName) throws Exception ;

	public T findOne(Query query, String collectionName,Class<T> entityClass);
	public T findOne(Query query,Class<T> entityClass);
	public List<T> findAll(Class<T> entityClass, String collectionName);
	public List<T> findAll(Class<T> entityClass);
	public void remove(T obj);
	public void remove(T obj, String collectionName);
	
	public WriteResult update(Query query, Update update, Class<T> entityClass,boolean multiUpdate);	
	
	
}
