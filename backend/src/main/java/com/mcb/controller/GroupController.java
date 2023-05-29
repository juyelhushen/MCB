package com.mcb.controller;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Groups;
import com.mcb.service.GroupService;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.GroupRequest;
import com.mcb.wrapper.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/add")
    public ResponseEntity<String> addGroup(@RequestBody GroupRequest request) {
        try {
            return groupService.addGroup(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGroup(@PathVariable int id, @RequestBody GroupRequest request) {
        try {
            return groupService.updateGroup(id,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable int id) {
        try {
            return groupService.deleteGroup(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        try {
            return groupService.getAllGroups();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGroupById(@PathVariable int id) {
        try {
            return groupService.findGroupById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
