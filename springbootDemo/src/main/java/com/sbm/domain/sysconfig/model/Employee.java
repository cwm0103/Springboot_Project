package com.sbm.domain.sysconfig.model;

public class Employee {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.FIRST_NAME
     *
     * @mbg.generated
     */
    private String firstName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.LAST_NAME
     *
     * @mbg.generated
     */
    private String lastName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.AGE
     *
     * @mbg.generated
     */
    private Integer age;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.SEX
     *
     * @mbg.generated
     */
    private String sex;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column employee.INCOME
     *
     * @mbg.generated
     */
    private Float income;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.FIRST_NAME
     *
     * @return the value of employee.FIRST_NAME
     *
     * @mbg.generated
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.FIRST_NAME
     *
     * @param firstName the value for employee.FIRST_NAME
     *
     * @mbg.generated
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.LAST_NAME
     *
     * @return the value of employee.LAST_NAME
     *
     * @mbg.generated
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.LAST_NAME
     *
     * @param lastName the value for employee.LAST_NAME
     *
     * @mbg.generated
     */
    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.AGE
     *
     * @return the value of employee.AGE
     *
     * @mbg.generated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.AGE
     *
     * @param age the value for employee.AGE
     *
     * @mbg.generated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.SEX
     *
     * @return the value of employee.SEX
     *
     * @mbg.generated
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.SEX
     *
     * @param sex the value for employee.SEX
     *
     * @mbg.generated
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column employee.INCOME
     *
     * @return the value of employee.INCOME
     *
     * @mbg.generated
     */
    public Float getIncome() {
        return income;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column employee.INCOME
     *
     * @param income the value for employee.INCOME
     *
     * @mbg.generated
     */
    public void setIncome(Float income) {
        this.income = income;
    }
}