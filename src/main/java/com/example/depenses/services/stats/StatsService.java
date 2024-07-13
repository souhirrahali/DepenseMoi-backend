package com.example.depenses.services.stats;

import com.example.depenses.dao.entities.User;
import com.example.depenses.web.dto.GraphDto;
import com.example.depenses.web.dto.StatsDto;

public interface StatsService {
    GraphDto getChartData(User user);
    StatsDto getStats(User user);
}