package com.oper.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="pub_cim_instance")
public class PubCimInstance {
  @Id
  private long instanceId;
  private String classCode;
  private String instanceCode;
  private long instanceNo;
  private String instanceName;
  private long equId;
  private long organizationId;




}
