package irvingmx.microservices.friend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(FriendController.class)
class FriendControllerTest {

    @MockBean
    private FriendService friendService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<List<Friend>> friendsListJSON;
    @Autowired
    private JacksonTester<Friend> friendJSON;
    private List<Friend> expectedFriends;
    @BeforeEach
    void setUp(){
        Friend ricky = new Friend(1L, "Ricky", "Sabritones");
        Friend peter = new Friend(2L, "Peter", "Sabritones");
        expectedFriends = new ArrayList<>();
        expectedFriends.add(ricky);
        expectedFriends.add(peter);
    }
    @Test
    void getFriends() throws Exception {
        // given
        given(friendService.getFriends()).willReturn(expectedFriends);
        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/friends")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(friendsListJSON.write(expectedFriends).getJson());

    }

    @Test
    void getFriend() throws Exception {
        // given
        Friend ricky = new Friend(1L, "Ricky", "Sabritones");
        given(friendService.getFriend(anyLong())).willReturn(ricky);
        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/friends/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(friendJSON.write(ricky).getJson());
    }

    @Test
    void deleteFriend() throws Exception {
        // given
        Friend ricky = new Friend(1L, "Ricky", "Sabritones");
        given(friendService.getFriend(anyLong())).willReturn(ricky);
        // when
        MockHttpServletResponse response = mvc.perform(
                        delete("/friends/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void createFriend() throws Exception {
        // given
        Friend teresa = new Friend(5L, "Teresa", "Manzanas");
        given(friendService.createFriend(teresa)).willReturn(teresa);
        // when

        MockHttpServletResponse response = mvc.perform(
                        post("/friends").contentType(MediaType.APPLICATION_JSON)
                                .content(friendJSON.write(teresa).getJson()))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void updateFriend() throws Exception {
        // given
        Friend teresa = new Friend(1L, "Diana", "Peras");
        given(friendService.getFriend(anyLong())).willReturn(teresa);
        // when
        MockHttpServletResponse response = mvc.perform(
                        put("/friends/1").contentType(MediaType.APPLICATION_JSON)
                                .content(friendJSON.write(teresa).getJson()))
                .andReturn().getResponse();
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        then(response.getContentAsString()).isEqualTo("");
    }
}