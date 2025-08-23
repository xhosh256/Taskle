package xhosh.dev.taskle.dto.mapper;

public interface Mapper <F, T> {
    T map(F object);
}
