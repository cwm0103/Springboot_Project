package com.bom.dataservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by chenwangming on 2017/11/21.
 */
@Entity
public class StationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String  station_id;
    private BigDecimal station_lat;
    private BigDecimal station_lng;
    private String station_name;
    private String station_name_en;
    private String station_esp;
    private String  location;
    private String addr;
    private String file_id;
    private String connect_time;
    private int    station_state;
    private Double Doustation_level;
    private String station_type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public BigDecimal getStation_lat() {
        return station_lat;
    }

    public void setStation_lat(BigDecimal station_lat) {
        this.station_lat = station_lat;
    }

    public BigDecimal getStation_lng() {
        return station_lng;
    }

    public void setStation_lng(BigDecimal station_lng) {
        this.station_lng = station_lng;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_name_en() {
        return station_name_en;
    }

    public void setStation_name_en(String station_name_en) {
        this.station_name_en = station_name_en;
    }

    public String getStation_esp() {
        return station_esp;
    }

    public void setStation_esp(String station_esp) {
        this.station_esp = station_esp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getConnect_time() {
        return connect_time;
    }

    public void setConnect_time(String connect_time) {
        this.connect_time = connect_time;
    }

    public int getStation_state() {
        return station_state;
    }

    public void setStation_state(int station_state) {
        this.station_state = station_state;
    }

    public Double getDoustation_level() {
        return Doustation_level;
    }

    public void setDoustation_level(Double doustation_level) {
        Doustation_level = doustation_level;
    }

    public String getStation_type() {
        return station_type;
    }

    public void setStation_type(String station_type) {
        this.station_type = station_type;
    }
}
