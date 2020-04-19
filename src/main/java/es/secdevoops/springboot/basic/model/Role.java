package es.secdevoops.springboot.basic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="role")
@Data
public class Role {
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String USER_ROLE = "USER";
	
	@Id
	private Integer id;
	
	@NotNull
	private String rolename;
	
	@NotNull
	private String description;

}
