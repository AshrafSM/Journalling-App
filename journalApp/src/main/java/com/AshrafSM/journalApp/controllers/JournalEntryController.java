package com.AshrafSM.journalApp.controllers;

import com.AshrafSM.journalApp.entities.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/getAll")
    public List<JournalEntry> getAll()
    {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping("/enterData")
    public boolean createEntry(@RequestBody JournalEntry journalEntry)
    {
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/getById/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId)
    {
        return journalEntries.get(myId);
    }

    @DeleteMapping("/deleteById/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable long myId)
    {
        return journalEntries.remove(myId);
    }

    @PutMapping("/update/{myId}")
    public JournalEntry updataJournalEntryById(@PathVariable long myId, @RequestBody JournalEntry journalEntry)
    {
        return journalEntries.put(myId, journalEntry);
    }

}
