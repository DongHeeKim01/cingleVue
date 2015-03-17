package cingleVue.framework.mongo;

import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.WriteConcernResolver;

import com.mongodb.WriteConcern;

public class CustomWriteConcernResolver implements WriteConcernResolver {

	@Override
	public WriteConcern resolve(MongoAction action) {
		// TODO Auto-generated method stub
		return null;
	}

}
