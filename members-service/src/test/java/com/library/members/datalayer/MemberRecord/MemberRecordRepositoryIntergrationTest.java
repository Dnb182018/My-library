package com.library.members.datalayer.MemberRecord;

import com.library.members.datalayer.Users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRecordRepositoryIntergrationTest {
    @Autowired
    private MemberRecordRepository memberRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setupDb(){
        memberRecordRepository.deleteAll();
        userRepository.deleteAll();

    }




    @Test
    public void whenMembersExit_thenReturnAllMembers(){
        //arrange
        UserIdentifier userIdentifier1 = new UserIdentifier("c195fd3a-3580-4b71-80f1-010000000000");

        MemberRecord memberRecord1 = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-01"),
                MemberType.REGULAR,
                userIdentifier1);

        UserIdentifier userIdentifier2 = new UserIdentifier("c195fd3a-3580-4b71-80f1-020000000000");

        MemberRecord memberRecord2 = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-01"),
                MemberType.REGULAR,
                userIdentifier2);

        memberRecordRepository.save(memberRecord2);
        memberRecordRepository.save(memberRecord1);

        long afterSizeDB = memberRecordRepository.count();

        //act
        List<MemberRecord> memberRecords = memberRecordRepository.findAll();

        //assert
        assertNotNull(memberRecord1);
        assertNotEquals(0,afterSizeDB);
        assertEquals(afterSizeDB, memberRecords.size());

    }

    @Test
    public void whenUsersExist_thenReturnAllUsers() {
        // arrange
        User user1 = new User(
                "John",
                "Doe",
                "john.doe@example.com",
                new ArrayList<>(Arrays.asList(new UserPhoneNumber(PhoneType.MOBILE,"514-111-2222"))),
                new UserAddress("123 Maple Street", "Toronto", "Ontario", "Canada", "M4B1B3")
        );

        User user2 = new User(
                "Jane",
                "Smith",
                "john.doe@example.com",
                new ArrayList<>(Arrays.asList(new UserPhoneNumber(PhoneType.MOBILE,"514-111-2222"))),
                new UserAddress("456 Oak Avenue", "Montreal", "Quebec", "Canada", "H2X1Y4")
        );

        userRepository.save(user1);
        userRepository.save(user2);

        long afterSizeDB = userRepository.count();

        // act
        List<User> users = userRepository.findAll();

        // assert
        assertNotNull(users);
        assertNotEquals(0, afterSizeDB);
        assertEquals(afterSizeDB, users.size());
    }




    //ById
    @Test
    public void whenMemberExists_ReturnMemberById() {
        //arrange
        UserIdentifier userIdentifier = new UserIdentifier("c195fd3a-3580-4b71-80f1-010000000000");

        MemberRecord memberRecord = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-01"),
                MemberType.REGULAR,
                userIdentifier);

        memberRecordRepository.save(memberRecord);
        //act
        MemberRecord savedMember = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId(memberRecord.getMemberRecordIdentifier().getMemberId());

        //assert
        assertNotNull(savedMember);
        assertEquals(memberRecord.getMemberRecordIdentifier(), savedMember.getMemberRecordIdentifier());
        assertEquals(memberRecord.getType(), savedMember.getType());
        assertEquals(memberRecord.getMemberRecordIdentifier(), savedMember.getMemberRecordIdentifier());
    }

    @Test
    public void whenUserExists_ReturnUserById() {
        // arrange

        User user = new User(
                "John",
                "Doe",
                "john.doe@example.com",
                new ArrayList<>(Arrays.asList(new UserPhoneNumber(PhoneType.MOBILE, "514-111-2222"))),
                new UserAddress("123 Maple Street", "Toronto", "Ontario", "Canada", "M4B1B3")
        );

        userRepository.save(user);

        // act
        User savedUser = userRepository.findUserByUserIdentifier_UserId(user.getUserIdentifier().getUserId());

        // assert
        assertNotNull(savedUser);
        assertEquals(user.getUserIdentifier(), savedUser.getUserIdentifier());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
    }


    //New
    @Test
    public void whenMemberEntityIsValid_thenAddMember(){
        //arrange
        UserIdentifier userIdentifier = new UserIdentifier("c195fd3a-3580-4b71-80f1-010000000000");

        MemberRecord memberRecord = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-01"),
                MemberType.REGULAR,
                userIdentifier);


        //act
        MemberRecord savedMember = memberRecordRepository.save(memberRecord);

        //assert
        assertNotNull(savedMember);
        assertNotNull(savedMember.getId());
        assertNotNull(savedMember.getMemberRecordIdentifier());
        assertNotNull(savedMember.getMemberRecordIdentifier().getMemberId());
        assertEquals(memberRecord.getStartDate(),savedMember.getStartDate());
        assertEquals(memberRecord.getEndDate(), savedMember.getEndDate());
        assertEquals(memberRecord.getType(), savedMember.getType());

    }


    @Test
    public void whenUserEntityIsValid_thenAddUser() {
        // arrange
        User user = new User(
                "John",
                "Doe",
                "john.doe@example.com",
                new ArrayList<>(Arrays.asList(new UserPhoneNumber(PhoneType.MOBILE, "514-111-2222"))),
                new UserAddress("123 Maple Street", "Toronto", "Ontario", "Canada", "M4B1B3")
        );

        // act
        User savedUser = userRepository.save(user);

        // assert
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserIdentifier());
        assertNotNull(savedUser.getUserIdentifier().getUserId());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
    }



    //UPDATE
    @Test
    public void whenMemberIsExist_thenUpdateMember(){
        //arrange
        UserIdentifier userIdentifier = new UserIdentifier("c195fd3a-3580-4b71-80f1-010000000000");

        MemberRecord memberRecord = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-01"),
                MemberType.REGULAR,
                userIdentifier);


        memberRecordRepository.save(memberRecord);

        //act
        MemberRecord existMember = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId(memberRecord.getMemberRecordIdentifier().getMemberId());

        userIdentifier = new UserIdentifier("c195fd3a-3580-4b71-80f1-010000000000");

        MemberRecord updateMember = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-31"),
                MemberType.REGULAR,
                userIdentifier);

        updateMember.setId(existMember.getId());

        MemberRecord savedMember = memberRecordRepository.save(updateMember);

        //assert
        assertNotNull(savedMember);
        assertNotNull(savedMember.getId());
        assertNotNull(savedMember.getMemberRecordIdentifier());
        assertNotNull(savedMember.getMemberRecordIdentifier().getMemberId());
        assertEquals(existMember.getMemberRecordIdentifier().getMemberId(),savedMember.getMemberRecordIdentifier().getMemberId());
        assertEquals(existMember.getType(),savedMember.getType());
        assertEquals(existMember.getStartDate(), savedMember.getStartDate());

    }

    @Test
    public void whenUserExists_thenUpdateUser() {
        // arrange
        User user = new User(
                "John",
                "Doe",
                "john.doe@example.com",
                new ArrayList<>(Arrays.asList(new UserPhoneNumber(PhoneType.MOBILE, "514-111-2222"))),
                new UserAddress("123 Maple Street", "Toronto", "Ontario", "Canada", "M4B1B3")
        );

        // Save the original user
        User savedUser = userRepository.save(user);

        // act
        User existingUser = userRepository.findUserByUserIdentifier_UserId(savedUser.getUserIdentifier().getUserId());

        // Update the user's email and address
        existingUser.setEmail("john.doe@newdomain.com");
        existingUser.setUserAddress(new UserAddress("789 Birch Road", "Vancouver", "British Columbia", "Canada", "V6C1B4"));

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);

        // assert
        assertNotNull(updatedUser);
        assertNotNull(updatedUser.getUserIdentifier());
        assertNotNull(updatedUser.getUserIdentifier().getUserId());
        assertEquals(savedUser.getUserIdentifier().getUserId(), updatedUser.getUserIdentifier().getUserId());
        assertEquals("john.doe@newdomain.com", updatedUser.getEmail());
        assertEquals("789 Birch Road", updatedUser.getUserAddress().getStreet_name());
        assertEquals("Vancouver", updatedUser.getUserAddress().getCity());
        assertEquals("British Columbia", updatedUser.getUserAddress().getProvince());
        assertEquals("V6C1B4", updatedUser.getUserAddress().getPostal_code());
    }




    //DELETE
    @Test
    public void whenMemeerExist_thenDeleteMember(){
        //arrange
        UserIdentifier userIdentifier = new UserIdentifier("c195fd3a-3580-4b71-80f1-010000000000");

        MemberRecord memberRecord = new MemberRecord(
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2027-01-01"),
                MemberType.REGULAR,
                userIdentifier);
        memberRecordRepository.save(memberRecord);


        //act
        memberRecordRepository.delete(memberRecord);

        MemberRecord deletedMember = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId(memberRecord.getMemberRecordIdentifier().getMemberId());

        //assert
        assertNull(deletedMember);

    }

    @Test
    public void whenUserExists_thenDeleteUser() {
        // arrange
        User user = new User(
                "John",
                "Doe",
                "john.doe@example.com",
                new ArrayList<>(Arrays.asList(new UserPhoneNumber(PhoneType.MOBILE, "514-111-2222"))),
                new UserAddress("123 Maple Street", "Toronto", "Ontario", "Canada", "M4B1B3")
        );
        // Save the user
        User savedUser = userRepository.save(user);

        // act
        userRepository.delete(savedUser);

        // Try to find the deleted user
        User deletedUser = userRepository.findUserByUserIdentifier_UserId(savedUser.getUserIdentifier().getUserId());

        // assert
        assertNull(deletedUser);
    }




    //------------NEGATIVE--------------
    @Test
    public void whenMemberDoesNotExist_ReturnNull() {
        //act
        MemberRecord savedMember = memberRecordRepository.findMemberRecordByMemberRecordIdentifier_MemberId("43b06e4a-c1c5-41d4-a716-446655440001");

        //assert
        assertNull(savedMember);
    }

    @Test
    public void whenUserDoesNotExist_ReturnNull() {
        // act
        User savedUser = userRepository.findUserByUserIdentifier_UserId("43b06e4a-c1c5-41d4-a716-446655440001");

        // assert
        assertNull(savedUser);
    }


}