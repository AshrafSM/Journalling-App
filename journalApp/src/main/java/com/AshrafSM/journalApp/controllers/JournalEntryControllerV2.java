package com.AshrafSM.journalApp.controllers;

import com.AshrafSM.journalApp.entities.JournalEntry;
import com.AshrafSM.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping("/getAll")
    public List<JournalEntry> getAll()
    {
        return journalEntryService.getAll();
    }

    @PostMapping("/enterData")
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry)
    {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/getById/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId)
    {
        return journalEntryService.findbyId(myId).orElse(null);
    }

    @DeleteMapping("/deleteById/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId)
    {
        journalEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("/update/{myId}")
    public JournalEntry updataJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry)
    {
        JournalEntry old= journalEntryService.findbyId(myId).orElse(null);
        if(old != null)
        {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return old;
        }
        return null;
    }

}
