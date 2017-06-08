package br.com.escolares.domain;

import java.util.List;

/**
 * @author vinicius Ribeiro
 *
 * 26 de mai de 2017
 *
 */
public class AjaxResponseBody<T> {

    String msg;
    List<T> results;
    T result;
    
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
    
}
