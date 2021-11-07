package com.appsdeveloperblog.app.ws.io.repositories;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest extends Object {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() throws Exception{
        if (!recordsCreated) createdRecord();
    }

    private void createdRecord() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Phan");
        userEntity.setUserId("1231sada");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationToken("afjalfjlkafjlfasf");
        userEntity.setEmailVerificationStatus(true);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("asdafwefw");
        addressEntity.setCity("Vancover");
        addressEntity.setCountry("Canada");
        addressEntity.setPostalCode("ABC123");
        addressEntity.setStreetName("123 Street name");

        AddressEntity billingAddressEntity = new AddressEntity();
        billingAddressEntity.setType("billing");
        billingAddressEntity.setAddressId("sadafqwew");
        billingAddressEntity.setCity("Vancover");
        billingAddressEntity.setCountry("Canada");
        billingAddressEntity.setPostalCode("ABC123");
        billingAddressEntity.setStreetName("123 Street name");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);
        addresses.add(billingAddressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setFirstName("Sergey");
        userEntity2.setLastName("Phan");
        userEntity2.setUserId("1231sada2");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("test1@test.com");
        userEntity2.setEmailVerificationToken("afjalfjlkasdasd");
        userEntity2.setEmailVerificationStatus(true);

        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("asdafwefw2");
        addressEntity2.setCity("Vancover");
        addressEntity2.setCountry("Canada");
        addressEntity2.setPostalCode("ABC123");
        addressEntity2.setStreetName("123 Street name");

        AddressEntity billingAddressEntity2 = new AddressEntity();
        billingAddressEntity2.setType("billing");
        billingAddressEntity2.setAddressId("sadafqwew2");
        billingAddressEntity2.setCity("Vancover");
        billingAddressEntity2.setCountry("Canada");
        billingAddressEntity2.setPostalCode("ABC123");
        billingAddressEntity2.setStreetName("123 Street name");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);
        addresses2.add(billingAddressEntity2);

        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }

    @Test
    final void testGetVerifiedUser(){

        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 2);
    }

    @Test
    final void testFindUserByFirstName(){

        String firstName="Sergey";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().equals(firstName));
    }

    @Test
    final void testFindUserByLastName(){

        String lastName="Phan";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equals(lastName));
    }

    @Test
    final void testFindUserByKeyword(){

        String keyword="ha";
        List<UserEntity> users = userRepository.findUserByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().contains(keyword) ||
                user.getFirstName().contains(keyword));
    }

    @Test
    final void testFindUserFirstNameAndLastNameByKeyword(){

        String keyword="ha";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        Object[] user = users.get(0);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println(userFirstName);
        System.out.println(userLastName);
    }

    @Test
    final void testUpdateUserEmailVerification(){

        boolean newEmailVerification = true;
        userRepository.updateUserEmailVerificationStatus(newEmailVerification, "1231sada");

        UserEntity storedUserDetails = userRepository.findByUserId("1231sada");

        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus == newEmailVerification);
    }

    @Test
    final void testFindUserEntityByUserId(){
        String userId="1231sada";

        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);

        assertNotNull(userEntity);
        assertTrue(userEntity.getUserId().equals(userId));
    }

    @Test
    final void testGetUserEntityFullNameById(){
        String userId="1231sada";
        List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

        assertNotNull(records);
        assertTrue(records.size()==1);

        Object[] userDetails = records.get(0);

        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }

    @Test
    final void testUpdateUserEntityEmailVerification(){

        boolean newEmailVerification = true;
        userRepository.updateUserEntityEmailVerificationStatus(newEmailVerification, "1231sada");

        UserEntity storedUserDetails = userRepository.findByUserId("1231sada");

        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();

        assertTrue(storedEmailVerificationStatus == newEmailVerification);
    }
}