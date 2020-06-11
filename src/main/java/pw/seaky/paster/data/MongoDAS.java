package pw.seaky.paster.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pw.seaky.paster.model.Paste;

import java.util.List;
import java.util.Optional;

@Repository("mongodas")
public class MongoDAS implements PasteDao {


    MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String daoName() {
        return "Mongo";
    }

    @Override
    public Paste addPaste(Paste paste) {

        return mongoTemplate.save(paste);
    }

    @Override
    public Optional<Paste> getPaste(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return Optional.ofNullable(mongoTemplate.findOne(query, Paste.class));
    }

    @Override
    public int deletePaste(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.findAndRemove(query, Paste.class);
        return 0;
    }

    @Override
    public List<Paste> getPastes() {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").ne(null));
        return mongoTemplate.find(query, Paste.class);
    }
}
