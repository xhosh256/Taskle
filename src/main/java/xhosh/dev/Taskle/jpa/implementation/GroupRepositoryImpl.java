package xhosh.dev.Taskle.jpa.implementation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.entity.Group;
import xhosh.dev.Taskle.entity.QGroup;

import java.util.List;

@Component
@AllArgsConstructor
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    private JPAQueryFactory queryFactory;

    @Override
    public List<Group> findAllByUsername(String username) {
        QGroup group = QGroup.group;

        return queryFactory
                .selectFrom(group)
                .where(group.user.username.eq(username))
                .fetch();
    }
}
