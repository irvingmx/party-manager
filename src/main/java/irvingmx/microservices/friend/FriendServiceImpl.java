package irvingmx.microservices.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;

    public List<Friend> getFriends() {
        return friendRepository.findAll();
    }
    public Friend getFriend(long friendId) throws FriendNotFoundException {
        Optional<Friend> friend = friendRepository.findById(friendId);
        if (!friend.isPresent())
            throw new FriendNotFoundException();

        return friend.get();
    }
    public void deleteFriend(long friendId) {
        friendRepository.deleteById(friendId);
    }
    public Friend createFriend(Friend friend) {
        Friend savedFriend = friendRepository.save(friend);
        return savedFriend;

    }
    public Friend updateFriend(Friend friend, long friendId) throws FriendNotFoundException {

        Optional<Friend> friendOptional = friendRepository.findById(friendId);
        if (!friendOptional.isPresent())
            throw new FriendNotFoundException();
        friend.setFriendId(friendId);
        friendRepository.save(friend);
        return friend;
    }
}
