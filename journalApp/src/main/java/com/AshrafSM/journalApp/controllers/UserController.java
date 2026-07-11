package com.AshrafSM.journalApp.controllers;

import com.AshrafSM.journalApp.entities.User;
import com.AshrafSM.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers()
    {
        List<User> all=userService.getAll();
        if(!all.isEmpty() && all != null)
        {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> createUser(@RequestBody User user)
    {
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateUser/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName)
    {
        User userInDb = userService.findByUserName(userName);
        if(userInDb != null)
        {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
