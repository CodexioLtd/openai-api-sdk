package bg.codexio.ai.openai.api.payload;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * <p>
 * This interface mostly makes sense
 * in conjunction with {@link Streamable#stream()} == true.
 * So each streamed line, can be joined to the next one,
 * forming one big response at the end.
 * </p>
 * <p>
 * Most response objects actually implements this
 * interface without the necessity of doing it,
 * as the API they belong to will never support
 * streaming, such as Images Creation API.
 * </p>
 *
 * @param <T> Another streamable. Most probably object of the same type.
 */
public interface Mergeable<T extends Mergeable<?>> {

    static <U extends Mergeable<U>> U doMerge(
            U source,
            U target
    ) {
        return target == null
               ? source
               : source.merge(target);
    }

    static <T, U> List<T> join(
            Collection<T> source,
            Collection<T> target,
            Predicate<T> filter,
            Function<? super T, U> grouping,
            Supplier<T> emptySupplier,
            BinaryOperator<T> merger,
            Predicate<T> shallPersist
    ) {
        var sourceMap = Objects.requireNonNullElse(
                                       source,
                                       new ArrayList<T>()
                               )
                               .stream()
                               .filter(filter)
                               .collect(Collectors.groupingBy(grouping));

        var targetMap = Objects.requireNonNullElse(
                                       target,
                                       new ArrayList<T>()
                               )
                               .stream()
                               .filter(filter)
                               .collect(Collectors.groupingBy(grouping));

        var keys = new HashSet<>(sourceMap.keySet());
        keys.addAll(targetMap.keySet());

        var resultList = new ArrayList<T>();

        keys.forEach(role -> {
            var sourceList = Objects.requireNonNullElse(
                    sourceMap.get(role),
                    new ArrayList<T>()
            );
            var targetList = Objects.requireNonNullElse(
                    targetMap.get(role),
                    new ArrayList<T>()
            );

            var initial = sourceList.stream()
                                    .reduce(
                                            emptySupplier.get(),
                                            merger
                                    );
            var result = targetList.stream()
                                   .reduce(
                                           initial,
                                           merger
                                   );

            if (shallPersist.test(result)) {
                resultList.add(result);
            }
        });

        return resultList;
    }

    T merge(T other);
}
