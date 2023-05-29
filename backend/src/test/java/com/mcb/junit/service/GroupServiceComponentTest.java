package com.mcb.junit.service;

import com.mcb.entity.Groups;
import com.mcb.repository.GroupRepository;
import com.mcb.service.GroupService;
import com.mcb.wrapper.GroupRequest;
import com.mcb.wrapper.GroupResponse;
import com.mcb.wrapper.MarkResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GroupServiceComponentTest {

    @Autowired
    private GroupService groupService;

    @MockBean
    private GroupRepository groupRepository;

    private Groups groups;

    @BeforeEach
    public void setUp() {
        groups = Groups.builder()
                .id(1)
                .name("Dango")
                .build();
    }

    @DisplayName("Junit Tests for add groups")
    @Test
    public void addGroupTest() {
        GroupRequest request = new GroupRequest();
        request.setName("Danggo");
        ResponseEntity<?> response = groupService.addGroup(request);
        verify(groupRepository,times(1)).save(any(Groups.class));
        assertEquals("{\"message\":\""+"Group Successfully created."+"\"}",response.getBody());
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @DisplayName("Junit Tests for update groups")
    @Test
    public void updateGroup_Test()  {
        int groupId = 1;
        GroupRequest request = new GroupRequest();
        request.setName("MarryKom");
        groups.setName(request.getName());
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(groups));
        ResponseEntity<?> response = groupService.updateGroup(groupId,request);
        assertEquals("{\"message\":\"" + "Group name Successfully updated" + "\"}", response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(request.getName(), groups.getName());
    }

    @DisplayName("Junit Test for Delete group")
    @Test
    public void deleteGroupTest() {
        int id = 1;
        when(groupRepository.findById(id)).thenReturn(Optional.of(groups));
        ResponseEntity<String> response = groupService.deleteGroup(id);
        verify(groupRepository, times(1)).deleteById(id);
        assertEquals("{\"message\":\"" + "Group id " +id + " deleted successfully." + "\"}", response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @DisplayName("JUnit Test to find all Groups")
    @Test
    public void getAllGroupsTest() {
        when(groupRepository.getAllGroups()).thenReturn(Stream.of(new GroupResponse(1,"Danjgo"),
                new GroupResponse(2,"Kantiram")).collect(Collectors.toList()));
        assertEquals(2, Objects.requireNonNull(groupService.getAllGroups().getBody()).size());
    }

    @DisplayName("JUnit Test for get group by id")
    @Test
    public void getGroupByIdTest() {
        int groupId = 1;
        GroupResponse group = new GroupResponse(1,"Fat-X");
        when(groupRepository.findGroupById(groupId)).thenReturn(Optional.of(group));
        ResponseEntity<?> response = groupService.findGroupById(groupId);
        assertEquals(group, response.getBody());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

}
