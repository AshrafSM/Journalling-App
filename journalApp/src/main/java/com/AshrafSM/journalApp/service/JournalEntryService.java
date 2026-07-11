package com.AshrafSM.journalApp.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.AshrafSM.journalApp.entities.JournalEntry;
import com.AshrafSM.journalApp.entities.User;
import com.AshrafSM.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName)
    {
        try {

            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            //user.setUserName(null);   ->  This shows why transaction management is required.
            userService.saveEntry(user);

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

    }

    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(String userName, ObjectId id)
    {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


}
