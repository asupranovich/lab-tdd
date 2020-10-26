package com.itechart.tdd.atm.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Account implements Serializable {

  @Id
  private Integer id;
  private Integer balance;
}