package com.bom.domain.mix.model;

import java.util.Date;

public class MixHour {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.project_id
     *
     * @mbg.generated
     */
    private String projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.cal_code
     *
     * @mbg.generated
     */
    private String calCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.rec_time
     *
     * @mbg.generated
     */
    private Date recTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.data_value
     *
     * @mbg.generated
     */
    private Double dataValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.state
     *
     * @mbg.generated
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_hour.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.id
     *
     * @return the value of mix_hour.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.id
     *
     * @param id the value for mix_hour.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.project_id
     *
     * @return the value of mix_hour.project_id
     *
     * @mbg.generated
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.project_id
     *
     * @param projectId the value for mix_hour.project_id
     *
     * @mbg.generated
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.cal_code
     *
     * @return the value of mix_hour.cal_code
     *
     * @mbg.generated
     */
    public String getCalCode() {
        return calCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.cal_code
     *
     * @param calCode the value for mix_hour.cal_code
     *
     * @mbg.generated
     */
    public void setCalCode(String calCode) {
        this.calCode = calCode == null ? null : calCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.rec_time
     *
     * @return the value of mix_hour.rec_time
     *
     * @mbg.generated
     */
    public Date getRecTime() {
        return recTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.rec_time
     *
     * @param recTime the value for mix_hour.rec_time
     *
     * @mbg.generated
     */
    public void setRecTime(Date recTime) {
        this.recTime = recTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.data_value
     *
     * @return the value of mix_hour.data_value
     *
     * @mbg.generated
     */
    public Double getDataValue() {
        return dataValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.data_value
     *
     * @param dataValue the value for mix_hour.data_value
     *
     * @mbg.generated
     */
    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.state
     *
     * @return the value of mix_hour.state
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.state
     *
     * @param state the value for mix_hour.state
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_hour.remarks
     *
     * @return the value of mix_hour.remarks
     *
     * @mbg.generated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_hour.remarks
     *
     * @param remarks the value for mix_hour.remarks
     *
     * @mbg.generated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}