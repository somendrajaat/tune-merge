package com.tunemerge.Repository;

import com.tunemerge.Model.Client;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Client, ObjectId> {
        Client findByUsername(String username);
}

