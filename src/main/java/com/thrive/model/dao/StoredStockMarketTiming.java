package com.thrive.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "market_timing", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
public class StoredStockMarketTiming {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "end_time")
    private Time endTime;
}
