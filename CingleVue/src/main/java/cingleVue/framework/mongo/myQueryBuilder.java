package cingleVue.framework.mongo;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class myQueryBuilder {

	
	public Query queryBuilder(Query query,String key, Object value) {
		if(query!=null) {
			return query.addCriteria(new Criteria(key).is(value));
		} else {
			return new Query().addCriteria(new Criteria(key).is(value));
		}
	}
	
	//TODO  query type, comparison type
	
	//TODO method for basic query
}

