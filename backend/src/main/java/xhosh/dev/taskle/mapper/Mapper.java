package xhosh.dev.taskle.mapper;

public interface Mapper <F, T> {
    T map(F from);
    T map(F from, T updated)
}
