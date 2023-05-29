package com.mcb.service;

import com.mcb.constant.MCBConstant;
import com.mcb.entity.Groups;
import com.mcb.repository.GroupRepository;
import com.mcb.utils.MCBUtils;
import com.mcb.wrapper.GroupRequest;
import com.mcb.wrapper.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    @Autowired
    private final GroupRepository repository;

    public ResponseEntity<String> addGroup(GroupRequest request) {
        try {
            if (request.getName() != null) {
                repository.save(saveGroups(request));
                return MCBUtils.getResponse("Group Successfully created.", HttpStatus.CREATED);
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Groups saveGroups(GroupRequest request) {
        return Groups.builder()
                .name(request.getName())
                .build();
    }


    public ResponseEntity<String> updateGroup(int id, GroupRequest request) {
        try {
            Groups groups = repository.findById(id).get();
            groups.setName(request.getName());
            repository.save(groups);
            return MCBUtils.getResponse("Group name Successfully updated", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteGroup(int id) {
        try {
            Optional<Groups> groups = repository.findById(id);
            if (groups.isPresent()) {
                repository.deleteById(id);
                return MCBUtils.getResponse("Group id " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
            } else {
                return MCBUtils.getResponse("Group id " + id + " does not exist.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        try {
            List<GroupResponse> groups = repository.getAllGroups();
            return new ResponseEntity<List<GroupResponse>>(groups, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<GroupResponse>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> findGroupById(int id) {
        try {
            Optional<GroupResponse> responses = repository.findGroupById(id);
            if (responses.isPresent()) {
                return new ResponseEntity<>(responses.get(), HttpStatus.FOUND);
            } else {
                return MCBUtils.getResponse("Group id " + id + " does not exist", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
