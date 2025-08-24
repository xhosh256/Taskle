package xhosh.dev.taskle.mapper;

public interface Mapper <F, T> {
    T map(F from);
    default T map(F from, T updated) {
        return updated;
    }
}