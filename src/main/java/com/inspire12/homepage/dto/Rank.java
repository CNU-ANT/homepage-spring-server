package com.inspire12.homepage.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Rank<T> {
    T item;
    int rank;
}
