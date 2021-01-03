package com.inspire12.homepage.dto;

import lombok.Value;

@Value
public class Rank<T> {
    T item;
    int rank;
}
