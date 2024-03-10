package ru.practicum.ewm.dto;

import lombok.Data;
import java.util.List;

@Data
public class SearchAdminParameters extends SearchParameters {
    private List<Long> users;
    private List<String> states;
    private List<Long> categories;
}
