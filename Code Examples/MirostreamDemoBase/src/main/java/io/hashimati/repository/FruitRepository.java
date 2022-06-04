package io.hashimati.repository;

import io.hashimati.domains.Fruit;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface FruitRepository {

    @NonNull
    Fruit save(@NonNull @NotNull @Valid Fruit fruit);

    void update(@NonNull @NotBlank String id,
                @NonNull @NotNull @Valid Fruit fruit);

    @NonNull
    Optional<Fruit> findById(@NonNull @NotBlank String id);

    void deleteById(@NonNull @NotBlank String id);
}