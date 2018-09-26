package  com.oper.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pub_cim_class")
@Data
public class PubCimClass {

  @Id
  private long id;
  private String cimCode;
  private String cimName;
  private String cimNameEn;
  private long bigType;
  private String classType;
  private String enType;
  private String status;
  private String cimDesc;
  private String enMenu;




}
