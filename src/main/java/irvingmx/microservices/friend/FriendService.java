package irvingmx.microservices.friend;

import java.util.List;

public interface FriendService {
    List<Friend> getFriends() ;
    Friend getFriend(long friendId) throws FriendNotFoundException;
    void deleteFriend(long friendId) ;
    Friend createFriend(Friend friend);
    Friend updateFriend(Friend friend, long friendId) throws FriendNotFoundException;
}
