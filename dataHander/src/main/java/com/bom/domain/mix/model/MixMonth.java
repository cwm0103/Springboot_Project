package com.bom.domain.mix.model;

import java.util.Date;

public class MixMonth {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.project_id
     *
     * @mbg.generated
     */
    private String projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.cal_code
     *
     * @mbg.generated
     */
    private String calCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.rec_time
     *
     * @mbg.generated
     */
    private Date recTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.data_value
     *
     * @mbg.generated
     */
    private Double dataValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.state
     *
     * @mbg.generated
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mix_month.remarks
     *
     * @mbg.generated
     */
    private String remarks;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.id
     *
     * @return the value of mix_month.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.id
     *
     * @param id the value for mix_month.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.project_id
     *
     * @return the value of mix_month.project_id
     *
     * @mbg.generated
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.project_id
     *
     * @param projectId the value for mix_month.project_id
     *
     * @mbg.generated
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.cal_code
     *
     * @return the value of mix_month.cal_code
     *
     * @mbg.generated
     */
    public String getCalCode() {
        return calCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.cal_code
     *
     * @param calCode the value for mix_month.cal_code
     *
     * @mbg.generated
     */
    public void setCalCode(String calCode) {
        this.calCode = calCode == null ? null : calCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.rec_time
     *
     * @return the value of mix_month.rec_time
     *
     * @mbg.generated
     */
    public Date getRecTime() {
        return recTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.rec_time
     *
     * @param recTime the value for mix_month.rec_time
     *
     * @mbg.generated
     */
    public void setRecTime(Date recTime) {
        this.recTime = recTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.data_value
     *
     * @return the value of mix_month.data_value
     *
     * @mbg.generated
     */
    public Double getDataValue() {
        return dataValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.data_value
     *
     * @param dataValue the value for mix_month.data_value
     *
     * @mbg.generated
     */
    public void setDataValue(Double dataValue) {
        this.dataValue = dataValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.state
     *
     * @return the value of mix_month.state
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.state
     *
     * @param state the value for mix_month.state
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mix_month.remarks
     *
     * @return the value of mix_month.remarks
     *
     * @mbg.generated
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mix_month.remarks
     *
     * @param remarks the value for mix_month.remarks
     *
     * @mbg.generated
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}