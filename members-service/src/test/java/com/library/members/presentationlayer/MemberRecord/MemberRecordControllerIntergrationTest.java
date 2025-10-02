package com.library.members.presentationlayer.MemberRecord;

import com.library.members.datalayer.MemberRecord.MemberRecord;
import com.library.members.datalayer.MemberRecord.MemberType;
import com.library.members.datalayer.Users.PhoneType;
import com.library.members.datalayer.Users.UserAddress;
import com.library.members.datalayer.Users.UserPhoneNumber;
import com.library.members.mappinglayer.MemberRecord.MemberRecordRequestMapper;
import com.library.members.presentationlayer.Users.UserRequestModel;
import com.library.members.presentationlayer.Users.UserResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"/data-h2.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberRecordControllerIntergrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private final String BASE_URI_MEMBER = "/api/v1/memberRecords";
    private final String BASE_URI_USER = "/api/v1/users";
    private final String VALID_MEMBER_ID = "43b06e4a-c1c5-41d4-a716-446655440001";
    private final String NOT_FOUND_MEMBER_ID = "43b06e4a-c1c5-41d4-a716-446655441111";
    private final String INVALID_MEMBER_ID = "invalid-id-123";

    private final String VALID_USER_ID = "c195fd3a-3580-4b71-80f1-010000000000";
    private final String NOT_FOUND_USER_ID = "c195fd3a-3580-4b71-80f1-000000001000";
    private final String INVALID_USER_ID = "invalid-id-123";

    //GET All
    @Test
    void whenMembersExist_thenReturnAllMember() {
        webTestClient.get()
                .uri(BASE_URI_MEMBER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MemberRecordResponseModel.class)
                .value(Members -> {
                    assertNotNull(Members);
                    assertFalse(Members.isEmpty());
                });
    }


    @Test
    void whenMemberExists_thenReturnMemberById() {
        // Arrange: Create a member first
        MemberRecordRequestModel memberRequestModel = buildSampleMember();

        MemberRecordResponseModel createdMember = webTestClient.post()
                .uri(BASE_URI_MEMBER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(memberRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MemberRecordResponseModel.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdMember);
        String memberId = createdMember.getMemberId();

        // Act & Assert: Retrieve member by ID
        webTestClient.get()
                .uri(BASE_URI_MEMBER + "/" + memberId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MemberRecordResponseModel.class)
                .value(member -> {
                    assertNotNull(member);
                    assertEquals(memberId, member.getMemberId());
                    assertEquals(memberRequestModel.getUserId(), member.getUserId());
                });
    }





    @Test
    public void whenMemberRequestIsValid_thenReturnNewMember() {
        MemberRecordRequestModel memberRequestModel = buildSampleMember();

        webTestClient.post()
                .uri(BASE_URI_MEMBER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(memberRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(MemberRecordResponseModel.class)
                .value(member -> {
                    assertNotNull(member);
                    assertNotNull(member.getMemberId());
                    assertEquals(memberRequestModel.getUserId(), member.getUserId());
                    assertEquals(memberRequestModel.getType(), member.getType());
                });
    }

    @Test
    public void whenUpdateRequestIsValid_thenUpdateMember() {
        // Arrange: Create a member first
        MemberRecordRequestModel memberRequestModel = buildSampleMember();

        MemberRecordResponseModel createdMember = webTestClient.post()
                .uri(BASE_URI_MEMBER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(memberRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MemberRecordResponseModel.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdMember);
        String memberId = createdMember.getMemberId();

        // Act: Update the member
        memberRequestModel.setType(MemberType.REGULAR);

        webTestClient.put()
                .uri(BASE_URI_MEMBER + "/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(memberRequestModel)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MemberRecordResponseModel.class)
                .value(updatedMember -> {
                    assertEquals(memberRequestModel.getType(), updatedMember.getType());
                    assertEquals(memberId, updatedMember.getMemberId());
                });
    }

    @Test
    void whenUsersExist_thenReturnAllUsers() {
        // Arrange: Create a user to ensure at least one exists
        UserRequestModel userRequestModel = buildSampleUser();

        webTestClient.post()
                .uri(BASE_URI_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestModel)
                .exchange()
                .expectStatus().isCreated();

        // Act & Assert: Retrieve all users and verify non-empty list
        webTestClient.get()
                .uri(BASE_URI_USER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(UserResponseModel.class)
                .value(users -> {
                    assertNotNull(users);
                    assertFalse(users.isEmpty(), "Expected users list to not be empty");
                });
    }



    @Test
    void whenUserExists_thenReturnUserById() {
        // Arrange: Create a user first
        UserRequestModel userRequestModel = buildSampleUser();

        UserResponseModel createdUser = webTestClient.post()
                .uri(BASE_URI_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseModel.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(createdUser);
        String userId = createdUser.getUserId();

        // Act & Assert: Retrieve user by ID
        webTestClient.get()
                .uri(BASE_URI_USER + "/" + userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserResponseModel.class)
                .value(user -> {
                    assertNotNull(user);
                    assertEquals(userId, user.getUserId());
                    assertEquals(userRequestModel.getFirstName(), user.getFirstName());
                });
    }


    // POST - create new user
    @Test
    public void whenUserRequestIsValid_thenReturnNewUser() {
        UserRequestModel userRequestModel = buildSampleUser();

        webTestClient.post()
                .uri(BASE_URI_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(UserResponseModel.class)
                .value(user -> {
                    assertNotNull(user);
                    assertNotNull(user.getUserId());
                    assertEquals(userRequestModel.getFirstName(), user.getFirstName());
                });
    }




    @Test
    public void whenUpdateRequestIsValid_thenUpdateUser() {
        UserRequestModel updatedUser = buildSampleUser();
        updatedUser.setFirstName("UpdatedName");

        webTestClient.put()
                .uri(BASE_URI_USER + "/" + VALID_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatedUser)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseModel.class)
                .value(response -> {
                    assertEquals("UpdatedName", response.getFirstName());
                });
    }

//


    //_____________________________NEGATIVE_________________________
//

    @Test
    void whenMembersNotExist_thenReturnEmptyList() {
        webTestClient.get()
                .uri(BASE_URI_MEMBER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MemberRecordResponseModel.class)
                .value(Members -> {
                    assertNotNull(Members);
                    assertFalse(Members.isEmpty());
                });
    }

    @Test
    public void whenMembersIdIsInvalid_ThenReturnUnprocessableEntity() {

        webTestClient.delete()
                .uri(BASE_URI_MEMBER + "/" + INVALID_MEMBER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // GET by not found ID
    @Test
    void whenMemberNotFound_thenReturnNotFound() {
        webTestClient.get()
                .uri(BASE_URI_MEMBER + "/" + NOT_FOUND_MEMBER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }



    @Test
    void whenMembersExistsOnDeleted_thenReturnNoContent() {
        webTestClient.delete()
                .uri(BASE_URI_MEMBER + "/" + VALID_MEMBER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }


    // DELETE - Invalid User ID
    @Test
    public void whenUserIdIsInvalid_ThenReturnUnprocessableEntity() {
        webTestClient.delete()
                .uri(BASE_URI_USER + "/" + INVALID_USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // GET - User Not Found
    @Test
    void whenUserNotFound_thenReturnNotFound() {
        webTestClient.get()
                .uri(BASE_URI_USER + "/" + NOT_FOUND_USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    // DELETE - Valid User ID (User Deleted)
    @Test
    void whenUserExistsAndDeleted_thenReturnNoContent() {
        webTestClient.delete()
                .uri(BASE_URI_USER + "/" + VALID_USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }



    private UserRequestModel buildSampleUser() {

        List<UserPhoneNumber> phoneNumbers = List.of(
                new UserPhoneNumber(PhoneType.MOBILE,"1234567890")
        );

        return UserRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .street_name("123 Maple Street")
                .city("Toronto")
                .province("Ontario")
                .country("Canada")
                .postal_code( "M4B1B3" )
                .phoneNumberList(phoneNumbers)
                .build();
    }

    private MemberRecordRequestModel buildSampleMember() {
        return MemberRecordRequestModel.builder()
                .start_date(LocalDate.of(2023, 1, 1))
                .end_date(LocalDate.of(2027, 1, 1))
                .type(MemberType.REGULAR)
                .userId("c195fd3a-3580-4b71-80f1-010000000000")  // User ID for John Doe
                .build();
    }





}