package com.bom.domain.data.model;

public class StationCodesType {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_type.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_type.station_id
     *
     * @mbg.generated
     */
    private Integer stationId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_type.station_name
     *
     * @mbg.generated
     */
    private String stationName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_type.code
     *
     * @mbg.generated
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_type.code_type
     *
     * @mbg.generated
     */
    private Integer codeType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_type.id
     *
     * @return the value of station_codes_type.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_type.id
     *
     * @param id the value for station_codes_type.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_type.station_id
     *
     * @return the value of station_codes_type.station_id
     *
     * @mbg.generated
     */
    public Integer getStationId() {
        return stationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_type.station_id
     *
     * @param stationId the value for station_codes_type.station_id
     *
     * @mbg.generated
     */
    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_type.station_name
     *
     * @return the value of station_codes_type.station_name
     *
     * @mbg.generated
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_type.station_name
     *
     * @param stationName the value for station_codes_type.station_name
     *
     * @mbg.generated
     */
    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_type.code
     *
     * @return the value of station_codes_type.code
     *
     * @mbg.generated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_type.code
     *
     * @param code the value for station_codes_type.code
     *
     * @mbg.generated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_type.code_type
     *
     * @return the value of station_codes_type.code_type
     *
     * @mbg.generated
     */
    public Integer getCodeType() {
        return codeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_type.code_type
     *
     * @param codeType the value for station_codes_type.code_type
     *
     * @mbg.generated
     */
    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }
}