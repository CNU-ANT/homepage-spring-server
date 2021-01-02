package com.inspire12.homepage.viewmodel;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public class TableView {
    String tableType;
    String tableTitle;
    List<TableRow> rows;
    PageRequest pager;
}
