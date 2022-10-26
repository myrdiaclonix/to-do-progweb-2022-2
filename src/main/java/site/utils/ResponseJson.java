package site.utils;

import java.util.ArrayList;

import com.google.gson.Gson;

public class ResponseJson {
	
	private String msg;
	private Integer status;
	private ArrayList<String> res;
	
	public ResponseJson() {
		this.msg = "Erro na requisição!";
		this.status = 0;
		this.res = null;
	}
	
	public ResponseJson(String msg, Integer status) {
		this.msg = msg;
		this.status = status;
	}
	
	public ResponseJson(String msg, Integer status, ArrayList<String> res) {
		this.msg = msg;
		this.status = status;
		this.res = res;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ArrayList<String> getRes() {
		return res;
	}

	public void setRes(ArrayList<String> res) {
		this.res = res;
	}
	
	public String toJson() {
		Gson json = new Gson();
		return json.toJson(this);
	}
	
}
