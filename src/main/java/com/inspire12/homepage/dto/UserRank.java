package com.inspire12.homepage.dto;

import com.inspire12.homepage.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class UserRank {
    private List<Rank<User>> items;

    public void setRank(List<User> users) {
        if (CollectionUtils.isEmpty(items)){
            return ;
        }
        users.sort(Comparator.comparing(User::getLastAccess));

        User prevUser = users.get(0);
        int rank = 1;
        int size = users.size();
        for (int i = 0; i < size; i++) {
            User user = users.get(i);
            if (!user.getLastAccess().equals(prevUser.getLastAccess())) {
                rank++;
            }
            items.add(new Rank<>(user, rank));
            prevUser = user;
        }
    }
}
