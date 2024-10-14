package com.aliev.tgbot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
    private double wind_spd;
    private int temp;
    private int pop;
    private Weather weather;
}
