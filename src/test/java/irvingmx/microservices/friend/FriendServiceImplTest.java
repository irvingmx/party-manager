package irvingmx.microservices.friend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceImplTest {

    private FriendServiceImpl friendService;

    @Mock
    private FriendRepository friendRepository;
    private List<Friend> expectedFriends;
    @BeforeEach
    void setUp() {
        friendService = new FriendServiceImpl(friendRepository);
        Friend ricky = new Friend(1L, "Ricky", "Sabritones");
        Friend peter = new Friend(2L, "Peter", "Sabritones");
        expectedFriends = new ArrayList<>();
        expectedFriends.add(ricky);
        expectedFriends.add(peter);
    }

    @Test
    void getFriends() {
        // given
        given(friendRepository.findAll()).willReturn(expectedFriends);
        // when
        List<Friend> friends = friendService.getFriends();
        //then
        then(friends).isEqualTo(expectedFriends);

    }

    @Test
    void getFriend() throws FriendNotFoundException {
        // given
        Friend ricky = new Friend(1L, "Ricky", "Sabritones");
        given(friendRepository.findById(anyLong())).willReturn(Optional.of(ricky));
        // when
        Friend friend = friendService.getFriend(1L);
        //then
        then(friend).isEqualTo(ricky);
    }

    @Test
    void deleteFriend() throws FriendNotFoundException {
        // given
        Friend ricky = new Friend(1L, "Ricky", "Sabritones");
        // when
        friendService.deleteFriend(1L);
        //then
        assertThatThrownBy(() -> friendService.getFriend(1L)).isInstanceOf(FriendNotFoundException.class);
    }

    @Test
    void createFriend() throws FriendNotFoundException {
        // given
        Friend teresa = new Friend(5L, "Teresa", "Gominolas");
        given(friendRepository.findById(anyLong())).willReturn(Optional.of(teresa));
        // when
        Friend friend = friendService.createFriend(teresa);
        //then
        then(friendService.getFriend(5L)).isEqualTo(teresa);
    }

    @Test
    void updateFriend() throws FriendNotFoundException {
        // given
        Friend teresa = new Friend(1L, "Teresa", "Gominolas");
        given(friendRepository.findById(anyLong())).willReturn(Optional.of(teresa));
        // when
        Friend friend = friendService.updateFriend(teresa,1L);
        //then
        then(friendService.getFriend(1L)).isEqualTo(teresa);
    }
}