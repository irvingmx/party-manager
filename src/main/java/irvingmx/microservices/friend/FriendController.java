package irvingmx.microservices.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping
    public List<Friend> getFriends() {
        return friendService.getFriends();
    }
    @GetMapping("/{friendId}")
    public Friend getFriend(@PathVariable long friendId) throws FriendNotFoundException {
        return friendService.getFriend(friendId);
    }
    @DeleteMapping("/{friendId}")
    public void deleteFriend(@PathVariable long friendId) {
        friendService.deleteFriend(friendId);
    }
    @PostMapping
    public ResponseEntity<Object> createFriend(@RequestBody Friend friend) {
        Friend savedFriend = friendService.createFriend(friend);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{friendId}")
                .buildAndExpand(savedFriend.getFriendId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{friendId}")
    public ResponseEntity<Object> updateFriend(@RequestBody Friend friend, @PathVariable long friendId) {
        try {
            friend = friendService.getFriend(friendId);
        } catch (FriendNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        friend.setFriendId(friendId);
        try {
            friendService.updateFriend(friend, friendId);
        } catch (FriendNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }
}
