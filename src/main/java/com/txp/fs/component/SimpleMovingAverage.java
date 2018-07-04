package com.txp.fs.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleMovingAverage {
	
    private Queue<BigDecimal> window = new LinkedList<BigDecimal>();
    private int period = 1;
    private BigDecimal sum = BigDecimal.ZERO;

    public void MovingAverage(int period) {
        assert period > 0 : "Period must be a positive integer";
        this.period = period;
    }

    public void add(BigDecimal num) {
        sum = sum.add(num);
        window.add(num);
        if (window.size() > period) {
            sum = sum.subtract(window.remove());
        }
    }
    
    public BigDecimal getAverage() {
        if (window.isEmpty()) return BigDecimal.ZERO; // technically the average is undefined
        BigDecimal divisor = BigDecimal.valueOf(window.size());
        return sum.divide(divisor, 2, RoundingMode.HALF_UP);
    }
}
