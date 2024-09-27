package org.example.demo2.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchTestDto {
    private String startDT;
    private String endDT;
    private Integer limit;
    private Integer offSet;
}
