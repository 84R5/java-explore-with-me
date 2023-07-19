package ru.practicum.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.error.NotFoundException;

import javax.validation.constraints.NotNull;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateManager {

    public static final String NOT_FOUND_WITH_ID = "not found %s with id=%s";

    @NotNull
    public static <T, I> T getNonNullObject(@NotNull CrudRepository<T, I> storage, I id, Class<T> tClass) throws NotFoundException {
        return storage.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_WITH_ID, tClass.getSimpleName(), id)));
    }

    @NotNull
    public static <T, I> T getNonNullObject(@NotNull CrudRepository<T, I> storage, I id) throws NotFoundException {
        return getNonNullObject(storage, id, getTClass(storage));
    }

    public static <T, I> void checkId(@NotNull CrudRepository<T, I> storage, I id, Class<T> tClass) throws ChangeSetPersister.NotFoundException {
        if (!storage.existsById(id)) {
            throw new NotFoundException(String.format(NOT_FOUND_WITH_ID, tClass.getSimpleName(), id));
        }
    }

    public static <T, I> void checkId(@NotNull CrudRepository<T, I> storage, I id) throws NotFoundException {
        try {
            checkId(storage, id, getTClass(storage));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T, I> Class<T> getTClass(CrudRepository<T, I> storage) {
        ResolvableType resolvableType = ResolvableType.forClass(storage.getClass()).as(CrudRepository.class);
        return (Class<T>) resolvableType.getGeneric(0).toClass();
    }

    public static void checkRate(Integer rate){
        if (rate == null){
            return;
        }
        if(rate >5 || rate < -5){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rate value, from -5 to +5");
        }
    }

}
