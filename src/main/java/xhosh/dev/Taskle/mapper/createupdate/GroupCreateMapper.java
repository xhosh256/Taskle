package xhosh.dev.Taskle.mapper.createupdate;

import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.dto.group.GroupCreateDto;
import xhosh.dev.Taskle.entity.Group;
import xhosh.dev.Taskle.mapper.Mapper;

@Component
public class GroupCreateMapper implements Mapper<GroupCreateDto, Group> {
    @Override
    public Group map(GroupCreateDto obj) {
        var group = new Group();
        group.setName(obj.getName());
        return group;
    }
}
