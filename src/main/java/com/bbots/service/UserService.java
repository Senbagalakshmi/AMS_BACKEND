package com.bbots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbots.model.User;
import com.bbots.repository.UserRepository;
import com.bbots.model.AuthRecord;
import com.bbots.model.AuthDataBlock;
import com.bbots.repository.AuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bbots.dto.UserProfileDTO;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long userscd) {
        return repository.findById(userscd);
    }

    public void createUser(User user) {
        repository.save(user);
    }

    public void createUserAuthRequest(User user) {
        try {
            String userJson = objectMapper.writeValueAsString(user);

            AuthRecord authRecord = new AuthRecord();
            authRecord.setOrgCode(user.getOrgcode());
            authRecord.setProgramId("AUTH002"); // Adjust your Program ID appropriately
            authRecord.setDisplayRemarks("User Creation Request for " + user.getUserscd());
            authRecord.setEntryUser(user.getEuser());

            AuthDataBlock block = new AuthDataBlock();
            block.setOrgCode(user.getOrgcode());
            block.setEffDate(new Date());
            block.setProgramId("AUTH002");
            block.setPrimaryKey(user.getUserscd());
            block.setRecSl(1);
            block.setTableName("USERS");
            block.setDataBlock(userJson);

            authRecord.setDataBlocks(Collections.singletonList(block));
            authRepository.insertAuthRequest(authRecord);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initiate user creation process", e);
        }
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    public void deleteUser(String userscd) {
        repository.delete(userscd);
    }
    public UserProfileDTO getUserProfileByUsername(String username) {

        Object[] data = repository.getUserProfileByUsername(username);

        System.out.println("USERNAME : " + data[0]);
        System.out.println("EMAIL : " + data[1]);
        System.out.println("ROLE : " + data[2]);

        UserProfileDTO dto = new UserProfileDTO();
        dto.setUsername((String) data[0]);
        dto.setEmail((String) data[1]);
        dto.setRole((String) data[2]);

        return dto;
    }
}
