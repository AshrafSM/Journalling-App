package com.AshrafSM.journalApp.repository;

import com.AshrafSM.journalApp.entities.JournalEntry;
import com.AshrafSM.journalApp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);

}
