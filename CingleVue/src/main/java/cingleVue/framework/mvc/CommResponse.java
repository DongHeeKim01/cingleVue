package cingleVue.framework.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CommResponse {
	private boolean transactionStatus;
	private List<String> errorMsg;
	private Object body;

	public CommResponse(boolean transactionStatus, List<String> errorMsg, Object body) {
		super();
		this.transactionStatus = transactionStatus;
		this.errorMsg = errorMsg;
		this.body = body;
	}
	
	public CommResponse() {
		// TODO Auto-generated constructor stub
	}

	public CommResponse(boolean status) {
		this.transactionStatus=status;
	}

	public void setErrorMsgFromBindResult(BindingResult result) {
		setTransactionStatus(result.hasErrors()?false:true);
		List<String> errList = new ArrayList<String>();
		for(ObjectError error: result.getAllErrors()) {
			errList.add(error.getDefaultMessage());
		}
		setErrorMsg(errList);
	}
	
	public boolean isTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(boolean transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public List<String> getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	public void setErrorMsgSingle(String errorMsg) {
		if(this.errorMsg!=null) {
			this.errorMsg.add(errorMsg);
		} else {
			ArrayList<String> listErrorMsg= new ArrayList<>();
			listErrorMsg.add(errorMsg);
			setErrorMsg(listErrorMsg);
		}		
		
		
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
}
