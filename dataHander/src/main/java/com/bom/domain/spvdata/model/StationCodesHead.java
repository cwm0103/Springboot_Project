package com.bom.domain.spvdata.model;

public class StationCodesHead {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_head.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_head.project_id
     *
     * @mbg.generated
     */
    private String projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_head.domain
     *
     * @mbg.generated
     */
    private String domain;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column station_codes_head.staId
     *
     * @mbg.generated
     */
    private String staid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_head.id
     *
     * @return the value of station_codes_head.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_head.id
     *
     * @param id the value for station_codes_head.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_head.project_id
     *
     * @return the value of station_codes_head.project_id
     *
     * @mbg.generated
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_head.project_id
     *
     * @param projectId the value for station_codes_head.project_id
     *
     * @mbg.generated
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_head.domain
     *
     * @return the value of station_codes_head.domain
     *
     * @mbg.generated
     */
    public String getDomain() {
        return domain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_head.domain
     *
     * @param domain the value for station_codes_head.domain
     *
     * @mbg.generated
     */
    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column station_codes_head.staId
     *
     * @return the value of station_codes_head.staId
     *
     * @mbg.generated
     */
    public String getStaid() {
        return staid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column station_codes_head.staId
     *
     * @param staid the value for station_codes_head.staId
     *
     * @mbg.generated
     */
    public void setStaid(String staid) {
        this.staid = staid == null ? null : staid.trim();
    }
}