package cn.com.officedepot.model;

import java.io.Serializable;

public class Model implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id = null;

	private String str = null;

	public Model() {
		super();
	}

	public Model(Integer id) {
		super();
		this.id = id;
	}

	public Model(Integer id, String str) {
		super();
		this.id = id;
		this.str = str;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Model [id=");
		builder.append(id);
		builder.append(", str=");
		builder.append(str);
		builder.append("]");
		return builder.toString();
	}

}
