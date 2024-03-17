package ma.adria.document_validation.administration.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SortUtils {

    public static List<Sort.Order> getOrders(String sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if (sort != null) {
            String[] sortArray = sort.split(",");
            if (sortArray[0].contains(":")) {
                for (String sortOrder : sortArray) {
                    String[] sortElement = sortOrder.split(":");
                    orders.add(new Sort.Order(getSortDirection(sortElement[1]), sortElement[0]));
                }
            }
        }
        return orders;
    }

    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

}
