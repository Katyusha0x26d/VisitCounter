package me.katyusha.visitcounter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitCount {
    private Long id;
    private String pageKey;
    private Long count;
}
