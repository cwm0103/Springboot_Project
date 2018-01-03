package com.bom.domain.data.model;

import java.util.List;

public class Querie{
     public Querie() {
     }

     private String aggregator;
    private String metric;
    private TSDBTag tags;

     public String getAggregator() {
         return aggregator;
     }

     public void setAggregator(String aggregator) {
         this.aggregator = aggregator;
     }

     public String getMetric() {
         return metric;
     }

     public void setMetric(String metric) {
         this.metric = metric;
     }

     public TSDBTag getTags() {
         return tags;
     }

     public void setTags(TSDBTag tags) {
         this.tags = tags;
     }
 }
